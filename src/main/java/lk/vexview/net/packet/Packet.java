/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.net.packet;

public abstract class Packet {
    public String f1;
    public String f2;
    public String f3;

    public Packet(String string, String string2, String string3) {
        Packet packet = this;
        Packet packet2 = this;
        Packet packet3 = this;
        packet3.f2 = "unknow";
        packet3.f3 = "";
        packet2.f1 = "";
        packet2.f2 = string;
        packet.f3 = string2;
        packet.f1 = string3;
    }
}

