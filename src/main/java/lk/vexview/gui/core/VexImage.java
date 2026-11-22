/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.core;

import lk.vexview.engine.BufferImageLib;
import lk.vexview.gui.core.Downable;
import lk.vexview.gui.core.VexComponent;
import lk.vexview.scoreboard.SBPacketBuffer;
import lk.vexview.ui.verification.VerNetManager;

public class VexImage
extends VexComponent
implements Downable {
    public int f1;
    public int f2;
    public int f3;
    public int f4;
    public int f5;
    public int f6;
    public int f7;
    boolean f8;
    public int f9;
    public int f10;
    public String f11;
    public int f12;

    public boolean m1() {
        return this.f8;
    }

    public boolean equals(Object object) {
        if (object instanceof VexImage) {
            VexImage vexImage = (VexImage)object;
            if (vexImage.f1 == this.f1 && vexImage.f2 == this.f2 && vexImage.f6 == this.f6 && vexImage.f12 == this.f12 && vexImage.f10 == this.f10 && vexImage.f7 == this.f7 && vexImage.f11.equals(this.f11)) {
                return true;
            }
        }
        return false;
    }

    public VexImage(int n, int n2, int n3, int n4, int n5, int n6, String string) {
        VexImage vexImage = this;
        VexImage vexImage2 = this;
        VexImage vexImage3 = this;
        VexImage vexImage4 = this;
        VexImage vexImage5 = this;
        this.f9 = 0;
        vexImage5.f5 = 0;
        vexImage5.f8 = false;
        vexImage4.f1 = n;
        vexImage4.f2 = n2;
        vexImage3.f10 = n3;
        vexImage3.f7 = n4;
        vexImage2.f6 = n5;
        vexImage2.f12 = n6;
        vexImage.f3 = n5;
        vexImage.f4 = n6;
        this.f11 = string;
        if (this.f11.contains("[local]")) {
            this.f8 = true;
        }
    }

    public VexImage(int n, int n2, int n3, int n4, int n5, int n6, String string, int n7, int n8, int n9, int n10) {
        VexImage vexImage = this;
        VexImage vexImage2 = this;
        VexImage vexImage3 = this;
        VexImage vexImage4 = this;
        VexImage vexImage5 = this;
        VexImage vexImage6 = this;
        this.f9 = 0;
        vexImage6.f5 = 0;
        vexImage6.f8 = false;
        vexImage5.f1 = n;
        vexImage5.f2 = n2;
        vexImage4.f10 = n3;
        vexImage4.f7 = n4;
        vexImage3.f6 = n5;
        vexImage3.f12 = n6;
        vexImage2.f9 = n7;
        vexImage2.f5 = n8;
        vexImage.f3 = n9;
        vexImage.f4 = n10;
        this.f11 = string;
        if (this.f11.contains("[local]")) {
            this.f8 = true;
        }
    }

    @Override
    public void m2() {
        VexImage vexImage = this;
        vexImage.m3(BufferImageLib.m4(this.f11, vexImage, "IMAGE"));
    }
}

