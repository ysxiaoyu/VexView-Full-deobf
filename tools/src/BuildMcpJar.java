import com.nothome.delta.GDiffPatcher;
import lzma.sdk.lzma.Decoder;
import lzma.streams.LzmaInputStream;
import org.objectweb.asm.*;
import org.objectweb.asm.commons.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.jar.JarInputStream;
import java.util.jar.Pack200;
import java.util.zip.*;

/**
 * Builds an MCP-named Minecraft + Forge jar without ForgeGradle / userdev:
 *   1. merge client + server notch jars
 *   2. apply forge binpatches (lzma -> pack200 -> javaxdelta)
 *   3. remap notch -> SRG via joined.tsrg (hierarchy aware)
 *   4. remap SRG -> MCP names via mcp stable CSVs
 *   5. fold in forge classes from the universal jar
 */
public final class BuildMcpJar {

    static final Map<String, String> CLASS_MAP = new HashMap<String, String>();
    static final Map<String, String> METHOD_MAP = new HashMap<String, String>(); // owner.name desc -> new
    static final Map<String, String> FIELD_MAP = new HashMap<String, String>();  // owner.name -> new
    static final Map<String, String> SRG_METHODS = new HashMap<String, String>();
    static final Map<String, String> SRG_FIELDS = new HashMap<String, String>();
    static final Map<String, String[]> HIER = new HashMap<String, String[]>(); // name -> {super, itf1, itf2...}

    public static void main(String[] a) throws Exception {
        File client = new File(a[0]), server = new File(a[1]), universal = new File(a[2]);
        File tsrg = new File(a[3]);
        File stableDir = new File(a[4]);
        File out = new File(a[5]);

        Map<String, byte[]> classes = new LinkedHashMap<String, byte[]>();
        readJarInto(client, classes);
        System.out.println("client entries: " + classes.size());
        Map<String, byte[]> srv = new LinkedHashMap<String, byte[]>();
        int added = 0;
        for (Map.Entry<String, byte[]> e : srv.entrySet()) {
            if (!classes.containsKey(e.getKey()) && e.getKey().indexOf('/') < 0) { classes.put(e.getKey(), e.getValue()); added++; }
        }
        System.out.println("server-only entries added: " + added + " total " + classes.size());

        Map<String, ClassPatch> patches = loadPatches(universal);
        System.out.println("binpatches: " + patches.size());

        int patched = 0, checksumFail = 0, missing = 0;
        GDiffPatcher patcher = new GDiffPatcher();
        Map<String, byte[]> mc = new LinkedHashMap<String, byte[]>();
        for (Map.Entry<String, byte[]> e : classes.entrySet()) {
            String name = e.getKey();
            byte[] data = e.getValue();
            if (!name.endsWith(".class")) { mc.put(name, data); continue; }
            ClassPatch p = patches.get(name);
            if (p != null) {
                int h = adler(data);
                if (p.existsAtTarget && h != p.inputChecksum) { checksumFail++; }
                else { data = patcher.patch(data, p.patch); patched++; }
            }
            mc.put(name, data);
        }
        System.out.println("patched=" + patched + " checksumFail=" + checksumFail);

        // hierarchy of notch classes
        for (Map.Entry<String, byte[]> e : mc.entrySet()) {
            if (!e.getKey().endsWith(".class")) continue;
            try {
                ClassReader cr = new ClassReader(e.getValue());
                String[] sup = cr.getSuperName() == null ? new String[0] : new String[]{cr.getSuperName()};
                String[] itf = cr.getInterfaces();
                String[] all = new String[sup.length + itf.length];
                System.arraycopy(sup, 0, all, 0, sup.length);
                System.arraycopy(itf, 0, all, sup.length, itf.length);
                HIER.put(cr.getClassName(), all);
            } catch (Throwable t) { }
        }

        loadTsrg(tsrg);
        System.out.println("tsrg classes=" + CLASS_MAP.size() + " methods=" + METHOD_MAP.size() + " fields=" + FIELD_MAP.size());
        loadSrg(new File(stableDir, "fields.csv"), new File(stableDir, "methods.csv"));

        Map<String, byte[]> outEntries = new LinkedHashMap<String, byte[]>();
        int remapped = 0, failed = 0;
        for (Map.Entry<String, byte[]> e : mc.entrySet()) {
            if (!e.getKey().endsWith(".class")) continue;
            try {
                byte[] nb = remapToMcp(e.getValue());
                outEntries.put(new ClassReader(nb).getClassName() + ".class", nb);
                remapped++;
            } catch (Throwable t) {
                failed++;
                if (failed < 6) System.err.println("remap fail " + e.getKey() + " : " + t);
            }
        }
        System.out.println("remapped=" + remapped + " failed=" + failed);

        // forge classes
        Map<String, byte[]> uni = new LinkedHashMap<String, byte[]>();
        readJarInto(universal, uni);
        int forgeAdded = 0;
        for (Map.Entry<String, byte[]> e : uni.entrySet()) {
            String n = e.getKey();
            if (!n.endsWith(".class")) continue;
            if (n.startsWith("META-INF/")) continue;
            if (outEntries.containsKey(n)) continue;
            try {
                byte[] nb = remapToMcp(e.getValue());
                outEntries.put(new ClassReader(nb).getClassName() + ".class", nb);
                forgeAdded++;
            } catch (Throwable t) { }
        }
        System.out.println("forge classes added: " + forgeAdded);

        writeJar(out, outEntries);
        System.out.println("wrote " + out + " entries=" + outEntries.size());
    }

    static int adler(byte[] b) {
        Adler32 h = new Adler32();
        h.update(b);
        return (int) h.getValue();
    }

    static final class ClassPatch {
        String source, target;
        boolean existsAtTarget;
        int inputChecksum;
        byte[] patch;
    }

    static Map<String, ClassPatch> loadPatches(File universal) throws IOException {
        Map<String, byte[]> entries = new LinkedHashMap<String, byte[]>();
        readJarInto(universal, entries);
        byte[] lzma = entries.get("binpatches.pack.lzma");
        if (lzma == null) throw new IOException("binpatches.pack.lzma not found");
        ByteArrayOutputStream jarBytes = new ByteArrayOutputStream();
        LzmaInputStream in = new LzmaInputStream(new ByteArrayInputStream(lzma), new Decoder());
        java.util.jar.JarOutputStream jos = new java.util.jar.JarOutputStream(jarBytes);
        Pack200.newUnpacker().unpack(in, jos);
        jos.close();
        in.close();
        Map<String, ClassPatch> out = new HashMap<String, ClassPatch>();
        JarInputStream jis = new JarInputStream(new ByteArrayInputStream(jarBytes.toByteArray()));
        java.util.jar.JarEntry entry;
        while ((entry = jis.getNextJarEntry()) != null) {
            if (!entry.getName().matches("binpatch/(client|merged)/.*\\.binpatch")) continue;
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            byte[] buf = new byte[8192]; int r;
            while ((r = jis.read(buf)) > 0) bo.write(buf, 0, r);
            DataInputStream d = new DataInputStream(new ByteArrayInputStream(bo.toByteArray()));
            ClassPatch cp = new ClassPatch();
            d.readUTF();                        // name
            cp.source = d.readUTF();
            cp.target = d.readUTF();
            cp.existsAtTarget = d.readBoolean();
            if (cp.existsAtTarget) cp.inputChecksum = d.readInt();
            int len = d.readInt();
            cp.patch = new byte[len];
            d.readFully(cp.patch);
            out.put(cp.source.replace('.', '/') + ".class", cp);
            jis.closeEntry();
        }
        jis.close();
        return out;
    }

    static void loadTsrg(File f) throws IOException {
        String cur = null;
        for (String line : Files.readAllLines(f.toPath(), StandardCharsets.UTF_8)) {
            if (line.isEmpty()) continue;
            if (line.charAt(0) != '\t') {
                String[] p = line.split("\\s+");
                if (p.length >= 2) { cur = p[0]; CLASS_MAP.put(p[0], p[1]); }
            } else {
                String[] p = line.trim().split("\\s+");
                if (p.length == 2) {
                    FIELD_MAP.put(cur + '.' + p[0], p[1]);
                } else if (p.length >= 3) {
                    METHOD_MAP.put(cur + '.' + p[0] + ' ' + p[1], p[2]);
                }
            }
        }
    }

    static void loadSrg(File fieldsCsv, File methodsCsv) throws IOException {
        for (String line : Files.readAllLines(fieldsCsv.toPath(), StandardCharsets.UTF_8)) {
            if (line.isEmpty() || line.startsWith("searge")) continue;
            String[] p = line.split(",", 3);
            if (p.length >= 2) SRG_FIELDS.put(p[0], p[1]);
        }
        for (String line : Files.readAllLines(methodsCsv.toPath(), StandardCharsets.UTF_8)) {
            if (line.isEmpty() || line.startsWith("searge")) continue;
            String[] p = line.split(",", 3);
            if (p.length >= 2) SRG_METHODS.put(p[0], p[1]);
        }
    }

    /** resolve a member up the hierarchy */
    static String resolve(Map<String, String> map, String owner, String name, String desc) {
        String k = owner + '.' + name + (desc == null ? "" : ' ' + desc);
        String v = map.get(k);
        if (v != null) return v;
        Set<String> seen = new HashSet<String>();
        Deque<String> q = new ArrayDeque<String>();
        q.add(owner);
        while (!q.isEmpty()) {
            String c = q.poll();
            if (c == null || !seen.add(c)) continue;
            String[] sups = HIER.get(c);
            if (sups != null) for (String s : sups) if (s != null) q.add(s);
        }
        for (String c : seen) {
            String kk = c + '.' + name + (desc == null ? "" : ' ' + desc);
            v = map.get(kk);
            if (v != null) return v;
        }
        return null;
    }

    static byte[] remapToMcp(byte[] data) {
        ClassReader cr = new ClassReader(data);
        ClassWriter cw = new ClassWriter(0);
        cr.accept(new ClassRemapper(cw, new Remapper() {
            @Override public String map(String internalName) {
                String v = CLASS_MAP.get(internalName);
                return v != null ? v : internalName;
            }
            @Override public String mapMethodName(String owner, String name, String desc) {
                String v = resolve(METHOD_MAP, owner, name, desc);
                if (v != null) v = SRG_METHODS.containsKey(v) ? SRG_METHODS.get(v) : v;
                else if (SRG_METHODS.containsKey(name)) v = SRG_METHODS.get(name);
                return v != null ? v : name;
            }
            @Override public String mapFieldName(String owner, String name, String desc) {
                String v = resolve(FIELD_MAP, owner, name, null);
                if (v != null) v = SRG_FIELDS.containsKey(v) ? SRG_FIELDS.get(v) : v;
                else if (SRG_FIELDS.containsKey(name)) v = SRG_FIELDS.get(name);
                return v != null ? v : name;
            }
        }), 0);
        return cw.toByteArray();
    }

    static void readJarInto(File f, Map<String, byte[]> map) throws IOException {
        ZipFile z = new ZipFile(f);
        for (Enumeration<? extends ZipEntry> en = z.entries(); en.hasMoreElements();) {
            ZipEntry e = en.nextElement();
            if (e.isDirectory()) continue;
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            InputStream in = z.getInputStream(e);
            byte[] buf = new byte[16384]; int r;
            while ((r = in.read(buf)) > 0) bo.write(buf, 0, r);
            in.close();
            map.put(e.getName(), bo.toByteArray());
        }
        z.close();
    }

    static void writeJar(File f, Map<String, byte[]> entries) throws IOException {
        ZipOutputStream z = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
        z.setLevel(9);
        for (Map.Entry<String, byte[]> e : entries.entrySet()) {
            z.putNextEntry(new ZipEntry(e.getKey()));
            z.write(e.getValue());
            z.closeEntry();
        }
        z.close();
    }
}



