/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.tutorial;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class GuideGui
extends GuiScreen {
    private static final ResourceLocation s1;
    public static boolean s2;

    public void drawScreen(int n, int n2, float f) {
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        Minecraft.getMinecraft().getTextureManager().bindTexture(s1);
        GuideGui guideGui = this;
        Gui.drawScaledCustomSizeModalRect((int)(this.width / 2 - 120), (int)(guideGui.height / 2 - 75), (float)0.0f, (float)0.0f, (int)240, (int)150, (int)240, (int)150, (float)240.0f, (float)150.0f);
        guideGui.drawText(100, 58, I18n.format((String)"guide.text", (Object[])new Object[0]));
        this.drawText(7, 12, I18n.format((String)"guide.a", (Object[])new Object[0]));
        this.drawText(7, 85, I18n.format((String)"guide.b", (Object[])new Object[0]));
        super.drawScreen(n, n2, f);
    }

    static {
        s2 = false;
        s1 = new ResourceLocation("vexview", "textures/guide_.png");
    }

    public void initGui() {
        GuideGui guideGui = this;
        int n = guideGui.width / 2 - 120;
        int n2 = guideGui.height / 2 - 75;
        guideGui.buttonList.add(new GuiButton(20, n + 10, n2 + 48, 68, 20, I18n.format((String)"guide.button.a", (Object[])new Object[0])));
        this.buttonList.add(new GuiButton(21, n + 10, n2 + 120, 68, 20, I18n.format((String)"guide.button.b", (Object[])new Object[0])));
    }

    void drawText(int n, int n2, String string) {
        int n3;
        GuideGui guideGui = this;
        int n4 = guideGui.width / 2 - 120;
        int n5 = guideGui.height / 2 - 75;
        String[] stringArray = string.replace("\\n", "<OXO>").split("<OXO>");
        FontRenderer fontRenderer = guideGui.mc.fontRenderer;
        int n6 = 0;
        int n7 = stringArray.length;
        int n8 = n3 = 0;
        while (n8 < n7) {
            String string2 = stringArray[n3];
            int n9 = n5 + n2 + n6 * 10;
            ++n6;
            fontRenderer.drawStringWithShadow(string2, (float)(n4 + n), (float)n9, 0xFFFFFF);
            n8 = ++n3;
        }
    }

    void openWeb(String string) {
        Desktop desktop = Desktop.getDesktop();
        URI uRI = null;
        try {
            uRI = new URI(string);
            desktop.browse(uRI);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        if (guiButton.id == 20) {
            this.openWeb("https://www.vexview.top/threads/vexview-mcbbs.135/");
            return;
        }
        if (guiButton.id == 21) {
            this.openWeb("https://www.vexview.top/docs/index.html");
        }
    }
}

