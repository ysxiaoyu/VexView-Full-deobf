/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;

public class VexUrlConfirmGui
extends GuiScreen {
    String f1;

    void confirmClicked(String string) {
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
        switch (guiButton.id) {
            case 0: {
                this.confirmClicked(this.f1);
            }
            case 1: {
                this.mc.player.closeScreen();
            }
            case 2: {
                VexUrlConfirmGui.setClipboardString((String)this.f1);
            }
        }
    }

    public void initGui() {
        VexUrlConfirmGui vexUrlConfirmGui = this;
        vexUrlConfirmGui.buttonList.clear();
        vexUrlConfirmGui.buttonList.add(new GuiButton(0, this.width / 2 - 50 - 105, this.height / 6 + 96, 100, 20, I18n.format((String)"chat.link.open", (Object[])new Object[0])));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 50, this.height / 6 + 96, 100, 20, I18n.format((String)"chat.copy", (Object[])new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 50 + 105, this.height / 6 + 96, 100, 20, I18n.format((String)"gui.cancel", (Object[])new Object[0])));
    }

    public VexUrlConfirmGui(String string) {
        VexUrlConfirmGui vexUrlConfirmGui = this;
        vexUrlConfirmGui.f1 = "";
        vexUrlConfirmGui.f1 = string;
    }

    public void drawScreen(int n, int n2, float f) {
        VexUrlConfirmGui vexUrlConfirmGui = this;
        VexUrlConfirmGui vexUrlConfirmGui2 = this;
        vexUrlConfirmGui2.drawDefaultBackground();
        super.drawScreen(n, n2, f);
        vexUrlConfirmGui.drawCenteredString(vexUrlConfirmGui2.fontRenderer, I18n.format((String)"chat.link.confirmTrusted", (Object[])new Object[0]), this.width / 2, 70, 0xFFCCCC);
        VexUrlConfirmGui vexUrlConfirmGui3 = this;
        vexUrlConfirmGui3.drawCenteredString(vexUrlConfirmGui3.fontRenderer, I18n.format((String)"chat.link.warning", (Object[])new Object[0]), this.width / 2, 110, 0xFFCCCC);
    }
}


