/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.ui.verification;

import java.io.IOException;
import lk.vexview.ui.verification.ConnectGui;
import lk.vexview.ui.verification.VerHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class ConnectFailedGui
extends GuiScreen {
    protected void actionPerformed(GuiButton guiButton) throws IOException {
        if (guiButton.id == 0) {
            if (Minecraft.getMinecraft().player != null) {
                VerHandler.s1 = false;
                ConnectGui.s2 = I18n.format((String)"init.ask", (Object[])new Object[0]);
                VerHandler.m1();
                ConnectGui.m1();
                Minecraft.getMinecraft().player.closeScreen();
                return;
            }
        } else if (guiButton.id == 1 && Minecraft.getMinecraft().player != null) {
            Minecraft.getMinecraft().player.closeScreen();
        }
    }

    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 62, this.height / 2 + 5, 60, 20, I18n.format((String)"init.failed.again", (Object[])new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 + 2, this.height / 2 + 5, 60, 20, I18n.format((String)"init.failed.ok", (Object[])new Object[0])));
    }

    public void drawScreen(int n, int n2, float f) {
        ConnectFailedGui connectFailedGui = this;
        ConnectFailedGui.drawRect((int)0, (int)(this.height / 2 - 30), (int)connectFailedGui.width, (int)(connectFailedGui.height / 2 + 30), (int)0x33000000);
        String string = I18n.format((String)"init.failed", (Object[])new Object[0]);
        ConnectFailedGui connectFailedGui2 = this;
        String string2 = string;
        connectFailedGui2.drawString(connectFailedGui2.fontRenderer, string2, this.width / 2 - this.fontRenderer.getStringWidth(string2) / 2, this.height / 2 - 20, 0xFFFFFF);
        super.drawScreen(n, n2, f);
    }
}

