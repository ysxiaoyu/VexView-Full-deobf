/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.net.packet;

import lk.vexview.net.packet.Packet;
import net.minecraft.inventory.Slot;

public class PacketItemAction
extends Packet {
    public PacketItemAction(Slot slot, String string) {
        super("itemaction", slot.slotNumber - 36 + "", string);
    }
}

