/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.hud;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import lk.vexview.chat.CustomChatGui;
import lk.vexview.engine.BufferImageLib;
import lk.vexview.engine.ComponentFactory;
import lk.vexview.engine.collection.ShowElementCollection;
import lk.vexview.entity.VexEntityRender;
import lk.vexview.gui.core.VexGifImage;
import lk.vexview.hud.core.VexBase64ImageShow;
import lk.vexview.hud.core.VexButtonShow;
import lk.vexview.hud.core.VexEntityDrawShow;
import lk.vexview.hud.core.VexImageShow;
import lk.vexview.hud.core.VexMcImageShow;
import lk.vexview.hud.core.VexPlayerDrawShow;
import lk.vexview.hud.core.VexShow;
import lk.vexview.hud.core.VexSplitImageShow;
import lk.vexview.hud.core.VexTextShow;
import lk.vexview.net.NetPacketManager;
import lk.vexview.net.packet.PacketWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class HUDViewer {
    public static int s1;
    public static Map<String, VexButtonShow> s2;
    public static Map<String, VexShow> s3;
    public static int s4;

    public static void m1(int n, int n2) {
        NetPacketManager.m4(new PacketWindow(n + "<&>" + n2));
    }

    private static /* synthetic */ void m2(VexEntityDrawShow vexEntityDrawShow, int n, int n2) {
        if (vexEntityDrawShow.f2.f3 != null) {
            VexEntityDrawShow vexEntityDrawShow2 = vexEntityDrawShow;
            n = HUDViewer.m8(vexEntityDrawShow2, 0, n, vexEntityDrawShow2.f2.f1);
            VexEntityDrawShow vexEntityDrawShow3 = vexEntityDrawShow;
            n2 = HUDViewer.m3(vexEntityDrawShow3, 0, n2, vexEntityDrawShow3.f2.f5);
            VexEntityRender.m3(n, n2, vexEntityDrawShow.f2.f2, vexEntityDrawShow.f2.f3, vexEntityDrawShow.f1);
        }
    }

    public static int m3(VexShow vexShow, int n, int n2, int n3) {
        if (vexShow.f5 != -1.0) {
            if (vexShow.f5 == 1.0) {
                return n2 - n;
            }
            return (int)((double)n2 * vexShow.f5);
        }
        int n4 = 0;
        if (n3 < -1) {
            n4 = n2 + n3;
            return n4;
        }
        if (n3 == -1) {
            n4 = (n2 - n) / 2;
            return n4;
        }
        n4 = n3;
        return n4;
    }

    static int m4(VexTextShow vexTextShow, int n, String string, int n2, int n3) {
        if (vexTextShow.f5 != -1.0) {
            return (int)((double)n2 * vexTextShow.f5);
        }
        int n4 = 0;
        if (n3 < -1) {
            n4 = n2 + n3;
            return n4;
        }
        if (n3 == -1) {
            n4 = (n2 - 10 * n) / 2;
            return n4;
        }
        n4 = n3;
        return n4;
    }

    static void m6(VexImageShow vexImageShow, int n, int n2) {
        if (vexImageShow.f2 instanceof VexGifImage) {
            if (BufferImageLib.s2.containsKey(((VexGifImage)vexImageShow.f2).f11)) {
                GlStateManager.enableBlend();
                GlStateManager.enableAlpha();
                Minecraft.getMinecraft().getTextureManager().bindTexture(BufferImageLib.s2.get(vexImageShow.f2.f11).get(((VexGifImage)vexImageShow.f2).m7()));
                VexImageShow vexImageShow2 = vexImageShow;
                VexImageShow vexImageShow3 = vexImageShow;
                Gui.drawScaledCustomSizeModalRect((int)HUDViewer.m8(vexImageShow2, vexImageShow.f2.f10, n, vexImageShow2.f2.f1), (int)HUDViewer.m3(vexImageShow3, vexImageShow.f2.f7, n2, vexImageShow3.f2.f2), (float)0.0f, (float)0.0f, (int)vexImageShow.f2.f10, (int)vexImageShow.f2.f7, (int)vexImageShow.f2.f10, (int)vexImageShow.f2.f7, (float)vexImageShow.f2.f10, (float)vexImageShow.f2.f7);
                return;
            }
        } else if (BufferImageLib.s4.containsKey(vexImageShow.f2.f11)) {
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            Minecraft.getMinecraft().getTextureManager().bindTexture(BufferImageLib.s4.get(vexImageShow.f2.f11));
            VexImageShow vexImageShow4 = vexImageShow;
            VexImageShow vexImageShow5 = vexImageShow;
            Gui.drawScaledCustomSizeModalRect((int)HUDViewer.m8(vexImageShow4, vexImageShow.f2.f10, n, vexImageShow4.f2.f1), (int)HUDViewer.m3(vexImageShow5, vexImageShow.f2.f7, n2, vexImageShow5.f2.f2), (float)0.0f, (float)0.0f, (int)vexImageShow.f2.f10, (int)vexImageShow.f2.f7, (int)vexImageShow.f2.f10, (int)vexImageShow.f2.f7, (float)vexImageShow.f2.f10, (float)vexImageShow.f2.f7);
        }
    }

    private static /* synthetic */ Map m7(Map<String, ? extends VexShow> map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        map.keySet().stream().sorted((string, string2) -> {
            if (((VexShow)map.get((Object)string)).f6 > ((VexShow)map.get((Object)string2)).f6) {
                return 1;
            }
            return -1;
        }).forEach(string -> {
            String string2 = string;
            linkedHashMap.put(string2, map.get(string2));
        });
        return linkedHashMap;
    }

    public static int m8(VexShow vexShow, int n, int n2, int n3) {
        if (vexShow.f1 != -1.0) {
            if (vexShow.f1 == 1.0) {
                return n2 - n;
            }
            return (int)((double)n2 * vexShow.f1);
        }
        int n4 = 0;
        if (n3 < -1) {
            n4 = n2 + n3;
            return n4;
        }
        if (n3 == -1) {
            n4 = (n2 - n) / 2;
            return n4;
        }
        n4 = n3;
        return n4;
    }

    static int m9(VexTextShow vexTextShow, FontRenderer fontRenderer, String string, int n, int n2) {
        if (((VexShow)vexTextShow).f1 != -1.0) {
            return (int)((double)n * ((VexShow)vexTextShow).f1);
        }
        int n3 = 0;
        if (n2 < -1) {
            n3 = n + n2;
            return n3;
        }
        if (n2 == -1) {
            n3 = (n - fontRenderer.getStringWidth(string)) / 2;
            return n3;
        }
        n3 = n2;
        return n3;
    }

    public static void m10(String string) {
        ShowElementCollection showElementCollection = new ShowElementCollection(string, "<&>", "<#>", "<##>", "<#&>");
        VexShow vexShow = ComponentFactory.m2(showElementCollection);
        vexShow.m4();
        if (showElementCollection.m10().equals("HUDB")) {
            s3.put(vexShow.f7, vexShow);
            s2.put(vexShow.f7, (VexButtonShow)vexShow);
        } else {
            s3.put(vexShow.f7, vexShow);
            s2.remove(vexShow.f7);
        }
        s3 = HUDViewer.m7(s3);
        s2 = HUDViewer.m7(s2);
    }

    static {
        s3 = new LinkedHashMap<String, VexShow>();
        s2 = new LinkedHashMap<String, VexButtonShow>();
        s1 = 0;
        s4 = 0;
    }

    private static /* synthetic */ void m11(VexButtonShow vexButtonShow, int n, int n2) {
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
        if ((guiScreen == null || guiScreen != null && !(guiScreen instanceof GuiChat) && !(guiScreen instanceof CustomChatGui)) && BufferImageLib.s4.containsKey(vexButtonShow.f2.f5)) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(BufferImageLib.s4.get(vexButtonShow.f2.f5));
            VexButtonShow vexButtonShow2 = vexButtonShow;
            VexButtonShow vexButtonShow3 = vexButtonShow;
            Gui.drawScaledCustomSizeModalRect((int)HUDViewer.m8(vexButtonShow2, vexButtonShow2.f2.f11, n, vexButtonShow.f2.f3), (int)HUDViewer.m3(vexButtonShow3, vexButtonShow3.f2.f8, n2, vexButtonShow.f2.f7), (float)0.0f, (float)0.0f, (int)vexButtonShow.f2.f11, (int)vexButtonShow.f2.f8, (int)vexButtonShow.f2.f11, (int)vexButtonShow.f2.f8, (float)vexButtonShow.f2.f11, (float)vexButtonShow.f2.f8);
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            n = HUDViewer.m8(vexButtonShow2, vexButtonShow3.f2.f11, n, vexButtonShow.f2.f3);
            n2 = HUDViewer.m3(vexButtonShow2, vexButtonShow2.f2.f8, n2, vexButtonShow.f2.f7);
            FontRenderer fontRenderer2 = fontRenderer;
            fontRenderer2.drawString(vexButtonShow.f2.f9, n + vexButtonShow.f2.f11 / 2 - fontRenderer2.getStringWidth(vexButtonShow.f2.f9) / 2, n2 + vexButtonShow.f2.f8 / 2 - 4, 0xFFFFFF);
        }
    }

    private static /* synthetic */ void m12(VexPlayerDrawShow vexPlayerDrawShow, int n, int n2) {
        if (vexPlayerDrawShow.f1.f5 != null) {
            VexPlayerDrawShow vexPlayerDrawShow2 = vexPlayerDrawShow;
            n = HUDViewer.m8(vexPlayerDrawShow2, 0, n, vexPlayerDrawShow2.f1.f2);
            VexPlayerDrawShow vexPlayerDrawShow3 = vexPlayerDrawShow;
            n2 = HUDViewer.m3(vexPlayerDrawShow3, 0, n2, vexPlayerDrawShow3.f1.f6);
            VexEntityRender.m3(n, n2, vexPlayerDrawShow.f1.f1, (Entity)vexPlayerDrawShow.f1.f5, vexPlayerDrawShow.f2);
        }
    }

    private static /* synthetic */ void m14(VexMcImageShow vexMcImageShow, int n, int n2) {
        if (BufferImageLib.s1.containsKey(vexMcImageShow.f2.f11)) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(BufferImageLib.s1.get(vexMcImageShow.f2.f11));
            VexMcImageShow vexMcImageShow2 = vexMcImageShow;
            VexMcImageShow vexMcImageShow3 = vexMcImageShow;
            Gui.drawScaledCustomSizeModalRect((int)HUDViewer.m8(vexMcImageShow2, vexMcImageShow.f2.f10, n, vexMcImageShow2.f2.f1), (int)HUDViewer.m3(vexMcImageShow3, vexMcImageShow.f2.f7, n2, vexMcImageShow3.f2.f2), (float)vexMcImageShow.f2.f9, (float)vexMcImageShow.f2.f5, (int)vexMcImageShow.f2.f3, (int)vexMcImageShow.f2.f4, (int)vexMcImageShow.f2.f6, (int)vexMcImageShow.f2.f12, (float)vexMcImageShow.f2.f10, (float)vexMcImageShow.f2.f7);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @SubscribeEvent
    public void onHUD(RenderGameOverlayEvent.Pre pre) {
        RenderGameOverlayEvent.Pre pre2 = pre;
        int n = pre2.getResolution().getScaledWidth();
        int n2 = pre2.getResolution().getScaledHeight();
        if (n != s1 || n2 != s4) {
            s1 = n;
            s4 = n2;
            HUDViewer.m1(n, n2);
        }
        if (pre.getType() != RenderGameOverlayEvent.ElementType.CROSSHAIRS) return;
        try {
            Iterator<VexShow> iterator = new ArrayList<VexShow>(s3.values()).iterator();
            block4: while (true) {
                Iterator<VexShow> iterator2 = iterator;
                while (iterator2.hasNext()) {
                    VexShow vexShow = iterator.next();
                    if (vexShow.f3) {
                        s3.remove(vexShow.f7);
                        s2.remove(vexShow.f7);
                        iterator2 = iterator;
                        continue;
                    }
                    GlStateManager.enableBlend();
                    GlStateManager.enableAlpha();
                    GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                    if (vexShow instanceof VexTextShow) {
                        HUDViewer.m16((VexTextShow)vexShow, n, n2);
                        vexShow.m3();
                        continue block4;
                    }
                    VexShow vexShow2 = vexShow;
                    if (vexShow instanceof VexImageShow) {
                        HUDViewer.m6((VexImageShow)vexShow2, n, n2);
                        vexShow.m3();
                        continue block4;
                    }
                    VexShow vexShow3 = vexShow;
                    if (vexShow2 instanceof VexBase64ImageShow) {
                        HUDViewer.m17((VexBase64ImageShow)vexShow3, n, n2);
                        vexShow.m3();
                        continue block4;
                    }
                    VexShow vexShow4 = vexShow;
                    if (vexShow3 instanceof VexMcImageShow) {
                        HUDViewer.m14((VexMcImageShow)vexShow4, n, n2);
                        vexShow.m3();
                        continue block4;
                    }
                    if (vexShow4 instanceof VexSplitImageShow) {
                        this.m15((VexSplitImageShow)vexShow, n, n2);
                        vexShow.m3();
                        continue block4;
                    }
                    VexShow vexShow5 = vexShow;
                    if (vexShow instanceof VexButtonShow) {
                        HUDViewer.m11((VexButtonShow)vexShow5, n, n2);
                        vexShow.m3();
                        continue block4;
                    }
                    VexShow vexShow6 = vexShow;
                    if (vexShow5 instanceof VexEntityDrawShow) {
                        HUDViewer.m2((VexEntityDrawShow)vexShow6, n, n2);
                        vexShow.m3();
                        continue block4;
                    }
                    if (!(vexShow6 instanceof VexPlayerDrawShow)) continue block4;
                    HUDViewer.m12((VexPlayerDrawShow)vexShow, n, n2);
                    vexShow.m3();
                    continue block4;
                }
                return;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private /* synthetic */ void m15(VexSplitImageShow vexSplitImageShow, int n, int n2) {
        if (BufferImageLib.s4.containsKey(vexSplitImageShow.f2.f11)) {
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            Minecraft.getMinecraft().getTextureManager().bindTexture(BufferImageLib.s4.get(vexSplitImageShow.f2.f11));
            VexSplitImageShow vexSplitImageShow2 = vexSplitImageShow;
            VexSplitImageShow vexSplitImageShow3 = vexSplitImageShow;
            Gui.drawScaledCustomSizeModalRect((int)HUDViewer.m8(vexSplitImageShow2, vexSplitImageShow.f2.f10, n, vexSplitImageShow2.f2.f1), (int)HUDViewer.m3(vexSplitImageShow3, vexSplitImageShow.f2.f7, n2, vexSplitImageShow3.f2.f2), (float)vexSplitImageShow.f2.f9, (float)vexSplitImageShow.f2.f5, (int)vexSplitImageShow.f2.f3, (int)vexSplitImageShow.f2.f4, (int)vexSplitImageShow.f2.f6, (int)vexSplitImageShow.f2.f12, (float)vexSplitImageShow.f2.f10, (float)vexSplitImageShow.f2.f7);
        }
    }

    static void m16(VexTextShow vexTextShow, int n, int n2) {
        Iterator<String> iterator;
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        int n3 = 0;
        VexTextShow vexTextShow2 = vexTextShow;
        GL11.glScaled((double)vexTextShow.f2, (double)vexTextShow2.f2, (double)vexTextShow.f2);
        Iterator<String> iterator2 = iterator = vexTextShow2.m1().iterator();
        while (iterator2.hasNext()) {
            String string = iterator.next();
            iterator2 = iterator;
            FontRenderer fontRenderer2 = fontRenderer;
            FontRenderer fontRenderer3 = fontRenderer;
            VexTextShow vexTextShow3 = vexTextShow;
            double d = ((double)HUDViewer.m4(vexTextShow3, vexTextShow3.m1().size(), string, n2, vexTextShow.f4) + (double)n3 * (2.0 + 8.0 * vexTextShow.f2)) / vexTextShow.f2;
            ++n3;
            fontRenderer3.drawString("\u00a7r" + string + "\u00a7r", (int)((double)HUDViewer.m9(vexTextShow, fontRenderer3, string, n, vexTextShow.f3) / vexTextShow.f2), (int)d, 0xFFFFFF);
        }
        GL11.glScaled((double)(1.0 / vexTextShow.f2), (double)(1.0 / vexTextShow.f2), (double)(1.0 / vexTextShow.f2));
        GL11.glColor4f((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
    }

    static void m17(VexBase64ImageShow vexBase64ImageShow, int n, int n2) {
        if (BufferImageLib.s3.containsKey(vexBase64ImageShow.f7)) {
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
            Minecraft.getMinecraft().getTextureManager().bindTexture(BufferImageLib.s3.get(vexBase64ImageShow.f7));
            VexBase64ImageShow vexBase64ImageShow2 = vexBase64ImageShow;
            VexBase64ImageShow vexBase64ImageShow3 = vexBase64ImageShow;
            Gui.drawScaledCustomSizeModalRect((int)HUDViewer.m8(vexBase64ImageShow2, vexBase64ImageShow.f1.f5, n, vexBase64ImageShow2.f1.f3), (int)HUDViewer.m3(vexBase64ImageShow3, vexBase64ImageShow.f1.f1, n2, vexBase64ImageShow3.f1.f4), (float)0.0f, (float)0.0f, (int)vexBase64ImageShow.f1.f5, (int)vexBase64ImageShow.f1.f1, (int)vexBase64ImageShow.f1.f5, (int)vexBase64ImageShow.f1.f1, (float)vexBase64ImageShow.f1.f5, (float)vexBase64ImageShow.f1.f1);
        }
    }
}

