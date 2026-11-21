/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.tag.core;

import lk.vexview.VexView;
import lk.vexview.engine.BufferImageLib;
import lk.vexview.tag.core.SpiltImageTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class McImageTag
extends SpiltImageTag {
    @Override
    public void m5(String string, double d, double d2, double d3, float f, float f2, float f3, boolean bl) {
        if (BufferImageLib.s1.containsKey(string)) {
            EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().player;
            float f4 = Minecraft.getMinecraft().getRenderPartialTicks();
            EntityPlayerSP entityPlayerSP2 = entityPlayerSP;
            double d4 = entityPlayerSP2.prevPosX + (entityPlayerSP.posX - entityPlayerSP.prevPosX) * (double)f4;
            EntityPlayerSP entityPlayerSP3 = entityPlayerSP;
            double d5 = entityPlayerSP2.prevPosY + (entityPlayerSP3.posY - entityPlayerSP.prevPosY) * (double)f4;
            double d6 = entityPlayerSP3.prevPosZ + (entityPlayerSP.posZ - entityPlayerSP.prevPosZ) * (double)f4;
            GlStateManager.pushMatrix();
            GlStateManager.enableTexture2D();
            GlStateManager.disablePolygonOffset();
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.disableCull();
            GL11.glEnable((int)34383);
            GL11.glTranslated((double)(d - d4), (double)(d2 - d5), (double)(d3 - d6));
            McImageTag mcImageTag = this;
            McImageTag mcImageTag2 = this;
            GL11.glScaled((double)(this.f2 / (float)this.f8), (double)(mcImageTag.f1 / (float)mcImageTag.f4), (double)(mcImageTag2.f1 / (float)mcImageTag2.f4));
            GlStateManager.rotate((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            if (Minecraft.getMinecraft().getRenderManager().options != null && bl) {
                GlStateManager.rotate((float)Minecraft.getMinecraft().getRenderManager().playerViewY, (float)0.0f, (float)1.0f, (float)0.0f);
                GlStateManager.rotate((float)((float)(Minecraft.getMinecraft().getRenderManager().options.thirdPersonView == 2 ? -1 : 1) * Minecraft.getMinecraft().getRenderManager().playerViewX), (float)1.0f, (float)0.0f, (float)0.0f);
            }
            GlStateManager.rotate((float)f, (float)1.0f, (float)0.0f, (float)0.0f);
            GlStateManager.rotate((float)f2, (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)f3, (float)0.0f, (float)0.0f, (float)1.0f);
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
            Minecraft.getMinecraft().getTextureManager().bindTexture(BufferImageLib.s1.get(string));
            McImageTag mcImageTag3 = this;
            McImageTag mcImageTag4 = this;
            McImageTag.m4(0.0, 0.0, 0.0, this.f3, this.f9, mcImageTag3.f6, mcImageTag3.f7, mcImageTag4.f8, mcImageTag4.f4, this.f8, this.f4);
            GlStateManager.enableLighting();
            GlStateManager.enableCull();
            GL11.glPopMatrix();
        }
    }

    @Override
    public void m1() {
        VexView.m2(() -> {
            BufferImageLib.m14(this.f5);
            return true;
        });
    }

    public McImageTag(String string, double d, double d2, double d3, int n, int n2, float f, float f2, int n3, int n4, int n5, int n6, float f3, float f4, float f5, boolean bl, boolean bl2) {
        super(string, d, d2, d3, n, n2, f, f2, n3, n4, n5, n6, f3, f4, f5, bl, bl2);
    }
}

