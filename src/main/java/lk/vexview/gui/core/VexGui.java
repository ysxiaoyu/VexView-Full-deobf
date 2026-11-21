/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import lk.vexview.engine.BufferImageLib;
import lk.vexview.engine.collection.ComponentElementCollection;
import lk.vexview.gui.core.Downable;
import lk.vexview.gui.core.DynamicUpdate;
import lk.vexview.gui.core.VexBase64Image;
import lk.vexview.gui.core.VexButton;
import lk.vexview.gui.core.VexCheckBox;
import lk.vexview.gui.core.VexComponent;
import lk.vexview.gui.core.VexImage;
import lk.vexview.gui.core.VexList;
import lk.vexview.gui.core.VexMcImage;
import lk.vexview.gui.core.VexSlot;
import lk.vexview.gui.core.VexTextArea;
import lk.vexview.gui.core.VexTextBox;
import lk.vexview.gui.core.widget.VexTextAreaWidget;
import lk.vexview.gui.core.widget.VexTextFieldWidget;
import lk.vexview.gui.util.VexInventory;
import lk.vexview.ui.verification.VerNetManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class VexGui
implements Downable,
DynamicUpdate {
    public int f1;
    public int f2;
    public int f3;
    boolean f4;
    public boolean f5;
    VexInventory f6;
    public Future<Boolean> f7;
    public int f8;
    public String f9;
    public int f10;
    List<Slot> f11;
    List<VexComponent> f12;
    public VexList f13;
    public int f14;
    public boolean f15;
    public boolean f16;

    public boolean m1() {
        return this.f4;
    }

    @Override
    public void m1(VexComponent vexComponent) {
        if (vexComponent != null && !this.m5(vexComponent)) {
            if (vexComponent instanceof VexImage || vexComponent instanceof VexMcImage || vexComponent instanceof VexBase64Image) {
                this.f12.add(0, vexComponent);
                return;
            }
            this.f12.add(vexComponent);
        }
    }

    public void m2() {
        this.f7 = BufferImageLib.m4(this.f9, this, "GUI");
    }

    public List<GuiButton> m3(int n, int n2, int n3, int n4, Minecraft minecraft) {
        ArrayList<GuiButton> arrayList = new ArrayList<GuiButton>();
        for (VexComponent vexComponent : this.m7()) {
            if (!(vexComponent instanceof VexButton)) continue;
            arrayList.add(((VexButton)vexComponent).m5(n, n2, n3, n4, minecraft));
        }
        return arrayList;
    }

    public List<Slot> m4() {
        return this.f11;
    }

    public boolean m5(VexComponent vexComponent) {
        return this.f12.contains(vexComponent);
    }

    public void m6(List<VexComponent> list) {
        this.f12.addAll(list);
    }

    public List<VexComponent> m7() {
        return this.f12;
    }

    public VexCheckBox m8(int n) {
        for (VexComponent vexComponent : this.f12) {
            if (!(vexComponent instanceof VexCheckBox) || ((VexCheckBox)vexComponent).f6 != n) continue;
            return (VexCheckBox)vexComponent;
        }
        return null;
    }

    public void m9() {
        for (VexComponent vexComponent : this.m7()) {
            VexSlot vexSlot;
            byte[] byArray;
            if (!(vexComponent instanceof VexSlot) || (byArray = (vexSlot = (VexSlot)vexComponent).m1()) == null) continue;
            VexSlot vexSlot2 = vexSlot;
            Slot slot = new Slot((IInventory)this.f6, vexSlot.f1 + (this.f16 ? 36 : 0), vexSlot2.f2, vexSlot2.f4);
            new Slot((IInventory)this.f6, vexSlot.f1 + (this.f16 ? 36 : 0), vexSlot2.f2, vexSlot2.f4).slotNumber = vexSlot.f1 + (this.f16 ? 36 : 0);
            VexGui vexGui = this;
            slot.putStack(vexGui.m13(byArray));
            vexGui.f11.add(slot);
        }
    }

    public VexTextBox m10(int n) {
        for (VexComponent vexComponent : this.f12) {
            if (!(vexComponent instanceof VexTextBox) || ((VexTextBox)vexComponent).f6 != n) continue;
            return (VexTextBox)vexComponent;
        }
        return null;
    }

    @Override
    public void m2(VexComponent vexComponent) {
        this.f12.remove(vexComponent);
    }

    public Slot m11(VexSlot vexSlot) {
        byte[] byArray = vexSlot.m1();
        if (byArray != null) {
            VexSlot vexSlot2 = vexSlot;
            Slot slot = new Slot((IInventory)this.f6, vexSlot.f1 + (this.f16 ? 36 : 0), vexSlot2.f2, vexSlot2.f4);
            new Slot((IInventory)this.f6, vexSlot.f1 + (this.f16 ? 36 : 0), vexSlot2.f2, vexSlot2.f4).slotNumber = vexSlot.f1 + (this.f16 ? 36 : 0);
            Slot slot2 = slot;
            slot2.putStack(this.m13(byArray));
            return slot2;
        }
        return null;
    }

    public void m12() {
        this.f12.clear();
    }

    private /* synthetic */ ItemStack m13(byte[] byArray) {
        return ByteBufUtils.readItemStack((ByteBuf)Unpooled.wrappedBuffer((byte[])byArray));
    }

    public VexButton m14(int n) {
        for (VexComponent vexComponent : this.f12) {
            if (!(vexComponent instanceof VexButton) || ((VexButton)vexComponent).f12 != n) continue;
            return (VexButton)vexComponent;
        }
        return null;
    }

    public List<VexTextFieldWidget> m15(int n, int n2, int n3, int n4, Minecraft minecraft) {
        ArrayList<VexTextFieldWidget> arrayList = new ArrayList<VexTextFieldWidget>();
        for (VexComponent vexComponent : this.m7()) {
            if (!(vexComponent instanceof VexTextBox)) continue;
            arrayList.add(((VexTextBox)vexComponent).m1(n, n2, n3, n4, minecraft));
        }
        return arrayList;
    }

    public List<VexTextAreaWidget> m16(int n, int n2, int n3, int n4, Minecraft minecraft) {
        ArrayList<VexTextAreaWidget> arrayList = new ArrayList<VexTextAreaWidget>();
        for (VexComponent vexComponent : this.m7()) {
            if (!(vexComponent instanceof VexTextArea)) continue;
            arrayList.add(((VexTextArea)vexComponent).m2(n, n2, n3, n4, minecraft));
        }
        return arrayList;
    }

    public VexGui(int n, int n2, int n3, int n4, int n5, int n6, String string, boolean bl, boolean bl2) {
        VexGui vexGui = this;
        VexGui vexGui2 = this;
        VexGui vexGui3 = this;
        VexGui vexGui4 = this;
        VexGui vexGui5 = this;
        VexGui vexGui6 = this;
        VexGui vexGui7 = this;
        this.f12 = new CopyOnWriteArrayList<VexComponent>();
        this.f4 = false;
        vexGui6.f11 = new ArrayList<Slot>();
        vexGui6.f6 = new VexInventory();
        vexGui6.f16 = false;
        vexGui5.f15 = true;
        vexGui5.f5 = false;
        vexGui4.f1 = n;
        vexGui4.f10 = n2;
        vexGui3.f14 = n3;
        vexGui3.f2 = n4;
        vexGui2.f3 = n5;
        vexGui2.f8 = n6;
        vexGui.f9 = string;
        vexGui.f5 = bl2;
        if (string.contains(VerNetManager.m2("kn_aQnm"))) {
            this.f4 = true;
        }
        this.f15 = bl;
    }

    public void m17(String string) {
        int n;
        ComponentElementCollection componentElementCollection;
        ComponentElementCollection componentElementCollection2 = componentElementCollection = new ComponentElementCollection(string, ":");
        int n2 = componentElementCollection2.m3(0);
        int n3 = componentElementCollection2.m3(1);
        this.f16 = true;
        int n4 = n = 0;
        while (n4 < 36) {
            Slot slot;
            if (n < 9) {
                int n5 = n;
                slot = new Slot((IInventory)this.f6, n5, n2 + n5 % 9 * 18, n3 + 54);
            } else {
                int n6 = n;
                slot = new Slot((IInventory)this.f6, n6, n2 + n % 9 * 18, n3 + (n6 / 9 - 1) * 18);
            }
            slot.slotNumber = n;
            slot.putStack(Minecraft.getMinecraft().player.inventory.getStackInSlot(n));
            this.f11.add(slot);
            n4 = ++n;
        }
    }
}

