/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.tools;

import lk.vexview.gui.tools.VexGuiBuilder;
import org.json.JSONObject;

public class GuiPacketTools {
    static String s1 = "";

    public static void m1(JSONObject jSONObject) {
        if (jSONObject.getString("packet_sub_type").equals("start")) {
            s1 = jSONObject.getString("packet_data");
            return;
        }
        if (jSONObject.getString("packet_sub_type").equals("continue")) {
            s1 = new StringBuilder().insert(0, s1).append(jSONObject.getString("packet_data")).toString();
            return;
        }
        if (jSONObject.getString("packet_sub_type").equals("end")) {
            s1 = new StringBuilder().insert(0, s1).append(jSONObject.getString("packet_data")).toString();
            VexGuiBuilder.m3(s1);
            s1 = "";
        }
    }
}

