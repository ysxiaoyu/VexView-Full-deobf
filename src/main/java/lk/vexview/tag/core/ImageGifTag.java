/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.tag.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import lk.vexview.VexConfig;
import lk.vexview.VexView;
import lk.vexview.engine.BufferImageLib;
import lk.vexview.gui.core.GifDecoder;
import lk.vexview.gui.core.VexGifImage;
import lk.vexview.net.NetPacketManager;
import lk.vexview.net.packet.PacketDownload;
import lk.vexview.tag.core.ImageTag;
import lk.vexview.tag.core.VexTag;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class ImageGifTag
extends VexTag {
    private int f1;
    static long s1 = System.currentTimeMillis();
    float f2;
    float f3;
    int f4;
    int f5;
    String f6;
    int f7;

    public ImageGifTag(String string, double d, double d2, double d3, int n, int n2, float f, float f2, float f3, float f4, float f5, boolean bl, boolean bl2) {
        super(d - (double)(f / 2.0f), d2 - (double)(f2 / 2.0f), d3, f3, f4, f5, bl, bl2);
        ImageGifTag imageGifTag = this;
        ImageGifTag imageGifTag2 = this;
        ImageGifTag imageGifTag3 = this;
        imageGifTag3.f1 = 0;
        imageGifTag3.f6 = string;
        imageGifTag2.f5 = n;
        imageGifTag2.f7 = n2;
        imageGifTag.f3 = f2;
        imageGifTag.f2 = f;
        if (VexGifImage.s1.containsKey(string)) {
            this.f4 = VexGifImage.s1.get(string);
        }
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
            ImageGifTag imageGifTag = this;
            ImageGifTag imageGifTag2 = this;
            this.m4(this.f6, d + ((VexTag)this).f5, d2 + ((VexTag)this).f3, d3 + ((VexTag)this).f2, ((VexTag)this).f1, ((VexTag)this).f6, imageGifTag2.f8, ((VexTag)this).f7);
        }
    }

    @Override
    public void m2() {
        ImageGifTag imageGifTag = this;
        ImageGifTag imageGifTag2 = this;
        ImageGifTag imageGifTag3 = this;
        imageGifTag.m4(this.f6, ((VexTag)this).f5, ((VexTag)this).f3, ((VexTag)this).f2, ((VexTag)this).f1, ((VexTag)this).f6, imageGifTag3.f8, ((VexTag)this).f7);
    }

    @Override
    public void m1() {
        VexView.m2(() -> {
            try {
                HttpURLConnection httpURLConnection;
                if (BufferImageLib.s2.containsKey(this.f6)) {
                    return true;
                }
                if (this.f6.contains("[local]")) {
                    File file = new File(VexConfig.s1, this.f6.replace("[local]", ""));
                    FileInputStream fileInputStream = new FileInputStream(file);
                    GifDecoder gifDecoder = new GifDecoder();
                    gifDecoder.m12(fileInputStream);
                    int n = gifDecoder.m21();
                    if (this.f4 == 0) {
                        this.f4 = gifDecoder.f1;
                    }
                    ImageGifTag imageGifTag = this;
                    VexGifImage.s1.put(imageGifTag.f6, imageGifTag.f4);
                    ArrayList<BufferedImage> arrayList = new ArrayList<BufferedImage>();
                    int n2 = 0;
                    int n3 = n2;
                    while (n3 < n) {
                        BufferedImage bufferedImage = gifDecoder.m9(n2);
                        arrayList.add(bufferedImage);
                        n3 = ++n2;
                    }
                    BufferImageLib.m13(this.f6, arrayList);
                    ((InputStream)fileInputStream).close();
                    return true;
                }
                GifDecoder gifDecoder = new GifDecoder();
                URL uRL = new URL(this.f6);
                HttpURLConnection httpURLConnection2 = httpURLConnection = (HttpURLConnection)uRL.openConnection();
                httpURLConnection2.setRequestMethod("GET");
                httpURLConnection2.setConnectTimeout(3000);
                InputStream inputStream = httpURLConnection2.getInputStream();
                gifDecoder.m12(inputStream);
                int n = gifDecoder.m21();
                if (this.f4 == 0) {
                    this.f4 = gifDecoder.f1;
                }
                ImageGifTag imageGifTag = this;
                VexGifImage.s1.put(imageGifTag.f6, imageGifTag.f4);
                ArrayList<BufferedImage> arrayList = new ArrayList<BufferedImage>();
                int n4 = 0;
                int n5 = n4;
                while (true) {
                    if (n5 >= n) {
                        BufferImageLib.m13(this.f6, arrayList);
                        inputStream.close();
                        return true;
                    }
                    BufferedImage bufferedImage = gifDecoder.m9(n4);
                    arrayList.add(bufferedImage);
                    n5 = ++n4;
                }
            }
            catch (Exception exception) {
                NetPacketManager.m4(new PacketDownload("GIFIMAGE"));
                return false;
            }
        });
    }

    public void m4(String string, double d, double d2, double d3, float f, float f2, float f3, boolean bl) {
        if (BufferImageLib.s2.containsKey(string)) {
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
            ImageGifTag imageGifTag = this;
            ImageGifTag imageGifTag2 = this;
            GL11.glScaled((double)(this.f2 / (float)this.f5), (double)(imageGifTag.f3 / (float)imageGifTag.f7), (double)(imageGifTag2.f3 / (float)imageGifTag2.f7));
            GlStateManager.rotate((float)180.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            if (Minecraft.getMinecraft().getRenderManager().options != null && bl) {
                GlStateManager.rotate((float)Minecraft.getMinecraft().getRenderManager().playerViewY, (float)0.0f, (float)1.0f, (float)0.0f);
                GlStateManager.rotate((float)((float)(Minecraft.getMinecraft().getRenderManager().options.thirdPersonView == 2 ? -1 : 1) * Minecraft.getMinecraft().getRenderManager().playerViewX), (float)1.0f, (float)0.0f, (float)0.0f);
            }
            GlStateManager.rotate((float)f, (float)1.0f, (float)0.0f, (float)0.0f);
            GlStateManager.rotate((float)f2, (float)0.0f, (float)1.0f, (float)0.0f);
            GlStateManager.rotate((float)f3, (float)0.0f, (float)0.0f, (float)1.0f);
            GlStateManager.enableDepth();
            Minecraft.getMinecraft().getTextureManager().bindTexture(BufferImageLib.s2.get(string).get(this.m5()));
            ImageGifTag imageGifTag3 = this;
            ImageTag.m5(0.0, 0.0, 0.0, 0.0f, 0.0f, imageGifTag3.f5, imageGifTag3.f7, this.f5, this.f7);
            GlStateManager.enableCull();
            GL11.glPopMatrix();
        }
    }

    public int m5() {
        ImageGifTag imageGifTag;
        if (System.currentTimeMillis() - s1 < (long)this.f4) {
            return this.f1;
        }
        s1 = System.currentTimeMillis();
        if (this.f1 < BufferImageLib.s2.get(this.f6).size() - 1) {
            ImageGifTag imageGifTag2 = this;
            imageGifTag = imageGifTag2;
            ++imageGifTag2.f1;
        } else {
            imageGifTag = this;
            this.f1 = 0;
        }
        return imageGifTag.f1;
    }
}

