/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.scoreboard;

import lk.vexview.scoreboard.NewScoreBroad;
import lk.vexview.scoreboard.ScoreBroadBuilder;
import org.json.JSONObject;

public class SBPacketBuffer {
    public static String m1(String string) {
        int n;
        StackTraceElement stackTraceElement = new RuntimeException().getStackTrace()[1];
        String string2 = new StringBuffer(stackTraceElement.getMethodName()).insert(0, stackTraceElement.getClassName()).toString();
        int n2 = string.length();
        int n3 = n2 - 1;
        char[] cArray = new char[n2];
        int n4 = (2 ^ 5) << 4;
        int cfr_ignored_0 = 4 << 4 ^ 5 << 1;
        int n5 = 2 << 3 ^ 3;
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
        block10: {
            String string = jSONObject.getString("packet_sub_type");
            int n2 = -1;
            switch (string.hashCode()) {
                case 94756344: {
                    if (!string.equals("close")) break;
                    n = n2 = 0;
                    break block10;
                }
                case 113762: {
                    if (!string.equals("set")) break;
                    n = n2 = 1;
                    break block10;
                }
                case 1094496948: {
                    if (!string.equals("replace")) break;
                    n2 = 2;
                }
            }
            n = n2;
        }
        switch (n) {
            case 0: {
                NewScoreBroad.s5 = 0;
                return;
            }
            case 1: {
                ScoreBroadBuilder.m3(jSONObject.getString("packet_data"));
                return;
            }
            case 2: {
                ScoreBroadBuilder.m4(jSONObject.getString("packet_data"));
                return;
            }
        }
    }
}

