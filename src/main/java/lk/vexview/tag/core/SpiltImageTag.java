/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.tag.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;
import lk.vexview.VexConfig;
import lk.vexview.VexView;
import lk.vexview.engine.BufferImageLib;
import lk.vexview.net.NetPacketManager;
import lk.vexview.net.packet.PacketDownload;
import lk.vexview.tag.core.VexTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class SpiltImageTag
extends VexTag {
    float f1;
    float f2;
    int f3;
    int f4;
    String f5;
    int f6;
    int f7;
    int f8;
    int f9;

    public SpiltImageTag(String string, double d, double d2, double d3, int n, int n2, float f, float f2, int n3, int n4, int n5, int n6, float f3, float f4, float f5, boolean bl, boolean bl2) {
        super(d - (double)(f / 2.0f), d2 - (double)(f2 / 2.0f), d3, f3, f4, f5, bl, bl2);
        SpiltImageTag spiltImageTag = this;
        SpiltImageTag spiltImageTag2 = this;
        SpiltImageTag spiltImageTag3 = this;
        SpiltImageTag spiltImageTag4 = this;
        this.f5 = string;
        spiltImageTag4.f8 = n;
        spiltImageTag4.f4 = n2;
        spiltImageTag3.f1 = f2;
        spiltImageTag3.f2 = f;
        spiltImageTag2.f3 = n3;
        spiltImageTag2.f9 = n4;
        spiltImageTag.f6 = n5;
        spiltImageTag.f7 = n6;
    }

    @Override
    public void m2() {
        SpiltImageTag spiltImageTag = this;
        SpiltImageTag spiltImageTag2 = this;
        SpiltImageTag spiltImageTag3 = this;
        spiltImageTag.m5(this.f5, ((VexTag)this).f5, ((VexTag)this).f3, ((VexTag)this).f2, spiltImageTag2.f1, ((VexTag)this).f6, ((VexTag)this).f8, ((VexTag)this).f7);
    }

    public static void m4(double d, double d2, double d3, float f, float f2, int n, int n2, int n3, int n4, float f3, float f4) {
        BufferBuilder bufferBuilder;
        f3 = 1.0f / f3;
        f4 = 1.0f / f4;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder2 = bufferBuilder = tessellator.getBuffer();
        double d4 = d;
        BufferBuilder bufferBuilder3 = bufferBuilder;
        bufferBuilder3.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder3.pos(d - (double)(n3 / 2), d2 + (double)(n4 / 2), d3).tex((double)(f * f3), (double)((f2 + (float)n2) * f4)).endVertex();
        bufferBuilder.pos(d4 + (double)(n3 / 2), d2 + (double)(n4 / 2), d3).tex((double)((f + (float)n) * f3), (double)((f2 + (float)n2) * f4)).endVertex();
        bufferBuilder2.pos(d4 + (double)(n3 / 2), d2 - (double)(n4 / 2), d3).tex((double)((f + (float)n) * f3), (double)(f2 * f4)).endVertex();
        bufferBuilder2.pos(d - (double)(n3 / 2), d2 - (double)(n4 / 2), d3).tex((double)(f * f3), (double)(f2 * f4)).endVertex();
        tessellator.draw();
    }

    @Override
    public void m1() {
        VexView.m2(() -> {
            block6: {
                FileInputStream fileInputStream;
                try {
                    if (BufferImageLib.s4.containsKey(this.f5)) return true;
                }
                catch (Exception exception) {
                    return false;
                }
                if (!this.f5.contains("[local]")) break block6;
                File file = new File(VexConfig.s1, this.f5.replace("[local]", ""));
                FileInputStream fileInputStream2 = fileInputStream = new FileInputStream(file);
                BufferImageLib.m8(this.f5, ImageIO.read(fileInputStream2));
                ((InputStream)fileInputStream2).close();
                return true;
            }
            try {
                InputStream inputStream;
                HttpURLConnection httpURLConnection;
                URL uRL = new URL(this.f5);
                HttpURLConnection httpURLConnection2 = httpURLConnection = (HttpURLConnection)uRL.openConnection();
                httpURLConnection2.setRequestMethod("GET");
                httpURLConnection2.setConnectTimeout(3000);
                InputStream inputStream2 = inputStream = httpURLConnection2.getInputStream();
                BufferImageLib.m8(this.f5, ImageIO.read(inputStream2));
                inputStream2.close();
                return true;
            }
            catch (Exception exception) {
                NetPacketManager.m4(new PacketDownload("IMAGE"));
            }
            return true;
        });
    }

    @Override
    public void m3(EntityPlayer entityPlayer) {
        double d = 0.0;
        double d2 = 0.0;
        double d3 = 0.0;
        float f = Minecraft.getMinecraft().getRenderPartialTicks();
        if (entityPlayer != null) {
            EntityPlayer entityPlayer2 = entityPlayer;
            d = entityPlayer2.prevPosX + (entityPlayer.posX - entityPlayer.prevPosX) * (double)f;
            EntityPlayer entityPlayer3 = entityPlayer;
            d2 = entityPlayer2.prevPosY + (entityPlayer3.posY - entityPlayer.prevPosY) * (double)f;
            d3 = entityPlayer3.prevPosZ + (entityPlayer.posZ - entityPlayer.prevPosZ) * (double)f;
            SpiltImageTag spiltImageTag = this;
            SpiltImageTag spiltImageTag2 = this;
            this.m5(this.f5, d + ((VexTag)this).f5, d2 + ((VexTag)this).f3, d3 + ((VexTag)this).f2, spiltImageTag.f1, ((VexTag)this).f6, ((VexTag)this).f8, ((VexTag)this).f7);
        }
    }

    public void m5(String string, double d, double d2, double d3, float f, float f2, float f3, boolean bl) {
        if (BufferImageLib.s4.containsKey(string)) {
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
            SpiltImageTag spiltImageTag = this;
            SpiltImageTag spiltImageTag2 = this;
            GL11.glScaled((double)(this.f2 / (float)this.f8), (double)(spiltImageTag.f1 / (float)spiltImageTag.f4), (double)(spiltImageTag2.f1 / (float)spiltImageTag2.f4));
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
            Minecraft.getMinecraft().getTextureManager().bindTexture(BufferImageLib.s4.get(string));
            SpiltImageTag spiltImageTag3 = this;
            SpiltImageTag spiltImageTag4 = this;
            SpiltImageTag.m4(0.0, 0.0, 0.0, this.f3, this.f9, spiltImageTag3.f6, spiltImageTag3.f7, spiltImageTag4.f8, spiltImageTag4.f4, this.f8, this.f8);
            GlStateManager.enableLighting();
            GlStateManager.enableCull();
            GL11.glPopMatrix();
        }
    }
}

