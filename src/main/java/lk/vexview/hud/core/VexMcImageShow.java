/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.hud.core;

import lk.vexview.gui.core.VexMcImage;
import lk.vexview.hud.core.VexSplitImageShow;

public class VexMcImageShow
extends VexSplitImageShow {
    VexMcImage f1;

    @Override
    public void m4() {
        this.f1.m2();
    }

    public VexMcImageShow(String string, String string2, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10, int n11, int n12) {
        super(string, string2, n, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12);
        VexMcImageShow vexMcImageShow = this;
        vexMcImageShow.f1 = new VexMcImage(n, n2, n3, n4, n5, n6, string, n8, n9, n10, n11);
    }
}

