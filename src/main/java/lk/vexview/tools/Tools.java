/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class Tools {
    public static List<String[]> m1(List<String> list) {
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        String[] stringArray = null;
        for (String string : list) {
            if (!string.contains("[but]")) continue;
            stringArray = string.replace("[but]", "").split("<&>");
            arrayList.add(stringArray);
        }
        return arrayList;
    }

    public static List<String> m2(String string) {
        int n;
        String[] stringArray = string.split("<#>");
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = stringArray.length;
        int n3 = n = 0;
        while (n3 < n2) {
            String string2 = stringArray[n];
            if (!string2.contains("[img]") && !string2.contains("[gui]") && !string2.contains("[but]") && !string2.contains("[tf]") && !string2.contains("[hov]") && !string2.contains("[txt]")) {
                arrayList.add(string2);
            }
            n3 = ++n;
        }
        return arrayList;
    }

    public static List<String> m3(String string) {
        int n;
        String[] stringArray = string.split("<#>");
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = stringArray.length;
        int n3 = n = 0;
        while (n3 < n2) {
            String string2 = stringArray[n];
            if (!string2.contains("[img]") && !string2.contains("[gui]") && !string2.contains("[but]") && !string2.contains("[tf]")) {
                arrayList.add(string2);
            }
            n3 = ++n;
        }
        return arrayList;
    }

    public static List<String> m4(List<String> list) {
        for (String string : list) {
            if (!string.contains("[hov]")) continue;
            return Arrays.asList(string.split("<##>"));
        }
        return new ArrayList<String>();
    }

    public static String[] m5(List<String> list) {
        String[] stringArray = null;
        for (String string : list) {
            if (!string.contains("[gui]")) continue;
            stringArray = string.replace("[gui]", "").split("<&>");
            return stringArray;
        }
        return stringArray;
    }

    public static List<String> m6(String string) {
        int n;
        String[] stringArray = string.split("<#>");
        ArrayList<String> arrayList = new ArrayList<String>();
        int n2 = stringArray.length;
        int n3 = n = 0;
        while (n3 < n2) {
            String string2 = stringArray[n];
            arrayList.add(string2);
            n3 = ++n;
        }
        return arrayList;
    }

    public static List<String[]> m7(List<String> list) {
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        for (String string : list) {
            if (!string.contains("[img]")) continue;
            arrayList.add(string.replace("[img]", "").split("<&>"));
        }
        return arrayList;
    }

    public static String m8(String string) {
        int n;
        StackTraceElement stackTraceElement = new RuntimeException().getStackTrace()[1];
        String string2 = new StringBuffer(stackTraceElement.getMethodName()).insert(0, stackTraceElement.getClassName()).toString();
        int n2 = string.length();
        int n3 = n2 - 1;
        char[] cArray = new char[n2];
        int n4 = 2;
        int cfr_ignored_0 = (2 ^ 5) << 4 ^ 2 << 1;
        int n5 = 4 << 4 ^ 1 << 1;
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



