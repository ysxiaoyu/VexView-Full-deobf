import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;

/**
 * Transcript-based differential test for the bundled org.json library.
 *
 * Runs the same script (parse / put / opt / has / remove / write / XML / Cookie /
 * HTTP / CDL / JSONPointer / JSONTokener) against two isolated class loaders and
 * prints both transcripts so they can be diffed line by line.
 */
public final class JsonScript {

    static StringBuilder sb;

    public static void main(String[] a) throws Exception {
        File jar = new File(a[0]);
        List<URL> urls = new ArrayList<URL>();
        urls.add(jar.toURI().toURL());
        for (int i = 2; i < a.length; i++) urls.add(new File(a[i]).toURI().toURL());
        ClassLoader cl = new URLClassLoader(urls.toArray(new URL[0]), JsonScript.class.getClassLoader());
        sb = new StringBuilder();
        run(cl);
        File out = new File(a[1]);
        Files.write(out.toPath(), sb.toString().getBytes(StandardCharsets.UTF_8));
        System.out.println("transcript lines: " + sb.toString().split("\n").length);
    }

    static void log(String tag, Object v) {
        sb.append(tag).append(" = ").append(v).append('\n');
    }

    static Object newObj(ClassLoader cl, String s) throws Exception {
        return Class.forName("org.json.JSONObject", true, cl).getConstructor(String.class).newInstance(s);
    }

    static Object newArr(ClassLoader cl, String s) throws Exception {
        return Class.forName("org.json.JSONArray", true, cl).getConstructor(String.class).newInstance(s);
    }

    static Object call(Object target, String name, Class<?>[] t, Object... args) {
        try {
            Method m = target.getClass().getMethod(name, t);
            return m.invoke(target, args);
        } catch (InvocationTargetException e) {
            return "throw " + e.getTargetException().getClass().getName();
        } catch (Throwable t2) {
            return "err " + t2.getClass().getSimpleName();
        }
    }

    static Object callStatic(ClassLoader cl, String cls, String name, Class<?>[] t, Object... args) {
        try {
            Class<?> c = Class.forName(cls, true, cl);
            return c.getMethod(name, t).invoke(null, args);
        } catch (InvocationTargetException e) {
            return "throw " + e.getTargetException().getClass().getName();
        } catch (Throwable t2) {
            return "err " + t2.getClass().getSimpleName();
        }
    }

    static final String[] DOCS = {
        "{}",
        "{\"a\":1,\"b\":[1,2,3],\"c\":{\"d\":true,\"e\":null}}",
        "{\"s\":\"text\",\"n\":-0.5,\"big\":1234567890123,\"t\":false}",
        "[1,2.5,\"three\",false,null,{\"k\":\"v\"},[1,[2,[3]]]]",
        "{\"single\":{\"only\":1}}",
        "{\"unicode\":\"中文\u00a7r\",\"esc\":\"q\\\"w\\\\e\\n\"}",
        "{\"dup\":1,\"dup\":2}",
    };

    static void run(ClassLoader cl) throws Exception {
        for (String doc : DOCS) {
            boolean isArr = doc.startsWith("[");
            Object v;
            try {
                v = isArr ? newArr(cl, doc) : newObj(cl, doc);
            } catch (Throwable t) {
                Throwable e = t instanceof InvocationTargetException ? ((InvocationTargetException) t).getTargetException() : t;
                log("parse.throw " + doc, e.getClass().getName() + ": " + e.getMessage());
                continue;
            }
            log("parse.toString " + doc, call(v, "toString", new Class<?>[]{}));
            log("parse.toString(2) " + doc, call(v, "toString", new Class<?>[]{int.class}, 2));
            log("parse.length " + doc, call(v, "length", new Class<?>[]{}));
            if (!isArr) {
                log("parse.keys " + doc, call(v, "keys", new Class<?>[]{}));
                log("parse.names " + doc, call(v, "names", new Class<?>[]{}));
            }
            log("parse.similar " + doc, call(v, "similar", new Class<?>[]{Object.class}, v));
        }

        // mutation script
        Object o = newObj(cl, "{\"a\":1,\"b\":\"two\",\"c\":[true,false]}");
        log("put.new", call(o, "put", new Class<?>[]{String.class, Object.class}, "new", 42));
        log("put.chain", call(o, "put", new Class<?>[]{String.class, int.class}, "seven", 7));
        log("accumulate", call(o, "accumulate", new Class<?>[]{String.class, Object.class}, "c", "extra"));
        log("append", call(o, "append", new Class<?>[]{String.class, Object.class}, "arr", "x"));
        log("opt.missing", call(o, "opt", new Class<?>[]{String.class}, "nope"));
        log("optString", call(o, "optString", new Class<?>[]{String.class, String.class}, "nope", "dflt"));
        log("has", call(o, "has", new Class<?>[]{String.class}, "a"));
        log("isNull", call(o, "isNull", new Class<?>[]{String.class}, "a"));
        log("getInt", call(o, "getInt", new Class<?>[]{String.class}, "a"));
        log("getDouble", call(o, "getDouble", new Class<?>[]{String.class}, "seven"));
        log("getBoolean", call(o, "getBoolean", new Class<?>[]{String.class}, "a"));
        log("getString.bad", call(o, "getString", new Class<?>[]{String.class}, "a"));
        log("remove", call(o, "remove", new Class<?>[]{String.class}, "b"));
        log("toString.after", call(o, "toString", new Class<?>[]{}));
        log("keySet", call(o, "keySet", new Class<?>[]{}));
        log("toMap", call(o, "toMap", new Class<?>[]{}));
        log("toJSONArray", call(o, "toJSONArray", new Class<?>[]{Class.forName("org.json.JSONArray", true, cl)}, newArr(cl, "[\"p\"]")));
        log("hashCode.stable", call(newObj(cl, "{\"a\":1}"), "hashCode", new Class<?>[]{}).equals(call(newObj(cl, "{\"a\":1}"), "hashCode", new Class<?>[]{})));
        log("equals", call(newObj(cl, "{\"a\":1}"), "equals", new Class<?>[]{Object.class}, newObj(cl, "{\"a\":1}")));

        // arrays
        Object arr = newArr(cl, "[3,1,2]");
        log("arr.get(1)", call(arr, "get", new Class<?>[]{int.class}, 1));
        log("arr.put", call(arr, "put", new Class<?>[]{Object.class}, "z"));
        log("arr.remove", call(arr, "remove", new Class<?>[]{int.class}, 0));
        log("arr.join", call(arr, "join", new Class<?>[]{String.class}, "-"));
        log("arr.toList", call(arr, "toList", new Class<?>[]{}));
        log("arr.toJSONObject", call(arr, "toJSONObject", new Class<?>[]{Class.forName("org.json.JSONArray", true, cl)}, newArr(cl, "[\"x\",\"y\"]")));
        log("arr.query", call(arr, "query", new Class<?>[]{String.class}, "/0"));
        log("arr.similar", call(arr, "similar", new Class<?>[]{Object.class}, newArr(cl, "[3,1,2]")));
        log("arr.write", call(arr, "write", new Class<?>[]{Class.forName("java.io.Writer", true, cl)}, new StringWriter()));
        log("arr.toString", call(arr, "toString", new Class<?>[]{}));

        // static helpers
        log("quote", callStatic(cl, "org.json.JSONObject", "quote", new Class<?>[]{String.class}, "a\"b\\c\n\t"));
        log("stringToValue.int", callStatic(cl, "org.json.JSONObject", "stringToValue", new Class<?>[]{String.class}, "42"));
        log("stringToValue.dot", callStatic(cl, "org.json.JSONObject", "stringToValue", new Class<?>[]{String.class}, "4.2"));
        log("stringToValue.bool", callStatic(cl, "org.json.JSONObject", "stringToValue", new Class<?>[]{String.class}, "true"));
        log("stringToValue.null", callStatic(cl, "org.json.JSONObject", "stringToValue", new Class<?>[]{String.class}, "null"));
        log("stringToValue.hex", callStatic(cl, "org.json.JSONObject", "stringToValue", new Class<?>[]{String.class}, "0x1f"));
        log("numberToString", callStatic(cl, "org.json.JSONObject", "numberToString", new Class<?>[]{Number.class}, 1.0E10));
        log("doubleToString", callStatic(cl, "org.json.JSONObject", "doubleToString", new Class<?>[]{double.class}, 0.30000000000000004));
        log("getNames", callStatic(cl, "org.json.JSONObject", "getNames", new Class<?>[]{Class.forName("org.json.JSONObject", true, cl)},
                newObj(cl, "{\"p\":1,\"q\":2}")));

        // XML / Cookie / HTTP / CDL / Pointer / Tokener
        String xml = "<root a=\"1\"><child>x</child><child>y</child><empty/></root>";
        Object xmlJson = callStatic(cl, "org.json.XML", "toJSONObject", new Class<?>[]{String.class}, xml);
        log("xml.toJSONObject", xmlJson);
        log("xml.toString", callStatic(cl, "org.json.XML", "toString", new Class<?>[]{Object.class}, xmlJson));
        log("xml.escape", callStatic(cl, "org.json.XML", "escape", new Class<?>[]{String.class}, "a<b>&\"'"));
        log("xml.unescape", callStatic(cl, "org.json.XML", "unescape", new Class<?>[]{String.class}, "&lt;a&gt;&amp;&quot;"));

        Object cookie = callStatic(cl, "org.json.Cookie", "toJSONObject", new Class<?>[]{String.class}, "name=v; path=/; secure; expires=now");
        log("cookie.toJSONObject", cookie);
        log("cookie.toString", callStatic(cl, "org.json.Cookie", "toString", new Class<?>[]{Class.forName("org.json.JSONObject", true, cl)}, cookie));
        log("cookie.escape", callStatic(cl, "org.json.Cookie", "escape", new Class<?>[]{String.class}, "a;b=c"));

        log("http.parse", callStatic(cl, "org.json.HTTP", "toJSONObject", new Class<?>[]{String.class},
                "GET /i?x=1 HTTP/1.1\nHost: e.com\nAccept: */*\n\n"));
        log("http.toString", callStatic(cl, "org.json.HTTP", "toString", new Class<?>[]{Class.forName("org.json.JSONObject", true, cl)},
                callStatic(cl, "org.json.HTTP", "toJSONObject", new Class<?>[]{String.class}, "GET / HTTP/1.1\nHost: e.com\n\n")));

        log("cdl.rowsToJSONArray", callStatic(cl, "org.json.CDL", "toJSONArray", new Class<?>[]{String.class},
                "a,b\n1,2\n3,4\n"));
        log("cdl.rowToString", callStatic(cl, "org.json.CDL", "rowToString", new Class<?>[]{Class.forName("org.json.JSONArray", true, cl)},
                newArr(cl, "[\"x\",\"y\",1]")));

        try {
            Object ptr = Class.forName("org.json.JSONPointer", true, cl)
                    .getConstructor(Class.forName("org.json.JSONObject", true, cl), String.class)
                    .newInstance(newObj(cl, "{\"a\":{\"b\":[10,20]}}"), "/a/b/1");
            log("pointer.get", call(ptr, "get", new Class<?>[]{}));
        } catch (Throwable t) {
            log("pointer.skip", t.getClass().getSimpleName());
        }
        try {
            Object tok = Class.forName("org.json.JSONTokener", true, cl).getConstructor(String.class).newInstance("  {\"x\":[1,2]}");
            log("tokener.nextValue", call(tok, "nextValue", new Class<?>[]{}));
            log("tokener.more", call(tok, "more", new Class<?>[]{}));
        } catch (Throwable t) {
            log("tokener.skip", t.getClass().getSimpleName());
        }
    }
}
