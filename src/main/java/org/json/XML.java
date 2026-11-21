/*
 * Decompiled with CFR 0.152.
 */
package org.json;

import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XMLTokener;

public class XML {
    public static final Character AMP = Character.valueOf('&');
    public static final Character APOS = Character.valueOf('\'');
    public static final Character BANG = Character.valueOf('!');
    public static final Character EQ = Character.valueOf('=');
    public static final Character GT = Character.valueOf('>');
    public static final Character LT = Character.valueOf('<');
    public static final Character QUEST = Character.valueOf('?');
    public static final Character QUOT = Character.valueOf('\"');
    public static final Character SLASH = Character.valueOf('/');

    private static Iterable<Integer> codePointIterator(final String string) {
        return new Iterable<Integer>(){

            @Override
            public Iterator<Integer> iterator() {
                return new Iterator<Integer>(){
                    private int nextIndex = 0;
                    private int length;
                    {
                        this.length = string.length();
                    }

                    @Override
                    public boolean hasNext() {
                        return this.nextIndex < this.length;
                    }

                    @Override
                    public Integer next() {
                        int n = string.codePointAt(this.nextIndex);
                        this.nextIndex += Character.charCount(n);
                        return n;
                    }

                    @Override
                    public void remove() {
                        throw new UnsupportedOperationException();
                    }
                };
            }
        };
    }

    public static String escape(String string) {
        StringBuilder stringBuilder = new StringBuilder(string.length());
        block7: for (int n : XML.codePointIterator(string)) {
            switch (n) {
                case 38: {
                    stringBuilder.append("&amp;");
                    continue block7;
                }
                case 60: {
                    stringBuilder.append("&lt;");
                    continue block7;
                }
                case 62: {
                    stringBuilder.append("&gt;");
                    continue block7;
                }
                case 34: {
                    stringBuilder.append("&quot;");
                    continue block7;
                }
                case 39: {
                    stringBuilder.append("&apos;");
                    continue block7;
                }
            }
            if (XML.mustEscape(n)) {
                stringBuilder.append("&#x");
                stringBuilder.append(Integer.toHexString(n));
                stringBuilder.append(';');
                continue;
            }
            stringBuilder.appendCodePoint(n);
        }
        return stringBuilder.toString();
    }

    private static boolean mustEscape(int n) {
        return Character.isISOControl(n) && n != 9 && n != 10 && n != 13 || (n < 32 || n > 55295) && (n < 57344 || n > 65533) && (n < 65536 || n > 0x10FFFF);
    }

    public static String unescape(String string) {
        StringBuilder stringBuilder = new StringBuilder(string.length());
        int n = string.length();
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c == '&') {
                int n2 = string.indexOf(59, i);
                if (n2 > i) {
                    String string2 = string.substring(i + 1, n2);
                    stringBuilder.append(XMLTokener.unescapeEntity(string2));
                    i += string2.length() + 1;
                    continue;
                }
                stringBuilder.append(c);
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static void noSpace(String string) throws JSONException {
        int n = string.length();
        if (n == 0) {
            throw new JSONException("Empty string.");
        }
        for (int i = 0; i < n; ++i) {
            if (!Character.isWhitespace(string.charAt(i))) continue;
            throw new JSONException("'" + string + "' contains a space character.");
        }
    }

    private static boolean parse(XMLTokener xMLTokener, JSONObject jSONObject, String string, boolean bl) throws JSONException {
        String string2;
        JSONObject jSONObject2 = null;
        Object object = xMLTokener.nextToken();
        if (object == BANG) {
            char c = xMLTokener.next();
            if (c == '-') {
                if (xMLTokener.next() == '-') {
                    xMLTokener.skipPast("-->");
                    return false;
                }
                xMLTokener.back();
            } else if (c == '[') {
                object = xMLTokener.nextToken();
                if ("CDATA".equals(object) && xMLTokener.next() == '[') {
                    String string3 = xMLTokener.nextCDATA();
                    if (string3.length() > 0) {
                        jSONObject.accumulate("content", string3);
                    }
                    return false;
                }
                throw xMLTokener.syntaxError("Expected 'CDATA['");
            }
            int n = 1;
            do {
                if ((object = xMLTokener.nextMeta()) == null) {
                    throw xMLTokener.syntaxError("Missing '>' after '<!'.");
                }
                if (object == LT) {
                    ++n;
                    continue;
                }
                if (object != GT) continue;
                --n;
            } while (n > 0);
            return false;
        }
        if (object == QUEST) {
            xMLTokener.skipPast("?>");
            return false;
        }
        if (object == SLASH) {
            object = xMLTokener.nextToken();
            if (string == null) {
                throw xMLTokener.syntaxError("Mismatched close tag " + object);
            }
            if (!object.equals(string)) {
                throw xMLTokener.syntaxError("Mismatched " + string + " and " + object);
            }
            if (xMLTokener.nextToken() != GT) {
                throw xMLTokener.syntaxError("Misshaped close tag");
            }
            return true;
        }
        if (object instanceof Character) {
            throw xMLTokener.syntaxError("Misshaped tag");
        }
        String string4 = (String)object;
        object = null;
        jSONObject2 = new JSONObject();
        while (true) {
            if (object == null) {
                object = xMLTokener.nextToken();
            }
            if (!(object instanceof String)) break;
            string2 = (String)object;
            object = xMLTokener.nextToken();
            if (object == EQ) {
                object = xMLTokener.nextToken();
                if (!(object instanceof String)) {
                    throw xMLTokener.syntaxError("Missing value");
                }
                jSONObject2.accumulate(string2, bl ? (String)object : XML.stringToValue((String)object));
                object = null;
                continue;
            }
            jSONObject2.accumulate(string2, "");
        }
        if (object == SLASH) {
            if (xMLTokener.nextToken() != GT) {
                throw xMLTokener.syntaxError("Misshaped tag");
            }
            if (jSONObject2.length() > 0) {
                jSONObject.accumulate(string4, jSONObject2);
            } else {
                jSONObject.accumulate(string4, "");
            }
            return false;
        }
        if (object == GT) {
            while (true) {
                if ((object = xMLTokener.nextContent()) == null) {
                    if (string4 != null) {
                        throw xMLTokener.syntaxError("Unclosed tag " + string4);
                    }
                    return false;
                }
                if (object instanceof String) {
                    string2 = (String)object;
                    if (string2.length() <= 0) continue;
                    jSONObject2.accumulate("content", bl ? string2 : XML.stringToValue(string2));
                    continue;
                }
                if (object == LT && XML.parse(xMLTokener, jSONObject2, string4, bl)) break;
            }
            if (jSONObject2.length() == 0) {
                jSONObject.accumulate(string4, "");
            } else if (jSONObject2.length() == 1 && jSONObject2.opt("content") != null) {
                jSONObject.accumulate(string4, jSONObject2.opt("content"));
            } else {
                jSONObject.accumulate(string4, jSONObject2);
            }
            return false;
        }
        throw xMLTokener.syntaxError("Misshaped tag");
    }

    public static Object stringToValue(String string) {
        if (string.equals("")) {
            return string;
        }
        if (string.equalsIgnoreCase("true")) {
            return Boolean.TRUE;
        }
        if (string.equalsIgnoreCase("false")) {
            return Boolean.FALSE;
        }
        if (string.equalsIgnoreCase("null")) {
            return JSONObject.NULL;
        }
        char c = string.charAt(0);
        if (c >= '0' && c <= '9' || c == '-') {
            try {
                if (string.indexOf(46) > -1 || string.indexOf(101) > -1 || string.indexOf(69) > -1 || "-0".equals(string)) {
                    Double d = Double.valueOf(string);
                    if (!d.isInfinite() && !d.isNaN()) {
                        return d;
                    }
                } else {
                    Long l = Long.valueOf(string);
                    if (string.equals(l.toString())) {
                        if (l == (long)l.intValue()) {
                            return l.intValue();
                        }
                        return l;
                    }
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return string;
    }

    public static JSONObject toJSONObject(String string) throws JSONException {
        return XML.toJSONObject(string, false);
    }

    public static JSONObject toJSONObject(Reader reader) throws JSONException {
        return XML.toJSONObject(reader, false);
    }

    public static JSONObject toJSONObject(Reader reader, boolean bl) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        XMLTokener xMLTokener = new XMLTokener(reader);
        while (xMLTokener.more()) {
            xMLTokener.skipPast("<");
            if (!xMLTokener.more()) continue;
            XML.parse(xMLTokener, jSONObject, null, bl);
        }
        return jSONObject;
    }

    public static JSONObject toJSONObject(String string, boolean bl) throws JSONException {
        return XML.toJSONObject(new StringReader(string), bl);
    }

    public static String toString(Object object) throws JSONException {
        return XML.toString(object, null);
    }

    public static String toString(Object object, String string) throws JSONException {
        String string2;
        StringBuilder stringBuilder = new StringBuilder();
        if (object instanceof JSONObject) {
            if (string != null) {
                stringBuilder.append('<');
                stringBuilder.append(string);
                stringBuilder.append('>');
            }
            JSONObject jSONObject = (JSONObject)object;
            for (String string3 : jSONObject.keySet()) {
                Object value = jSONObject.opt(string3);
                if (value == null) {
                    value = "";
                } else if (value.getClass().isArray()) {
                    value = new JSONArray(value);
                }
                if ("content".equals(string3)) {
                    if (value instanceof JSONArray) {
                        JSONArray jSONArray = (JSONArray)value;
                        int n2 = jSONArray.length();
                        for (int n = 0; n < n2; ++n) {
                            if (n > 0) {
                                stringBuilder.append('\n');
                            }
                            Object object2 = jSONArray.opt(n);
                            stringBuilder.append(XML.escape(object2.toString()));
                        }
                        continue;
                    }
                    stringBuilder.append(XML.escape(value.toString()));
                    continue;
                }
                if (value instanceof JSONArray) {
                    JSONArray jSONArray = (JSONArray)value;
                    int n2 = jSONArray.length();
                    for (int n = 0; n < n2; ++n) {
                        Object object2 = jSONArray.opt(n);
                        if (object2 instanceof JSONArray) {
                            stringBuilder.append('<');
                            stringBuilder.append(string3);
                            stringBuilder.append('>');
                            stringBuilder.append(XML.toString(object2));
                            stringBuilder.append("</");
                            stringBuilder.append(string3);
                            stringBuilder.append('>');
                            continue;
                        }
                        stringBuilder.append(XML.toString(object2, string3));
                    }
                    continue;
                }
                if ("".equals(value)) {
                    stringBuilder.append('<');
                    stringBuilder.append(string3);
                    stringBuilder.append("/>");
                    continue;
                }
                stringBuilder.append(XML.toString(value, string3));
            }
            if (string != null) {
                stringBuilder.append("</");
                stringBuilder.append(string);
                stringBuilder.append('>');
            }
            return stringBuilder.toString();
        }
        if (object != null && (object instanceof JSONArray || object.getClass().isArray())) {
            JSONArray jSONArray = object.getClass().isArray() ? new JSONArray(object) : (JSONArray)object;
            int n = jSONArray.length();
            for (int i = 0; i < n; ++i) {
                Object object4 = jSONArray.opt(i);
                stringBuilder.append(XML.toString(object4, string == null ? "array" : string));
            }
            return stringBuilder.toString();
        }
        String string5 = string2 = object == null ? "null" : XML.escape(object.toString());
        return string == null ? "\"" + string2 + "\"" : (string2.length() == 0 ? "<" + string + "/>" : "<" + string + ">" + string2 + "</" + string + ">");
    }
}

