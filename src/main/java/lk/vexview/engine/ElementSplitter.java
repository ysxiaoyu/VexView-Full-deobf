/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lk.vexview.engine.base.AbstractElementCollection;
import lk.vexview.engine.collection.HoverTextElementCollection;

public class ElementSplitter {
    public static Map<String, List<HoverTextElementCollection>> m1(String string2, String string3, String string4, String string5) {
        int n;
        LinkedHashMap<String, List<HoverTextElementCollection>> linkedHashMap = new LinkedHashMap<String, List<HoverTextElementCollection>>();
        String[] stringArray = new String[5];
        stringArray[0] = "img";
        stringArray[1] = "gimg";
        stringArray[2] = "bimg";
        stringArray[3] = "imgs";
        stringArray[4] = "imgx";
        Arrays.asList(stringArray).forEach(string -> {
            List cfr_ignored_0 = linkedHashMap.put((String)string, new ArrayList());
        });
        String[] stringArray2 = string2.split(string3);
        int n2 = stringArray2.length;
        int n3 = n = 0;
        while (n3 < n2) {
            Object object = stringArray2[n];
            if (linkedHashMap.containsKey(((AbstractElementCollection)(object = new HoverTextElementCollection((String)object, string4, string5))).m10())) {
                ((List)linkedHashMap.get(((AbstractElementCollection)object).m10())).add(object);
            } else {
                HoverTextElementCollection[] hoverTextElementCollectionArray = new HoverTextElementCollection[1];
                hoverTextElementCollectionArray[0] = (HoverTextElementCollection)object;
                linkedHashMap.put(((AbstractElementCollection)object).m10(), new ArrayList<HoverTextElementCollection>(Arrays.asList(hoverTextElementCollectionArray)));
            }
            n3 = ++n;
        }
        return linkedHashMap;
    }

    public static Map<String, List<HoverTextElementCollection>> m2(String string2, String string3, String string4, String string5) {
        int n;
        LinkedHashMap<String, List<HoverTextElementCollection>> linkedHashMap = new LinkedHashMap<String, List<HoverTextElementCollection>>();
        String[] stringArray = new String[5];
        stringArray[0] = "img";
        stringArray[1] = "gimg";
        stringArray[2] = "bimg";
        stringArray[3] = "imgs";
        stringArray[4] = "imgx";
        Arrays.asList(stringArray).forEach(string -> {
            List cfr_ignored_0 = linkedHashMap.put((String)string, new ArrayList());
        });
        String[] stringArray2 = string2.split(string3);
        int n2 = stringArray2.length;
        int n3 = n = 0;
        while (n3 < n2) {
            String string6;
            Object object = stringArray2[n];
            String string7 = string6 = ((AbstractElementCollection)(object = new HoverTextElementCollection((String)object, string4, string5))).m10().equals("error_data") ? "list" : ((AbstractElementCollection)object).m10();
            if (linkedHashMap.containsKey(((AbstractElementCollection)object).m10())) {
                ((List)linkedHashMap.get(string6)).add(object);
            } else {
                HoverTextElementCollection[] hoverTextElementCollectionArray = new HoverTextElementCollection[1];
                hoverTextElementCollectionArray[0] = (HoverTextElementCollection)object;
                linkedHashMap.put(string6, new ArrayList<HoverTextElementCollection>(Arrays.asList(hoverTextElementCollectionArray)));
            }
            n3 = ++n;
        }
        return linkedHashMap;
    }

    public static String m5(String string) {
        int n;
        StackTraceElement stackTraceElement = new RuntimeException().getStackTrace()[1];
        String string2 = new StringBuffer(stackTraceElement.getMethodName()).append(stackTraceElement.getClassName()).toString();
        int n2 = string.length();
        int n3 = n2 - 1;
        char[] cArray = new char[n2];
        int n4 = 5 << 4 ^ 1;
        int cfr_ignored_0 = 5 << 3 ^ (3 ^ 5);
        int n5 = (3 ^ 5) << 4 ^ (3 << 2 ^ 1);
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

