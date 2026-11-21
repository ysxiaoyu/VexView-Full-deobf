/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.hud.util;

import lk.vexview.hud.HUDViewer;
import lk.vexview.hud.MinecraftHUD;
import org.json.JSONObject;

public class HudNetManager {
    public static String m1(String string) {
        int n;
        StackTraceElement stackTraceElement = new RuntimeException().getStackTrace()[1];
        String string2 = new StringBuffer(stackTraceElement.getClassName()).insert(0, stackTraceElement.getMethodName()).toString();
        int n2 = string.length();
        int n3 = n2 - 1;
        char[] cArray = new char[n2];
        int n4 = 3 << 3 ^ 2;
        int cfr_ignored_0 = 4 << 4 ^ (3 << 2 ^ 1);
        int n5 = (3 ^ 5) << 3 ^ (3 ^ 5);
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

    /*
     * Enabled aggressive block sorting
     */
    public static void m2(JSONObject jSONObject) {
        int n;
        String string;
        block14: {
            string = jSONObject.getString("packet_sub_type");
            int n2 = -1;
            switch (string.hashCode()) {
                case -1039745817: {
                    if (!string.equals("normal")) break;
                    n = n2 = 0;
                    break block14;
                }
                case 109801339: {
                    if (!string.equals("super")) break;
                    n = n2 = 1;
                    break block14;
                }
                case -934610812: {
                    if (!string.equals("remove")) break;
                    n = n2 = 2;
                    break block14;
                }
                case 1282376349: {
                    if (!string.equals("removeall")) break;
                    n = n2 = 3;
                    break block14;
                }
                case 695073197: {
                    if (!string.equals("minecraft")) break;
                    n2 = 4;
                }
            }
            n = n2;
        }
        switch (n) {
            case 0: {
                HUDViewer.m10(jSONObject.getString("packet_data"));
                return;
            }
            case 1: {
                HUDViewer.m10(jSONObject.getString("packet_data"));
                return;
            }
            case 2: {
                string = jSONObject.getString("packet_data");
                HUDViewer.s3.remove(string);
                HUDViewer.s2.remove(string);
                return;
            }
            case 3: {
                HUDViewer.s3.clear();
                HUDViewer.s2.clear();
                return;
            }
            case 4: {
                MinecraftHUD.m1(jSONObject.getString("packet_data"));
                return;
            }
        }
    }
}

