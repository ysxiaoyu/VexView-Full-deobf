/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.tag.core;

import lk.vexview.gui.core.VexEntityDraw;
import lk.vexview.tag.core.VexTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class VexEntityDrawTag
extends VexTag {
    VexEntityDraw f1;

    @Override
    public void m2() {
        VexEntityDrawTag vexEntityDrawTag = this;
        vexEntityDrawTag.m2(this.f5, vexEntityDrawTag.f3, this.f2, null);
    }

    public void m1(int n, Entity entity, EntityPlayer entityPlayer) {
        RenderManager renderManager;
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.scale((float)((float)n / 30.0f), (float)((float)n / 30.0f), (float)((float)n / 30.0f));
        VexEntityDrawTag vexEntityDrawTag = this;
        VexEntityDrawTag vexEntityDrawTag2 = this;
        GlStateManager.rotate((float)((VexTag)this).f1, (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.rotate((float)vexEntityDrawTag2.f6, (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)vexEntityDrawTag.f8, (float)0.0f, (float)0.0f, (float)1.0f);
        entity.rotationPitch = 0.0f;
        if (vexEntityDrawTag.f7) {
            float f = Minecraft.getMinecraft().getRenderPartialTicks();
            EntityPlayer entityPlayer2 = entityPlayer;
            float f2 = entityPlayer2.prevRotationYaw + (entityPlayer2.rotationYaw - entityPlayer.prevRotationYaw) * f;
            EntityPlayer entityPlayer3 = entityPlayer;
            Entity entity2 = entity;
            entity2.setRenderYawOffset(f2);
            entity2.setRotationYawHead(entityPlayer2.prevRotationYawHead + (entityPlayer3.rotationYawHead - entityPlayer3.prevRotationYawHead) * f);
        } else {
            entity.setRenderYawOffset(0.0f);
            entity.setRotationYawHead(0.0f);
        }
        RenderManager renderManager2 = renderManager = Minecraft.getMinecraft().getRenderManager();
        RenderManager renderManager3 = renderManager;
        renderManager3.setPlayerViewY(180.0f);
        renderManager3.setRenderShadow(false);
        renderManager2.renderEntity(entity, 0.0, 0.0, 0.0, 0.0f, 1.0f, false);
        renderManager2.setRenderShadow(true);
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture((int)OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture((int)OpenGlHelper.defaultTexUnit);
    }

    @Override
    public void m1() {
    }

    public VexEntityDrawTag(double d, double d2, double d3, float f, float f2, float f3, boolean bl, boolean bl2, int n, boolean bl3, String string, String string2) {
        super(d, d2, d3, f, f2, f3, bl, bl2);
        VexEntityDrawTag vexEntityDrawTag = this;
        vexEntityDrawTag.f1 = new VexEntityDraw(0, 0, n, bl3, string, string2);
    }

    @Override
    public void m3(EntityPlayer entityPlayer) {
        double d = 0.0;
        double d2 = 0.0;
        double d3 = 0.0;
        float f = Minecraft.getMinecraft().getRenderPartialTicks();
        if (entityPlayer != null) {
            EntityPlayer entityPlayer2 = entityPlayer;
            EntityPlayer entityPlayer3 = entityPlayer;
            d = entityPlayer2.prevPosX + (entityPlayer3.posX - entityPlayer.prevPosX) * (double)f;
            d2 = entityPlayer3.prevPosY + (entityPlayer.posY - entityPlayer.prevPosY) * (double)f;
            d3 = entityPlayer2.prevPosZ + (entityPlayer.posZ - entityPlayer.prevPosZ) * (double)f;
            VexEntityDrawTag vexEntityDrawTag = this;
            vexEntityDrawTag.m2(d + vexEntityDrawTag.f5, d2 + this.f3, d3 + this.f2, entityPlayer);
        }
    }

    public void m2(double d, double d2, double d3, EntityPlayer entityPlayer) {
        EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().player;
        float f = Minecraft.getMinecraft().getRenderPartialTicks();
        EntityPlayerSP entityPlayerSP2 = entityPlayerSP;
        EntityPlayerSP entityPlayerSP3 = entityPlayerSP;
        double d4 = entityPlayerSP2.prevPosX + (entityPlayerSP3.posX - entityPlayerSP.prevPosX) * (double)f;
        double d5 = entityPlayerSP3.prevPosY + (entityPlayerSP.posY - entityPlayerSP.prevPosY) * (double)f;
        double d6 = entityPlayerSP2.prevPosZ + (entityPlayerSP.posZ - entityPlayerSP.prevPosZ) * (double)f;
        GlStateManager.pushMatrix();
        GlStateManager.enableTexture2D();
        GlStateManager.disablePolygonOffset();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.disableCull();
        GL11.glEnable((int)34383);
        GL11.glTranslated((double)(d - d4), (double)(d2 - d5), (double)(d3 - d6));
        VexEntityDrawTag vexEntityDrawTag = this;
        vexEntityDrawTag.m1(this.f1.f2, vexEntityDrawTag.f1.f3, entityPlayer);
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }
}

