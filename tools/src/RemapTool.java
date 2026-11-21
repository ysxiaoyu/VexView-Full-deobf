import org.objectweb.asm.*;
import org.objectweb.asm.commons.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;

/**
 * VexView deobfuscation toolchain helper.
 *
 * Modes:
 *   list   <in.jar>                       -> print "internalName" per line
 *   scan   <in.jar> <out.json>            -> structural dump of every class
 *   normalize <in.jar> <out.jar> <prefix> <mapOut>  -> rename matching prefix to PREFIX/Cnn, SRG->MCP
 *   remap  <in.jar> <out.jar> <mapFile>   -> apply explicit map (+ SRG->MCP)
 *
 * Explicit map file lines (whitespace separated, '#' comments):
 *   CLASS  <obfInternal> <newInternal>
 *   METHOD <ownerInternal> <obfName> <desc> <newName>
 *   FIELD  <ownerInternal> <obfName> <desc> <newName>
 */
public final class RemapTool {

    // ---------- mappings ----------
    static final Map<String, String> SRG_METHODS = new HashMap<String, String>();
    static final Map<String, String> SRG_FIELDS = new HashMap<String, String>();
    static final Map<String, String[]> HIER = new HashMap<String, String[]>();

    static void loadSrg(File fieldsCsv, File methodsCsv) throws IOException {
        if (fieldsCsv != null && fieldsCsv.isFile()) {
            for (String line : Files.readAllLines(fieldsCsv.toPath(), StandardCharsets.UTF_8)) {
                if (line.isEmpty() || line.startsWith("searge")) continue;
                String[] p = splitCsv(line);
                if (p.length >= 2 && !p[0].isEmpty()) SRG_FIELDS.put(p[0], p[1]);
            }
        }
        if (methodsCsv != null && methodsCsv.isFile()) {
            for (String line : Files.readAllLines(methodsCsv.toPath(), StandardCharsets.UTF_8)) {
                if (line.isEmpty() || line.startsWith("searge")) continue;
                String[] p = splitCsv(line);
                if (p.length >= 2 && !p[0].isEmpty()) SRG_METHODS.put(p[0], p[1]);
            }
        }
        System.out.println("srg methods=" + SRG_METHODS.size() + " fields=" + SRG_FIELDS.size());
    }

    static String[] splitCsv(String line) {
        List<String> out = new ArrayList<String>();
        StringBuilder sb = new StringBuilder();
        boolean q = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (q) {
                if (c == '"') { if (i + 1 < line.length() && line.charAt(i + 1) == '"') { sb.append('"'); i++; } else q = false; }
                else sb.append(c);
            } else if (c == '"') q = true;
            else if (c == ',') { out.add(sb.toString()); sb.setLength(0); }
            else sb.append(c);
        }
        out.add(sb.toString());
        return out.toArray(new String[0]);
    }

    // ---------- remapper ----------
    static final class Mapper extends Remapper {
        final Map<String, String> classMap = new HashMap<String, String>();
        final Map<String, String> methodMap = new HashMap<String, String>(); // owner.name desc -> new
        final Map<String, String> fieldMap = new HashMap<String, String>();

        Mapper(Map<String, String> cm) { if (cm != null) classMap.putAll(cm); }

        @Override public String map(String internalName) {
            String v = classMap.get(internalName);
            return v != null ? v : internalName;
        }

        @Override public String mapMethodName(String owner, String name, String desc) {
            String v = methodMap.get(owner + '.' + name + ' ' + desc);
            if (v != null) return v;
            for (String c : hierarchyWalk(owner)) {
                v = methodMap.get(c + '.' + name + ' ' + desc);
                if (v != null) return v;
            }
            v = SRG_METHODS.get(name);
            return v != null ? v : name;
        }

        @Override public String mapFieldName(String owner, String name, String desc) {
            String v = fieldMap.get(owner + '.' + name + ' ' + desc);
            if (v != null) return v;
            for (String c : hierarchyWalk(owner)) {
                v = fieldMap.get(c + '.' + name + ' ' + desc);
                if (v != null) return v;
            }
            v = SRG_FIELDS.get(name);
            return v != null ? v : name;
        }

        private List<String> hierarchyWalk(String owner) {
            List<String> out = new ArrayList<String>();
            if (owner == null) return out;
            java.util.LinkedHashSet<String> seen = new java.util.LinkedHashSet<String>();
            java.util.ArrayDeque<String> q = new java.util.ArrayDeque<String>();
            String[] sup = HIER.get(owner);
            if (sup != null) for (String s : sup) if (s != null) q.add(s);
            while (!q.isEmpty()) {
                String c = q.poll();
                if (!seen.add(c)) continue;
                out.add(c);
                String[] s2 = HIER.get(c);
                if (s2 != null) for (String s : s2) if (s != null) q.add(s);
            }
            return out;
        }
    }

    static byte[] remapClass(byte[] data, final Mapper mapper) {
        ClassReader cr = new ClassReader(data);
        ClassWriter cw = new ClassWriter(0);
        ClassVisitor cv = new ClassRemapper(cw, mapper);
        cr.accept(cv, 0);
        return cw.toByteArray();
    }

    // ---------- scan ----------
    static void buildHierarchy(Map<String, byte[]> cs) {
        for (Map.Entry<String, byte[]> e : cs.entrySet()) {
            if (!e.getKey().endsWith(".class")) continue;
            try {
                ClassReader cr = new ClassReader(e.getValue());
                String sup = cr.getSuperName();
                String[] itf = cr.getInterfaces();
                int n = (sup == null ? 0 : 1) + (itf == null ? 0 : itf.length);
                String[] all = new String[n];
                int k = 0;
                if (sup != null) all[k++] = sup;
                if (itf != null) for (String s : itf) all[k++] = s;
                HIER.put(cr.getClassName(), all);
            } catch (Throwable t) { }
        }
    }

    static String esc(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '"': sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\n': sb.append("\\n"); break;
                case '\r': sb.append("\\r"); break;
                case '\t': sb.append("\\t"); break;
                default:
                    if (c < 0x20 || c > 0x7e) sb.append(String.format("\\\\u%04x", (int) c));
                    else sb.append(c);
            }
        }
        return sb.toString();
    }

    static final class ClassInfo {
        String name, superName, signature;
        int access, version;
        List<String> interfaces = new ArrayList<String>();
        List<String[]> fields = new ArrayList<String[]>();   // name, desc, access, signature
        List<String[]> methods = new ArrayList<String[]>();  // name, desc, access, signature, exceptions
        List<String> annotations = new ArrayList<String>();
        List<String> refClasses = new ArrayList<String>();
        List<String> refMethods = new ArrayList<String>();   // owner.name desc
        List<String> refFields = new ArrayList<String>();    // owner.name desc
        List<String> strings = new ArrayList<String>();
        List<String> classLdc = new ArrayList<String>();
        List<String> methodAnnotations = new ArrayList<String>(); // "methodName desc @Ann"
        List<String> innerClasses = new ArrayList<String>();
        List<String> numbers = new ArrayList<String>();
    }

    static ClassInfo scan(byte[] data) {
        final ClassInfo ci = new ClassInfo();
        ClassReader cr = new ClassReader(data);
        ci.version = cr.readUnsignedShort(6);
        cr.accept(new ClassVisitor(Opcodes.ASM9) {
            @Override public void visit(int version, int access, String name, String sig, String sup, String[] itf) {
                ci.name = name; ci.access = access; ci.signature = sig; ci.superName = sup;
                if (itf != null) ci.interfaces.addAll(Arrays.asList(itf));
            }
            @Override public AnnotationVisitor visitAnnotation(String desc, boolean vis) {
                ci.annotations.add(desc); return null;
            }
            @Override public void visitInnerClass(String name, String outer, String inner, int access) {
                ci.innerClasses.add(name + " | " + outer + " | " + inner + " | " + access);
            }
            @Override public FieldVisitor visitField(int access, String name, String desc, String sig, Object value) {
                ci.fields.add(new String[]{name, desc, Integer.toString(access), sig == null ? "" : sig});
                if (value instanceof String) ci.numbers.add("F:" + name + "=" + esc((String) value));
                else if (value != null) ci.numbers.add("F:" + name + "=" + value);
                return null;
            }
            @Override public MethodVisitor visitMethod(int access, String name, String desc, String sig, String[] ex) {
                ci.methods.add(new String[]{name, desc, Integer.toString(access), sig == null ? "" : sig,
                        ex == null ? "" : Arrays.toString(ex)});
                return new MethodVisitor(Opcodes.ASM9) {
                    @Override public AnnotationVisitor visitAnnotation(String d, boolean v) {
                        ci.methodAnnotations.add(name + " " + desc + " @" + d); return null;
                    }
                    @Override public void visitTypeInsn(int op, String type) { ci.refClasses.add(type); }
                    @Override public void visitFieldInsn(int op, String o, String n, String d) { ci.refFields.add(o + '.' + n + ' ' + d); }
                    @Override public void visitMethodInsn(int op, String o, String n, String d, boolean itf) { ci.refMethods.add(o + '.' + n + ' ' + d); }
                    @Override public void visitLdcInsn(Object cst) {
                        if (cst instanceof String) ci.strings.add((String) cst);
                        else if (cst instanceof Type) ci.classLdc.add(((Type) cst).getDescriptor());
                        else if (cst instanceof Integer || cst instanceof Long || cst instanceof Float || cst instanceof Double) ci.numbers.add(cst.toString());
                    }
                    @Override public void visitMultiANewArrayInsn(String d, int dim) { ci.refClasses.add(d); }
                };
            }
        }, 0);
        return ci;
    }

    static void writeScanJson(File out, Map<String, byte[]> classes) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        boolean first = true;
        List<String> names = new ArrayList<String>(classes.keySet());
        Collections.sort(names);
        for (String n : names) {
            if (!n.endsWith(".class")) continue;
            ClassInfo ci = scan(classes.get(n));
            if (!first) sb.append(",\n");
            first = false;
            sb.append('"').append(esc(ci.name)).append("\":{");
            sb.append("\"super\":").append(ci.superName == null ? "null" : "\"" + esc(ci.superName) + "\"");
            sb.append(",\"access\":").append(ci.access).append(",\"version\":").append(ci.version);
            sb.append(",\"signature\":").append(ci.signature == null ? "null" : "\"" + esc(ci.signature) + "\"");
            sb.append(",\"interfaces\":").append(arr(ci.interfaces));
            sb.append(",\"annotations\":").append(arr(ci.annotations));
            sb.append(",\"methodAnnotations\":").append(arr(ci.methodAnnotations));
            sb.append(",\"innerClasses\":").append(arr(ci.innerClasses));
            sb.append(",\"refClasses\":").append(arr(new ArrayList<String>(new LinkedHashSet<String>(ci.refClasses))));
            sb.append(",\"refMethods\":").append(arr(new ArrayList<String>(new LinkedHashSet<String>(ci.refMethods))));
            sb.append(",\"refFields\":").append(arr(new ArrayList<String>(new LinkedHashSet<String>(ci.refFields))));
            sb.append(",\"strings\":").append(arr(new ArrayList<String>(new LinkedHashSet<String>(ci.strings))));
            sb.append(",\"classLdc\":").append(arr(new ArrayList<String>(new LinkedHashSet<String>(ci.classLdc))));
            sb.append(",\"numbers\":").append(arr(new ArrayList<String>(new LinkedHashSet<String>(ci.numbers))));
            sb.append(",\"fields\":[");
            for (int i = 0; i < ci.fields.size(); i++) {
                if (i > 0) sb.append(',');
                String[] f = ci.fields.get(i);
                sb.append("[\"").append(esc(f[0])).append("\",\"").append(esc(f[1])).append("\",").append(f[2])
                        .append(",\"").append(esc(f[3])).append("\"]");
            }
            sb.append("],\"methods\":[");
            for (int i = 0; i < ci.methods.size(); i++) {
                if (i > 0) sb.append(',');
                String[] m = ci.methods.get(i);
                sb.append("[\"").append(esc(m[0])).append("\",\"").append(esc(m[1])).append("\",").append(m[2])
                        .append(",\"").append(esc(m[3])).append("\",\"").append(esc(m[4])).append("\"]");
            }
            sb.append("]}");
        }
        sb.append("\n}\n");
        Files.write(out.toPath(), sb.toString().getBytes(StandardCharsets.UTF_8));
    }

    static String arr(List<String> l) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < l.size(); i++) {
            if (i > 0) sb.append(',');
            sb.append('"').append(esc(l.get(i))).append('"');
        }
        return sb.append(']').toString();
    }

    // ---------- main ----------
    public static void main(String[] a) throws Exception {
        File srgDir = new File(System.getProperty("srg.dir", "D:/deobf/_tools/mcp_stable"));
        loadSrg(new File(srgDir, "fields.csv"), new File(srgDir, "methods.csv"));

        String mode = a[0];
        if ("list".equals(mode)) {
            Map<String, byte[]> cs = readJar(new File(a[1]));
            List<String> n = new ArrayList<String>(cs.keySet());
            Collections.sort(n);
            for (String s : n) System.out.println(s);
            return;
        }
        if ("scan".equals(mode)) {
            Map<String, byte[]> cs = readJar(new File(a[1]));
            writeScanJson(new File(a[2]), cs);
            System.out.println("scanned " + cs.size() + " classes");
            return;
        }
        if ("normalize".equals(mode)) {
            final String prefix = a[3];
            Map<String, byte[]> cs = readJar(new File(a[1]));
            List<String> victims = new ArrayList<String>();
            for (String n : cs.keySet()) {
                if (n.endsWith(".class")) {
                    String in = n.substring(0, n.length() - 6);
                    if (in.startsWith(prefix)) victims.add(in);
                }
            }
            Collections.sort(victims);
            Map<String, String> cm = new HashMap<String, String>();
            StringBuilder map = new StringBuilder();
            int idx = 0;
            for (String v : victims) {
                String nn = "obf/C" + idx++;
                cm.put(v, nn);
                map.append("CLASS ").append(v).append(' ').append(nn).append('\n');
            }
            System.out.println("renaming " + victims.size() + " classes with prefix " + prefix);
            Files.write(new File(a[4]).toPath(), map.toString().getBytes(StandardCharsets.UTF_8));
            Mapper m = new Mapper(cm);
            writeJar(new File(a[2]), cs, m);
            return;
        }
        if ("strip".equals(mode)) {
            Map<String, byte[]> cs = readJar(new File(a[1]));
            Map<String, byte[]> outE = new LinkedHashMap<String, byte[]>();
            int n = 0;
            for (Map.Entry<String, byte[]> e : cs.entrySet()) {
                if (!e.getKey().endsWith(".class")) { outE.put(e.getKey(), e.getValue()); continue; }
                try {
                    ClassReader cr = new ClassReader(e.getValue());
                    ClassWriter cw = new ClassWriter(0);
                    cr.accept(cw, ClassReader.SKIP_DEBUG);
                    outE.put(e.getKey(), cw.toByteArray());
                    n++;
                } catch (Throwable t) {
                    outE.put(e.getKey(), e.getValue());
                }
            }
            writeJar(new File(a[2]), outE, new Mapper(null));
            System.out.println("stripped debug info from " + n + " classes");
            return;
        }
        if ("remap".equals(mode)) {
            Map<String, byte[]> cs = readJar(new File(a[1]));
            buildHierarchy(cs);
            Mapper m = new Mapper(null);
            for (String line : Files.readAllLines(new File(a[3]).toPath(), StandardCharsets.UTF_8)) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                String[] p = line.split("\\s+");
                if ("CLASS".equals(p[0])) {
                    m.classMap.put(p[1], p[2]);
                } else if ("METHOD".equals(p[0])) {
                    m.methodMap.put(p[1] + '.' + p[2] + ' ' + p[3], p[4]);
                } else if ("FIELD".equals(p[0])) {
                    m.fieldMap.put(p[1] + '.' + p[2] + ' ' + p[3], p[4]);
                }
            }
            System.out.println("class=" + m.classMap.size() + " method=" + m.methodMap.size() + " field=" + m.fieldMap.size());
            writeJar(new File(a[2]), cs, m);
            return;
        }
        System.err.println("unknown mode " + mode);
        System.exit(2);
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

    static void writeJar(File f, Map<String, byte[]> entries, Mapper m) throws IOException {
        ZipOutputStream z = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(f)));
        z.setLevel(9);
        for (Map.Entry<String, byte[]> en : entries.entrySet()) {
            String name = en.getKey();
            byte[] data = en.getValue();
            if (name.endsWith(".class")) {
                try {
                    data = remapClass(data, m);
                    ClassReader cr = new ClassReader(data);
                    name = cr.getClassName() + ".class";
                } catch (Throwable t) {
                    System.err.println("remap failed for " + name + ": " + t);
                    continue;
                }
            }
            ZipEntry ze = new ZipEntry(name);
            z.putNextEntry(ze);
            z.write(data);
            z.closeEntry();
        }
        z.close();
    }
}



