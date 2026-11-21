/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.effect;

import java.util.Random;
import lk.vexview.effect.CustomRender;
import lk.vexview.effect.Lighting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundEvent;

public class VexEffect
extends Lighting {
    public float f1;
    public float f2;
    long f3;
    public float f4;
    public double f5;
    public double f6;
    public double f7;

    @Override
    public void m2() {
        VexEffect.m3(this.f7, this.f6, this.f2 / 255.0f, this.f1 / 255.0f, this.f4 / 255.0f, this.f5, Minecraft.getMinecraft().getRenderPartialTicks());
    }

    public VexEffect(double d, double d2, double d3, float f, float f2, float f3) {
        VexEffect vexEffect = this;
        VexEffect vexEffect2 = this;
        VexEffect vexEffect3 = this;
        this.f3 = 0L;
        vexEffect3.f7 = d;
        vexEffect3.f6 = d2;
        vexEffect2.f5 = d3;
        vexEffect2.f2 = f;
        vexEffect.f1 = f2;
        vexEffect.f4 = f3;
    }

    public static void m1(String string) {
        String[] stringArray = string.split("<&>");
        CustomRender.s1.add(new VexEffect(Double.valueOf(stringArray[0]), Double.valueOf(stringArray[1]), Double.valueOf(stringArray[2]), Float.valueOf(stringArray[3]).floatValue(), Float.valueOf(stringArray[4]).floatValue(), Float.valueOf(stringArray[5]).floatValue()));
    }

    private static /* synthetic */ void m2(double d, double d2, double d3, float f, float f2, float f3, float f4) {
        int n;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        double[] dArray = new double[8];
        double[] dArray2 = new double[8];
        double d4 = 0.0;
        double d5 = 0.0;
        Random random = new Random(100L);
        int n2 = n = 7;
        while (n2 >= 0) {
            int n3 = n--;
            dArray[n3] = d4;
            dArray2[n3] = d5;
            d4 += (double)(random.nextInt(11) - 5);
            d5 += (double)(random.nextInt(11) - 5);
            n2 = n;
        }
        int n4 = n = 0;
        while (n4 < 4) {
            int n5;
            random = new Random(100L);
            int n6 = n5 = 0;
            while (n6 < 3) {
                int n7 = 7;
                int n8 = 0;
                if (n5 > 0) {
                    n7 = 7 - n5;
                }
                if (n5 > 0) {
                    n8 = n7 - 2;
                }
                double d6 = dArray[n7] - d4;
                double d7 = dArray2[n7] - d5;
                int n9 = n7 = n7;
                while (n9 >= n8) {
                    int n10;
                    BufferBuilder bufferBuilder2;
                    double d8 = d6;
                    double d9 = d7;
                    if (n5 == 0) {
                        d6 += (double)(random.nextInt(11) - 5);
                        d7 += (double)(random.nextInt(11) - 5);
                        bufferBuilder2 = bufferBuilder;
                    } else {
                        d6 += (double)(random.nextInt(31) - 15);
                        d7 += (double)(random.nextInt(31) - 15);
                        bufferBuilder2 = bufferBuilder;
                    }
                    bufferBuilder2.begin(5, DefaultVertexFormats.POSITION_COLOR);
                    double d10 = 0.1 + (double)n * 0.2;
                    if (n5 == 0) {
                        d10 *= (double)n7 * 0.1 + 1.0;
                    }
                    double d11 = 0.1 + (double)n * 0.2;
                    if (n5 == 0) {
                        d11 *= (double)(n7 - 1) * 0.1 + 1.0;
                    }
                    int n11 = n10 = 0;
                    while (n11 < 5) {
                        double d12 = d - d10;
                        double d13 = d3 - d10;
                        if (n10 == 1 || n10 == 2) {
                            d12 += d10 * 2.0;
                        }
                        if (n10 == 2 || n10 == 3) {
                            d13 += d10 * 2.0;
                        }
                        double d14 = d - d11;
                        double d15 = d3 - d11;
                        if (n10 == 1 || n10 == 2) {
                            d14 += d11 * 2.0;
                        }
                        if (n10 == 2 || n10 == 3) {
                            d15 += d11 * 2.0;
                        }
                        bufferBuilder.pos(d14 + d6, d2 + (double)(n7 * 16), d15 + d7).color(f, f2, f3, 0.3f).endVertex();
                        bufferBuilder.pos(d12 + d8, d2 + (double)((n7 + 1) * 16), d13 + d9).color(f, f2, f3, 0.3f).endVertex();
                        n11 = ++n10;
                    }
                    tessellator.draw();
                    n9 = --n7;
                }
                n6 = ++n5;
            }
            n4 = ++n;
        }
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
    }

    private static /* synthetic */ void m3(double d, double d2, float f, float f2, float f3, double d3, float f4) {
        EntityPlayerSP entityPlayerSP = Minecraft.getMinecraft().player;
        double d4 = 0.0;
        double d5 = 0.0;
        double d6 = 0.0;
        float f5 = Minecraft.getMinecraft().getRenderPartialTicks();
        if (entityPlayerSP != null) {
            EntityPlayerSP entityPlayerSP2 = entityPlayerSP;
            d4 = entityPlayerSP2.prevPosX + (entityPlayerSP.posX - entityPlayerSP.prevPosX) * (double)f5;
            EntityPlayerSP entityPlayerSP3 = entityPlayerSP;
            d5 = entityPlayerSP2.prevPosY + (entityPlayerSP3.posY - entityPlayerSP.prevPosY) * (double)f5;
            d6 = entityPlayerSP3.prevPosZ + (entityPlayerSP.posZ - entityPlayerSP.prevPosZ) * (double)f5;
            VexEffect.m2(d - d4, d2 - d5, d3 - d6, f, f2, f3, f4);
        }
    }

    @Override
    public boolean m1() {
        if (this.f3 == 0L) {
            Minecraft.getMinecraft().getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.ENTITY_LIGHTNING_THUNDER, (float)10000.0f));
            Minecraft.getMinecraft().getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.ENTITY_LIGHTNING_IMPACT, (float)2.0f));
            this.f3 = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - this.f3 > 500L) {
            return true;
        }
        return false;
    }
}

