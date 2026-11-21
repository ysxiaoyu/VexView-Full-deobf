/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.hud.core;

import java.util.ArrayList;
import java.util.List;
import lk.vexview.hud.core.VexShow;

public class VexTextShow
extends VexShow {
    private List<String> f1;
    public double f2;
    public int f3;
    public int f4;

    public VexTextShow(String string, int n, int n2, double d, int n3, List<String> list, int n4) {
        VexTextShow vexTextShow = this;
        VexTextShow vexTextShow2 = this;
        VexTextShow vexTextShow3 = this;
        this.f2 = 1.0;
        vexTextShow3.f7 = string;
        vexTextShow3.f3 = n;
        vexTextShow2.f4 = n2;
        vexTextShow2.f2 = n3;
        vexTextShow.f2 = d;
        VexTextShow vexTextShow4 = this;
        vexTextShow.f1 = new ArrayList<String>(list);
        vexTextShow.f6 = n4;
    }

    @Override
    public void m4() {
    }

    public List<String> m1() {
        return this.f1;
    }
}

