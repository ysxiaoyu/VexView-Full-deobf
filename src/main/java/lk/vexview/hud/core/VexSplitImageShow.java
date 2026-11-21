/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.hud.core;

import lk.vexview.gui.core.VexImage;
import lk.vexview.hud.core.VexShow;

public class VexSplitImageShow
extends VexShow {
    String f1;
    public VexImage f2;

    public VexSplitImageShow(String string, String string2, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12) {
        VexSplitImageShow vexSplitImageShow = this;
        VexSplitImageShow vexSplitImageShow2 = this;
        vexSplitImageShow2.f7 = string2;
        vexSplitImageShow2.f1 = string;
        super.f2 = n7;
        VexSplitImageShow vexSplitImageShow3 = this;
        vexSplitImageShow.f2 = new VexImage(n, n2, n3, n4, n5, n6, string, n8, n9, n10, n11);
        vexSplitImageShow.f6 = n12;
    }

    @Override
    public void m4() {
        this.f2.m2();
    }
}

