import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.tree.analysis.*;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.zip.*;

/**
 * Rewrites methods so that a JVM local slot never carries two different types.
 *
 * The obfuscator reuses one slot for values of unrelated types (a parameter slot
 * later holding a byte[], junk stack juggling in between). Every decompiler merges
 * those into a single Java variable and emits uncompilable or plain wrong code.
 * This pass gives each (slot, type-run) its own slot and recomputes stack map frames.
 */
public final class SplitSlots {

    static final Map<String, String[]> HIERARCHY = new HashMap<String, String[]>();
    static final Map<String, String> SUPER_CACHE = new HashMap<String, String>();

    static void loadHierarchy(File f) {
        try {
            ZipInputStream z = new ZipInputStream(new BufferedInputStream(new FileInputStream(f)));
            ZipEntry e;
            while ((e = z.getNextEntry()) != null) {
                if (!e.getName().endsWith(".class")) continue;
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                byte[] buf = new byte[8192];
                int r;
                while ((r = z.read(buf)) > 0) bo.write(buf, 0, r);
                try {
                    ClassReader cr = new ClassReader(bo.toByteArray());
                    String sup = cr.getSuperName();
                    String[] itf = cr.getInterfaces();
                    int n = (sup == null ? 0 : 1) + (itf == null ? 0 : itf.length);
                    String[] all = new String[n];
                    int k = 0;
                    if (sup != null) all[k++] = sup;
                    if (itf != null) for (String s : itf) all[k++] = s;
                    HIERARCHY.put(cr.getClassName(), all);
                } catch (Throwable t) { }
            }
            z.close();
        } catch (Throwable t) { }
    }

    static String commonSuper(String a, String b) {
        if (a.equals(b)) return a;
        Set<String> chainA = new LinkedHashSet<String>();
        Deque<String> q = new ArrayDeque<String>();
        q.add(a);
        while (!q.isEmpty()) {
            String c = q.poll();
            if (c == null || !chainA.add(c)) continue;
            String[] s = HIERARCHY.get(c);
            if (s != null) for (String x : s) if (x != null) q.add(x);
            if (c.equals("java/lang/Object")) break;
        }
        q.add(b);
        Set<String> seen = new HashSet<String>();
        while (!q.isEmpty()) {
            String c = q.poll();
            if (c == null || !seen.add(c)) continue;
            if (chainA.contains(c)) return c;
            String[] s = HIERARCHY.get(c);
            if (s != null) for (String x : s) if (x != null) q.add(x);
        }
        return "java/lang/Object";
    }
    static final Map<String, Class<?>> CLASS_CACHE = new HashMap<String, Class<?>>();

    static final class Resolver extends URLClassLoader {
        Resolver(URL[] u) { super(u, SplitSlots.class.getClassLoader()); }
        @Override protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
            try { return super.loadClass(name, resolve); }
            catch (Throwable t) { return Object.class; }
        }
    }

    // ---------------------------------------------------------------- typed values
    static final class TV implements Value {
        final Type type;
        final int size;
        TV(Type t) { this.type = t; this.size = (t != null && (t.getSort() == Type.LONG || t.getSort() == Type.DOUBLE)) ? 2 : 1; }
        public int getSize() { return size; }
        @Override public String toString() { return String.valueOf(type); }
        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TV)) return false;
            TV other = (TV) o;
            return type == null ? other.type == null : type.equals(other.type);
        }
        @Override public int hashCode() { return type == null ? 0 : type.hashCode(); }
    }

    static final class TI extends Interpreter<TV> {
        TI() { super(Opcodes.ASM9); }

        @Override public TV newValue(Type t) {
            if (t == null) return new TV(null);
            if (t.getSort() == Type.VOID) return null;
            return new TV(t);
        }

        @Override public TV newOperation(AbstractInsnNode insn) throws AnalyzerException {
            switch (insn.getOpcode()) {
                case Opcodes.ACONST_NULL: return new TV(Type.getObjectType("null"));
                case Opcodes.ICONST_M1: case Opcodes.ICONST_0: case Opcodes.ICONST_1:
                case Opcodes.ICONST_2: case Opcodes.ICONST_3: case Opcodes.ICONST_4:
                case Opcodes.ICONST_5: case Opcodes.BIPUSH: case Opcodes.SIPUSH: return new TV(Type.INT_TYPE);
                case Opcodes.LCONST_0: case Opcodes.LCONST_1: return new TV(Type.LONG_TYPE);
                case Opcodes.FCONST_0: case Opcodes.FCONST_1: case Opcodes.FCONST_2: return new TV(Type.FLOAT_TYPE);
                case Opcodes.DCONST_0: case Opcodes.DCONST_1: return new TV(Type.DOUBLE_TYPE);
                case Opcodes.LDC: {
                    Object cst = ((LdcInsnNode) insn).cst;
                    if (cst instanceof Type) {
                        Type t = (Type) cst;
                        return new TV(t.getSort() == Type.OBJECT ? Type.getObjectType("java/lang/Class") : t);
                    }
                    if (cst instanceof Handle) return new TV(Type.getObjectType("java/lang/invoke/MethodHandle"));
                    return new TV(Type.getType(cst.getClass() == Integer.class ? "I"
                            : cst.getClass() == Long.class ? "J"
                            : cst.getClass() == Float.class ? "F"
                            : cst.getClass() == Double.class ? "D" : "Ljava/lang/String;"));
                }
                case Opcodes.GETSTATIC: return new TV(Type.getType(((FieldInsnNode) insn).desc));
                case Opcodes.NEW: return new TV(Type.getObjectType(((TypeInsnNode) insn).desc));
                case Opcodes.JSR: return new TV(Type.getObjectType("java/lang/Object"));
                case Opcodes.NEWARRAY: {
                    int t = ((IntInsnNode) insn).operand;
                    String d = t == Opcodes.T_BOOLEAN ? "[Z" : t == Opcodes.T_CHAR ? "[C" : t == Opcodes.T_FLOAT ? "[F"
                            : t == Opcodes.T_DOUBLE ? "[D" : t == Opcodes.T_BYTE ? "[B" : t == Opcodes.T_SHORT ? "[S"
                            : t == Opcodes.T_INT ? "[I" : t == Opcodes.T_LONG ? "[J" : null;
                    return d == null ? new TV(null) : new TV(Type.getType(d));
                }
                default: return new TV(null);
            }
        }

        @Override public TV copyOperation(AbstractInsnNode insn, TV value) { return value; }

        private TV ret(AbstractInsnNode insn) {
            if (insn instanceof MethodInsnNode) return new TV(Type.getReturnType(((MethodInsnNode) insn).desc));
            if (insn.getOpcode() == Opcodes.INVOKEDYNAMIC) return new TV(Type.getReturnType(((InvokeDynamicInsnNode) insn).desc));
            return null;
        }

        @Override public TV unaryOperation(AbstractInsnNode insn, TV value) throws AnalyzerException {
            switch (insn.getOpcode()) {
                case Opcodes.GETFIELD: return new TV(Type.getType(((FieldInsnNode) insn).desc));
                case Opcodes.CHECKCAST: {
                    String d = ((TypeInsnNode) insn).desc;
                    return new TV(d.startsWith("[") ? Type.getType(d) : Type.getObjectType(d));
                }
                case Opcodes.INSTANCEOF: return new TV(Type.INT_TYPE);
                case Opcodes.ARRAYLENGTH: return new TV(Type.INT_TYPE);
                case Opcodes.IALOAD: case Opcodes.BALOAD: case Opcodes.CALOAD: case Opcodes.SALOAD: return new TV(Type.INT_TYPE);
                case Opcodes.LALOAD: return new TV(Type.LONG_TYPE);
                case Opcodes.FALOAD: return new TV(Type.FLOAT_TYPE);
                case Opcodes.DALOAD: return new TV(Type.DOUBLE_TYPE);
                case Opcodes.AALOAD: return new TV(Type.getObjectType("java/lang/Object"));
                case Opcodes.I2L: return new TV(Type.LONG_TYPE);
                case Opcodes.I2F: return new TV(Type.FLOAT_TYPE);
                case Opcodes.I2D: return new TV(Type.DOUBLE_TYPE);
                case Opcodes.L2I: case Opcodes.F2I: case Opcodes.D2I: return new TV(Type.INT_TYPE);
                case Opcodes.L2F: case Opcodes.D2F: return new TV(Type.FLOAT_TYPE);
                case Opcodes.L2D: case Opcodes.F2D: return new TV(Type.DOUBLE_TYPE);
                case Opcodes.I2B: case Opcodes.I2C: case Opcodes.I2S: return new TV(Type.INT_TYPE);
                case Opcodes.INEG: return new TV(Type.INT_TYPE);
                case Opcodes.LNEG: return new TV(Type.LONG_TYPE);
                case Opcodes.FNEG: return new TV(Type.FLOAT_TYPE);
                case Opcodes.DNEG: return new TV(Type.DOUBLE_TYPE);
                case Opcodes.IFEQ: case Opcodes.IFNE: case Opcodes.IFLT: case Opcodes.IFGE:
                case Opcodes.IFGT: case Opcodes.IFLE: case Opcodes.TABLESWITCH:
                case Opcodes.LOOKUPSWITCH: case Opcodes.MONITORENTER: case Opcodes.MONITOREXIT:
                case Opcodes.PUTSTATIC: case Opcodes.PUTFIELD: case Opcodes.POP: case Opcodes.ARETURN:
                case Opcodes.ATHROW: case Opcodes.IRETURN: case Opcodes.LRETURN:
                case Opcodes.FRETURN: case Opcodes.DRETURN: return null;
                case Opcodes.ANEWARRAY: {
                    String d = ((TypeInsnNode) insn).desc;
                    return new TV(Type.getType(d.startsWith("[") ? "[" + d : "[L" + d + ";"));
                }
                case Opcodes.IINC: case Opcodes.IFNULL: case Opcodes.IFNONNULL: return null;
                default: {
                    TV r = ret(insn);
                    if (r != null) return r;
                    return new TV(null);
                }
            }
        }

        @Override public TV binaryOperation(AbstractInsnNode insn, TV v1, TV v2) throws AnalyzerException {
            switch (insn.getOpcode()) {
                case Opcodes.LADD: case Opcodes.LSUB: case Opcodes.LMUL: case Opcodes.LDIV:
                case Opcodes.LREM: case Opcodes.LSHL: case Opcodes.LSHR: case Opcodes.LUSHR:
                case Opcodes.LAND: case Opcodes.LOR: case Opcodes.LXOR: return new TV(Type.LONG_TYPE);
                case Opcodes.FADD: case Opcodes.FSUB: case Opcodes.FMUL: case Opcodes.FDIV:
                case Opcodes.FREM: return new TV(Type.FLOAT_TYPE);
                case Opcodes.DADD: case Opcodes.DSUB: case Opcodes.DMUL: case Opcodes.DDIV:
                case Opcodes.DREM: return new TV(Type.DOUBLE_TYPE);
                case Opcodes.IADD: case Opcodes.ISUB: case Opcodes.IMUL: case Opcodes.IDIV:
                case Opcodes.IREM: case Opcodes.ISHL: case Opcodes.ISHR: case Opcodes.IUSHR:
                case Opcodes.IAND: case Opcodes.IOR: case Opcodes.IXOR: return new TV(Type.INT_TYPE);
                case Opcodes.LCMP: case Opcodes.FCMPL: case Opcodes.FCMPG:
                case Opcodes.DCMPL: case Opcodes.DCMPG: return new TV(Type.INT_TYPE);
                case Opcodes.IF_ICMPEQ: case Opcodes.IF_ICMPNE: case Opcodes.IF_ICMPLT:
                case Opcodes.IF_ICMPGE: case Opcodes.IF_ICMPGT: case Opcodes.IF_ICMPLE:
                case Opcodes.IF_ACMPEQ: case Opcodes.IF_ACMPNE: case Opcodes.PUTFIELD:
                case Opcodes.PUTSTATIC: return null;
                default: {
                    TV r = ret(insn);
                    if (r != null) return r;
                    return new TV(null);
                }
            }
        }

        @Override public TV ternaryOperation(AbstractInsnNode insn, TV v1, TV v2, TV v3) {
            TV r = ret(insn);
            return r;
        }

        @Override public TV naryOperation(AbstractInsnNode insn, List<? extends TV> values) {
            if (insn.getOpcode() == Opcodes.MULTIANEWARRAY) {
                return new TV(Type.getType(((MultiANewArrayInsnNode) insn).desc));
            }
            return ret(insn);
        }

        @Override public void returnOperation(AbstractInsnNode insn, TV value, TV expected) { }

        @Override public TV merge(TV v1, TV v2) {
            if (v1 == null) return v2;
            if (v2 == null) return v1;
            if (v1.type == null || v2.type == null) return new TV(null);
            if (v1.type.equals(v2.type)) return v1;
            if (v1.type.getSort() == Type.ARRAY || v2.type.getSort() == Type.ARRAY) {
                if (v1.type.getDescriptor().equals(v2.type.getDescriptor())) return v1;
                return new TV(Type.getObjectType("java/lang/Object"));
            }
            if (v1.type.getSort() == Type.OBJECT && v2.type.getSort() == Type.OBJECT) {
                return new TV(Type.getObjectType("java/lang/Object"));
            }
            return new TV(null);
        }
    }

    // ---------------------------------------------------------------- main
    public static void main(String[] a) throws Exception {
        File in = new File(a[0]);
        File out = new File(a[1]);
        URL[] cp = new URL[a.length - 2];
        for (int i = 2; i < a.length; i++) cp[i - 2] = new File(a[i]).toURI().toURL();
        final Resolver resolver = new Resolver(cp);
        loadHierarchy(in);
        for (URL u : cp) { try { loadHierarchy(new File(u.toURI())); } catch (Throwable t) { } }

        Map<String, byte[]> entries = readJar(in);
        Map<String, byte[]> outEntries = new LinkedHashMap<String, byte[]>();
        int changed = 0, kept = 0, failed = 0, methods = 0;

        for (Map.Entry<String, byte[]> e : entries.entrySet()) {
            if (!e.getKey().endsWith(".class")) { outEntries.put(e.getKey(), e.getValue()); continue; }
            try {
                Result r = processClass(e.getValue(), resolver);
                outEntries.put(e.getKey(), r.data);
                if (r.changedClasses > 0) { changed++; methods += r.changedMethods; } else kept++;
            } catch (Throwable t) {
                failed++;
                outEntries.put(e.getKey(), e.getValue());
            }
        }
        writeJar(out, outEntries);
        System.out.println("split: changedClasses=" + changed + " changedMethods=" + methods + " unchanged=" + kept + " failed=" + failed);
    }

    static final class Result {
        byte[] data;
        int changedClasses, changedMethods;
    }

    static Result processClass(byte[] data, final Resolver resolver) {
        Result res = new Result();
        res.data = data;
        final ClassNode cn = new ClassNode();
        new ClassReader(data).accept(cn, ClassReader.EXPAND_FRAMES);

        int changedMethods = 0;
        for (Object o : cn.methods) {
            MethodNode mn = (MethodNode) o;
            if (mn.instructions.size() == 0) continue;
            try {
                if (splitMethod(cn.name, mn)) changedMethods++;
            } catch (Throwable t) {
                if (System.getProperty("dbg") != null) System.err.println("skip " + cn.name + "." + mn.name + " : " + t);
            }
        }
        if (changedMethods == 0) return res;

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS) {
            @Override protected String getCommonSuperClass(String a, String b) {
                String key = a + "|" + b;
                String cached = SUPER_CACHE.get(key);
                if (cached != null) return cached;
                String r = commonSuper(a, b);
                SUPER_CACHE.put(key, r);
                return r;
            }
        };
        try {
            cn.accept(cw);
            byte[] out = cw.toByteArray();
            res.data = out;
            res.changedClasses = 1;
            res.changedMethods = changedMethods;
            return res;
        } catch (Throwable t) {
            System.err.println("rejected " + cn.name + " : " + t.getMessage());
            return res;
        }
    }

    static boolean splitMethod(String owner, MethodNode mn) throws AnalyzerException {
        Analyzer<TV> analyzer = new Analyzer<TV>(new TI());
        analyzer.analyze(owner, mn);
        Frame<TV>[] frames = analyzer.getFrames();

        AbstractInsnNode[] insns = mn.instructions.toArray();
        Map<Integer, Integer> slotMap = new HashMap<Integer, Integer>();
        Map<Integer, String> slotDesc = new HashMap<Integer, String>();
        int nextLocal = mn.maxLocals;

        int idx = 0;
        if ((mn.access & Opcodes.ACC_STATIC) == 0) {
            slotMap.put(0, 0); slotDesc.put(0, "L" + owner + ";");
            idx = 1;
        }
        for (Type t : Type.getArgumentTypes(mn.desc)) {
            slotMap.put(idx, idx);
            slotDesc.put(idx, t.getDescriptor());
            idx += t.getSize();
        }

        boolean modified = false;
        for (int i = 0; i < insns.length; i++) {
            AbstractInsnNode insn = insns[i];
            if (insn instanceof VarInsnNode) {
                VarInsnNode vin = (VarInsnNode) insn;
                int op = insn.getOpcode();
                boolean store = (op >= Opcodes.ISTORE && op <= Opcodes.ASTORE);
                Integer cur = slotMap.get(vin.var);
                if (!store) {
                    if (cur != null && cur.intValue() != vin.var) { vin.var = cur; modified = true; }
                    continue;
                }
                String desc = null;
                Frame<TV> frame = (i < frames.length) ? frames[i] : null;
                if (frame != null && frame.getStackSize() > 0) {
                    TV v = frame.getStack(frame.getStackSize() - 1);
                    if (v != null && v.type != null) desc = v.type.getDescriptor();
                }
                String curDesc = cur == null ? null : slotDesc.get(cur);
                boolean split;
                if (cur == null) split = true;
                else if (desc == null) split = false;
                else if (desc.equals("Ljava/lang/Object;null") || "Lnull;".equals(desc)) split = false;
                else if (curDesc == null) split = false;
                else split = !desc.equals(curDesc) && !"Lnull;".equals(curDesc);
                if (split) {
                    int size = (desc != null && (desc.equals("J") || desc.equals("D"))) ? 2 : 1;
                    int fresh = nextLocal;
                    nextLocal += size;
                    slotMap.put(vin.var, fresh);
                    if (desc != null) slotDesc.put(fresh, desc);
                    vin.var = fresh;
                } else if (cur.intValue() != vin.var) {
                    vin.var = cur;
                }
                modified = true;
            } else if (insn instanceof IincInsnNode) {
                IincInsnNode in = (IincInsnNode) insn;
                Integer cur = slotMap.get(in.var);
                if (cur != null && cur.intValue() != in.var) { in.var = cur; modified = true; }
            }
        }
        if (modified) {
            mn.maxLocals = Math.max(mn.maxLocals, nextLocal);
            mn.localVariables = null;
            mn.visibleLocalVariableAnnotations = null;
            mn.invisibleLocalVariableAnnotations = null;
        }
        return modified;
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







