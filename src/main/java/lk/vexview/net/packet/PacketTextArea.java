/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.net.packet;

import java.util.List;
import lk.vexview.net.packet.Packet;
import org.json.JSONArray;

public class PacketTextArea
extends Packet {
    public PacketTextArea(List<String> list, int n) {
        super("areatext", "", n + "");
        this.f3 = new JSONArray(list).toString();
    }
}

