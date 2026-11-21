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
import net.minecraftforge.fml.client.FMLClientHandler;

public class DisConnectGui
extends GuiScreen {
    private static final ResourceLocation s1 = new ResourceLocation("vexview", "textures/custom/back.png");

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        GuiButton guiButton2 = guiButton;
        super.actionPerformed(guiButton2);
        if (guiButton2.id == 0) {
            FMLClientHandler.instance().connectToServer((GuiScreen)new DisConnectGui(), VexConfig.s12);
            return;
        }
        if (guiButton.id == 1) {
            Minecraft.getMinecraft().shutdown();
        }
    }

    public void drawScreen(int n, int n2, float f) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(s1);
        DisConnectGui disConnectGui = this;
        DisConnectGui disConnectGui2 = this;
        DisConnectGui.drawScaledCustomSizeModalRect((int)0, (int)0, (float)0.0f, (float)0.0f, (int)disConnectGui.width, (int)disConnectGui.height, (int)disConnectGui2.width, (int)disConnectGui2.height, (float)this.width, (float)this.height);
        DisConnectGui disConnectGui3 = this;
        super.drawScreen(n, n2, f);
        disConnectGui3.drawCenteredString(disConnectGui3.fontRenderer, I18n.format((String)"auto.disconnect", (Object[])new Object[0]), this.width / 2, this.height / 2 - 20, 0xFFFFFF);
    }

    public void initGui() {
        DisConnectGui disConnectGui = this;
        super.initGui();
        disConnectGui.buttonList.add(new GuiButton(0, this.width / 2 - 75, this.height / 2, 70, 20, I18n.format((String)"auto.buttom.reconnect", (Object[])new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 5, this.height / 2, 70, 20, I18n.format((String)"auto.buttom.left", (Object[])new Object[0])));
    }
}

