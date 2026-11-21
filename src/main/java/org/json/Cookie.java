/*
 * Decompiled with CFR 0.152.
 */
package org.json;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Cookie {
    public static String escape(String string) {
        String string2 = string.trim();
        int n = string2.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        for (int i = 0; i < n; ++i) {
            char c = string2.charAt(i);
            if (c < ' ' || c == '+' || c == '%' || c == '=' || c == ';') {
                stringBuilder.append('%');
                stringBuilder.append(Character.forDigit((char)(c >>> 4 & 0xF), 16));
                stringBuilder.append(Character.forDigit((char)(c & 0xF), 16));
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static JSONObject toJSONObject(String string) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        JSONTokener jSONTokener = new JSONTokener(string);
        jSONObject.put("name", jSONTokener.nextTo('='));
        jSONTokener.next('=');
        jSONObject.put("value", jSONTokener.nextTo(';'));
        jSONTokener.next();
        while (jSONTokener.more()) {
            Object value;
            String string3 = Cookie.unescape(jSONTokener.nextTo("=;"));
            if (jSONTokener.next() != '=') {
                if (!string3.equals("secure")) throw jSONTokener.syntaxError("Missing '=' in cookie parameter.");
                value = Boolean.TRUE;
            } else {
                value = Cookie.unescape(jSONTokener.nextTo(';'));
                jSONTokener.next();
            }
            jSONObject.put(string3, value);
        }
        return jSONObject;
    }

    public static String toString(JSONObject jSONObject) throws JSONException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Cookie.escape(jSONObject.getString("name")));
        stringBuilder.append("=");
        stringBuilder.append(Cookie.escape(jSONObject.getString("value")));
        if (jSONObject.has("expires")) {
            stringBuilder.append(";expires=");
            stringBuilder.append(jSONObject.getString("expires"));
        }
        if (jSONObject.has("domain")) {
            stringBuilder.append(";domain=");
            stringBuilder.append(Cookie.escape(jSONObject.getString("domain")));
        }
        if (jSONObject.has("path")) {
            stringBuilder.append(";path=");
            stringBuilder.append(Cookie.escape(jSONObject.getString("path")));
        }
        if (jSONObject.optBoolean("secure")) {
            stringBuilder.append(";secure");
        }
        return stringBuilder.toString();
    }

    public static String unescape(String string) {
        int n = string.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        for (int i = 0; i < n; ++i) {
            char c = string.charAt(i);
            if (c == '+') {
                c = ' ';
            } else if (c == '%' && i + 2 < n) {
                int n2 = JSONTokener.dehexchar(string.charAt(i + 1));
                int n3 = JSONTokener.dehexchar(string.charAt(i + 2));
                if (n2 >= 0 && n3 >= 0) {
                    c = (char)(n2 * 16 + n3);
                    i += 2;
                }
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }
}

