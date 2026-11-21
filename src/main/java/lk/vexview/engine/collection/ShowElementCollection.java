/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.engine.collection;

import java.util.Arrays;
import java.util.List;
import lk.vexview.engine.base.AbstractElementCollection;
import lk.vexview.engine.base.Element;
import lk.vexview.engine.collection.SimpleElementCollection;

public class ShowElementCollection
extends AbstractElementCollection {
    private List<String> f1;
    private AbsolutePosition f2;
    private String f3;
    private SimpleElementCollection f4;

    public String m1() {
        return this.f3;
    }

    public double m2() {
        if (this.f2 != null) {
            return this.f2.m2();
        }
        return -1.0;
    }

    public SimpleElementCollection m3() {
        return this.f4;
    }

    public double m4() {
        if (this.f2 != null) {
            return this.f2.m1();
        }
        return -1.0;
    }

    private /* synthetic */ void m5(String string, String string2, String string3, String string4, String string5) {
        int n;
        String[] stringArray;
        String string6 = string;
        String[] stringArray2 = string6.split(string5);
        string = stringArray2[0];
        string5 = stringArray2[1];
        if (string6.contains("<S>")) {
            stringArray = string5.split(string4);
            double d = Double.parseDouble(stringArray[1]);
            double d2 = Double.parseDouble(stringArray[2]);
            ShowElementCollection showElementCollection = this;
            this.f2 = new AbsolutePosition(this, d, d2);
            string5 = stringArray[0];
            string = string.replace("<S>", "");
        }
        stringArray = string5.split(string3);
        this.f3 = stringArray[1];
        string5 = stringArray[0];
        this.f4 = new SimpleElementCollection(string5, "<&>");
        String[] stringArray3 = this.m7(string).split(string2);
        int n2 = stringArray3.length;
        int n3 = n = 0;
        while (n3 < n2) {
            String string7 = stringArray3[n];
            super.f2.add(new Element(string7));
            n3 = ++n;
        }
    }

    public List<String> m6() {
        return this.f1;
    }

    private /* synthetic */ void m7(String string, String string2, String string3, String string4) {
        int n;
        String string5;
        String string6;
        String[] stringArray;
        if (string.contains("<S>")) {
            stringArray = string.split(string4);
            double d = Double.parseDouble(stringArray[1]);
            double d2 = Double.parseDouble(stringArray[2]);
            ShowElementCollection showElementCollection = this;
            this.f2 = new AbsolutePosition(this, d, d2);
            string5 = string6 = stringArray[0].replace("<S>", "");
        } else {
            string5 = string6 = string;
        }
        stringArray = string5.split(string3);
        if (stringArray.length >= 3) {
            this.f1 = Arrays.asList(stringArray[1].split(string2));
        }
        this.f3 = stringArray[stringArray.length - 1];
        string6 = stringArray[0];
        String[] stringArray2 = this.m7(string6).split(string2);
        int n2 = stringArray2.length;
        int n3 = n = 0;
        while (n3 < n2) {
            String string7 = stringArray2[n];
            super.f2.add(new Element(string7));
            n3 = ++n;
        }
    }

    public ShowElementCollection(String string, String string2, String string3, String string4, String string5) {
        if (string.contains(string5)) {
            this.m5(string, string2, string3, string4, string5);
            return;
        }
        this.m7(string, string2, string3, string4);
    }

    private class AbsolutePosition {
        private double f1;
        private double f2;
        final /* synthetic */ ShowElementCollection f3;

        public double m1() {
            return this.f1;
        }

        public AbsolutePosition(ShowElementCollection showElementCollection, double d, double d2) {
            AbsolutePosition absolutePosition = this;
            AbsolutePosition absolutePosition2 = this;
            this.f3 = showElementCollection;
            absolutePosition2.f2 = 0.0;
            absolutePosition2.f1 = 0.0;
            absolutePosition.f2 = d;
            absolutePosition.f1 = d2;
        }

        public double m2() {
            return this.f2;
        }
    }
}

