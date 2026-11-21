/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.announcement;

import lk.vexview.announcement.AnnGui;
import lk.vexview.announcement.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class Announcement {
    public String f1;
    public Type f2;
    int f3;

    public Announcement(String string, Type type) {
        Announcement announcement = this;
        this.f3 = 0;
        announcement.f1 = string;
        announcement.f2 = type;
    }

    void m1(int n, int n2) {
        int n3;
        int n4 = Math.min(this.f3, this.m2());
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        int n5 = n2;
        Gui.drawRect((int)(n - n4), (int)n5, (int)(n - n4 + this.m2()), (int)(n5 + 12), (int)0x33000000);
        Announcement announcement = this;
        fontRenderer.drawString(announcement.f1, n - n4 + 12, n2 + 1, 0xFFFFFF);
        if (announcement.f2 == Type.s4) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(AnnGui.s4);
            n3 = n;
        } else if (this.f2 == Type.s3) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(AnnGui.s3);
            n3 = n;
        } else {
            if (this.f2 == Type.s1) {
                Minecraft.getMinecraft().getTextureManager().bindTexture(AnnGui.s1);
            }
            n3 = n;
        }
        Gui.drawScaledCustomSizeModalRect((int)(n3 - n4 + 1), (int)(n2 + 1), (float)0.0f, (float)0.0f, (int)10, (int)10, (int)10, (int)10, (float)10.0f, (float)10.0f);
        if (this.f3 < this.m3()) {
            ++this.f3;
        }
    }

    private /* synthetic */ int m2() {
        return Minecraft.getMinecraft().fontRenderer.getStringWidth(this.f1) + 15;
    }

    public int m3() {
        if (this.m2() < 300) {
            return 1000;
        }
        return this.m2() + 2000;
    }
}

