/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.hud.core;

import lk.vexview.gui.core.VexGifImage;
import lk.vexview.gui.core.VexImage;
import lk.vexview.hud.core.VexShow;

public class VexImageShow
extends VexShow {
    boolean f1;
    public VexImage f2;
    String f3;

    @Override
    public void m4() {
        this.f2.m2();
    }

    public VexImageShow(String string, String string2, int n, int n2, int n3, int n4, int n5, boolean bl, int n6, int n7) {
        VexImageShow vexImageShow;
        if (bl) {
            vexImageShow = this;
            int n8 = n3;
            this.f2 = new VexGifImage(n, n2, n8, n4, n8, n4, string, n6);
        } else {
            vexImageShow = this;
            int n9 = n3;
            this.f2 = new VexImage(n, n2, n9, n4, n9, n4, string);
        }
        vexImageShow.f6 = n7;
        VexImageShow vexImageShow2 = this;
        VexImageShow vexImageShow3 = this;
        vexImageShow3.f7 = string2;
        super.f2 = n5;
        vexImageShow2.f1 = bl;
        vexImageShow2.f3 = string;
    }
}

