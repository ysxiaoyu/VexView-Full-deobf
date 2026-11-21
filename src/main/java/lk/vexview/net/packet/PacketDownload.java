/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.net.packet;

import lk.vexview.net.packet.Packet;

public class PacketDownload
extends Packet {
    public PacketDownload(String string) {
        super("downloadfailed", string, "null");
    }
}

