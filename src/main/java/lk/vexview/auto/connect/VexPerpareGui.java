/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.auto.connect;

import java.io.IOException;
import lk.vexview.VexConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class VexPerpareGui
extends GuiScreen {
    private static final ResourceLocation s1 = new ResourceLocation("vexview", "textures/custom/back.png");

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        GuiButton guiButton2 = guiButton;
        super.actionPerformed(guiButton2);
        if (guiButton2.id == 0) {
            Minecraft.getMinecraft().shutdown();
        }
    }

    public void initGui() {
        VexPerpareGui vexPerpareGui = this;
        super.initGui();
        vexPerpareGui.buttonList.add(new GuiButton(0, this.width / 2 - 35, this.height / 2 + 50, 70, 20, I18n.format((String)"auto.buttom.left", (Object[])new Object[0])));
    }

    public void drawScreen(int n, int n2, float f) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(s1);
        VexPerpareGui vexPerpareGui = this;
        VexPerpareGui vexPerpareGui2 = this;
        VexPerpareGui.drawScaledCustomSizeModalRect((int)0, (int)0, (float)0.0f, (float)0.0f, (int)vexPerpareGui.width, (int)vexPerpareGui.height, (int)vexPerpareGui2.width, (int)vexPerpareGui2.height, (float)this.width, (float)this.height);
        super.drawScreen(n, n2, f);
        if (VexConfig.s12.populationInfo == null) {
            VexPerpareGui vexPerpareGui3 = this;
            vexPerpareGui3.drawCenteredString(vexPerpareGui3.fontRenderer, I18n.format((String)"auto.connect", (Object[])new Object[0]), this.width / 2, this.height / 2 - 50, 0xFFFFFF);
            return;
        }
        if (VexConfig.s12.populationInfo.equals("No")) {
            VexPerpareGui vexPerpareGui4 = this;
            vexPerpareGui4.drawCenteredString(vexPerpareGui4.fontRenderer, I18n.format((String)"auto.connect.lose", (Object[])new Object[0]), this.width / 2, this.height / 2 - 50, 0xFFFFFF);
        }
    }
}

