/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.ui.verification;

import lk.vexview.hud.HUDViewer;
import lk.vexview.net.NetPacketManager;
import lk.vexview.net.packet.PacketVerification;

public class VerHandler {
    public static boolean s1 = false;

    public static void m1() {
        String string = "2.7.3";
        NetPacketManager.m4(new PacketVerification(string, HUDViewer.s1 + ":" + HUDViewer.s4));
    }
}

