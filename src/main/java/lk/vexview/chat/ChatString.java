/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.chat;

public class ChatString {
    String f1;

    public static String m1(String string) {
        int n;
        StackTraceElement stackTraceElement = new RuntimeException().getStackTrace()[1];
        String string2 = new StringBuffer(stackTraceElement.getMethodName()).append(stackTraceElement.getClassName()).toString();
        int n2 = string.length();
        int n3 = n2 - 1;
        char[] cArray = new char[n2];
        int n4 = 4 << 4 ^ (2 ^ 5) << 1;
        int cfr_ignored_0 = (3 ^ 5) << 4 ^ 3 << 1;
        int n5 = (2 ^ 5) << 4 ^ (2 ^ 5);
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

    public String toString() {
        return this.f1;
    }

    public ChatString(String string) {
        this.f1 = string;
    }
}

