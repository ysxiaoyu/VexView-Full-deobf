import org.objectweb.asm.*;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.Type;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.zip.*;

/**
 * Link-level verification.
 *
 * Walks every member reference in a jar and resolves it against a real class path
 * (the rebuilt Minecraft/Forge SDK). A reference counts as resolved when the owner
 * class loads and a field/method with that name and descriptor exists on the owner
 * or on one of its super types.
 *
 * With -Dsrg2mcp=<mcpStableDir> the referenced member names are first translated
 * from SRG (func_/field_) to MCP names, so the original production jar can be
 * checked against the same MCP-named SDK.
 */
public final class LinkCheck {

    static final Map<String, String> SRG_METHODS = new HashMap<String, String>();
    static final Map<String, String> SRG_FIELDS = new HashMap<String, String>();

    public static void main(String[] a) throws Exception {
        File jar = new File(a[0]);
        File out = new File(a[1]);
        List<URL> cp = new ArrayList<URL>();
        cp.add(jar.toURI().toURL());
        for (int i = 2; i < a.length; i++) cp.add(new File(a[i]).toURI().toURL());
        URLClassLoader cl = new URLClassLoader(cp.toArray(new URL[0]), LinkCheck.class.getClassLoader());

        String srgDir = System.getProperty("srg2mcp");
        if (srgDir != null) {
            loadCsv(new File(srgDir, "methods.csv"), SRG_METHODS);
            loadCsv(new File(srgDir, "fields.csv"), SRG_FIELDS);
        }

        Map<String, byte[]> classes = readJar(jar);
        int mr = 0, mfail = 0, fr = 0, ffail = 0;
        List<String> failures = new ArrayList<String>();
        Set<String> checked = new HashSet<String>();

        for (Map.Entry<String, byte[]> e : classes.entrySet()) {
            if (!e.getKey().endsWith(".class")) continue;
            ClassNode cn = new ClassNode();
            new ClassReader(e.getValue()).accept(cn, ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES);
            Class<?> self;
            try {
                self = Class.forName(cn.name.replace('/', '.'), false, cl);
            } catch (Throwable t) {
                continue;
            }
            for (Object o : cn.methods) {
                MethodNode mn = (MethodNode) o;
                for (AbstractInsnNode insn : mn.instructions) {
                    if (insn instanceof MethodInsnNode) {
                        MethodInsnNode mi = (MethodInsnNode) insn;
                        String name = mi.name;
                        if (!SRG_METHODS.isEmpty() && SRG_METHODS.containsKey(name)) name = SRG_METHODS.get(name);
                        String key = "M " + mi.owner + "." + name + mi.desc;
                        if (!checked.add(key)) continue;
                        mr++;
                        if (!resolveMethod(cl, mi.owner, name, mi.desc)) {
                            mfail++;
                            if (failures.size() < 200) failures.add("UNRESOLVED " + key);
                        }
                    } else if (insn instanceof FieldInsnNode) {
                        FieldInsnNode fi = (FieldInsnNode) insn;
                        String name = fi.name;
                        if (!SRG_FIELDS.isEmpty() && SRG_FIELDS.containsKey(name)) name = SRG_FIELDS.get(name);
                        String key = "F " + fi.owner + "." + name + " " + fi.desc;
                        if (!checked.add(key)) continue;
                        fr++;
                        if (!resolveField(cl, fi.owner, name, fi.desc)) {
                            ffail++;
                            if (failures.size() < 200) failures.add("UNRESOLVED " + key);
                        }
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        sb.append("{\"methods\":").append(mr).append(",\"methodFailures\":").append(mfail)
          .append(",\"fields\":").append(fr).append(",\"fieldFailures\":").append(ffail)
          .append(",\"failures\":[");
        for (int i = 0; i < failures.size(); i++) {
            if (i > 0) sb.append(',');
            sb.append('"').append(failures.get(i).replace("\\", "\\\\").replace("\"", "\\\"")).append('"');
        }
        sb.append("]}\n");
        Files.write(out.toPath(), sb.toString().getBytes(StandardCharsets.UTF_8));
        System.out.println("methods=" + mr + " unresolved=" + mfail + "  fields=" + fr + " unresolved=" + ffail);
    }

    static boolean resolveMethod(ClassLoader cl, String owner, String name, String desc) {
        if (owner.startsWith("[")) return true;
        try {
            Class<?> c = Class.forName(owner.replace('/', '.'), false, cl);
            org.objectweb.asm.Type[] args = org.objectweb.asm.Type.getArgumentTypes(desc);
            Class<?>[] params = new Class<?>[args.length];
            for (int i = 0; i < args.length; i++) params[i] = typeOf(cl, args[i]);
            return findMethod(c, name, params) != null;
        } catch (Throwable t) {
            return false;
        }
    }

    static Object findMethod(Class<?> c, String name, Class<?>[] params) {
        if ("<clinit>".equals(name)) return Boolean.TRUE;
        Set<Class<?>> seen = new HashSet<Class<?>>();
        Deque<Class<?>> q = new ArrayDeque<Class<?>>();
        q.add(c);
        while (!q.isEmpty()) {
            Class<?> k = q.poll();
            if (k == null || !seen.add(k)) continue;
            try {
                if ("<init>".equals(name)) {
                    Constructor<?> ctor = k.getDeclaredConstructor(params);
                    if (ctor != null) return ctor;
                } else {
                    Method m = k.getDeclaredMethod(name, params);
                    if (m != null) return m;
                }
            } catch (NoSuchMethodException ignored) {
            } catch (Throwable ignored) {
            }
            if (k.getSuperclass() != null) q.add(k.getSuperclass());
            for (Class<?> i : k.getInterfaces()) q.add(i);
        }
        return null;
    }

    static boolean resolveField(ClassLoader cl, String owner, String name, String desc) {
        if (owner.startsWith("[")) return true;
        try {
            Class<?> c = Class.forName(owner.replace('/', '.'), false, cl);
            Class<?> want = typeOf(cl, org.objectweb.asm.Type.getType(desc));
            Set<Class<?>> seen = new HashSet<Class<?>>();
            Deque<Class<?>> q = new ArrayDeque<Class<?>>();
            q.add(c);
            while (!q.isEmpty()) {
                Class<?> k = q.poll();
                if (k == null || !seen.add(k)) continue;
                try {
                    Field f = k.getDeclaredField(name);
                    if (f.getType() == want) return true;
                } catch (NoSuchFieldException ignored) {
                } catch (Throwable ignored) {
                }
                if (k.getSuperclass() != null) q.add(k.getSuperclass());
                for (Class<?> i : k.getInterfaces()) q.add(i);
            }
            return false;
        } catch (Throwable t) {
            return false;
        }
    }

    static Class<?> typeOf(ClassLoader cl, org.objectweb.asm.Type t) throws ClassNotFoundException {
        switch (t.getSort()) {
            case org.objectweb.asm.Type.VOID: return void.class;
            case org.objectweb.asm.Type.BOOLEAN: return boolean.class;
            case org.objectweb.asm.Type.CHAR: return char.class;
            case org.objectweb.asm.Type.BYTE: return byte.class;
            case org.objectweb.asm.Type.SHORT: return short.class;
            case org.objectweb.asm.Type.INT: return int.class;
            case org.objectweb.asm.Type.FLOAT: return float.class;
            case org.objectweb.asm.Type.LONG: return long.class;
            case org.objectweb.asm.Type.DOUBLE: return double.class;
            case org.objectweb.asm.Type.ARRAY: return Class.forName(t.getDescriptor().replace('/', '.'), false, cl);
            default: return Class.forName(t.getClassName(), false, cl);
        }
    }

    static void loadCsv(File f, Map<String, String> into) throws IOException {
        for (String line : Files.readAllLines(f.toPath(), StandardCharsets.UTF_8)) {
            if (line.isEmpty() || line.startsWith("searge")) continue;
            String[] p = line.split(",", 3);
            if (p.length >= 2 && !p[1].isEmpty()) into.put(p[0], p[1]);
        }
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
}


