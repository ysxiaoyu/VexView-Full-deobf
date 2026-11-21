/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.engine.base;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lk.vexview.engine.base.Element;

public abstract class AbstractElementCollection {
    protected StringBuilder f1;
    protected List<Element> f2;

    public boolean m12(int n) {
        return Boolean.parseBoolean(this.f2.get((int)n).f1);
    }

    public boolean m1(int n, int... ignored) {
        return this.m12(n);
    }

    public int m13() {
        return this.f2.size() - 1;
    }

    public int m2(int... ignored) {
        return this.m13();
    }

    public int m3(int n) {
        return Integer.parseInt(this.f2.get((int)n).f1);
    }

    public double m4(int n) {
        return Double.parseDouble(this.f2.get((int)n).f1);
    }

    public float m5(int n) {
        return Float.parseFloat(this.f2.get((int)n).f1);
    }

    protected String m7(String string) {
        StringBuilder stringBuilder;
        StringBuilder stringBuilder2 = new StringBuilder(string);
        if (string == null || string.equals("")) {
            this.f1.append("error_data");
            return stringBuilder2.toString();
        }
        char c = stringBuilder2.charAt(0);
        if (c == '[') {
            stringBuilder2.delete(0, 1);
            c = stringBuilder2.charAt(0);
            char c2 = c;
            while (c2 != ']') {
                this.f1.append(c);
                stringBuilder2.delete(0, 1);
                c = stringBuilder2.charAt(0);
                c2 = c;
            }
            StringBuilder stringBuilder3 = stringBuilder2;
            stringBuilder = stringBuilder3;
            stringBuilder3.delete(0, 1);
        } else {
            this.f1.append("error_data");
            stringBuilder = stringBuilder2;
        }
        return stringBuilder.toString();
    }

    public String m8(int n) {
        return this.f2.get((int)n).f1;
    }

    public List<String> m9(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        this.m11(n).forEach(element -> arrayList.add(element.m1()));
        return arrayList;
    }

    public AbstractElementCollection() {
        AbstractElementCollection abstractElementCollection = this;
        this.f1 = new StringBuilder();
        abstractElementCollection.f2 = new ArrayList<Element>();
    }

    public String m10() {
        return this.f1.toString();
    }

    public List<Element> m11(int n) {
        if (n > this.f2.size()) {
            return Collections.emptyList();
        }
        return this.f2.subList(n, this.f2.size());
    }
}

