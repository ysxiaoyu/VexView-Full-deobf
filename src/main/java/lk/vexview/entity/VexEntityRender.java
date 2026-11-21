/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;

public class VexEntityRender {
    private static long s1;
    private static float s2;

    private static /* synthetic */ void m1(boolean bl) {
        if (bl && System.currentTimeMillis() - s1 >= 1L) {
            s2 = s2 >= 360.0f ? 0.0f : (float)((double)s2 + 0.2);
            s1 = System.currentTimeMillis();
        }
    }

    static {
        s2 = 0.0f;
        s1 = System.currentTimeMillis();
    }

    public static void m2(int n, int n2, int n3, float f, float f2, Entity entity, boolean bl) {
        if (!bl) {
            f = 0.0f;
            f2 = 0.0f;
        }
        if (entity instanceof EntityLivingBase) {
            RenderManager renderManager;
            EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            GlStateManager.enableColorMaterial();
            GlStateManager.pushMatrix();
            GlStateManager.translate((float)n, (float)n2, (float)50.0f);
            GlStateManager.scale((float)(-n3), (float)n3, (float)n3);
            GlStateManager.rotate((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
            EntityLivingBase entityLivingBase2 = entityLivingBase;
            float f3 = entityLivingBase2.renderYawOffset;
            float f4 = entityLivingBase2.rotationYaw;
            float f5 = entityLivingBase2.rotationPitch;
            float f6 = entityLivingBase2.prevRotationYawHead;
            float f7 = entityLivingBase2.rotationYawHead;
            GlStateManager.rotate((float)135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            RenderHelper.enableStandardItemLighting();
            GlStateManager.rotate((float)-135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)(-((float)Math.atan(f2 / 40.0f)) * 20.0f), (float)1.0f, (float)0.0f, (float)0.0f);
            entityLivingBase2.renderYawOffset = (float)Math.atan(f / 40.0f) * 20.0f;
            entityLivingBase2.rotationYaw = (float)Math.atan(f / 40.0f) * 40.0f;
            entityLivingBase2.rotationPitch = -((float)Math.atan(f2 / 40.0f)) * 20.0f;
            entityLivingBase2.rotationYawHead = entityLivingBase2.rotationYaw;
            entityLivingBase2.prevRotationYawHead = entityLivingBase2.rotationYaw;
            GlStateManager.translate((float)0.0f, (float)0.0f, (float)0.0f);
            RenderManager renderManager2 = renderManager = Minecraft.getMinecraft().getRenderManager();
            renderManager.setPlayerViewY(180.0f);
            renderManager2.setRenderShadow(false);
            RenderLivingBase renderLivingBase = (RenderLivingBase)renderManager2.getEntityRenderObject((Entity)entityLivingBase);
            RenderManager renderManager3 = renderManager;
            renderManager3.renderEntity((Entity)entityLivingBase, 0.0, 0.0, 0.0, 0.0f, 1.0f, false);
            renderManager3.setRenderShadow(true);
            EntityLivingBase entityLivingBase3 = entityLivingBase;
            EntityLivingBase entityLivingBase4 = entityLivingBase;
            entityLivingBase4.renderYawOffset = f3;
            entityLivingBase4.rotationYaw = f4;
            entityLivingBase3.rotationPitch = f5;
            entityLivingBase.prevRotationYawHead = f6;
            entityLivingBase3.rotationYawHead = f7;
            GlStateManager.popMatrix();
            RenderHelper.disableStandardItemLighting();
            GlStateManager.disableRescaleNormal();
            GlStateManager.setActiveTexture((int)OpenGlHelper.lightmapTexUnit);
            GlStateManager.disableTexture2D();
            GlStateManager.setActiveTexture((int)OpenGlHelper.defaultTexUnit);
            return;
        }
        VexEntityRender.m3(n, n2, n3, entity, false);
    }

    public static void m3(int n, int n2, int n3, Entity entity, boolean bl) {
        RenderManager renderManager;
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)n, (float)n2, (float)50.0f);
        GlStateManager.scale((float)(-n3), (float)n3, (float)n3);
        GlStateManager.rotate((float)180.0f, (float)0.0f, (float)0.0f, (float)1.0f);
        GlStateManager.rotate((float)135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate((float)-135.0f, (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.translate((float)0.0f, (float)0.0f, (float)0.0f);
        if (bl) {
            Entity entity2 = entity;
            entity2.setRotationYawHead(0.0f);
            entity2.setRenderYawOffset(0.0f);
            VexEntityRender.m1(bl);
            GlStateManager.rotate((float)s2, (float)0.0f, (float)1.0f, (float)0.0f);
        } else {
            entity.setRotationYawHead(-30.0f);
            entity.setRenderYawOffset(-30.0f);
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
}

