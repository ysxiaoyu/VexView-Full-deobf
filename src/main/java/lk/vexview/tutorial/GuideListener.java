/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.tutorial;

import lk.vexview.tutorial.GuideGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

public class GuideListener {
    public static long s1;
    public static boolean s2;
    public static int s3;
    private static final ResourceLocation s4;
    public static boolean s5;

    public static void m1() {
        s5 = true;
        s3 = 0;
        s1 = 0L;
        s2 = false;
    }

    @SubscribeEvent
    public void onHUD(RenderGameOverlayEvent.Pre pre) {
        if (pre.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS && s5) {
            int n = pre.getResolution().getScaledWidth();
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            Minecraft.getMinecraft().getTextureManager().bindTexture(s4);
            if (s2) {
                Gui.drawScaledCustomSizeModalRect((int)(n / 2 - 65), (int)(0 - GuideListener.m2()), (float)0.0f, (float)0.0f, (int)130, (int)13, (int)130, (int)13, (float)130.0f, (float)13.0f);
                fontRenderer.drawString(I18n.format((String)"guide.hud", (Object[])new Object[0]), (n - fontRenderer.getStringWidth(I18n.format((String)"guide.hud", (Object[])new Object[0]))) / 2, 1 - GuideListener.m2(), 0xFFFFFF);
                return;
            }
            Gui.drawScaledCustomSizeModalRect((int)(n / 2 - 65), (int)0, (float)0.0f, (float)0.0f, (int)130, (int)13, (int)130, (int)13, (float)130.0f, (float)13.0f);
            fontRenderer.drawString(I18n.format((String)"guide.hud", (Object[])new Object[0]), (n - fontRenderer.getStringWidth(I18n.format((String)"guide.hud", (Object[])new Object[0]))) / 2, 1, 0xFFFFFF);
        }
    }

    static {
        s5 = false;
        s4 = new ResourceLocation("vexview", "textures/guide.png");
        s2 = false;
        s1 = 0L;
        s3 = 0;
    }

    @SubscribeEvent
    public void onPress(InputEvent.KeyInputEvent keyInputEvent) {
        if (s5 && Keyboard.getEventKey() == 22) {
            s2 = true;
            Minecraft.getMinecraft().addScheduledTask(() -> Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new GuideGui()));
        }
    }

    static int m2() {
        if (s1 == 0L) {
            s1 = System.currentTimeMillis();
        } else if (System.currentTimeMillis() - s1 > 30L) {
            s1 = System.currentTimeMillis();
            if (s3 >= 13) {
                s5 = false;
                return 13;
            }
            ++s3;
        }
        return s3;
    }
}

