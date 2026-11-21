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

public class ImageTag
extends VexTag {
    float f1;
    float f2;
    String f3;
    int f4;
    int f5;

    @Override
    public void m1() {
        VexView.m2(() -> {
            block6: {
                FileInputStream fileInputStream;
                try {
                    if (BufferImageLib.s4.containsKey(this.f3)) return true;
                }
                catch (Exception exception) {
                    return false;
                }
                if (!this.f3.contains("[local]")) break block6;
                File file = new File(VexConfig.s1, this.f3.replace("[local]", ""));
                FileInputStream fileInputStream2 = fileInputStream = new FileInputStream(file);
                BufferImageLib.m8(this.f3, ImageIO.read(fileInputStream2));
                ((InputStream)fileInputStream2).close();
                return true;
            }
            try {
                InputStream inputStream;
                HttpURLConnection httpURLConnection;
                URL uRL = new URL(this.f3);
                HttpURLConnection httpURLConnection2 = httpURLConnection = (HttpURLConnection)uRL.openConnection();
                httpURLConnection2.setRequestMethod("GET");
                httpURLConnection2.setConnectTimeout(3000);
                InputStream inputStream2 = inputStream = httpURLConnection2.getInputStream();
                BufferImageLib.m8(this.f3, ImageIO.read(inputStream2));
                inputStream2.close();
                return true;
            }
            catch (Exception exception) {
                NetPacketManager.m4(new PacketDownload("IMAGE"));
            }
            return true;
        });
    }

    public ImageTag(String string, double d, double d2, double d3, int n, int n2, float f, float f2, float f3, float f4, float f5, boolean bl, boolean bl2) {
        super(d - (double)(f / 2.0f), d2 - (double)(f2 / 2.0f), d3, f3, f4, f5, bl, bl2);
        ImageTag imageTag = this;
        ImageTag imageTag2 = this;
        this.f3 = string;
        imageTag2.f4 = n;
        imageTag2.f5 = n2;
        imageTag.f2 = f2;
        imageTag.f1 = f;
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
            ImageTag imageTag = this;
            ImageTag imageTag2 = this;
            this.m4(this.f3, d + ((VexTag)this).f5, d2 + ((VexTag)this).f3, d3 + ((VexTag)this).f2, imageTag.f1, imageTag.f6, imageTag2.f8, imageTag2.f7);
        }
    }

    @Override
    public void m2() {
        ImageTag imageTag = this;
        ImageTag imageTag2 = this;
        ImageTag imageTag3 = this;
        imageTag.m4(this.f3, ((VexTag)this).f5, ((VexTag)this).f3, ((VexTag)this).f2, imageTag2.f1, imageTag2.f6, imageTag3.f8, imageTag3.f7);
    }

    public void m4(String string, double d, double d2, double d3, float f, float f2, float f3, boolean bl) {
        if (BufferImageLib.s4.containsKey(string)) {
            EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().player;
            float f4 = Minecraft.getMinecraft().getRenderPartialTicks();
            EntityPlayerSP entityPlayerSP2 = entityPlayerSP;
            double d4 = entityPlayerSP2.prevPosX + (entityPlayerSP.posX - entityPlayerSP.prevPosX) * (double)f4;
            EntityPlayerSP entityPlayerSP3 = entityPlayerSP;
            double d5 = entityPlayerSP2.prevPosY + (entityPlayerSP3.posY - entityPlayerSP.prevPosY) * (double)f4;
            double d6 = entityPlayerSP3.prevPosZ + (entityPlayerSP.posZ - entityPlayerSP.prevPosZ) * (double)f4;
            GlStateManager.disableLighting();
            GlStateManager.pushMatrix();
            GlStateManager.color((float)255.0f, (float)255.0f, (float)255.0f);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.disableCull();
            GL11.glEnable((int)34383);
            GL11.glTranslated((double)(d - d4), (double)(d2 - d5), (double)(d3 - d6));
            ImageTag imageTag = this;
            ImageTag imageTag2 = this;
            GL11.glScaled((double)(this.f1 / (float)this.f4), (double)(imageTag.f2 / (float)imageTag.f5), (double)(imageTag2.f2 / (float)imageTag2.f5));
            GlStateManager.rotate((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            if (Minecraft.getMinecraft().getRenderManager().options != null && bl) {
                GlStateManager.rotate((float)Minecraft.getMinecraft().getRenderManager().playerViewY, (float)0.0f, (float)1.0f, (float)0.0f);
                GlStateManager.rotate((float)((float)(Minecraft.getMinecraft().getRenderManager().options.thirdPersonView == 2 ? -1 : 1) * Minecraft.getMinecraft().getRenderManager().playerViewX), (float)1.0f, (float)0.0f, (float)0.0f);
            }
            GlStateManager.rotate((float)f, (float)1.0f, (float)0.0f, (float)0.0f);
            GlStateManager.rotate((float)f2, (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)f3, (float)0.0f, (float)0.0f, (float)1.0f);
            GlStateManager.enableDepth();
            Minecraft.getMinecraft().getTextureManager().bindTexture(BufferImageLib.s4.get(string));
            ImageTag imageTag3 = this;
            ImageTag.m5(0.0, 0.0, 0.0, 0.0f, 0.0f, imageTag3.f4, imageTag3.f5, this.f4, this.f5);
            GlStateManager.enableCull();
            GL11.glPopMatrix();
        }
    }

    public static void m5(double d, double d2, double d3, float f, float f2, int n, int n2, float f3, float f4) {
        BufferBuilder bufferBuilder;
        f3 = 1.0f / f3;
        f4 = 1.0f / f4;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder2 = bufferBuilder = tessellator.getBuffer();
        double d4 = d;
        BufferBuilder bufferBuilder3 = bufferBuilder;
        bufferBuilder3.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder3.pos(d - (double)(n / 2), d2 + (double)(n2 / 2), d3).tex((double)(f * f3), (double)((f2 + (float)n2) * f4)).endVertex();
        bufferBuilder.pos(d4 + (double)(n / 2), d2 + (double)(n2 / 2), d3).tex((double)((f + (float)n) * f3), (double)((f2 + (float)n2) * f4)).endVertex();
        bufferBuilder2.pos(d4 + (double)(n / 2), d2 - (double)(n2 / 2), d3).tex((double)((f + (float)n) * f3), (double)(f2 * f4)).endVertex();
        bufferBuilder2.pos(d - (double)(n / 2), d2 - (double)(n2 / 2), d3).tex((double)(f * f3), (double)(f2 * f4)).endVertex();
        tessellator.draw();
    }
}

