/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.inventory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lk.vexview.engine.ComponentFactory;
import lk.vexview.engine.ElementSplitter;
import lk.vexview.gui.core.VexComponent;
import lk.vexview.inventory.VexInventoryGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class InventoryTools {
    public static List<VexComponent> s1 = new CopyOnWriteArrayList<VexComponent>();

    public static void m1(String string) {
        s1.clear();
        ElementSplitter.m1(string, "<#>", "<&>", "<#&>").values().forEach(list -> list.forEach(hoverTextElementCollection -> s1.add(ComponentFactory.m4(hoverTextElementCollection))));
        if (Minecraft.getMinecraft().currentScreen instanceof VexInventoryGui) {
            ((VexInventoryGui)Minecraft.getMinecraft().currentScreen).initGui();
        }
    }

    @SubscribeEvent
    public void onOpen(GuiOpenEvent guiOpenEvent) {
        if (!s1.isEmpty() && guiOpenEvent.getGui() instanceof GuiInventory && !(guiOpenEvent.getGui() instanceof VexInventoryGui)) {
            guiOpenEvent.setGui((GuiScreen)new VexInventoryGui((EntityPlayer)Minecraft.getMinecraft().player));
        }
    }
}

