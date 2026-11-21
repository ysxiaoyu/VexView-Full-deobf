/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.flowview;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lk.vexview.flowview.VexFlowView;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FlowTools {
    public static List<VexFlowView> s1 = new CopyOnWriteArrayList<VexFlowView>();

    /*
     * Enabled aggressive block sorting
     */
    public static int m1(int n) {
        switch (n) {
            case 0: {
                return 0x1000000;
            }
            case 1: {
                return 0x1000000;
            }
            case 2: {
                return 0x3000000;
            }
            case 3: {
                return 0x5000000;
            }
            case 4: {
                return 0x7000000;
            }
            case 5: {
                return 0x9000000;
            }
            case 6: {
                return 0x11000000;
            }
            case 7: {
                return 0x13000000;
            }
            case 8: {
                return 0x15000000;
            }
            case 9: {
                return 0x17000000;
            }
            case 10: {
                return 0x19000000;
            }
            case 11: {
                return 0x21000000;
            }
            case 12: {
                return 0x23000000;
            }
            case 13: {
                return 0x25000000;
            }
            case 14: {
                return 0x27000000;
            }
            case 15: {
                return 0x29000000;
            }
            case 16: {
                return 0x31000000;
            }
            case 17: {
                return 0x33000000;
            }
            case 18: {
                return 0x35000000;
            }
            case 19: {
                return 0x37000000;
            }
            case 20: {
                return 0x39000000;
            }
            case 21: {
                return 0x41000000;
            }
            case 22: {
                return 0x43000000;
            }
            case 23: {
                return 0x45000000;
            }
            case 24: {
                return 0x47000000;
            }
            case 25: {
                return 0x49000000;
            }
            case 26: {
                return 0x51000000;
            }
            case 27: {
                return 0x53000000;
            }
            case 28: {
                return 0x55000000;
            }
            case 29: {
                return 0x57000000;
            }
            case 30: {
                return 0x59000000;
            }
            case 31: {
                return 0x61000000;
            }
            case 32: {
                return 0x63000000;
            }
            case 33: {
                return 0x65000000;
            }
            case 34: {
                return 0x67000000;
            }
            case 35: {
                return 0x69000000;
            }
            case 36: {
                return 0x71000000;
            }
            case 37: {
                return 0x73000000;
            }
            case 38: {
                return 0x75000000;
            }
            case 39: {
                return 0x77000000;
            }
        }
        return 0x79000000;
    }

    @SubscribeEvent
    public void onHUD(RenderGameOverlayEvent.Pre pre) {
        if (pre.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
            ArrayList<VexFlowView> arrayList = new ArrayList<VexFlowView>();
            for (VexFlowView vexFlowView : s1) {
                if (!vexFlowView.f4) {
                    vexFlowView.m1(pre);
                    continue;
                }
                arrayList.add(vexFlowView);
            }
            Iterator<VexFlowView> iterator = arrayList.iterator();
            Iterator<VexFlowView> iterator2 = iterator;
            while (iterator2.hasNext()) {
                VexFlowView vexFlowView;
                vexFlowView = iterator.next();
                s1.remove(vexFlowView);
                iterator2 = iterator;
            }
        }
    }

    public static String m2(String string) {
        return new StringBuilder().insert(0, string.split("<#>")[2]).append("\u00a7f").toString();
    }

    public static void m3(String string) {
        for (VexFlowView vexFlowView : s1) {
            if (vexFlowView.f5 != FlowTools.m4(string)) continue;
            s1.remove(vexFlowView);
            s1.add(new VexFlowView(FlowTools.m2(string), FlowTools.m4(string), FlowTools.m5(string)));
            return;
        }
        s1.add(new VexFlowView(FlowTools.m2(string), FlowTools.m4(string), FlowTools.m5(string)));
    }

    public static int m4(String string) {
        return Integer.valueOf(string.split("<#>")[1]);
    }

    public static boolean m5(String string) {
        return Boolean.valueOf(string.split("<#>")[3]);
    }
}

