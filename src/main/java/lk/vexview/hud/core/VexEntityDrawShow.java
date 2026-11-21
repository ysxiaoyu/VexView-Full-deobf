/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.hud.core;

import lk.vexview.gui.core.VexEntityDraw;
import lk.vexview.hud.core.VexShow;

public class VexEntityDrawShow
extends VexShow {
    public boolean f1;
    public VexEntityDraw f2;

    @Override
    public void m4() {
    }

    public VexEntityDrawShow(String string, int n, int n2, boolean bl, int n3, int n4, int n5, boolean bl2, String string2, String string3) {
        VexEntityDrawShow vexEntityDrawShow = this;
        VexEntityDrawShow vexEntityDrawShow2 = this;
        this.f1 = false;
        vexEntityDrawShow2.f7 = string;
        vexEntityDrawShow2.f6 = n;
        super.f2 = n2;
        vexEntityDrawShow.f1 = bl;
        VexEntityDrawShow vexEntityDrawShow3 = this;
        vexEntityDrawShow.f2 = new VexEntityDraw(n3, n4, n5, bl2, string2, string3);
    }
}

