/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.ui.verification;

import lk.vexview.ui.verification.VerHandler;
import org.json.JSONObject;

public class VerNetManager {
    /*
     * Enabled aggressive block sorting
     */
    public static void m1(JSONObject jSONObject) {
        int n;
        block8: {
            String string = jSONObject.getString("packet_sub_type");
            int n2 = -1;
            switch (string.hashCode()) {
                case 3548: {
                    if (!string.equals("ok")) break;
                    n = n2 = 0;
                    break block8;
                }
                case 102230: {
                    if (!string.equals("get")) break;
                    n2 = 1;
                }
            }
            n = n2;
        }
        switch (n) {
            case 0: {
                VerHandler.s1 = true;
                return;
            }
            case 1: {
                VerHandler.m1();
                return;
            }
        }
    }

    public static String m2(String string) {
        int n;
        StackTraceElement stackTraceElement = new RuntimeException().getStackTrace()[1];
        String string2 = new StringBuffer(stackTraceElement.getClassName()).insert(0, stackTraceElement.getMethodName()).toString();
        int n2 = string.length();
        int n3 = n2 - 1;
        char[] cArray = new char[n2];
        int n4 = 4 << 4 ^ (2 << 2 ^ 3);
        int n5 = (2 ^ 5) << 4 ^ (2 << 2 ^ 1);
        int n6 = n = string2.length() - 1;
        int n7 = n3;
        String string3 = string2;
        while (n7 >= 0) {
            int n8 = n3--;
            cArray[n8] = (char)(n5 ^ (string.charAt(n8) ^ string3.charAt(n)));
            if (n3 < 0) break;
            int n9 = n3--;
            char c = cArray[n9] = (char)(n4 ^ (string.charAt(n9) ^ string3.charAt(n)));
            if (--n < 0) {
                n = n6;
            }
            n7 = n3;
        }
        return new String(cArray);
    }
}

