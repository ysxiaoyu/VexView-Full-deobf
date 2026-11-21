/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.core;

import java.util.ArrayList;
import java.util.List;

public class VexHoverText {
    public int f1;
    public int f2;
    public List<String> f3;
    public int f4;
    public int f5;

    public VexHoverText(int n, int n2, int n3, int n4, List<String> list) {
        VexHoverText vexHoverText = this;
        VexHoverText vexHoverText2 = this;
        VexHoverText vexHoverText3 = this;
        this.f3 = new ArrayList<String>();
        this.f1 = n;
        vexHoverText2.f5 = n2;
        vexHoverText2.f4 = n3;
        vexHoverText.f2 = n4;
        vexHoverText.f3 = list;
    }

    public boolean m1(int n, int n2, int n3, int n4, int n5, int n6) {
        int n7 = this.f1 == -1 ? n3 / 2 - this.f4 / 2 : this.f1;
        int n8 = n3 = this.f5 == -1 ? n4 / 2 - this.f2 / 2 : this.f5;
        return !this.f3.isEmpty() && n >= n5 + n7 && n <= n5 + n7 + this.f4 && n2 >= n6 + n3 && n2 <= n6 + n3 + this.f2;
    }

    public static String m2(String string) {
        int n;
        StackTraceElement stackTraceElement = new RuntimeException().getStackTrace()[1];
        String string2 = new StringBuffer(stackTraceElement.getMethodName()).append(stackTraceElement.getClassName()).toString();
        int n2 = string.length();
        int n3 = n2 - 1;
        char[] cArray = new char[n2];
        int n4 = (2 ^ 5) << 4 ^ (3 ^ 5) << 1;
        int cfr_ignored_0 = (2 ^ 5) << 4 ^ 5 << 1;
        int n5 = (2 ^ 5) << 3 ^ 1;
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

