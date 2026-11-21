/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.tag.core;

import lk.vexview.tag.core.VexTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class TextTag
extends VexTag {
    boolean f1;
    String f2;

    @Override
    public void m1() {
    }

    @Override
    public void m2() {
        TextTag textTag = this;
        textTag.m1(this.f5, textTag.f3, ((VexTag)this).f2);
    }

    public TextTag(double d, double d2, double d3, float f, float f2, float f3, boolean bl, boolean bl2, boolean bl3, String string) {
        super(d, d2, d3, f, f2, f3, bl, bl2);
        TextTag textTag = this;
        textTag.f2 = string;
        textTag.f1 = bl3;
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
            TextTag textTag = this;
            textTag.m1(d + textTag.f5, d2 + this.f3, d3 + ((VexTag)this).f2);
        }
    }

    public void m1(double d, double d2, double d3) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().player;
        float f = Minecraft.getMinecraft().getRenderPartialTicks();
        EntityPlayerSP entityPlayerSP2 = entityPlayerSP;
        double d4 = entityPlayerSP2.prevPosX + (entityPlayerSP.posX - entityPlayerSP.prevPosX) * (double)f;
        EntityPlayerSP entityPlayerSP3 = entityPlayerSP;
        double d5 = entityPlayerSP2.prevPosY + (entityPlayerSP3.posY - entityPlayerSP.prevPosY) * (double)f;
        double d6 = entityPlayerSP3.prevPosZ + (entityPlayerSP.posZ - entityPlayerSP.prevPosZ) * (double)f;
        GlStateManager.disableLighting();
        GlStateManager.pushMatrix();
        GL11.glTranslated((double)(d - d4), (double)(d2 - d5), (double)(d3 - d6));
        GlStateManager.disableCull();
        GlStateManager.scale((float)-0.025f, (float)-0.025f, (float)0.025f);
        if (Minecraft.getMinecraft().getRenderManager().options != null && this.f7) {
            GlStateManager.rotate((float)Minecraft.getMinecraft().getRenderManager().playerViewY, (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)((float)(Minecraft.getMinecraft().getRenderManager().options.thirdPersonView == 2 ? -1 : 1) * -Minecraft.getMinecraft().getRenderManager().playerViewX), (float)1.0f, (float)0.0f, (float)0.0f);
        }
        TextTag textTag = this;
        GlStateManager.rotate((float)((VexTag)this).f1, (float)1.0f, (float)0.0f, (float)0.0f);
        GlStateManager.rotate((float)textTag.f6, (float)0.0f, (float)1.0f, (float)0.0f);
        GlStateManager.rotate((float)textTag.f8, (float)0.0f, (float)0.0f, (float)1.0f);
        GlStateManager.color((float)255.0f, (float)255.0f, (float)255.0f);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate((GlStateManager.SourceFactor)GlStateManager.SourceFactor.SRC_ALPHA, (GlStateManager.DestFactor)GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, (GlStateManager.SourceFactor)GlStateManager.SourceFactor.ONE, (GlStateManager.DestFactor)GlStateManager.DestFactor.ZERO);
        if (!this.f1) {
            BufferBuilder bufferBuilder;
            int n = fontRenderer.getStringWidth(this.f2) / 2;
            GlStateManager.disableTexture2D();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferBuilder2 = bufferBuilder = tessellator.getBuffer();
            int n2 = n;
            BufferBuilder bufferBuilder3 = bufferBuilder;
            bufferBuilder3.begin(7, DefaultVertexFormats.POSITION_COLOR);
            bufferBuilder3.pos((double)(-n - 1), -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            bufferBuilder.pos((double)(-n2 - 1), 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            bufferBuilder2.pos((double)(n2 + 1), 8.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            bufferBuilder2.pos((double)(n + 1), -1.0, 0.0).color(0.0f, 0.0f, 0.0f, 0.25f).endVertex();
            tessellator.draw();
        }
        GlStateManager.enableTexture2D();
        FontRenderer fontRenderer2 = fontRenderer;
        fontRenderer2.drawString(this.f2, -fontRenderer2.getStringWidth(this.f2) / 2, 0, 0x20FFFFFF);
        GlStateManager.enableDepth();
        GlStateManager.depthMask((boolean)true);
        FontRenderer fontRenderer3 = fontRenderer;
        fontRenderer3.drawString(this.f2, -fontRenderer3.getStringWidth(this.f2) / 2, 0, -1);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        GlStateManager.enableCull();
        GL11.glPopMatrix();
    }
}

