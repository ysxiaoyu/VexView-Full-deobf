/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.engine.collection;

import java.util.ArrayList;
import java.util.List;
import lk.vexview.engine.base.AbstractElementCollection;
import lk.vexview.engine.base.Element;

public class SimpleElementCollection
extends AbstractElementCollection {
    protected SimpleElementCollection(String string, String string2) {
        int n;
        SimpleElementCollection simpleElementCollection = this;
        String[] stringArray = simpleElementCollection.m7(string).split(string2);
        int n2 = stringArray.length;
        int n3 = n = 0;
        while (n3 < n2) {
            String string3 = stringArray[n];
            this.f2.add(new Element(string3));
            n3 = ++n;
        }
    }

    public List<String> m1(int n) {
        ArrayList<String> arrayList = new ArrayList<String>();
        this.m11(n + 1).forEach(element -> arrayList.add(element.m1()));
        return arrayList;
    }
}

