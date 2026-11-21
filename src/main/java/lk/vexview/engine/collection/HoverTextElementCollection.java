/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.engine.collection;

import lk.vexview.engine.base.AbstractElementCollection;
import lk.vexview.engine.base.Element;
import lk.vexview.engine.collection.SimpleElementCollection;

public class HoverTextElementCollection
extends AbstractElementCollection {
    private SimpleElementCollection f1;

    public SimpleElementCollection m1() {
        return this.f1;
    }

    public void m2(String string) {
        HoverTextElementCollection hoverTextElementCollection = this;
        super.f1 = new StringBuilder(string);
    }

    public HoverTextElementCollection(String string, String string2, String string3) {
        int n;
        String[] stringArray;
        HoverTextElementCollection hoverTextElementCollection = this;
        string = hoverTextElementCollection.m7(string);
        if (string.contains(string3)) {
            stringArray = string.split(string3);
            HoverTextElementCollection hoverTextElementCollection2 = this;
            hoverTextElementCollection2.f1 = new SimpleElementCollection(stringArray[1], string2);
            string = stringArray[0];
        }
        stringArray = string.split(string2);
        int n2 = stringArray.length;
        int n3 = n = 0;
        while (n3 < n2) {
            string3 = stringArray[n];
            this.f2.add(new Element(string3));
            n3 = ++n;
        }
    }
}

