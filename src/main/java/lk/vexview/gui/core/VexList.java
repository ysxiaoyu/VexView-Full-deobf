/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.core;

import java.util.ArrayList;
import java.util.List;
import lk.vexview.gui.core.DynamicUpdate;
import lk.vexview.gui.core.VexComponent;
import lk.vexview.gui.core.VexSlot;

public class VexList
implements DynamicUpdate {
    public int f1;
    public int f2;
    public int f3;
    public int f4;
    public int f5;
    public List<VexComponent> f6;

    @Override
    public void m1(VexComponent vexComponent) {
        if (vexComponent != null && !this.m3(vexComponent)) {
            this.f6.add(vexComponent);
        }
    }

    @Override
    public void m2(VexComponent vexComponent) {
        this.f6.remove(vexComponent);
    }

    public List<VexComponent> m1() {
        return this.f6;
    }

    public boolean m3(VexComponent vexComponent) {
        return this.f6.contains(vexComponent);
    }

    public List<VexSlot> m4() {
        ArrayList<VexSlot> arrayList = new ArrayList<VexSlot>();
        for (VexComponent vexComponent : this.f6) {
            if (!(vexComponent instanceof VexSlot)) continue;
            arrayList.add((VexSlot)vexComponent);
        }
        return arrayList;
    }

    public VexList(int n, int n2, int n3, int n4, int n5) {
        VexList vexList = this;
        VexList vexList2 = this;
        VexList vexList3 = this;
        this.f6 = new ArrayList<VexComponent>();
        this.f4 = n;
        vexList2.f1 = n2;
        vexList2.f3 = n3;
        vexList.f5 = n4;
        vexList.f2 = n5;
    }
}

