/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.core;

import java.util.ArrayList;
import java.util.List;
import lk.vexview.gui.core.VexComponent;
import lk.vexview.gui.core.widget.VexTextAreaWidget;
import net.minecraft.client.Minecraft;
import org.json.JSONArray;

public class VexTextArea
extends VexComponent {
    public int f1;
    public int f2;
    public int f3;
    public int f4;
    public int f5;
    public int f6;
    public int f7;
    public int f8;
    public List<String> f9;

    public VexTextArea(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, String string) {
        VexTextArea vexTextArea = this;
        VexTextArea vexTextArea2 = this;
        VexTextArea vexTextArea3 = this;
        VexTextArea vexTextArea4 = this;
        VexTextArea vexTextArea5 = this;
        vexTextArea5.f9 = new ArrayList<String>();
        vexTextArea4.f2 = n;
        vexTextArea4.f7 = n2;
        vexTextArea3.f1 = n4;
        vexTextArea3.f5 = n3;
        vexTextArea2.f8 = n5;
        vexTextArea2.f3 = n6;
        vexTextArea.f6 = n7;
        vexTextArea.f4 = n8;
        new JSONArray(string).toList().forEach(object -> this.f9.add((String)object));
    }

    public VexTextAreaWidget m2(int n, int n2, int n3, int n4, Minecraft minecraft) {
        int n5 = this.f2 == -1 ? n3 / 2 - this.f5 / 2 : this.f2;
        n3 = this.f7 == -1 ? n4 / 2 - this.f1 / 2 : this.f7;
        VexTextArea vexTextArea = this;
        VexTextArea vexTextArea2 = this;
        return new VexTextAreaWidget(this.f3, minecraft.fontRenderer, n + n5, n2 + n3, vexTextArea.f5, vexTextArea.f1, vexTextArea2.f4, vexTextArea2.f6, this);
    }
}

