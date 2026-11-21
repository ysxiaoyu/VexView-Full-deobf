import org.objectweb.asm.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;

/**
 * Emits an owner-aware MCP -> SRG member map (the data Forge's reobf step needs).
 *
 * Sources of truth:
 *   - the vanilla notch client jar      (real member descriptors)
 *   - joined.tsrg                       (notch member -> SRG name)
 *   - mcp_stable fields/methods csv     (SRG name -> MCP name)
 *
 * Output format matches RemapTool's mapping file:
 *   METHOD <mcpOwner> <mcpName> <mcpDesc> <srgName>
 *   FIELD  <mcpOwner> <mcpName> <mcpDesc> <srgName>
 */
public final class GenReobf {

    static final Map<String, String> CLASS_MAP = new HashMap<String, String>();
    static final Map<String, String> METHOD_SRG = new HashMap<String, String>();  // notchOwner.obfName desc -> srg
    static final Map<String, String> FIELD_SRG = new HashMap<String, String>();   // notchOwner.obfName -> srg
    static final Map<String, String> SRG2MCP_M = new HashMap<String, String>();
    static final Map<String, String> SRG2MCP_F = new HashMap<String, String>();

    public static void main(String[] a) throws Exception {
        File client = new File(a[0]);
        File tsrg = new File(a[1]);
        File stableDir = new File(a[2]);
        File out = new File(a[3]);

        loadTsrg(tsrg);
        loadCsv(new File(stableDir, "fields.csv"), SRG2MCP_F);
        loadCsv(new File(stableDir, "methods.csv"), SRG2MCP_M);

        List<String> lines = new ArrayList<String>();
        int methods = 0, fields = 0, skipped = 0;

        ZipFile z = new ZipFile(client);
        for (Enumeration<? extends ZipEntry> en = z.entries(); en.hasMoreElements(); ) {
            ZipEntry e = en.nextElement();
            if (!e.getName().endsWith(".class")) continue;
            byte[] data;
            try {
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                InputStream in = z.getInputStream(e);
                byte[] buf = new byte[8192];
                int r;
                while ((r = in.read(buf)) > 0) bo.write(buf, 0, r);
                in.close();
                data = bo.toByteArray();
            } catch (Throwable t) { continue; }

            final String[] holder = new String[1];
            final List<String[]> flds = new ArrayList<String[]>();
            final List<String[]> mths = new ArrayList<String[]>();
            try {
                ClassReader cr = new ClassReader(data);
                holder[0] = cr.getClassName();
                cr.accept(new ClassVisitor(Opcodes.ASM9) {
                    @Override public FieldVisitor visitField(int acc, String n, String d, String s, Object v) {
                        flds.add(new String[]{n, d});
                        return null;
                    }
                    @Override public MethodVisitor visitMethod(int acc, String n, String d, String s, String[] ex) {
                        mths.add(new String[]{n, d});
                        return null;
                    }
                }, ClassReader.SKIP_CODE);
            } catch (Throwable t) { continue; }

            String notchOwner = holder[0];
            String mcpOwner = CLASS_MAP.get(notchOwner);
            if (mcpOwner == null) { skipped++; continue; }

            for (String[] f : flds) {
                String srg = FIELD_SRG.get(notchOwner + '.' + f[0]);
                if (srg == null) continue;
                String mcpName = SRG2MCP_F.containsKey(srg) ? SRG2MCP_F.get(srg) : srg;
                String mcpDesc = mapDesc(f[1]);
                if (mcpName.equals(srg) && !mcpDesc.equals(f[1])) {
                    // unmapped name but mapped type: still needs the SRG name
                }
                lines.add("FIELD " + mcpOwner + " " + mcpName + " " + mcpDesc + " " + srg);
                fields++;
            }
            for (String[] m : mths) {
                String srg = METHOD_SRG.get(notchOwner + '.' + m[0] + ' ' + m[1]);
                if (srg == null) continue;
                String mcpName = SRG2MCP_M.containsKey(srg) ? SRG2MCP_M.get(srg) : srg;
                lines.add("METHOD " + mcpOwner + " " + mcpName + " " + mapDesc(m[1]) + " " + srg);
                methods++;
            }
        }
        z.close();

        Files.write(out.toPath(), lines, StandardCharsets.UTF_8);
        System.out.println("reobf map: methods=" + methods + " fields=" + fields + " unmappedClasses=" + skipped);
    }

    static String mapDesc(String desc) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        while (i < desc.length()) {
            char c = desc.charAt(i);
            if (c == 'L') {
                int end = desc.indexOf(';', i);
                if (end < 0) { sb.append(desc.substring(i)); break; }
                String cls = desc.substring(i + 1, end);
                String mapped = CLASS_MAP.get(cls);
                sb.append('L').append(mapped != null ? mapped : cls).append(';');
                i = end + 1;
            } else {
                sb.append(c);
                i++;
            }
        }
        return sb.toString();
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
                if (p.length == 2) FIELD_SRG.put(cur + '.' + p[0], p[1]);
                else if (p.length >= 3) METHOD_SRG.put(cur + '.' + p[0] + ' ' + p[1], p[2]);
            }
        }
    }

    static void loadCsv(File f, Map<String, String> into) throws IOException {
        for (String line : Files.readAllLines(f.toPath(), StandardCharsets.UTF_8)) {
            if (line.isEmpty() || line.startsWith("searge")) continue;
            String[] p = line.split(",", 3);
            if (p.length >= 2 && !p[1].isEmpty()) into.put(p[0], p[1]);
        }
    }
}
