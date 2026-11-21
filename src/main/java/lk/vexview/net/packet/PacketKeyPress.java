/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.net.packet;

import lk.vexview.net.packet.Packet;

public class PacketKeyPress
extends Packet {
    public PacketKeyPress(String string) {
        super("key_press", string, "null");
    }
}

