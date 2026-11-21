import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.concurrent.*;

/**
 * Instance-method differential fuzz (separate driver so DiffTest stays small).
 * Constructs objects of both jars and compares every simple-signature getter.
 */
public final class InstDiff {

    static final String[] ARGS_STR = {"", "abc", "a<b>c", "[local]x", "<#>1", "1,2,3"};
    static final int[] ARGS_INT = {0, 1, -1, 7};
    static int compared, equal, problems;

    public static void main(String[] a) throws Exception {
        File origJar = new File(a[0]);
        File newJar = new File(a[1]);
        File sdk = new File(a[2]);
        ClassLoader orig = loader(origJar, sdk, a);
        ClassLoader rebuilt = loader(newJar, sdk, a);
        java.util.Map<String, String> map = classMap();

        java.util.List<String> cls = new java.util.ArrayList<String>();
        java.util.zip.ZipFile z = new java.util.zip.ZipFile(newJar);
        for (java.util.Enumeration<? extends java.util.zip.ZipEntry> en = z.entries(); en.hasMoreElements(); ) {
            String n = en.nextElement().getName();
            if (n.endsWith(".class") && n.startsWith("lk/")) cls.add(n.substring(0, n.length() - 6).replace('/', '.'));
        }
        z.close();

        for (String newName : cls) {
            String origName = map.containsKey(newName) ? map.get(newName) : newName;
            Class<?> o, b;
            try { o = Class.forName(origName, false, orig); } catch (Throwable t) { continue; }
            try { b = Class.forName(newName, false, rebuilt); } catch (Throwable t) { continue; }
            Object oi = instantiate(o), bi = instantiate(b);
            if (oi == null || bi == null) continue;
            for (Method om : o.getDeclaredMethods()) {
                if (Modifier.isStatic(om.getModifiers()) || Modifier.isPrivate(om.getModifiers())) continue;
                if (om.getReturnType() == void.class) continue;
                if (!simple(om.getParameterTypes())) continue;
                Method bm = match(b, om);
                if (bm == null) continue;
                int n = 0;
                for (Object[] args : tuples(om.getParameterTypes())) {
                    if (n++ > 3) break;
                    Object x = call(om, oi, args), y = call(bm, bi, args);
                    compared++;
                    String sx = desc(x), sy = desc(y);
                    if (sx.equals(sy)) equal++;
                    else {
                        problems++;
                        System.out.println("  !! " + newName + "#" + bm.getName() + "(" + show(args) + ")");
                        System.out.println("        original: " + sx);
                        System.out.println("        rebuilt : " + sy);
                    }
                }
            }
        }
        System.out.println("instance compared=" + compared + " equal=" + equal + " problems=" + problems);
    }

    static ClassLoader loader(File jar, File sdk, String[] a) throws Exception {
        java.util.List<URL> all = new java.util.ArrayList<URL>();
        all.add(jar.toURI().toURL());
        all.add(sdk.toURI().toURL());
        for (int i = 3; i < a.length; i++) all.add(new File(a[i]).toURI().toURL());
        return new URLClassLoader(all.toArray(new URL[0]), InstDiff.class.getClassLoader());
    }

    static Object call(Method m, Object target, Object[] args) {
        try {
            m.setAccessible(true);
            return with(m, target, args);
        } catch (Throwable t) {
            return "throw " + root(t).getClass().getName();
        }
    }

    static Object with(Method m, Object target, Object[] args) throws Throwable {
        try { return m.invoke(target, args); }
        catch (InvocationTargetException e) { return "throw " + e.getTargetException().getClass().getName(); }
    }

    static Throwable root(Throwable t) {
        return t instanceof InvocationTargetException ? ((InvocationTargetException) t).getTargetException() : t;
    }

    static String desc(Object o) {
        if (o == null) return "null";
        if (o instanceof Throwable) return "throw " + ((Throwable) o).getClass().getName();
        if (o instanceof java.util.Collection) {
            StringBuilder sb = new StringBuilder("[");
            for (Object e : (java.util.Collection<?>) o) sb.append(desc(e)).append(',');
            return sb.append(']').toString();
        }
        if (o instanceof java.util.Map) {
            java.util.List<String> parts = new java.util.ArrayList<String>();
            for (java.util.Map.Entry<?, ?> e : ((java.util.Map<?, ?>) o).entrySet()) parts.add(desc(e.getKey()) + "=" + desc(e.getValue()));
            java.util.Collections.sort(parts);
            return "{" + String.join(",", parts) + "}";
        }
        return o.getClass().getSimpleName() + ":" + o;
    }

    static boolean simple(Class<?>[] p) {
        for (Class<?> c : p) {
            if (c == String.class || c == int.class || c == long.class || c == double.class
                    || c == float.class || c == boolean.class || c == char.class) continue;
            return false;
        }
        return p.length <= 2;
    }

    static java.util.List<Object[]> tuples(Class<?>[] p) {
        java.util.List<Object[]> out = new java.util.ArrayList<Object[]>();
        for (int v = 0; v < 4; v++) {
            Object[] args = new Object[p.length];
            for (int i = 0; i < p.length; i++) {
                Class<?> c = p[i];
                if (c == String.class) args[i] = ARGS_STR[v % ARGS_STR.length];
                else if (c == int.class) args[i] = ARGS_INT[v % ARGS_INT.length];
                else if (c == long.class) args[i] = (long) ARGS_INT[v % ARGS_INT.length];
                else if (c == double.class) args[i] = (double) ARGS_INT[v % ARGS_INT.length];
                else if (c == float.class) args[i] = (float) ARGS_INT[v % ARGS_INT.length];
                else if (c == boolean.class) args[i] = v % 2 == 0;
                else if (c == char.class) args[i] = 'a';
            }
            out.add(args);
        }
        return out;
    }

    static String show(Object[] a) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < a.length; i++) {
            if (i > 0) sb.append(',');
            sb.append(a[i] instanceof String ? "\"" + a[i] + "\"" : String.valueOf(a[i]));
        }
        return sb.toString();
    }

    static String sig(Class<?>[] p) {
        StringBuilder sb = new StringBuilder("(");
        for (Class<?> c : p) {
            if (c == String.class) sb.append("Ljava/lang/String;");
            else if (c == int.class) sb.append('I');
            else if (c == long.class) sb.append('J');
            else if (c == double.class) sb.append('D');
            else if (c == float.class) sb.append('F');
            else if (c == boolean.class) sb.append('Z');
            else if (c == char.class) sb.append('C');
        }
        return sb.append(')').toString();
    }

    static Method match(Class<?> c, Method om) {
        String want = sig(om.getParameterTypes()) + ret(om.getReturnType());
        Method only = null;
        int hits = 0;
        for (Method m : c.getDeclaredMethods()) {
            if (Modifier.isStatic(m.getModifiers())) continue;
            if (!(sig(m.getParameterTypes()) + ret(m.getReturnType())).equals(want)) continue;
            only = m;
            hits++;
        }
        return hits == 1 ? only : null;   // ambiguous pairing is skipped
    }

    static String ret(Class<?> r) {
        if (r == void.class) return "V";
        if (r == String.class) return "Ljava/lang/String;";
        if (r == int.class) return "I";
        if (r == long.class) return "J";
        if (r == double.class) return "D";
        if (r == float.class) return "F";
        if (r == boolean.class) return "Z";
        if (r == char.class) return "C";
        return "L" + r.getName() + ";";
    }

    static Object instantiate(Class<?> c) {
        try {
            Constructor<?> k = c.getDeclaredConstructor();
            k.setAccessible(true);
            return k.newInstance();
        } catch (Throwable t) { }
        try {
            Constructor<?> k = c.getDeclaredConstructor(String.class);
            k.setAccessible(true);
            return k.newInstance("a<b>");
        } catch (Throwable t) { }
        return null;
    }

static java.util.Map<String, String> classMap() {
        // final class name -> original (obfuscated) internal name
        java.util.Map<String, String> origOfObf = new java.util.HashMap<String, String>();
        java.util.Map<String, String> finalOfObf = new java.util.HashMap<String, String>();
        try {
            for (String line : java.nio.file.Files.readAllLines(new File("D:/deobf/vexview/work/classmap2.txt").toPath(), java.nio.charset.StandardCharsets.UTF_8)) {
                String[] p = line.trim().split("\\s+");
                if (p.length == 3 && "CLASS".equals(p[0])) origOfObf.put(p[2], p[1]);
            }
            for (String line : java.nio.file.Files.readAllLines(new File("D:/deobf/vexview/work/deobf.map").toPath(), java.nio.charset.StandardCharsets.UTF_8)) {
                String[] p = line.trim().split("\\s+");
                if (p.length == 3 && "CLASS".equals(p[0])) finalOfObf.put(p[1], p[2]);
            }
        } catch (Throwable t) { }
        java.util.Map<String, String> map = new java.util.HashMap<String, String>();
        for (java.util.Map.Entry<String, String> e : finalOfObf.entrySet()) {
            String orig = origOfObf.get(e.getKey());
            if (orig != null) map.put(e.getValue().replace('/', '.'), orig.replace('/', '.'));
        }
        return map;
    }
}



