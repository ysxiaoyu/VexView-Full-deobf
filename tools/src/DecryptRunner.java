import org.objectweb.asm.*;

import java.io.*;
import java.lang.reflect.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;

/**
 * Faithful string decryption: instead of re-implementing the obfuscator's routine,
 * this tool extracts each decryptor method into a minimal synthetic class and builds
 * caller stubs that carry the ORIGINAL class name and ORIGINAL method name, so the
 * obfuscator's own StackTraceElement based key resolves exactly as it did in production.
 * Strings are then inlined into the jar (LDC plaintext) and reported.
 */
public final class DecryptRunner {

    static final String DEC_NAME = "pcdlk,pcdlona";
    static Map<String, String> OWNER_MAP = new HashMap<String, String>();
    static final String DEC_DESC = "(Ljava/lang/String;)Ljava/lang/String;";

    static final class Site {
        String caller, method, methodDesc, enc, owner;
        String plain;
        int argCount;
        String field;
    }

    static final class StubClass extends ClassLoader {
        StubClass() { super(null); }
        Class<?> define(String name, byte[] data) { return defineClass(name.replace('/', '.'), data, 0, data.length); }
    }

    public static void main(String[] args) throws Exception {
        File in = new File(args[0]);
        File out = new File(args[1]);
        File report = new File(args[2]);

        Map<String, byte[]> entries = readJar(in);

        Map<String, byte[]> decryptors = new LinkedHashMap<String, byte[]>();
        OWNER_MAP.clear();
        for (Map.Entry<String, byte[]> e : entries.entrySet()) {
            if (!e.getKey().endsWith(".class")) continue;
            String orig = new ClassReader(e.getValue()).getClassName();
            String synth = "dec/D" + decryptors.size();
            byte[] minimal = minimalDecryptor(e.getValue(), synth);
            if (minimal != null) {
                OWNER_MAP.put(orig, synth);
                decryptors.put(synth, minimal);
            }
        }
        System.out.println("decryptor classes: " + decryptors.size());

        List<Site> sites = new ArrayList<Site>();
        String sitesFile = System.getProperty("sites");
        if (sitesFile != null) {
            String txt = new String(Files.readAllBytes(Paths.get(sitesFile)), StandardCharsets.UTF_8);
            java.util.regex.Matcher m = java.util.regex.Pattern.compile(
                    "\\{\"class\":\"((?:[^\"\\\\]|\\\\.)*)\",\"method\":\"((?:[^\"\\\\]|\\\\.)*)\",\"desc\":\"((?:[^\"\\\\]|\\\\.)*)\",\"enc\":\"((?:[^\"\\\\]|\\\\.)*)\",\"owner\":\"((?:[^\"\\\\]|\\\\.)*)\"\\}")
                    .matcher(txt);
            while (m.find()) {
                Site s = new Site();
                s.caller = unesc(m.group(1));
                s.method = unesc(m.group(2));
                s.methodDesc = unesc(m.group(3));
                s.enc = unesc(m.group(4));
                s.owner = unesc(m.group(5));
                sites.add(s);
            }
            System.out.println("sites from file: " + sites.size());
        }
        if (sitesFile == null) {
            for (Map.Entry<String, byte[]> e : entries.entrySet()) {
                if (!e.getKey().endsWith(".class")) continue;
                collectSites(e.getValue(), sites);
            }
        }
        System.out.println("sites: " + sites.size());

        Map<String, List<Site>> byCaller = new LinkedHashMap<String, List<Site>>();
        for (Site s : sites) byCaller.computeIfAbsent(s.caller, k -> new ArrayList<Site>()).add(s);

        StubClass loader = new StubClass();
        for (Map.Entry<String, byte[]> d : decryptors.entrySet()) loader.define(d.getKey(), d.getValue());

        int stubCount = 0;
        for (Map.Entry<String, List<Site>> ent : byCaller.entrySet()) {
            String caller = ent.getKey();
            List<Site> list = ent.getValue();
            List<Site> initSites = new ArrayList<Site>();
            List<Site> ctorSites = new ArrayList<Site>();
            Map<String, Integer> perMethod = new HashMap<String, Integer>();
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
            cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC | Opcodes.ACC_SUPER, caller, null, "java/lang/Object", null);

            for (Site s : list) {
                if ("<clinit>".equals(s.method)) {
                    s.field = "r" + initSites.size();
                    initSites.add(s);
                    continue;
                }
                if ("<init>".equals(s.method)) {
                    s.field = "c" + ctorSites.size();
                    ctorSites.add(s);
                    continue;
                }
                int idx = perMethod.merge(s.method, 1, Integer::sum);
                String desc = "(Ljava/lang/String;" + rep("I", idx) + ")Ljava/lang/String;";
                s.argCount = idx;
                MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, s.method, desc, null, null);
                mv.visitCode();
                mv.visitVarInsn(Opcodes.ALOAD, 0);
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, OWNER_MAP.getOrDefault(s.owner, s.owner), DEC_NAME, DEC_DESC, false);
                mv.visitInsn(Opcodes.ARETURN);
                mv.visitMaxs(2, 1);
                mv.visitEnd();
            }

            if (!initSites.isEmpty()) {
                cw.visitField(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "RES", "[Ljava/lang/String;", null, null).visitEnd();
                MethodVisitor mv = cw.visitMethod(Opcodes.ACC_STATIC, "<clinit>", "()V", null, null);
                mv.visitCode();
                mv.visitIntInsn(Opcodes.BIPUSH, Math.max(1, initSites.size()));
                mv.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/String");
                mv.visitFieldInsn(Opcodes.PUTSTATIC, caller, "RES", "[Ljava/lang/String;");
                for (int i = 0; i < initSites.size(); i++) {
                    Site s = initSites.get(i);
                    mv.visitFieldInsn(Opcodes.GETSTATIC, caller, "RES", "[Ljava/lang/String;");
                    mv.visitIntInsn(Opcodes.BIPUSH, i);
                    mv.visitLdcInsn(s.enc);
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, OWNER_MAP.getOrDefault(s.owner, s.owner), DEC_NAME, DEC_DESC, false);
                    mv.visitInsn(Opcodes.AASTORE);
                }
                mv.visitInsn(Opcodes.RETURN);
                mv.visitMaxs(4, 0);
                mv.visitEnd();
            }

            if (!ctorSites.isEmpty()) {
                cw.visitField(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "RESI", "[Ljava/lang/String;", null, null).visitEnd();
                MethodVisitor ct = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
                ct.visitCode();
                ct.visitIntInsn(Opcodes.BIPUSH, Math.max(1, ctorSites.size()));
                ct.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/String");
                ct.visitFieldInsn(Opcodes.PUTSTATIC, caller, "RESI", "[Ljava/lang/String;");
                for (int i = 0; i < ctorSites.size(); i++) {
                    Site s = ctorSites.get(i);
                    ct.visitFieldInsn(Opcodes.GETSTATIC, caller, "RESI", "[Ljava/lang/String;");
                    ct.visitIntInsn(Opcodes.BIPUSH, i);
                    ct.visitLdcInsn(s.enc);
                    ct.visitMethodInsn(Opcodes.INVOKESTATIC, OWNER_MAP.getOrDefault(s.owner, s.owner), DEC_NAME, DEC_DESC, false);
                    ct.visitInsn(Opcodes.AASTORE);
                }
                ct.visitVarInsn(Opcodes.ALOAD, 0);
                ct.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
                ct.visitInsn(Opcodes.RETURN);
                ct.visitMaxs(4, 1);
                ct.visitEnd();
            }
            cw.visitEnd();
            try {
                loader.define(caller, cw.toByteArray());
            } catch (Throwable t) {
                System.err.println("stub define failed " + caller + " : " + t);
            }
            stubCount++;
        }
        System.out.println("stubs: " + stubCount);

        int failed = 0;
        for (Site s : sites) {
            try {
                Class<?> c = Class.forName(s.caller.replace('/', '.'), true, loader);
                if ("<clinit>".equals(s.method)) {
                    String[] res = (String[]) c.getField("RES").get(null);
                    s.plain = res[Integer.parseInt(s.field.substring(1))];
                } else if ("<init>".equals(s.method)) {
                    c.newInstance();
                    String[] res = (String[]) c.getField("RESI").get(null);
                    s.plain = res[Integer.parseInt(s.field.substring(1))];
                } else {
                    Class<?>[] pt = new Class<?>[1 + s.argCount];
                    pt[0] = String.class;
                    for (int i = 1; i < pt.length; i++) pt[i] = int.class;
                    Method m = c.getDeclaredMethod(s.method, pt);
                    Object[] argv = new Object[pt.length];
                    argv[0] = s.enc;
                    for (int i = 1; i < argv.length; i++) argv[i] = 0;
                    s.plain = (String) m.invoke(null, argv);
                }
            } catch (Throwable t) {
                failed++;
                Throwable c2 = t instanceof InvocationTargetException ? ((InvocationTargetException) t).getTargetException() : t;
                if (failed <= 8) System.err.println("FAIL " + s.caller + "|" + s.method + " : " + c2);
            }
        }
        int insane = 0;
        for (Site s : sites) {
            if (s.plain == null) continue;
            boolean ok = true;
            for (int i = 0; i < s.plain.length(); i++) {
                char ch = s.plain.charAt(i);
                if (ch < 0x20 && ch != '\t') { ok = false; break; }
            }
            if (!ok) { s.plain = null; insane++; }
        }
        System.out.println("decrypted=" + (sites.size() - failed) + " failed=" + failed + " rejectedGarbage=" + insane);

        final Map<String, String> plainByKey = new HashMap<String, String>();
        for (Site s : sites) {
            if (s.plain != null) plainByKey.put(s.caller + "\u0000" + s.method + "\u0000" + s.enc, s.plain);
        }

        Map<String, byte[]> outEntries = new LinkedHashMap<String, byte[]>();
        for (Map.Entry<String, byte[]> e : entries.entrySet()) {
            if (!e.getKey().endsWith(".class")) { outEntries.put(e.getKey(), e.getValue()); continue; }
            try {
                outEntries.put(e.getKey(), rewrite(e.getValue(), plainByKey));
            } catch (Throwable t) {
                System.err.println("rewrite failed " + e.getKey() + " : " + t);
                outEntries.put(e.getKey(), e.getValue());
            }
        }
        writeJar(out, outEntries);

        StringBuilder sb = new StringBuilder("[\n");
        boolean first = true;
        for (Site s : sites) {
            if (!first) sb.append(",\n");
            first = false;
            sb.append("{\"class\":\"").append(esc(s.caller)).append("\",\"method\":\"").append(esc(s.method))
              .append("\",\"enc\":\"").append(esc(s.enc)).append("\",\"plain\":")
              .append(s.plain == null ? "null" : "\"" + esc(s.plain) + "\"").append("}");
        }
        sb.append("\n]\n");
        Files.write(report.toPath(), sb.toString().getBytes(StandardCharsets.UTF_8));
    }

    static String unesc(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '\\' && i + 1 < s.length()) {
                char n = s.charAt(i + 1);
                if (n == 'u' && i + 5 < s.length()) {
                    sb.append((char) Integer.parseInt(s.substring(i + 2, i + 6), 16));
                    i += 5;
                    continue;
                }
                if (n == 'n') { sb.append('\n'); i++; continue; }
                if (n == 'r') { sb.append('\r'); i++; continue; }
                if (n == 't') { sb.append('\t'); i++; continue; }
                if (n == '"' || n == '\\') { sb.append(n); i++; continue; }
            }
            sb.append(ch);
        }
        return sb.toString();
    }

    static String rep(String s, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) sb.append(s);
        return sb.toString();
    }

    static byte[] minimalDecryptor(byte[] data) { return minimalDecryptor(data, null); }

    static byte[] minimalDecryptor(byte[] data, String newName) {
        final byte[][] payload = new byte[1][];
        final String name = newName != null ? newName : new ClassReader(data).getClassName();
        new ClassReader(data).accept(new ClassVisitor(Opcodes.ASM9) {
            @Override public MethodVisitor visitMethod(int a, String n, final String d, String s, String[] e) {
                if (DEC_NAME.equals(n) && DEC_DESC.equals(d) && (a & Opcodes.ACC_STATIC) != 0) {
                    ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                    cw.visit(Opcodes.V1_8, Opcodes.ACC_PUBLIC | Opcodes.ACC_SUPER, name, null, "java/lang/Object", null);
                    final MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, DEC_NAME, DEC_DESC, null, null);
                    mv.visitCode();
                    new ClassReader(data).accept(new ClassVisitor(Opcodes.ASM9) {
                        @Override public MethodVisitor visitMethod(int a2, String n2, String d2, String s2, String[] e2) {
                            if (DEC_NAME.equals(n2) && DEC_DESC.equals(d2)) {
                                return new MethodVisitor(Opcodes.ASM9, mv) {
                                    @Override public void visitMaxs(int mx, int ml) { }
                                    @Override public void visitEnd() { }
                                    @Override public void visitFrame(int t, int nl, Object[] l, int ns, Object[] st) {
                                        super.visitFrame(t, nl, l, ns, st);
                                    }
                                };
                            }
                            return null;
                        }
                    }, ClassReader.EXPAND_FRAMES);
                    mv.visitMaxs(0, 0);
                    mv.visitEnd();
                    cw.visitEnd();
                    payload[0] = cw.toByteArray();
                }
                return null;
            }
        }, ClassReader.SKIP_FRAMES);
        return payload[0];
    }

    static void collectSites(byte[] data, List<Site> sites) {
        new ClassReader(data).accept(new ClassVisitor(Opcodes.ASM9) {
            @Override public MethodVisitor visitMethod(int acc, final String mname, final String mdesc, String sig, String[] ex) {
                return new MethodVisitor(Opcodes.ASM9) {
                    Object lastLdc = null;
                    boolean lastWasLdc = false;
                    String remembered = null;
                    final String cn = new ClassReader(data).getClassName();
                    void reset() { lastWasLdc = false; }
                    @Override public void visitLdcInsn(Object cst) {
                        lastLdc = cst;
                        lastWasLdc = cst instanceof String;
                        if (cst instanceof String) remembered = (String) cst;
                    }
                    @Override public void visitIntInsn(int op, int o) { reset(); }
                    @Override public void visitVarInsn(int op, int v) { reset(); }
                    @Override public void visitTypeInsn(int op, String t) { reset(); }
                    @Override public void visitJumpInsn(int op, Label l) { reset(); }
                    @Override public void visitInsn(int op) { reset(); }
                    @Override public void visitFieldInsn(int op, String o, String n, String d) { reset(); }
                    @Override public void visitIincInsn(int v, int i) { reset(); }
                    @Override public void visitTableSwitchInsn(int a, int b, Label d, Label... ls) { reset(); }
                    @Override public void visitLookupSwitchInsn(Label d, int[] k, Label[] l) { reset(); }
                    @Override public void visitMultiANewArrayInsn(String d, int dim) { reset(); }
                    @Override public void visitMethodInsn(int op, String owner, String name2, String d, boolean itf) {
                        if (op == Opcodes.INVOKESTATIC && DEC_NAME.equals(name2) && DEC_DESC.equals(d)) {
                            String arg = lastWasLdc ? (String) lastLdc : remembered;
                            if (arg != null) {
                                Site s = new Site();
                                s.caller = cn; s.method = mname; s.methodDesc = mdesc;
                                s.enc = arg; s.owner = owner;
                                sites.add(s);
                                remembered = null;
                            }
                        }
                        reset();
                    }
                };
            }
        }, ClassReader.SKIP_FRAMES);
    }

    static byte[] rewrite(byte[] data, final Map<String, String> plainByKey) {
        final String cn = new ClassReader(data).getClassName();
        ClassWriter cw = new ClassWriter(0);
        ClassVisitor cv = new ClassVisitor(Opcodes.ASM9, cw) {
            @Override public MethodVisitor visitMethod(int acc, final String mname, String mdesc, String sig, String[] ex) {
                MethodVisitor mv = super.visitMethod(acc, mname, mdesc, sig, ex);
                return new MethodVisitor(Opcodes.ASM9, mv) {
                    Object pending = null;   // buffered LDC constant
                    boolean pendingIsString = false;

                    void flush() {
                        if (pending != null) { mv.visitLdcInsn(pending); pending = null; }
                        else if (pendingIsString) { pendingIsString = false; }
                    }

                    @Override public void visitLdcInsn(Object cst) {
                        flush();
                        pending = cst;
                        pendingIsString = cst instanceof String;
                    }
                    @Override public void visitIntInsn(int o, int v) { flush(); mv.visitIntInsn(o, v); }
                    @Override public void visitVarInsn(int o, int v) { flush(); mv.visitVarInsn(o, v); }
                    @Override public void visitTypeInsn(int o, String t) { flush(); mv.visitTypeInsn(o, t); }
                    @Override public void visitJumpInsn(int o, Label l) { flush(); mv.visitJumpInsn(o, l); }
                    @Override public void visitInsn(int o) { flush(); mv.visitInsn(o); }
                    @Override public void visitFieldInsn(int o, String ow, String n, String d) { flush(); mv.visitFieldInsn(o, ow, n, d); }
                    @Override public void visitIincInsn(int v, int i) { flush(); mv.visitIincInsn(v, i); }
                    @Override public void visitTableSwitchInsn(int a, int b, Label d, Label... ls) { flush(); mv.visitTableSwitchInsn(a, b, d, ls); }
                    @Override public void visitLookupSwitchInsn(Label d, int[] k, Label[] l) { flush(); mv.visitLookupSwitchInsn(d, k, l); }
                    @Override public void visitMultiANewArrayInsn(String d, int dim) { flush(); mv.visitMultiANewArrayInsn(d, dim); }
                    @Override public void visitLabel(Label l) { flush(); mv.visitLabel(l); }
                    @Override public void visitTryCatchBlock(Label a, Label b, Label c, String t) { flush(); mv.visitTryCatchBlock(a, b, c, t); }
                    @Override public void visitLocalVariable(String n, String d, String s, Label a, Label b, int i) { flush(); mv.visitLocalVariable(n, d, s, a, b, i); }
                    @Override public void visitLineNumber(int line, Label start) { flush(); mv.visitLineNumber(line, start); }
                    @Override public void visitFrame(int t, int nl, Object[] l, int ns, Object[] st) { flush(); mv.visitFrame(t, nl, l, ns, st); }
                    @Override public void visitMaxs(int mx, int ml) { flush(); mv.visitMaxs(mx, ml); }

                    @Override public void visitMethodInsn(int op, String owner, String name2, String d, boolean itf) {
                        if (op == Opcodes.INVOKESTATIC && DEC_NAME.equals(name2) && DEC_DESC.equals(d)
                                && pending != null && pendingIsString) {
                            String plain = plainByKey.get(cn + "\u0000" + mname + "\u0000" + (String) pending);
                            if (plain != null) {
                                mv.visitLdcInsn(plain);
                                pending = null;
                                return;
                            }
                        }
                        flush();
                        mv.visitMethodInsn(op, owner, name2, d, itf);
                    }
                };
            }
        };
        new ClassReader(data).accept(cv, 0);
        byte[] result = cw.toByteArray();
        // validate: the rewritten class must still be parseable
        new ClassReader(result).accept(new ClassVisitor(Opcodes.ASM9) { }, ClassReader.SKIP_CODE | ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
        return result;
    }

    static String esc(String s) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (ch == '"') sb.append("\\\"");
            else if (ch == '\\') sb.append("\\\\");
            else if (ch == '\n') sb.append("\\n");
            else if (ch == '\r') sb.append("\\r");
            else if (ch == '\t') sb.append("\\t");
            else if (ch < 0x20 || ch > 0x7e) sb.append(String.format("\\u%04x", (int) ch));
            else sb.append(ch);
        }
        return sb.toString();
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








