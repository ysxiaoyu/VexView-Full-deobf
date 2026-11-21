/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.chat;

import java.io.IOException;
import lk.vexview.hud.HUDViewer;
import net.minecraft.client.gui.GuiChat;

public class VexChatGui
extends GuiChat {
    public VexChatGui(String string) {
        super(string);
    }

    protected void mouseClicked(int n, int n2, int n3) {
        super.mouseClicked(n, n2, n3);
        try {
            HUDViewer.s2.values().forEach(vexButtonShow -> vexButtonShow.m2().mouseReleased(n, n2));
            return;
        }
        catch (Exception exception) {
            return;
        }
    }
}

