/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.hud;

import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MinecraftHUD {
    public static boolean s1;
    public static boolean s2;
    public static boolean s3;
    public static boolean s4;
    public static boolean s5;
    public static boolean s6;
    public static boolean s7;

    @SubscribeEvent
    public void onHUD(RenderGameOverlayEvent.Pre pre) {
        if (pre.getType() == RenderGameOverlayEvent.ElementType.HEALTH && !s6) {
            pre.setCanceled(true);
            return;
        }
        if (pre.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE && !s5) {
            pre.setCanceled(true);
            return;
        }
        if (pre.getType() == RenderGameOverlayEvent.ElementType.FOOD && !s7) {
            pre.setCanceled(true);
            return;
        }
        if (pre.getType() == RenderGameOverlayEvent.ElementType.AIR && !s2) {
            pre.setCanceled(true);
            return;
        }
        if (pre.getType() == RenderGameOverlayEvent.ElementType.BOSSHEALTH && !s1) {
            pre.setCanceled(true);
            return;
        }
        if (pre.getType() == RenderGameOverlayEvent.ElementType.BOSSINFO && !s1) {
            pre.setCanceled(true);
            return;
        }
        if (pre.getType() == RenderGameOverlayEvent.ElementType.ARMOR && !s3) {
            pre.setCanceled(true);
            return;
        }
        if (pre.getType() == RenderGameOverlayEvent.ElementType.HOTBAR && !s4) {
            pre.setCanceled(true);
        }
    }

    static {
        s6 = true;
        s5 = true;
        s7 = true;
        s2 = true;
        s4 = true;
        s1 = true;
        s3 = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void m1(String string) {
        int n;
        block18: {
            String string2 = string.split("<&>")[0];
            int n2 = -1;
            switch (string2.hashCode()) {
                case 2127033948: {
                    if (!string2.equals("HEALTH")) break;
                    n = n2 = 0;
                    break block18;
                }
                case 69117: {
                    if (!string2.equals("EXP")) break;
                    n = n2 = 1;
                    break block18;
                }
                case 2142192307: {
                    if (!string2.equals("HUNGER")) break;
                    n = n2 = 2;
                    break block18;
                }
                case -1949272672: {
                    if (!string2.equals("OXYGEN")) break;
                    n = n2 = 3;
                    break block18;
                }
                case 2136824998: {
                    if (!string2.equals("HOTBAR")) break;
                    n = n2 = 4;
                    break block18;
                }
                case 786594150: {
                    if (!string2.equals("BOSSBAR")) break;
                    n = n2 = 5;
                    break block18;
                }
                case 62548255: {
                    if (!string2.equals("ARMOR")) break;
                    n2 = 6;
                }
            }
            n = n2;
        }
        switch (n) {
            case 0: {
                s6 = Boolean.valueOf(string.split("<&>")[1]);
                return;
            }
            case 1: {
                s5 = Boolean.valueOf(string.split("<&>")[1]);
                return;
            }
            case 2: {
                s7 = Boolean.valueOf(string.split("<&>")[1]);
                return;
            }
            case 3: {
                s2 = Boolean.valueOf(string.split("<&>")[1]);
                return;
            }
            case 4: {
                s4 = Boolean.valueOf(string.split("<&>")[1]);
                return;
            }
            case 5: {
                s1 = Boolean.valueOf(string.split("<&>")[1]);
                return;
            }
            case 6: {
                s3 = Boolean.valueOf(string.split("<&>")[1]);
                return;
            }
        }
    }
}

