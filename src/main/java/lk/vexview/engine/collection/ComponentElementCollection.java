/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.engine.collection;

import lk.vexview.engine.base.AbstractElementCollection;
import lk.vexview.engine.base.Element;

public class ComponentElementCollection
extends AbstractElementCollection {
    public ComponentElementCollection(String string, String string2) {
        int n;
        String[] stringArray = string.split(string2);
        int n2 = stringArray.length;
        int n3 = n = 0;
        while (n3 < n2) {
            String string3 = stringArray[n];
            this.f2.add(new Element(string3));
            n3 = ++n;
        }
    }
}

