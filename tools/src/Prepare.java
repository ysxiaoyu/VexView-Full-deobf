import org.objectweb.asm.*;

import java.io.*;
import java.util.*;
import java.util.zip.*;

/**
 * Final normalisation of the remapped jar:
 *   - drops the (lying) debug tables, optionally keeping line numbers out of the way
 *   - rebuilds InnerClasses attributes so that every "Outer$Inner" class is
 *     described consistently (the obfuscator left them half-broken, which makes
 *     decompilers treat real classes as anonymous ones)
 */
public final class Prepare {

    static final int ACC_MASK = Opcodes.ACC_PUBLIC | Opcodes.ACC_PRIVATE | Opcodes.ACC_PROTECTED
            | Opcodes.ACC_STATIC | Opcodes.ACC_FINAL | Opcodes.ACC_INTERFACE | Opcodes.ACC_ABSTRACT
            | Opcodes.ACC_SYNTHETIC | Opcodes.ACC_ANNOTATION | Opcodes.ACC_ENUM;

    public static void main(String[] a) throws Exception {
        Map<String, byte[]> entries = readJar(new File(a[0]));
        boolean stripDebug = Boolean.getBoolean("stripDebug");
        boolean fixInner = Boolean.parseBoolean(System.getProperty("fixInner", "false"));

        // class inventory
        final Set<String> all = new HashSet<String>();
        final Map<String, Integer> access = new HashMap<String, Integer>();
        for (Map.Entry<String, byte[]> e : entries.entrySet()) {
            if (!e.getKey().endsWith(".class")) continue;
            ClassReader cr = new ClassReader(e.getValue());
            all.add(cr.getClassName());
            access.put(cr.getClassName(), cr.getAccess() & ACC_MASK);
        }
        final Map<String, List<String>> children = new HashMap<String, List<String>>();
        for (String n : all) {
            int i = n.lastIndexOf('$');
            if (i <= 0) continue;
            String parent = n.substring(0, i);
            if (!all.contains(parent)) continue;
            children.computeIfAbsent(parent, k -> new ArrayList<String>()).add(n);
        }
        System.out.println("classes: " + all.size() + " with children: " + children.size());

        Map<String, byte[]> out = new LinkedHashMap<String, byte[]>();
        for (Map.Entry<String, byte[]> e : entries.entrySet()) {
            if (!e.getKey().endsWith(".class")) { out.put(e.getKey(), e.getValue()); continue; }
            ClassReader cr = new ClassReader(e.getValue());
            ClassWriter cw = new ClassWriter(0);
            ClassVisitor cv = new ClassVisitor(Opcodes.ASM9, cw) {
                String self;
                @Override public void visit(int v, int acc, String name, String sig, String sup, String[] itf) {
                    self = name;
                    super.visit(v, acc, name, sig, sup, itf);
                    if (!fixInner) return;
                    int i = name.lastIndexOf('$');
                    if (i > 0 && all.contains(name.substring(0, i))) {
                        super.visitInnerClass(name, name.substring(0, i), name.substring(i + 1),
                                access.getOrDefault(name, 0) | Opcodes.ACC_STATIC);
                    }
                    List<String> kids = children.get(name);
                    if (kids != null) {
                        Collections.sort(kids);
                        for (String k : kids) {
                            String simple = k.substring(name.length() + 1);
                            if (!simple.matches("[A-Za-z_$][A-Za-z0-9_$]*")) continue;
                            super.visitInnerClass(k, name, simple, access.getOrDefault(k, 0) | Opcodes.ACC_STATIC);
                        }
                    }
                }
            };
            cr.accept(cv, stripDebug ? ClassReader.SKIP_DEBUG : 0);
            out.put(e.getKey(), cw.toByteArray());
        }
        writeJar(new File(a[1]), out);
        System.out.println("prepared " + out.size() + " entries");
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

