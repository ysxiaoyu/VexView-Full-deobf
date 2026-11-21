/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import lk.vexview.engine.BufferImageLib;
import lk.vexview.entity.VexEntityRender;
import lk.vexview.gui.AbstractVexCustomGui;
import lk.vexview.gui.VexUrlConfirmGui;
import lk.vexview.gui.core.VexBase64Image;
import lk.vexview.gui.core.VexButton;
import lk.vexview.gui.core.VexCheckBox;
import lk.vexview.gui.core.VexComponent;
import lk.vexview.gui.core.VexEntityDraw;
import lk.vexview.gui.core.VexGifImage;
import lk.vexview.gui.core.VexGui;
import lk.vexview.gui.core.VexImage;
import lk.vexview.gui.core.VexMcImage;
import lk.vexview.gui.core.VexPlayerDraw;
import lk.vexview.gui.core.VexScrollingList;
import lk.vexview.gui.core.VexSlot;
import lk.vexview.gui.core.VexText;
import lk.vexview.gui.core.widget.VexTextAreaWidget;
import lk.vexview.gui.core.widget.VexTextFieldWidget;
import lk.vexview.gui.tools.ItemPickTools;
import lk.vexview.gui.util.VexContainer;
import lk.vexview.net.NetPacketManager;
import lk.vexview.net.packet.PacketButtonClick;
import lk.vexview.net.packet.PacketCheckBox;
import lk.vexview.net.packet.PacketGuiClose;
import lk.vexview.net.packet.PacketItemAction;
import lk.vexview.net.packet.PacketKeyPress;
import lk.vexview.net.packet.PacketReloadBackPack;
import lk.vexview.net.packet.PacketSlotClick;
import lk.vexview.net.packet.PacketTextArea;
import lk.vexview.net.packet.PacketTextField;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.Event;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class VexCustomGui
extends AbstractVexCustomGui {
    private Runnable f1;
    public Map<Integer, VexTextFieldWidget> f2;
    public VexGui f3;
    public boolean f4;
    public VexScrollingList f5;
    public Map<Integer, VexTextAreaWidget> f6;

    private /* synthetic */ void m1() throws IOException {
        int n = Mouse.getEventX() * this.width / this.mc.displayWidth;
        VexCustomGui vexCustomGui = this;
        int n2 = this.height - Mouse.getEventY() * vexCustomGui.height / this.mc.displayHeight - 1;
        vexCustomGui.f5.handleMouseInput(n, n2);
    }

    public void removeButton(VexButton vexButton, boolean bl) {
        if (bl) {
            for (GuiButton guiButton : this.f5.f4) {
                if (vexButton.f12 != guiButton.id) continue;
                this.f5.f4.remove(guiButton);
                this.f5.initButton();
            }
        } else {
            for (GuiButton guiButton : new ArrayList<GuiButton>(this.buttonList)) {
                if (vexButton.f12 != guiButton.id) continue;
                this.f3.m7().remove(vexButton);
                this.buttonList.remove(guiButton);
            }
        }
    }

    private /* synthetic */ int m2() {
        if (this.f3.f1 == -1) {
            VexCustomGui vexCustomGui = this;
            return (vexCustomGui.width - vexCustomGui.f3.f3) / 2;
        }
        return this.f3.f1;
    }

    private /* synthetic */ void m3(int n, int n2, int n3) throws IOException {
        int n4 = n3 = 0;
        while (n4 < this.buttonList.size()) {
            GuiButton guiButton = (GuiButton)this.buttonList.get(n3);
            if (guiButton.mousePressed(this.mc, n, n2)) {
                VexCustomGui vexCustomGui = this;
                GuiScreenEvent.ActionPerformedEvent.Pre pre = new GuiScreenEvent.ActionPerformedEvent.Pre((GuiScreen)vexCustomGui, guiButton, vexCustomGui.buttonList);
                if (MinecraftForge.EVENT_BUS.post((Event)pre)) {
                    return;
                }
                guiButton = pre.getButton();
                VexCustomGui vexCustomGui2 = this;
                GuiButton guiButton2 = this.selectedButton = guiButton;
                guiButton2.playPressSound(this.mc.getSoundHandler());
                this.actionPerformed(guiButton2);
                if (((Object)((Object)vexCustomGui2)).equals(vexCustomGui2.mc.currentScreen)) {
                    VexCustomGui vexCustomGui3 = this;
                    MinecraftForge.EVENT_BUS.post((Event)new GuiScreenEvent.ActionPerformedEvent.Post((GuiScreen)vexCustomGui3, pre.getButton(), vexCustomGui3.buttonList));
                }
            }
            n4 = ++n3;
        }
    }

    public void addSlot(VexSlot vexSlot, boolean bl) {
        boolean bl2 = bl;
        this.removeSlot(vexSlot, bl2);
        if (bl2) {
            this.f5.f3.add(vexSlot);
            return;
        }
        Slot slot = this.f3.m11(vexSlot);
        if (slot != null) {
            this.inventorySlots.inventorySlots.add(slot);
        }
    }

    private /* synthetic */ int m4() {
        return Math.min(this.f3.f3, this.width);
    }

    private /* synthetic */ int m6() {
        return Math.min(this.f3.f8, this.height);
    }

    protected void mouseClicked(int n, int n2, int n3) throws IOException {
        this.f3.m7().forEach(vexComponent -> {
            if (vexComponent instanceof VexCheckBox) {
                this.m21((VexCheckBox)vexComponent, n, n2);
            }
        });
        if (this.f5 != null) {
            this.m11(n, n2, n3);
        }
        if (n3 == 0) {
            this.m3(n, n2, n3);
        }
        if (this.f3.f16) {
            this.m12(n, n2, n3);
        }
        this.f2.values().forEach(vexTextFieldWidget -> vexTextFieldWidget.mouseClicked(n, n2, n3));
        this.f6.values().forEach(vexTextAreaWidget -> vexTextAreaWidget.mouseClicked(n, n2, n3));
    }

    public boolean isPointInRegion(int n, int n2, int n3, int n4, int n5, int n6) {
        return super.isPointInRegion(n, n2, n3, n4, n5, n6);
    }

    private /* synthetic */ void m8() {
        int n = Mouse.getEventButton();
        boolean bl = Mouse.getEventButtonState();
        if (n == 1 && bl) {
            if (this.getSlotUnderMouse() != null) {
                String string = new StringBuilder().insert(0, "VexSlotClick:RIGHT_CLICK:").append(this.getSlotUnderMouse().slotNumber).toString();
                NetPacketManager.m4(new PacketSlotClick(string));
                return;
            }
        } else if (n == 0 && bl) {
            if (this.getSlotUnderMouse() != null) {
                String string = new StringBuilder().insert(0, "VexSlotClick:LEFT_CLICK:").append(this.getSlotUnderMouse().slotNumber).toString();
                NetPacketManager.m4(new PacketSlotClick(string));
                return;
            }
        } else if (n == 2 && bl && this.getSlotUnderMouse() != null) {
            String string = new StringBuilder().insert(0, "VexSlotClick:MIDDLE_CLICK:").append(this.getSlotUnderMouse().slotNumber).toString();
            NetPacketManager.m4(new PacketSlotClick(string));
        }
    }

    public void initGui() {
        try {
            VexCustomGui vexCustomGui = this;
            VexCustomGui vexCustomGui2 = this;
            vexCustomGui2.buttonList.clear();
            vexCustomGui.f5 = null;
            vexCustomGui.guiLeft = vexCustomGui2.m2();
            vexCustomGui.guiTop = vexCustomGui.m20();
            if (vexCustomGui.f3.f13 != null) {
                VexCustomGui vexCustomGui3 = this;
                VexCustomGui vexCustomGui4 = this;
                VexCustomGui vexCustomGui5 = this;
                this.f5 = new VexScrollingList(this.f3.f13.m4(), this.f3.f13, (GuiScreen)vexCustomGui4, this.f3.f13.f3, this.f3.f13.f2, vexCustomGui4.m20() + this.f3.f13.f1, this.m20() + this.f3.f13.f1 + this.f3.f13.f5, this.m2() + this.f3.f13.f4, this.f3.f13.f5, vexCustomGui5.width, vexCustomGui5.height);
            }
            VexCustomGui vexCustomGui6 = this;
            VexCustomGui vexCustomGui7 = this;
            vexCustomGui6.buttonList.addAll(vexCustomGui6.f3.m3(vexCustomGui7.guiLeft, vexCustomGui7.guiTop, this.m4(), this.m6(), this.mc));
            VexCustomGui vexCustomGui8 = this;
            vexCustomGui6.f3.m15(vexCustomGui8.guiLeft, vexCustomGui8.guiTop, this.m4(), this.m6(), this.mc).forEach(vexTextFieldWidget -> {
                if (!this.f2.containsKey(vexTextFieldWidget.f1)) {
                    this.f2.put(vexTextFieldWidget.f1, (VexTextFieldWidget)((Object)vexTextFieldWidget));
                    return;
                }
                this.f2.get(vexTextFieldWidget.f1).resetPosition(this.m2(), this.m20(), this.m4(), this.m6());
            });
            VexCustomGui vexCustomGui9 = this;
            vexCustomGui9.f3.m16(vexCustomGui9.guiLeft, this.guiTop, this.m4(), this.m6(), this.mc).forEach(vexTextAreaWidget -> {
                if (!this.f6.containsKey(vexTextAreaWidget.f6)) {
                    this.f6.put(vexTextAreaWidget.f6, (VexTextAreaWidget)((Object)vexTextAreaWidget));
                    return;
                }
                this.f6.get(vexTextAreaWidget.f6).resetPosition(this.m2(), this.m20(), this.m4(), this.m6());
            });
            return;
        }
        catch (Exception exception) {
            System.out.println("\ufffd\ufffd\u02bc\ufffd\ufffdGui\u02b1\ufffd\ufffd\ufffd\ufffd\ufffd\ucce3\ufffd\ufffd");
            exception.printStackTrace();
            return;
        }
    }

    public void addList() {
        if (this.f3.f13 != null) {
            this.removeList();
            VexCustomGui vexCustomGui = this;
            VexCustomGui vexCustomGui2 = this;
            VexCustomGui vexCustomGui3 = this;
            this.f5 = new VexScrollingList(this.f3.f13.m4(), this.f3.f13, (GuiScreen)vexCustomGui2, this.f3.f13.f3, this.f3.f13.f2, vexCustomGui2.m20() + this.f3.f13.f1, this.m20() + this.f3.f13.f1 + this.f3.f13.f5, this.m2() + this.f3.f13.f4, this.f3.f13.f5, vexCustomGui3.width, vexCustomGui3.height);
        }
    }

    public void setContainerSlots(VexContainer vexContainer) {
        VexCustomGui vexCustomGui = this;
        vexCustomGui.inventorySlots.inventorySlots.clear();
        vexCustomGui.inventorySlots.inventorySlots.addAll(vexContainer.inventorySlots);
    }

    public void onGuiClosed() {
        if (this.f4) {
            NetPacketManager.m4(new PacketGuiClose());
        }
        if (this.f3.f16) {
            NetPacketManager.m4(new PacketReloadBackPack());
        }
    }

    public VexCustomGui(VexGui vexGui, Container container) {
        super(container);
        VexCustomGui vexCustomGui = this;
        VexCustomGui vexCustomGui2 = this;
        this.f2 = new HashMap<Integer, VexTextFieldWidget>();
        vexCustomGui2.f6 = new HashMap<Integer, VexTextAreaWidget>();
        vexCustomGui.f4 = true;
        vexCustomGui.f3 = vexGui;
    }

    public void handleMouseInput() throws IOException {
        VexCustomGui vexCustomGui = this;
        super.handleMouseInput();
        if (vexCustomGui.f5 != null) {
            this.m1();
        }
        if (!this.f3.f16) {
            this.m8();
        }
    }

    private /* synthetic */ void m11(int n, int n2, int n3) throws IOException {
        if (n3 == 0) {
            int x = this.f3.f1 == -1 ? (this.width - this.f3.f3) / 2 : this.f3.f1;
            int y = this.f3.f10 == -1 ? (this.height - this.f3.f8) / 2 : this.f3.f10;
            if (n > x + this.f3.f13.f4 && n < x + this.f3.f13.f4 + this.f3.f13.f3 && n2 > y + this.f3.f13.f1 && n2 < y + this.f3.f13.f1 + this.f3.f13.f5) {
                VexCustomGui vexCustomGui;
                block9: {
                    for (GuiButton guiButton : this.f5.f4) {
                        if (!guiButton.mousePressed(this.mc, n, n2)) continue;
                        VexCustomGui vexCustomGui2 = this;
                        GuiScreenEvent.ActionPerformedEvent.Pre pre = new GuiScreenEvent.ActionPerformedEvent.Pre((GuiScreen)vexCustomGui2, guiButton, vexCustomGui2.buttonList);
                        if (MinecraftForge.EVENT_BUS.post((Event)pre)) {
                            vexCustomGui = this;
                            break block9;
                        }
                        guiButton = pre.getButton();
                        VexCustomGui vexCustomGui3 = this;
                        GuiButton guiButton2 = this.selectedButton = guiButton;
                        guiButton2.playPressSound(this.mc.getSoundHandler());
                        this.actionPerformed(guiButton2);
                        if (!((Object)((Object)vexCustomGui3)).equals(vexCustomGui3.mc.currentScreen)) continue;
                        VexCustomGui vexCustomGui4 = this;
                        MinecraftForge.EVENT_BUS.post((Event)new GuiScreenEvent.ActionPerformedEvent.Post((GuiScreen)vexCustomGui4, pre.getButton(), vexCustomGui4.buttonList));
                    }
                    vexCustomGui = this;
                }
                for (VexComponent vexComponent : vexCustomGui.f5.f6.f6) {
                    if (!(vexComponent instanceof VexCheckBox)) continue;
                    VexCheckBox vexCheckBox = (VexCheckBox)vexComponent;
                    if (n <= vexCheckBox.f1 + n3 + this.f3.f13.f4) continue;
                    VexCheckBox vexCheckBox2 = vexCheckBox;
                    if (n >= vexCheckBox2.f1 + vexCheckBox2.f2 + n3 + this.f3.f13.f4 || n2 <= VexScrollingList.s1.get(vexCheckBox.f6) || n2 >= vexCheckBox.f4 + VexScrollingList.s1.get(vexCheckBox.f6)) continue;
                    vexCheckBox.f3 = !vexCheckBox.f3;
                    this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.BLOCK_STONE_BREAK, (float)1.0f));
                    NetPacketManager.m4(new PacketCheckBox(vexCheckBox.f3 + "", vexCheckBox.f6 + ""));
                }
            }
        }
    }

    private /* synthetic */ void m12(int n, int n2, int n3) {
        this.f1 = () -> ItemPickTools.m1(n, n2, n3, this.inventorySlots, this);
        Slot slot = this.getSlotUnderMouse();
        if (slot != null && n3 < 2) {
            NetPacketManager.m4(new PacketItemAction(slot, n3 == 0 ? "left" : "right"));
        }
    }

    private /* synthetic */ void m13(int n, int n2, int n3, int n4) {
        if (BufferImageLib.s4.containsKey(this.f3.f9)) {
            this.mc.getTextureManager().bindTexture(BufferImageLib.s4.get(this.f3.f9));
            VexCustomGui.drawScaledCustomSizeModalRect((int)n, (int)n2, (float)0.0f, (float)0.0f, (int)this.f3.f14, (int)this.f3.f2, (int)this.f3.f14, (int)this.f3.f2, (float)this.f3.f14, (float)this.f3.f2);
        }
    }

    public void handleKeyboardInput() throws IOException {
        VexCustomGui vexCustomGui = this;
        super.handleKeyboardInput();
        for (GuiTextField guiTextField : vexCustomGui.f2.values()) {
            if (!guiTextField.isFocused()) continue;
            return;
        }
        for (GuiTextField guiTextField : this.f6.values()) {
            if (!guiTextField.isFocused()) continue;
            return;
        }
        String string = new StringBuilder().insert(0, "KeyBoardPress:").append(Keyboard.getEventKey()).append(":").append(Keyboard.getEventKeyState() ? 1 : 0).append(":GUI").toString();
        NetPacketManager.m4(new PacketKeyPress(string));
    }

    protected void keyTyped(char c, int n) throws IOException {
        boolean bl = true;
        for (Integer n2 : this.f2.keySet()) {
            if (!this.f2.get(n2).isFocused()) continue;
            this.f2.get(n2).textboxKeyTyped(c, n);
            NetPacketManager.m4(new PacketTextField(this.f2.get(n2).getText(), n2));
            bl = false;
        }
        for (VexTextAreaWidget vexTextAreaWidget : this.f6.values()) {
            if (!vexTextAreaWidget.isFocused()) continue;
            vexTextAreaWidget.textboxKeyTyped(c, n);
            NetPacketManager.m4(new PacketTextArea(vexTextAreaWidget.getTextLines(), vexTextAreaWidget.f6));
            bl = false;
        }
        if (bl && this.f3.f15 && (n == 1 || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(n))) {
            this.mc.player.closeScreen();
        }
    }

    public void removeSlot(VexSlot vexSlot, boolean bl) {
        if (bl) {
            for (VexSlot vexSlot2 : this.f5.f3) {
                if (vexSlot2.m7() != vexSlot.m7()) continue;
                this.f5.f3.remove(vexSlot2);
            }
        } else {
            for (Slot slot : new ArrayList<Slot>(this.inventorySlots.inventorySlots)) {
                if (slot.getSlotIndex() != vexSlot.m7() + (this.f3.f16 ? 36 : 0)) continue;
                this.inventorySlots.inventorySlots.remove(slot);
            }
        }
    }

    public void runAction() {
        if (this.f1 != null) {
            this.f1.run();
        }
    }

    public void reInit() {
        VexCustomGui vexCustomGui = this;
        vexCustomGui.f2.clear();
        vexCustomGui.f6.clear();
        vexCustomGui.initGui();
    }

    public void setButtonClickable(int n, boolean bl) {
        this.buttonList.forEach(guiButton -> {
            if (guiButton.id == n) {
                guiButton.enabled = bl;
            }
        });
    }

    public void drawpickedItem(int n, int n2, ItemStack itemStack) {
        RenderHelper.enableGUIStandardItemLighting();
        VexCustomGui vexCustomGui = this;
        ItemStack itemStack2 = itemStack;
        vexCustomGui.itemRender.renderItemAndEffectIntoGUI((EntityLivingBase)vexCustomGui.mc.player, itemStack2, n - 8, n2 - 8);
        if (itemStack2.getCount() != 1) {
            VexCustomGui vexCustomGui2 = this;
            vexCustomGui2.itemRender.renderItemOverlayIntoGUI(vexCustomGui2.fontRenderer, itemStack, n - 8, n2 - 8, new StringBuilder().insert(0, "").append(itemStack.getCount()).toString());
        }
    }

    public void removeList() {
        this.f5 = null;
    }

    public void renderToolTip(ItemStack itemStack, int n, int n2) {
        super.renderToolTip(itemStack, n, n2);
    }

    public void cancelAction() {
        this.f1 = null;
    }

    public void addButton(VexButton vexButton, boolean bl) {
        boolean bl2 = bl;
        this.removeButton(vexButton, bl2);
        if (bl2) {
            VexCustomGui vexCustomGui = this;
            this.f5.f4.add(vexButton.m5(vexCustomGui.guiLeft, vexCustomGui.guiTop, this.m4(), this.m6(), this.mc));
            this.f5.initButton();
            return;
        }
        this.f3.m7().add(vexButton);
        VexCustomGui vexCustomGui = this;
        GuiButton guiButton = vexButton.m5(vexCustomGui.guiLeft, vexCustomGui.guiTop, this.m4(), this.m6(), this.mc);
        this.buttonList.add(guiButton);
    }

    protected void actionPerformed(GuiButton guiButton) {
        NetPacketManager.m4(new PacketButtonClick(guiButton.id + ""));
        Iterator<VexComponent> iterator = this.f3.m7().iterator();
        block0: while (true) {
            Iterator<VexComponent> iterator2 = iterator;
            while (iterator2.hasNext()) {
                VexComponent vexComponent = iterator.next();
                if (!(vexComponent instanceof VexButton)) continue block0;
                VexButton vexButton = (VexButton)vexComponent;
                if (vexButton.f12 != guiButton.id) {
                    iterator2 = iterator;
                    continue;
                }
                if (vexButton.f14 == null || vexButton.f14.equals("null") || !vexButton.f14.contains("http")) continue block0;
                this.mc.displayGuiScreen((GuiScreen)new VexUrlConfirmGui(vexButton.f14));
                continue block0;
            }
            break;
        }
    }

    private /* synthetic */ int m20() {
        if (this.f3.f10 == -1) {
            VexCustomGui vexCustomGui = this;
            return (vexCustomGui.height - vexCustomGui.f3.f8) / 2;
        }
        return this.f3.f10;
    }

    public void drawScreen(int n, int n2, float f) {
        block5: {
            VexCustomGui vexCustomGui = this;
            super.drawScreen(n, n2, f);
            if (vexCustomGui.f5 != null) {
                this.f5.drawScreen(n, n2, f);
            }
            this.f3.m7().forEach(vexComponent -> {
                if (vexComponent.m6(n, n2, this.m4(), this.m6(), this.m2(), this.m20())) {
                    this.drawHoveringText(vexComponent.m5().f3, n, n2);
                }
            });
            if (!ItemPickTools.m4(ItemPickTools.s1, ItemStack.EMPTY)) break block5;
            Iterator iterator = this.inventorySlots.inventorySlots.iterator();
            while (iterator.hasNext()) {
                Slot slot;
                Slot slot2 = slot = (Slot)iterator.next();
                if (!this.isPointInRegion(slot2.xPos, slot2.yPos, 16, 16, n, n2)) continue;
                VexCustomGui vexCustomGui2 = this;
                if (ItemPickTools.m4(vexCustomGui2.inventorySlots.transferStackInSlot((EntityPlayer)vexCustomGui2.mc.player, slot.getSlotIndex()), ItemStack.EMPTY)) continue;
                VexCustomGui vexCustomGui3 = this;
                vexCustomGui3.renderToolTip(this.inventorySlots.transferStackInSlot((EntityPlayer)vexCustomGui3.mc.player, slot.getSlotIndex()), n, n2);
            }
        }
        try {
            this.drawpickedItem(n, n2, ItemPickTools.s1);
            return;
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int n, int n2) {
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        VexCustomGui vexCustomGui = this;
        vexCustomGui.m13(vexCustomGui.m2(), this.m20(), this.m4(), this.m6());
        int n3 = 0;
        int n4 = 0;
        for (VexComponent vexComponent : vexCustomGui.f3.m7()) {
            if (vexComponent instanceof VexGifImage) {
                VexGifImage vexGifImage = (VexGifImage)vexComponent;
                VexCustomGui vexCustomGui2 = this;
                vexCustomGui2.drawImage(vexGifImage, this.m2(), vexCustomGui2.m20(), n3, ++n4, this.m4(), this.m6());
                continue;
            }
            if (vexComponent instanceof VexImage) {
                VexImage vexImage = (VexImage)vexComponent;
                VexCustomGui vexCustomGui3 = this;
                vexCustomGui3.drawImage(vexImage, this.m2(), vexCustomGui3.m20(), ++n3, n4, this.m4(), this.m6());
                continue;
            }
            VexComponent vexComponent2 = vexComponent;
            if (vexComponent instanceof VexMcImage) {
                VexMcImage vexMcImage = (VexMcImage)vexComponent2;
                ++n3;
                VexCustomGui vexCustomGui4 = this;
                vexCustomGui4.drawMCImage(vexMcImage, this.m2(), vexCustomGui4.m20(), this.m4(), this.m6());
                continue;
            }
            VexComponent vexComponent3 = vexComponent;
            if (vexComponent2 instanceof VexText) {
                VexText vexText = (VexText)vexComponent3;
                int n5 = 0;
                VexCustomGui vexCustomGui5 = this;
                vexCustomGui5.drawText(vexText, n5, vexCustomGui5.m2(), this.m20(), this.m4(), this.m6());
                continue;
            }
            VexComponent vexComponent4 = vexComponent;
            if (vexComponent3 instanceof VexBase64Image) {
                VexBase64Image vexBase64Image = (VexBase64Image)vexComponent4;
                VexCustomGui vexCustomGui6 = this;
                vexCustomGui6.drawBase64Image(vexBase64Image, this.m2(), vexCustomGui6.m20(), this.m4(), this.m6());
                continue;
            }
            if (vexComponent4 instanceof VexPlayerDraw) {
                try {
                    VexPlayerDraw vexPlayerDraw;
                    VexPlayerDraw vexPlayerDraw2 = vexPlayerDraw = (VexPlayerDraw)vexComponent;
                    VexEntityRender.m2(this.m2() + vexPlayerDraw.f2, this.m20() + vexPlayerDraw.f6, vexPlayerDraw.f1, (float)(this.m2() + vexPlayerDraw.f2) - (float)n, (float)(this.m20() + vexPlayerDraw.f6 - vexPlayerDraw.f1) - (float)n2, (Entity)vexPlayerDraw2.f5, vexPlayerDraw2.f3);
                }
                catch (Exception exception) {
                    exception.printStackTrace();
                }
                continue;
            }
            if (vexComponent instanceof VexEntityDraw) {
                VexEntityDraw vexEntityDraw = (VexEntityDraw)vexComponent;
                if (vexEntityDraw.f3 == null) continue;
                VexEntityDraw vexEntityDraw2 = vexEntityDraw;
                VexEntityRender.m2(this.m2() + vexEntityDraw.f1, this.m20() + vexEntityDraw.f5, vexEntityDraw.f2, (float)(this.m2() + vexEntityDraw.f1) - (float)n, (float)(this.m20() + vexEntityDraw.f5 - vexEntityDraw.f2) - (float)n2, vexEntityDraw2.f3, vexEntityDraw2.f4);
                continue;
            }
            VexComponent vexComponent5 = vexComponent;
            if (vexComponent instanceof VexCheckBox) {
                VexCheckBox vexCheckBox = (VexCheckBox)vexComponent5;
                VexCustomGui vexCustomGui7 = this;
                vexCustomGui7.drawCheckBox(vexCheckBox, this.m2(), vexCustomGui7.m20(), this.m4(), this.m6());
                continue;
            }
            if (!(vexComponent5 instanceof VexButton)) continue;
            this.drawButtons((VexButton)vexComponent, this.m2(), this.m20(), n, n2, this.m4(), this.m6());
        }
        this.f2.values().forEach(vexTextFieldWidget -> {
            vexTextFieldWidget.drawTextBox();
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        });
        this.f6.values().forEach(vexTextAreaWidget -> {
            vexTextAreaWidget.drawTextBox();
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
        });
    }

    private /* synthetic */ void m21(VexCheckBox vexCheckBox, int n, int n2) {
        int x = this.f3.f1 == -1 ? (this.width - this.f3.f3) / 2 : this.f3.f1;
        int y = this.f3.f10 == -1 ? (this.height - this.f3.f8) / 2 : this.f3.f10;
        if (n > vexCheckBox.f1 + x) {
            VexCheckBox vexCheckBox2 = vexCheckBox;
            if (n < vexCheckBox2.f1 + vexCheckBox2.f2 + x && n2 > vexCheckBox.f7 + y) {
                VexCheckBox vexCheckBox3 = vexCheckBox;
                if (n2 < vexCheckBox3.f7 + vexCheckBox3.f4 + y) {
                    vexCheckBox.f3 = !vexCheckBox.f3;
                    this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord((SoundEvent)SoundEvents.BLOCK_STONE_BREAK, (float)1.0f));
                    NetPacketManager.m4(new PacketCheckBox(vexCheckBox.f3 + "", vexCheckBox.f6 + ""));
                }
            }
        }
    }
}

