/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.net.packet;

import lk.vexview.net.packet.Packet;

public class PacketWindow
extends Packet {
    public PacketWindow(String string) {
        super("client_window", string, "");
    }
}

