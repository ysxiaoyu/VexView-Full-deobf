/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.tools;

import lk.vexview.engine.ComponentFactory;
import lk.vexview.engine.collection.HoverTextElementCollection;
import lk.vexview.gui.VexCustomGui;
import lk.vexview.gui.core.DynamicUpdate;
import lk.vexview.gui.core.VexButton;
import lk.vexview.gui.core.VexComponent;
import lk.vexview.gui.core.VexGui;
import lk.vexview.gui.core.VexList;
import lk.vexview.gui.core.VexSlot;
import lk.vexview.gui.tools.VexGuiBuilder;
import net.minecraft.client.Minecraft;
import org.json.JSONArray;
import org.json.JSONObject;

public class VexDynamicTools {
    public static void m1(String string) {
        if (Minecraft.getMinecraft().currentScreen == null) {
            return;
        }
        JSONObject jSONObject = new JSONObject(string);
        int n = jSONObject.getInt("id");
        boolean bl = jSONObject.getBoolean("clickable");
        if (Minecraft.getMinecraft().currentScreen instanceof VexCustomGui) {
            VexCustomGui vexCustomGui = (VexCustomGui)Minecraft.getMinecraft().currentScreen;
            if (vexCustomGui.f3.m14(n) != null && vexCustomGui.f3.m14((int)n).f10) {
                vexCustomGui.f3.m14((int)n).f2 = bl;
                vexCustomGui.setButtonClickable(n, bl);
            }
        }
    }

    public static void m2(String string) {
        if (Minecraft.getMinecraft().currentScreen == null) {
            return;
        }
        JSONObject jSONObject = new JSONObject(string);
        int n = jSONObject.getInt("id");
        boolean bl = jSONObject.getBoolean("check");
        if (Minecraft.getMinecraft().currentScreen instanceof VexCustomGui) {
            VexCustomGui vexCustomGui = (VexCustomGui)Minecraft.getMinecraft().currentScreen;
            if (vexCustomGui.f3.m8(n) != null) {
                vexCustomGui.f3.m8((int)n).f3 = bl;
            }
        }
    }

    public static void m3(String string, boolean bl) {
        JSONObject jSONObject = new JSONObject(string);
        if (Minecraft.getMinecraft().currentScreen == null) {
            return;
        }
        if (Minecraft.getMinecraft().currentScreen instanceof VexCustomGui) {
            VexCustomGui vexCustomGui = (VexCustomGui)Minecraft.getMinecraft().currentScreen;
            if (jSONObject.has("scrollinglist")) {
                if (jSONObject.getString("type").equals("add")) {
                    VexDynamicTools.m7(jSONObject.getString("scrollinglist"), vexCustomGui.f3, vexCustomGui);
                    return;
                }
                if (jSONObject.getString("type").equals("remove")) {
                    VexDynamicTools.m11(jSONObject.getString("scrollinglist"), vexCustomGui.f3, vexCustomGui);
                }
                return;
            }
            HoverTextElementCollection hoverTextElementCollection = new HoverTextElementCollection(jSONObject.getString("data"), "<&>", "<#&>");
            if (jSONObject.getString("type").equals("add")) {
                VexCustomGui vexCustomGui2;
                DynamicUpdate dynamicUpdate;
                VexCustomGui vexCustomGui3 = vexCustomGui;
                if (bl) {
                    dynamicUpdate = vexCustomGui3.f3.f13;
                    vexCustomGui2 = vexCustomGui;
                } else {
                    dynamicUpdate = vexCustomGui3.f3;
                    vexCustomGui2 = vexCustomGui;
                }
                VexDynamicTools.m6(hoverTextElementCollection, dynamicUpdate, vexCustomGui2, bl);
                return;
            }
            if (jSONObject.getString("type").equals("remove")) {
                VexCustomGui vexCustomGui4;
                DynamicUpdate dynamicUpdate;
                VexCustomGui vexCustomGui5 = vexCustomGui;
                if (bl) {
                    dynamicUpdate = vexCustomGui5.f3.f13;
                    vexCustomGui4 = vexCustomGui;
                } else {
                    dynamicUpdate = vexCustomGui5.f3;
                    vexCustomGui4 = vexCustomGui;
                }
                VexDynamicTools.m5(hoverTextElementCollection, dynamicUpdate, vexCustomGui4, bl);
            }
        }
    }

    public static String m4(String string) {
        int n;
        StackTraceElement stackTraceElement = new RuntimeException().getStackTrace()[1];
        String string2 = new StringBuffer(stackTraceElement.getMethodName()).append(stackTraceElement.getClassName()).toString();
        int n2 = string.length();
        int n3 = n2 - 1;
        char[] cArray = new char[n2];
        int n4 = 1 << 3 ^ 5;
        int cfr_ignored_0 = 1 << 3 ^ 1;
        int n5 = (2 ^ 5) << 3 ^ (3 ^ 5);
        int n6 = n = string2.length() - 1;
        int n7 = n3;
        String string3 = string2;
        while (n7 >= 0) {
            int n8 = n3--;
            cArray[n8] = (char)(n5 ^ (string.charAt(n8) ^ string3.charAt(n)));
            if (n3 < 0) break;
            int n9 = n3--;
            char c = cArray[n9] = (char)(n4 ^ (string.charAt(n9) ^ string3.charAt(n)));
            if (--n < 0) {
                n = n6;
            }
            n7 = n3;
        }
        return new String(cArray);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static /* synthetic */ void m5(HoverTextElementCollection hoverTextElementCollection, DynamicUpdate dynamicUpdate, VexCustomGui vexCustomGui, boolean bl) {
        int n;
        VexComponent vexComponent;
        block10: {
            vexComponent = ComponentFactory.m4(hoverTextElementCollection);
            if (vexComponent == null) {
                return;
            }
            dynamicUpdate.m2(vexComponent);
            String string = hoverTextElementCollection.m10();
            int n2 = -1;
            switch (string.hashCode()) {
                case 97921: {
                    if (!string.equals("but")) break;
                    n = n2 = 0;
                    break block10;
                }
                case 3047230: {
                    if (!string.equals("cbut")) break;
                    n = n2 = 1;
                    break block10;
                }
                case 113974: {
                    if (!string.equals("slo")) break;
                    n2 = 2;
                }
            }
            n = n2;
        }
        switch (n) {
            case 0: 
            case 1: {
                vexCustomGui.removeButton((VexButton)vexComponent, bl);
                return;
            }
            case 2: {
                vexCustomGui.removeSlot((VexSlot)vexComponent, bl);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static /* synthetic */ void m6(HoverTextElementCollection hoverTextElementCollection, DynamicUpdate dynamicUpdate, VexCustomGui vexCustomGui, boolean bl) {
        int n;
        VexComponent vexComponent;
        block10: {
            vexComponent = ComponentFactory.m4(hoverTextElementCollection);
            if (vexComponent == null) {
                return;
            }
            dynamicUpdate.m1(vexComponent);
            vexComponent.m2();
            String string = hoverTextElementCollection.m10();
            int n2 = -1;
            switch (string.hashCode()) {
                case 97921: {
                    if (!string.equals("but")) break;
                    n = n2 = 0;
                    break block10;
                }
                case 3047230: {
                    if (!string.equals("cbut")) break;
                    n = n2 = 1;
                    break block10;
                }
                case 113974: {
                    if (!string.equals("slo")) break;
                    n2 = 2;
                }
            }
            n = n2;
        }
        switch (n) {
            case 0: 
            case 1: {
                vexCustomGui.addButton((VexButton)vexComponent, bl);
                return;
            }
            case 2: {
                vexCustomGui.addSlot((VexSlot)vexComponent, bl);
                return;
            }
        }
    }

    private static /* synthetic */ void m7(String string, VexGui vexGui, VexCustomGui vexCustomGui) {
        VexList vexList;
        vexGui.f13 = vexList = VexGuiBuilder.m7(string);
        vexCustomGui.addList();
    }

    private static /* synthetic */ void m8(String string) {
        System.out.println(string);
        if (Minecraft.getMinecraft().currentScreen == null) {
            return;
        }
        JSONObject jSONObject = new JSONObject(string);
        int n = jSONObject.getInt("id");
        JSONArray jSONArray = jSONObject.getJSONArray("text");
        if (Minecraft.getMinecraft().currentScreen instanceof VexCustomGui) {
            VexCustomGui vexCustomGui = (VexCustomGui)Minecraft.getMinecraft().currentScreen;
            if (vexCustomGui.f6.containsKey(n)) {
                vexCustomGui.f6.get(n).setTextLines(jSONArray.toList());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void m9(JSONObject jSONObject) {
        int n;
        block16: {
            String string = jSONObject.getString("packet_sub_type");
            int n2 = -1;
            switch (string.hashCode()) {
                case -1039745817: {
                    if (!string.equals("normal")) break;
                    n = n2 = 0;
                    break block16;
                }
                case -1377687758: {
                    if (!string.equals("button")) break;
                    n = n2 = 1;
                    break block16;
                }
                case -1031434259: {
                    if (!string.equals("textfield")) break;
                    n = n2 = 2;
                    break block16;
                }
                case 1536891843: {
                    if (!string.equals("checkbox")) break;
                    n = n2 = 3;
                    break block16;
                }
                case -1003243718: {
                    if (!string.equals("textarea")) break;
                    n = n2 = 4;
                    break block16;
                }
                case 3322014: {
                    if (!string.equals("list")) break;
                    n2 = 5;
                }
            }
            n = n2;
        }
        switch (n) {
            case 0: {
                VexDynamicTools.m3(jSONObject.getString("packet_data"), false);
                return;
            }
            case 1: {
                VexDynamicTools.m1(jSONObject.getString("packet_data"));
                return;
            }
            case 2: {
                VexDynamicTools.m10(jSONObject.getString("packet_data"));
                return;
            }
            case 3: {
                VexDynamicTools.m2(jSONObject.getString("packet_data"));
                return;
            }
            case 4: {
                VexDynamicTools.m8(jSONObject.getString("packet_data"));
                return;
            }
            case 5: {
                VexDynamicTools.m3(jSONObject.getString("packet_data"), true);
                return;
            }
        }
    }

    public static void m10(String string) {
        if (Minecraft.getMinecraft().currentScreen == null) {
            return;
        }
        JSONObject jSONObject = new JSONObject(string);
        int n = jSONObject.getInt("id");
        String string2 = jSONObject.getString("text");
        if (Minecraft.getMinecraft().currentScreen instanceof VexCustomGui) {
            VexCustomGui vexCustomGui = (VexCustomGui)Minecraft.getMinecraft().currentScreen;
            if (vexCustomGui.f2.containsKey(n)) {
                vexCustomGui.f2.get(n).setText(string2);
            }
        }
    }

    private static /* synthetic */ void m11(String string, VexGui vexGui, VexCustomGui vexCustomGui) {
        vexGui.f13 = null;
        vexCustomGui.removeList();
    }
}

