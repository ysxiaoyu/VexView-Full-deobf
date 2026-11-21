/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.ui.verification;

import java.util.Timer;
import java.util.TimerTask;
import lk.vexview.VexConfig;
import lk.vexview.ui.verification.ConnectFailedGui;
import lk.vexview.ui.verification.VerHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class ConnectGui {
    static Timer s1;
    static String s2;
    private static final ResourceLocation s3;
    static boolean s4;

    @SubscribeEvent
    public void onDisconnect(FMLNetworkEvent.ClientConnectedToServerEvent clientConnectedToServerEvent) {
        VerHandler.s1 = false;
        s4 = false;
        s2 = I18n.format((String)"init.wait", (Object[])new Object[0]);
    }

    static void m1() {
        s1 = new Timer("");
        s1.schedule(new TimerTask(){

            @Override
            public void run() {
                if (!VerHandler.s1) {
                    VerHandler.s1 = true;
                    if (Minecraft.getMinecraft().currentScreen instanceof GuiMultiplayer) {
                        return;
                    }
                    if (VexConfig.s14) {
                        Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new ConnectFailedGui()));
                    }
                }
            }
        }, 3000L);
    }

    void m2(int n, int n2, FontRenderer fontRenderer) {
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        Gui.drawRect((int)0, (int)(n2 / 2 - 9), (int)n, (int)(n2 / 2 + 9), (int)0x33000000);
        FontRenderer fontRenderer2 = fontRenderer;
        fontRenderer2.drawString(s2, n / 2 - fontRenderer2.getStringWidth(s2) / 2, n2 / 2 - 5, 0xFFFFFF);
        Minecraft.getMinecraft().getTextureManager().bindTexture(s3);
        Gui.drawScaledCustomSizeModalRect((int)(n / 2 - fontRenderer.getStringWidth(s2) / 2 - 18), (int)(n2 / 2 - 8), (float)0.0f, (float)0.0f, (int)16, (int)16, (int)16, (int)16, (float)16.0f, (float)16.0f);
    }

    static {
        s4 = false;
        s3 = new ResourceLocation("vexview", "textures/connect.png");
        s2 = "";
    }

    public static void m3() {
        s1 = new Timer("");
        s1.schedule(new TimerTask(){

            @Override
            public void run() {
                if (!VerHandler.s1) {
                    s2 = I18n.format((String)"init.ask", (Object[])new Object[0]);
                    VerHandler.m1();
                    ConnectGui.m1();
                    return;
                }
                VerHandler.m1();
            }
        }, 1000 * VexConfig.s5);
    }

    @SubscribeEvent
    public void onToWorld(GuiOpenEvent guiOpenEvent) {
        if (guiOpenEvent.getGui() != null && guiOpenEvent.getGui() instanceof GuiDownloadTerrain) {
            s4 = false;
        }
    }

    @SubscribeEvent
    public void onGame(RenderGameOverlayEvent.Pre pre) {
        if (pre.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
            if (!VerHandler.s1) {
                RenderGameOverlayEvent.Pre pre2 = pre;
                int n = pre2.getResolution().getScaledWidth();
                int n2 = pre2.getResolution().getScaledHeight();
                FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
                if (VexConfig.s13) {
                    this.m2(n, n2, fontRenderer);
                }
            }
            if (!s4) {
                s2 = I18n.format((String)"init.wait", (Object[])new Object[0]);
                s4 = true;
                ConnectGui.m3();
            }
        }
    }
}

