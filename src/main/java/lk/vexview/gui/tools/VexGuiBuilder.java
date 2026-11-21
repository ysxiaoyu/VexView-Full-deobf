/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.tools;

import java.util.List;
import java.util.Map;
import lk.vexview.VexView;
import lk.vexview.engine.ComponentFactory;
import lk.vexview.engine.ElementSplitter;
import lk.vexview.engine.collection.HoverTextElementCollection;
import lk.vexview.gui.LoadingGui;
import lk.vexview.gui.VexCustomGui;
import lk.vexview.gui.core.VexGui;
import lk.vexview.gui.core.VexList;
import lk.vexview.gui.tools.ItemPickTools;
import lk.vexview.gui.util.VexContainer;
import lk.vexview.net.NetPacketManager;
import lk.vexview.net.packet.PacketGuiClose;
import lk.vexview.net.packet.PacketGuiOpen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.item.ItemStack;
import org.json.JSONObject;

public class VexGuiBuilder {
    public static void m3(String string) {
        if (Minecraft.getMinecraft().currentScreen != null && Minecraft.getMinecraft().currentScreen instanceof VexCustomGui) {
            ((VexCustomGui)Minecraft.getMinecraft().currentScreen).f4 = false;
            NetPacketManager.m4(new PacketGuiClose());
        }
        VexView.m1(() -> VexGuiBuilder.m6(new JSONObject(string)));
    }

    private static /* synthetic */ void m6(JSONObject jSONObject) {
        Map<String, List<HoverTextElementCollection>> map = ElementSplitter.m1(jSONObject.getString("base"), "<#>", "<&>", "<#&>");
        HoverTextElementCollection hoverTextElementCollection = map.get("gui").get(0);
        VexGui vexGui = new VexGui(hoverTextElementCollection.m3(1), hoverTextElementCollection.m3(2), hoverTextElementCollection.m3(3), hoverTextElementCollection.m3(4), hoverTextElementCollection.m3(5), hoverTextElementCollection.m3(6), hoverTextElementCollection.m8(0), hoverTextElementCollection.m1(7), hoverTextElementCollection.m1(8));
        Map<String, List<HoverTextElementCollection>> map2 = map;
        map2.remove("gui");
        vexGui.m2();
        map2.values().forEach(list -> list.forEach(hoverText -> vexGui.m1(ComponentFactory.m4(hoverText))));
        vexGui.m7().forEach(vexComponent -> vexComponent.m2());
        if (jSONObject.has("inventory")) {
            vexGui.m17(jSONObject.getString("inventory"));
        }
        vexGui.m9();
        if (jSONObject.has("scrollinglist")) {
            vexGui.f13 = VexGuiBuilder.m7(jSONObject.getString("scrollinglist"));
        }
        ItemPickTools.s1 = ItemStack.EMPTY;
        if (Minecraft.getMinecraft().currentScreen != null && Minecraft.getMinecraft().currentScreen instanceof VexCustomGui && !vexGui.f5) {
            VexCustomGui vexCustomGui = (VexCustomGui)Minecraft.getMinecraft().currentScreen;
            vexCustomGui.f3 = vexGui;
            vexCustomGui.setContainerSlots(new VexContainer(vexGui.m4()));
            vexCustomGui.reInit();
        } else {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                VexGui vexGui2 = vexGui;
                VexGui vexGui3 = vexGui;
                Minecraft.getMinecraft().displayGuiScreen((GuiScreen)new LoadingGui(new VexCustomGui(vexGui3, new VexContainer(vexGui3.m4()))));
            });
        }
        NetPacketManager.m4(new PacketGuiOpen());
    }

    public static VexList m7(String string) {
        Map<String, List<HoverTextElementCollection>> map = ElementSplitter.m2(string, "<#>", "<&>", "<#&>");
        HoverTextElementCollection hoverTextElementCollection = map.get("list").get(0);
        VexList vexList = new VexList(hoverTextElementCollection.m3(0), hoverTextElementCollection.m3(1), hoverTextElementCollection.m3(2), hoverTextElementCollection.m3(3), hoverTextElementCollection.m3(4));
        Map<String, List<HoverTextElementCollection>> map2 = map;
        map2.remove("list");
        map2.values().forEach(list -> list.forEach(hoverText -> vexList.m1(ComponentFactory.m4(hoverText))));
        vexList.m1().forEach(vexComponent -> vexComponent.m2());
        return vexList;
    }
}

