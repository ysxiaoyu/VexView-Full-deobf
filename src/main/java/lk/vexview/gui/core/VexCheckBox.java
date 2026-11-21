/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.core;

import lk.vexview.engine.BufferImageLib;
import lk.vexview.gui.core.Downable;
import lk.vexview.gui.core.VexComponent;

public class VexCheckBox
extends VexComponent
implements Downable {
    public int f1;
    public int f2;
    public boolean f3;
    public int f4;
    public String f5;
    public int f6;
    public int f7;
    public String f8;

    public VexCheckBox(int n, String string, String string2, int n2, int n3, int n4, int n5, boolean bl) {
        VexCheckBox vexCheckBox = this;
        VexCheckBox vexCheckBox2 = this;
        VexCheckBox vexCheckBox3 = this;
        VexCheckBox vexCheckBox4 = this;
        this.f3 = false;
        vexCheckBox4.f6 = n;
        vexCheckBox4.f1 = n2;
        vexCheckBox3.f7 = n3;
        vexCheckBox3.f2 = n4;
        vexCheckBox2.f4 = n5;
        vexCheckBox2.f5 = string;
        vexCheckBox.f8 = string2;
        vexCheckBox.f3 = bl;
    }

    @Override
    public void m2() {
        VexCheckBox vexCheckBox = this;
        VexCheckBox vexCheckBox2 = this;
        vexCheckBox.m3(BufferImageLib.m4(vexCheckBox.f5, vexCheckBox2, "CHECKBOX"));
        vexCheckBox.m3(BufferImageLib.m4(vexCheckBox2.f8, this, "CHECKBOX"));
    }
}

