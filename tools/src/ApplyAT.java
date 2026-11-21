import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;

/**
 * Applies Forge's access transformer rules to the reconstructed MCP jar.
 * Rules in forge_at.cfg are written with SRG member names; this tool translates
 * them through mcp_stable CSVs and rewrites the access flags.
 */
public final class ApplyAT {

    static final Map<String, String> SRG_FIELDS = new HashMap<String, String>();
    static final Map<String, String> SRG_METHODS = new HashMap<String, String>();

    public static void main(String[] a) throws Exception {
        File in = new File(a[0]);
        File atFile = new File(a[1]);
        File stableDir = new File(a[2]);
        File out = new File(a[3]);

        for (String line : Files.readAllLines(new File(stableDir, "fields.csv").toPath(), StandardCharsets.UTF_8)) {
            if (line.isEmpty() || line.startsWith("searge")) continue;
            String[] p = line.split(",", 3);
            if (p.length >= 2) SRG_FIELDS.put(p[0], p[1]);
        }
        for (String line : Files.readAllLines(new File(stableDir, "methods.csv").toPath(), StandardCharsets.UTF_8)) {
            if (line.isEmpty() || line.startsWith("searge")) continue;
            String[] p = line.split(",", 3);
            if (p.length >= 2) SRG_METHODS.put(p[0], p[1]);
        }

        Map<String, List<String[]>> rules = new HashMap<String, List<String[]>>();
        int count = 0;
        for (String raw : Files.readAllLines(atFile.toPath(), StandardCharsets.UTF_8)) {
            String line = raw.trim();
            if (line.isEmpty() || line.startsWith("#")) continue;
            int hash = line.indexOf('#');
            if (hash >= 0) line = line.substring(0, hash).trim();
            String[] p = line.split("\\s+");
            if (p.length < 3) continue;
            String access = p[0];
            String owner = p[1].replace('.', '/');
            String member = p[2];
            if (SRG_FIELDS.containsKey(member)) member = SRG_FIELDS.get(member);
            else if (SRG_METHODS.containsKey(member)) member = SRG_METHODS.get(member);
            rules.computeIfAbsent(owner, k -> new ArrayList<String[]>()).add(new String[]{access, member});
            count++;
        }
        System.out.println("at rules: " + count + " for " + rules.size() + " classes");

        Map<String, byte[]> entries = readJar(in);
        Map<String, byte[]> outEntries = new LinkedHashMap<String, byte[]>();
        int touched = 0, changed = 0;
        for (Map.Entry<String, byte[]> e : entries.entrySet()) {
            if (!e.getKey().endsWith(".class")) { outEntries.put(e.getKey(), e.getValue()); continue; }
            ClassNode cn = new ClassNode();
            new ClassReader(e.getValue()).accept(cn, 0);
            List<String[]> rs = rules.get(cn.name);
            if (rs != null) {
                touched++;
                boolean any = false;
                for (String[] r : rs) {
                    String acc = r[0];
                    String member = r[1];
                    String mName = member, mDesc = null;
                    if (member.contains("(")) { mName = member.substring(0, member.indexOf('(')); mDesc = member.substring(member.indexOf('(')); }
                    for (Object o : cn.fields) {
                        FieldNode f = (FieldNode) o;
                        if (!f.name.equals(member)) continue;
                        int old = f.access;
                        f.access = apply(f.access, acc);
                        if (f.access != old) any = true;
                    }
                    for (Object o : cn.methods) {
                        MethodNode m = (MethodNode) o;
                        if (!m.name.equals(mName)) continue;
                        if (mDesc != null && !m.desc.equals(mDesc)) continue;
                        int old = m.access;
                        m.access = apply(m.access, acc);
                        if (m.access != old) any = true;
                    }
                }
                if (any) {
                    changed++;
                    ClassWriter cw = new ClassWriter(0);
                    cn.accept(cw);
                    outEntries.put(e.getKey(), cw.toByteArray());
                    continue;
                }
            }
            outEntries.put(e.getKey(), e.getValue());
        }
        writeJar(out, outEntries);
        System.out.println("classes with rules: " + touched + " rewritten: " + changed);
    }

    static int apply(int access, String rule) {
        int mod = 0;
        String spec = rule;
        boolean removeFinal = false, addFinal = false;
        int dash = spec.indexOf('-');
        int plus = spec.indexOf('+');
        String base = spec;
        if (dash > 0) { base = spec.substring(0, dash); removeFinal = true; }
        else if (plus > 0) { base = spec.substring(0, plus); addFinal = true; }
        if (base.startsWith("public")) mod = Opcodes.ACC_PUBLIC;
        else if (base.startsWith("protected")) mod = Opcodes.ACC_PROTECTED;
        else if (base.startsWith("private")) mod = Opcodes.ACC_PRIVATE;
        int cleared = access & ~(Opcodes.ACC_PUBLIC | Opcodes.ACC_PROTECTED | Opcodes.ACC_PRIVATE);
        int res = cleared | mod;
        if (removeFinal) res &= ~Opcodes.ACC_FINAL;
        if (addFinal) res |= Opcodes.ACC_FINAL;
        return res;
    }

    static Map<String, byte[]> readJar(File f) throws IOException {
        Map<String, byte[]> out = new LinkedHashMap<String, byte[]>();
        ZipInputStream z = new ZipInputStream(new BufferedInputStream(new FileInputStream(f)));
        ZipEntry e;
        while ((e = z.getNextEntry()) != null) {
            if (e.isDirectory()) continue;
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            byte[] buf = new byte[8192];
            int r;
            while ((r = z.read(buf)) > 0) bo.write(buf, 0, r);
            out.put(e.getName(), bo.toByteArray());
        }
        z.close();
        return out;
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

