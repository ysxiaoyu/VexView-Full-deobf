/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.core.widget;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import net.minecraft.client.gui.FontRenderer;

public class StringLines {
    private List<StringBuilder> f1;
    private Set<Integer> f2;

    public boolean m1(int n) {
        if (this.f2.contains(n)) {
            return false;
        }
        StringLines stringLines = this;
        stringLines.f1.add(n + 1, new StringBuilder(""));
        stringLines.f2.add(n);
        return true;
    }

    public void m2(int n) {
        this.f1.remove(n);
        this.f2.remove(n - 1);
        HashSet<Integer> hashSet = new HashSet<Integer>(this.f2);
        this.f2.clear();
        hashSet.forEach(n2 -> this.f2.add(n2 >= n ? n2 - 1 : n2));
    }

    public boolean m3(int n, int n2) {
        if (n2 > 0) {
            this.f1.get(n).delete(n2 - 1, n2);
            if (this.f2.contains(n) && this.f1.size() - 1 > n) {
                if (this.f1.get(n + 1).length() > 0) {
                    this.f1.get(n).append(this.f1.get(n + 1).charAt(0));
                    this.f1.get(n + 1).delete(0, 1);
                } else {
                    this.m2(n + 1);
                }
            }
            return true;
        }
        return false;
    }

    public void m4(int n, int n3) {
        this.f1.add(n + 1, new StringBuilder(this.f1.get(n).substring(n3)));
        if (n3 < this.f1.get(n).length()) {
            this.f1.get(n).delete(n3, this.f1.get(n).length());
            HashSet<Integer> hashSet = new HashSet<Integer>(this.f2);
            this.f2.clear();
            hashSet.forEach(n2 -> this.f2.add(n2 >= n ? n2 + 1 : n2));
        }
    }

    public void m5() {
        System.out.println(Arrays.toString(this.f2.toArray()));
    }

    public void m6(List<Object> list) {
        StringLines stringLines = this;
        stringLines.f2.clear();
        stringLines.f1.clear();
        if (list.isEmpty()) {
            this.f1.add(new StringBuilder());
            return;
        }
        list.forEach(object -> this.f1.add(new StringBuilder((String)object)));
    }

    public boolean m7(int n) {
        Iterator<StringBuilder> iterator;
        int n2 = 0;
        Iterator<StringBuilder> iterator2 = iterator = this.f1.iterator();
        while (iterator2.hasNext()) {
            StringBuilder stringBuilder = iterator.next();
            n2 += stringBuilder.length();
            iterator2 = iterator;
        }
        return n2 < n;
    }

    public void m8(int n, int n2, char c, FontRenderer fontRenderer, int n3) {
        int n4 = this.f1.get(n).length() - 1;
        char c2 = this.f1.get(n).charAt(n4);
        int n5 = n4;
        this.f1.get(n).delete(n5, n5 + 1);
        this.f1.get(n).insert(n2, c);
        if (this.f2.contains(n)) {
            n2 = n + 1;
            StringLines stringLines = this;
            while (stringLines.f1.size() > n2) {
                if (fontRenderer.getStringWidth(this.m16(n2)) < n3 - 10) {
                    this.f1.get(n2).insert(0, c2);
                    return;
                }
                if (!this.f2.contains(n2)) {
                    this.m1(n2);
                }
                this.f1.get(n2).insert(0, c2);
                n4 = this.f1.get(n2).length() - 1;
                c2 = this.f1.get(n2).charAt(n4);
                int n6 = n4;
                this.f1.get(++n2).delete(n6, n6 + 1);
                stringLines = this;
            }
        } else {
            StringLines stringLines = this;
            stringLines.m1(n);
            stringLines.f1.get(n + 1).insert(0, c2);
        }
    }

    public void m10(int n, boolean bl, FontRenderer fontRenderer, int n2) {
        StringLines stringLines;
        if (bl) {
            int n3 = this.f1.get(n - 1).length();
            this.f1.get(n - 1).delete(n3 - 1, n3);
            this.f1.get(n - 1).append(this.f1.get(n).charAt(0));
            this.f1.get(n).delete(0, 1);
            stringLines = this;
        } else {
            FontRenderer fontRenderer2 = fontRenderer;
            while (fontRenderer2.getStringWidth(this.m16(n - 1)) < n2 - 10 && this.f1.get(n).length() > 0) {
                this.f1.get(n - 1).append(this.f1.get(n).charAt(0));
                this.f1.get(n).delete(0, 1);
                fontRenderer2 = fontRenderer;
            }
            stringLines = this;
        }
        stringLines.f2.add(n - 1);
        if (this.f1.get(n).length() == 0) {
            this.m2(n);
        }
    }

    public static String m13(String string) {
        int n;
        StackTraceElement stackTraceElement = new RuntimeException().getStackTrace()[1];
        String string2 = new StringBuffer(stackTraceElement.getClassName()).append(stackTraceElement.getMethodName()).toString();
        int n2 = string.length();
        int n3 = n2 - 1;
        char[] cArray = new char[n2];
        int n4 = 5 << 4 ^ (2 ^ 5);
        int cfr_ignored_0 = 5 << 4 ^ (2 << 2 ^ 3);
        int n5 = 2 ^ 5;
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

    public StringLines(List<String> list) {
        Iterator<String> iterator;
        StringLines stringLines = this;
        this.f1 = new ArrayList<StringBuilder>();
        stringLines.f2 = new HashSet<Integer>();
        Iterator<String> iterator2 = iterator = list.iterator();
        while (iterator2.hasNext()) {
            String string = iterator.next();
            iterator2 = iterator;
            this.f1.add(new StringBuilder(string));
        }
        if (this.f1.isEmpty()) {
            this.f1.add(new StringBuilder());
        }
    }

    public void m15(int n, int n2, char c, FontRenderer fontRenderer, int n3) {
        char c2 = c;
        ++n;
        StringLines stringLines = this;
        while (stringLines.f1.size() > n) {
            if (fontRenderer.getStringWidth(this.m16(n)) < n3 - 10) {
                this.f1.get(n).insert(0, c2);
                return;
            }
            if (!this.f2.contains(n)) {
                this.m1(n);
            }
            this.f1.get(n).insert(0, c2);
            int n4 = this.f1.get(n).length();
            c2 = this.f1.get(n).charAt(n4);
            int n5 = n4;
            this.f1.get(n).delete(n5, n5 + 1);
            stringLines = this;
        }
    }

    public String m16(int n) {
        return this.f1.get(n).toString();
    }

    public int m17() {
        return this.m22().size();
    }

    public String m18(int n, int n2) {
        return this.f1.get(n).substring(0, n2);
    }

    public List<String> m19() {
        ArrayList<String> arrayList = new ArrayList<String>();
        ArrayList<StringBuilder> arrayList2 = new ArrayList<StringBuilder>();
        if (!this.f1.isEmpty()) {
            arrayList2.add(new StringBuilder(this.f1.get(0)));
            int n = 1;
            int n2 = n;
            while (n2 < this.f1.size()) {
                if (this.f2.contains(n - 1)) {
                    ArrayList<StringBuilder> arrayList3 = arrayList2;
                    ((StringBuilder)arrayList3.get(arrayList3.size() - 1)).append((CharSequence)this.f1.get(n));
                } else {
                    arrayList2.add(new StringBuilder(this.f1.get(n)));
                }
                n2 = ++n;
            }
        }
        arrayList2.forEach(stringBuilder -> arrayList.add(stringBuilder.toString()));
        return arrayList;
    }

    public void m20(int n, int n2, char c) {
        this.f1.get(n).insert(n2, c);
    }

    public List<String> m22() {
        ArrayList<String> arrayList = new ArrayList<String>();
        this.f1.forEach(stringBuilder -> arrayList.add(stringBuilder.toString()));
        return arrayList;
    }
}

