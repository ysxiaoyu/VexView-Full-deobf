/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.hud.core;

import lk.vexview.gui.core.VexPlayerDraw;
import lk.vexview.hud.core.VexShow;

public class VexPlayerDrawShow
extends VexShow {
    public VexPlayerDraw f1;
    public boolean f2;

    @Override
    public void m4() {
    }

    public VexPlayerDrawShow(String string, int n, int n2, boolean bl, int n3, int n4, int n5, boolean bl2, String string2, String string3, String string4) {
        VexPlayerDrawShow vexPlayerDrawShow = this;
        VexPlayerDrawShow vexPlayerDrawShow2 = this;
        this.f2 = false;
        vexPlayerDrawShow2.f7 = string;
        vexPlayerDrawShow2.f6 = n;
        super.f2 = n2;
        vexPlayerDrawShow.f2 = bl;
        VexPlayerDrawShow vexPlayerDrawShow3 = this;
        vexPlayerDrawShow.f1 = new VexPlayerDraw(n3, n4, n5, bl2, string2, string3, string4);
    }
}

