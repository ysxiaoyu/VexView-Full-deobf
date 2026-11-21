/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.tools;

import java.util.Iterator;
import lk.vexview.gui.VexCustomGui;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ItemPickTools {
    public static ItemStack s1 = ItemStack.EMPTY;

    public static void m1(int n, int n2, int n3, Container container, VexCustomGui vexCustomGui) {
        Iterator iterator = container.inventorySlots.iterator();
        while (iterator.hasNext()) {
            Slot slot;
            Slot slot2 = slot = (Slot)iterator.next();
            if (!vexCustomGui.isPointInRegion(slot2.xPos, slot2.yPos, 16, 16, n, n2)) continue;
            if (n3 == 0) {
                ItemPickTools.m3(slot, container);
                continue;
            }
            if (n3 != 1) continue;
            ItemPickTools.m2(slot, container);
        }
    }

    static void m2(Slot slot, Container container) {
        if (s1 != null && !ItemPickTools.m4(s1, ItemStack.EMPTY)) {
            if (s1.getCount() > 1) {
                if (ItemPickTools.m4(slot.getStack(), ItemStack.EMPTY)) {
                    s1.setCount(s1.getCount() - 1);
                    Slot slot2 = slot;
                    slot2.putStack(s1.copy());
                    slot2.getStack().setCount(1);
                    return;
                }
                if (ItemPickTools.m4(slot.getStack(), s1) && slot.getStack().getCount() + 1 <= 64) {
                    if (s1.getCount() - 1 > 0) {
                        s1.setCount(s1.getCount() - 1);
                        slot.getStack().setCount(slot.getStack().getCount() + 1);
                        return;
                    }
                    if (s1.getCount() == 1) {
                        s1 = ItemStack.EMPTY;
                        slot.getStack().setCount(slot.getStack().getCount() + 1);
                        return;
                    }
                }
            }
        } else if (!ItemPickTools.m4(slot.getStack(), ItemStack.EMPTY) && slot.getStack().getCount() > 1) {
            Slot slot3 = slot;
            int n = slot3.getStack().getCount();
            Slot slot4 = slot;
            slot3.getStack().setCount(slot4.getStack().getCount() / 2);
            s1 = slot4.getStack().copy();
            s1.setCount(n - slot.getStack().getCount());
        }
    }

    static void m3(Slot slot, Container container) {
        if (s1 == null || s1.equals(ItemStack.EMPTY)) {
            Slot slot2 = slot;
            s1 = slot2.getStack().copy();
            slot2.putStack(ItemStack.EMPTY);
            return;
        }
        if (ItemPickTools.m4(slot.getStack(), s1) && !slot.getStack().isItemStackDamageable() && !s1.isItemStackDamageable()) {
            if (slot.getStack().getCount() < 64) {
                if (slot.getStack().getCount() + s1.getCount() > 64) {
                    Slot slot3 = slot;
                    int n = slot3.getStack().getCount();
                    slot3.getStack().setCount(s1.getCount());
                    s1.setCount(n);
                    return;
                }
                slot.getStack().setCount(slot.getStack().getCount() + s1.getCount());
                s1 = ItemStack.EMPTY;
                return;
            }
        } else {
            Slot slot4 = slot;
            ItemStack itemStack = slot4.getStack().copy();
            slot4.putStack(s1.copy());
            s1 = itemStack;
        }
    }

    public static boolean m4(ItemStack itemStack, ItemStack itemStack2) {
        if (itemStack.hasTagCompound()) {
            if (itemStack2.hasTagCompound()) {
                return itemStack.getTagCompound().equals((Object)itemStack2.getTagCompound());
            }
            return false;
        }
        if (itemStack2.hasTagCompound()) {
            return false;
        }
        return itemStack.getItem().getTranslationKey().equals(itemStack2.getItem().getTranslationKey());
    }
}

