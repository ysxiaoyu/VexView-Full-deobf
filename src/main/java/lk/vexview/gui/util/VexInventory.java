/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;

public class VexInventory
implements IInventory {
    Map<Integer, ItemStack> f1;

    public int getSizeInventory() {
        return this.f1.size();
    }

    public void openInventory(EntityPlayer entityPlayer) {
    }

    public ItemStack getStackInSlot(int n) {
        return this.f1.get(n);
    }

    public void markDirty() {
    }

    public int getFieldCount() {
        return 0;
    }

    public ItemStack decrStackSize(int n, int n2) {
        return null;
    }

    public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
        return true;
    }

    public ITextComponent getDisplayName() {
        return null;
    }

    public int getField(int n) {
        return 0;
    }

    public void closeInventory(EntityPlayer entityPlayer) {
    }

    public String getName() {
        return null;
    }

    public boolean isEmpty() {
        return this.f1.isEmpty();
    }

    public int getInventoryStackLimit() {
        return 1024;
    }

    public ItemStack removeStackFromSlot(int n) {
        return this.f1.remove(n);
    }

    public VexInventory() {
        VexInventory vexInventory = this;
        vexInventory.f1 = new ConcurrentHashMap<Integer, ItemStack>();
    }

    public void setField(int n, int n2) {
    }

    public void setInventorySlotContents(int n, ItemStack itemStack) {
        this.f1.put(n, itemStack);
    }

    public boolean isItemValidForSlot(int n, ItemStack itemStack) {
        return false;
    }

    public boolean hasCustomName() {
        return false;
    }

    public void clear() {
        this.f1.clear();
    }
}

