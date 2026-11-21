/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.hud.core;

import lk.vexview.hud.HUDViewer;

public abstract class VexShow {
    public double f1;
    public int f2;
    public boolean f3;
    long f4;
    public double f5;
    public int f6;
    public String f7;

    public VexShow m1(double d) {
        this.f5 = d;
        return this;
    }

    public VexShow m2(double d) {
        this.f1 = d;
        return this;
    }

    public void m3() {
        if (this.f2 != 0 && System.currentTimeMillis() - this.f4 >= (long)(this.f2 * 1000)) {
            this.f3 = true;
            HUDViewer.s3.remove(this.f7);
            HUDViewer.s2.remove(this.f7);
        }
    }

    public abstract void m4();

    public VexShow() {
        VexShow vexShow = this;
        this.f3 = false;
        vexShow.f1 = -1.0;
        vexShow.f5 = -1.0;
        vexShow.f4 = System.currentTimeMillis();
    }
}

