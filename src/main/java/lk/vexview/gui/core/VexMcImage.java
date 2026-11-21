/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.core;

import lk.vexview.chat.ChatString;
import lk.vexview.engine.BufferImageLib;
import lk.vexview.gui.core.VexComponent;
import lk.vexview.gui.core.VexImage;

public class VexMcImage
extends VexComponent {
    public int f1;
    public int f2;
    public int f3;
    public int f4;
    public int f5;
    public int f6;
    public int f7;
    public int f8;
    public int f9;
    public int f10;
    boolean f11;
    public String f12;

    public VexMcImage(int n, int n2, int n3, int n4, int n5, int n6, String string, int n7, int n8, int n9, int n10) {
        VexMcImage vexMcImage = this;
        VexMcImage vexMcImage2 = this;
        VexMcImage vexMcImage3 = this;
        VexMcImage vexMcImage4 = this;
        VexMcImage vexMcImage5 = this;
        VexMcImage vexMcImage6 = this;
        this.f2 = 0;
        vexMcImage6.f5 = 0;
        vexMcImage6.f11 = false;
        vexMcImage5.f1 = n;
        vexMcImage5.f10 = n2;
        vexMcImage4.f3 = n3;
        vexMcImage4.f8 = n4;
        vexMcImage3.f9 = n5;
        vexMcImage3.f7 = n6;
        vexMcImage2.f2 = n7;
        vexMcImage2.f5 = n8;
        vexMcImage.f4 = n9;
        vexMcImage.f6 = n10;
        this.f12 = string;
        if (this.f12.contains(ChatString.m1("eKqd_kc"))) {
            this.f11 = true;
        }
    }

    public boolean equals(Object object) {
        if (object instanceof VexImage) {
            VexImage vexImage = (VexImage)object;
            if (vexImage.f1 == this.f1 && vexImage.f2 == this.f10 && vexImage.f6 == this.f9 && vexImage.f12 == this.f7 && vexImage.f10 == this.f3 && vexImage.f7 == this.f8 && vexImage.f11.equals(this.f12)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void m2() {
        BufferImageLib.m14(this.f12);
    }
}

