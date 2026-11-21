/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.scoreboard;

import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import lk.vexview.VexView;
import lk.vexview.engine.BufferImageLib;
import lk.vexview.gui.core.VexImage;
import lk.vexview.gui.core.VexText;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.lwjgl.opengl.GL11;

public class NewScoreBroad {
    private static final ResourceLocation s1;
    static double s2;
    static boolean s3;
    public static VexText s4;
    public static int s5;
    public static VexImage s6;

    @SubscribeEvent
    public void displayScoreBoard(RenderGameOverlayEvent.Pre pre) {
        if (pre.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS && s5 != 0) {
            RenderGameOverlayEvent.Pre pre2 = pre;
            int n = pre2.getResolution().getScaledWidth();
            int n2 = pre2.getResolution().getScaledHeight();
            if (s5 == 2) {
                NewScoreBroad.m2(n + (int)((double)NewScoreBroad.s6.f6 * (1.0 - s2)), n2);
                return;
            }
            if (s5 == 1) {
                NewScoreBroad.m1(n, n2);
            }
        }
    }

    static void m1(int n, int n2) {
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        Minecraft.getMinecraft().getTextureManager().bindTexture(s1);
        Gui.drawScaledCustomSizeModalRect((int)(n - 18), (int)((n2 - 48) / 2), (float)0.0f, (float)0.0f, (int)9, (int)24, (int)18, (int)48, (float)9.0f, (float)24.0f);
    }

    static void m2(int n, int n2) {
        Iterator<String> iterator;
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        if (BufferImageLib.s4.containsKey(NewScoreBroad.s6.f11)) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(BufferImageLib.s4.get(NewScoreBroad.s6.f11));
            Gui.drawScaledCustomSizeModalRect((int)(n - NewScoreBroad.s6.f6), (int)((n2 - NewScoreBroad.s6.f12) / 2), (float)0.0f, (float)0.0f, (int)NewScoreBroad.s6.f6, (int)NewScoreBroad.s6.f12, (int)NewScoreBroad.s6.f6, (int)NewScoreBroad.s6.f12, (float)NewScoreBroad.s6.f6, (float)NewScoreBroad.s6.f12);
        }
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        int n3 = 0;
        GL11.glScaled((double)NewScoreBroad.s4.f2, (double)NewScoreBroad.s4.f2, (double)NewScoreBroad.s4.f2);
        Iterator<String> iterator2 = iterator = NewScoreBroad.s4.f1.iterator();
        while (iterator2.hasNext()) {
            String string = iterator.next();
            iterator2 = iterator;
            double d = ((double)((n2 - NewScoreBroad.s6.f12) / 2 + NewScoreBroad.s4.f4) + (double)n3 * (2.0 + 8.0 * NewScoreBroad.s4.f2)) / NewScoreBroad.s4.f2;
            ++n3;
            fontRenderer.drawString(string, (int)((double)(n - NewScoreBroad.s6.f6 + NewScoreBroad.s4.f3) / NewScoreBroad.s4.f2), (int)d, 0xFFFFFF);
        }
        GL11.glScaled((double)(1.0 / NewScoreBroad.s4.f2), (double)(1.0 / NewScoreBroad.s4.f2), (double)(1.0 / NewScoreBroad.s4.f2));
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    @SubscribeEvent
    public void onConnect(FMLNetworkEvent.ClientConnectedToServerEvent clientConnectedToServerEvent) {
        s5 = 0;
    }

    static {
        s5 = 0;
        s1 = new ResourceLocation("vexview", "textures/sb_load.png");
        s2 = 1.0;
        s3 = false;
    }

    @SubscribeEvent
    public void keyListener(InputEvent.KeyInputEvent keyInputEvent) {
        if (VexView.s3.isPressed() && !s3) {
            s3 = true;
            if (s2 >= 1.0) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask(){

                    @Override
                    public void run() {
                        if ((s2 -= 0.01) <= 0.1) {
                            s3 = false;
                            this.cancel();
                        }
                    }
                }, 0L, 8L);
                return;
            }
            if (s2 <= 0.1) {
                Timer timer = new Timer();
                timer.schedule(new TimerTask(){

                    @Override
                    public void run() {
                        if ((s2 += 0.01) >= 1.0) {
                            s3 = false;
                            this.cancel();
                        }
                    }
                }, 0L, 8L);
            }
        }
    }
}

