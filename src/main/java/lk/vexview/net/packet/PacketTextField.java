/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.net.packet;

import lk.vexview.net.packet.Packet;

public class PacketTextField
extends Packet {
    public PacketTextField(String string, int n) {
        super("fieldtext", string, n + "");
    }
}

