/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.core;

import lk.vexview.gui.core.VexComponent;
import lk.vexview.gui.core.widget.VexTextFieldWidget;
import net.minecraft.client.Minecraft;

public class VexTextBox
extends VexComponent {
    public int f1;
    public int f2;
    public int f3;
    public int f4;
    public int f5;
    public int f6;
    public int f7;
    public int f8;
    public String f9;
    public boolean f10;

    public VexTextBox(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, String string) {
        VexTextBox vexTextBox = this;
        VexTextBox vexTextBox2 = this;
        VexTextBox vexTextBox3 = this;
        VexTextBox vexTextBox4 = this;
        VexTextBox vexTextBox5 = this;
        vexTextBox5.f1 = n;
        vexTextBox5.f3 = n2;
        vexTextBox4.f4 = n4;
        vexTextBox4.f2 = n3;
        vexTextBox3.f5 = n5;
        vexTextBox3.f6 = n6;
        vexTextBox2.f7 = n8;
        vexTextBox2.f8 = n7;
        vexTextBox.f9 = string;
        vexTextBox.f10 = true;
    }

    public VexTextFieldWidget m1(int n, int n2, int n3, int n4, Minecraft minecraft) {
        int n5 = this.f1 == -1 ? n3 / 2 - this.f2 / 2 : this.f1;
        n3 = this.f3 == -1 ? n4 / 2 - this.f4 / 2 : this.f3;
        VexTextBox vexTextBox = this;
        return new VexTextFieldWidget(this.f6, minecraft.fontRenderer, n + n5, n2 + n3, vexTextBox.f2, vexTextBox.f4, this);
    }

    public VexTextBox(int n, int n2, int n3, int n4, int n5, int n6, String string) {
        VexTextBox vexTextBox = this;
        VexTextBox vexTextBox2 = this;
        VexTextBox vexTextBox3 = this;
        VexTextBox vexTextBox4 = this;
        vexTextBox4.f1 = n;
        vexTextBox4.f3 = n2;
        vexTextBox3.f4 = n4;
        vexTextBox3.f2 = n3;
        vexTextBox2.f5 = n5;
        vexTextBox2.f6 = n6;
        vexTextBox.f9 = string;
        vexTextBox.f10 = false;
    }
}

