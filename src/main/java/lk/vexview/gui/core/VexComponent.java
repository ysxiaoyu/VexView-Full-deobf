/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import lk.vexview.engine.collection.SimpleElementCollection;
import lk.vexview.gui.core.VexHoverText;

public abstract class VexComponent {
    VexHoverText f1;
    private List<Future<Boolean>> f2;

    public VexComponent m1(SimpleElementCollection simpleElementCollection) {
        if (simpleElementCollection != null && simpleElementCollection.m10().equals("hov")) {
            VexComponent vexComponent = this;
            vexComponent.f1 = new VexHoverText(simpleElementCollection.m3(0), simpleElementCollection.m3(1), simpleElementCollection.m3(2), simpleElementCollection.m3(3), simpleElementCollection.m1(4));
        }
        return this;
    }

    public void m2() {
    }

    public VexComponent() {
        VexComponent vexComponent = this;
        vexComponent.f2 = new ArrayList<Future<Boolean>>();
    }

    public void m3(Future<Boolean> future) {
        this.f2.add(future);
    }

    public List<Future<Boolean>> m4() {
        return this.f2;
    }

    public VexHoverText m5() {
        return this.f1;
    }

    public boolean m6(int n, int n2, int n3, int n4, int n5, int n6) {
        return this.f1 != null && this.f1.m1(n, n2, n3, n4, n5, n6);
    }
}

