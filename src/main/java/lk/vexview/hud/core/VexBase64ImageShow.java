/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.hud.core;

import lk.vexview.gui.core.VexBase64Image;
import lk.vexview.hud.core.VexShow;

public class VexBase64ImageShow
extends VexShow {
    public VexBase64Image f1;

    @Override
    public void m4() {
    }

    public VexBase64ImageShow(String string, int n, int n2, int n3, int n4, int n5, int n6) {
        VexBase64ImageShow vexBase64ImageShow = this;
        VexBase64ImageShow vexBase64ImageShow2 = this;
        this.f1 = new VexBase64Image(n, n2, n3, n4, "0");
        this.f2 = n5;
        vexBase64ImageShow.f7 = string;
        vexBase64ImageShow.f6 = n6;
    }
}

