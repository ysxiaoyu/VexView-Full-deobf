/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.engine.collection;

import lk.vexview.engine.base.AbstractElementCollection;
import lk.vexview.engine.base.Element;

public class TagElementCollection
extends AbstractElementCollection {
    public String m1() {
        return this.m8(0);
    }

    public String m2() {
        return this.m8(1);
    }

    public TagElementCollection(String string, String string2, String string3) {
        int n;
        TagElementCollection tagElementCollection = this;
        tagElementCollection.f1 = new StringBuilder(string3);
        String[] stringArray = string.split(string2);
        int n2 = stringArray.length;
        int n3 = n = 0;
        while (n3 < n2) {
            String string4 = stringArray[n];
            this.f2.add(new Element(string4));
            n3 = ++n;
        }
    }
}

