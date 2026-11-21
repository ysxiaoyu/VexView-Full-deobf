/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.util;

import java.util.Iterator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class VexContainer
extends Container {
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return true;
    }

    public VexContainer(List<Slot> list) {
        Iterator<Slot> iterator;
        Iterator<Slot> iterator2 = iterator = list.iterator();
        while (iterator2.hasNext()) {
            Slot slot = iterator.next();
            iterator2 = iterator;
            this.addSlotToContainer(slot);
        }
    }

    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int n) {
        try {
            return super.transferStackInSlot(entityPlayer, n);
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            System.out.println(new StringBuilder().insert(0, "\ufffd\ucce3\ufffd\ufffd\ufffd\ufffd\u01b7\ufffd\ufffd\ufffd\ufffd").append(indexOutOfBoundsException.getMessage()).toString());
            return ItemStack.EMPTY;
        }
    }
}

