/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.tag.core;

import net.minecraft.entity.player.EntityPlayer;

public abstract class VexTag {
    float f1;
    double f2;
    double f3;
    public boolean f4;
    double f5;
    float f6;
    boolean f7;
    float f8;

    public VexTag(double d, double d2, double d3, float f, float f2, float f3, boolean bl, boolean bl2) {
        VexTag vexTag = this;
        VexTag vexTag2 = this;
        VexTag vexTag3 = this;
        VexTag vexTag4 = this;
        vexTag4.f5 = d;
        vexTag4.f3 = d2;
        vexTag3.f2 = d3;
        vexTag3.f1 = f;
        vexTag2.f6 = f2;
        vexTag2.f8 = f3;
        vexTag.f7 = bl;
        vexTag.f4 = bl2;
    }

    public abstract void m1();

    public abstract void m2();

    public abstract void m3(EntityPlayer var1);
}

