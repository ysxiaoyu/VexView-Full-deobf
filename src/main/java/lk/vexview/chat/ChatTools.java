/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.chat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import lk.vexview.VexView;
import lk.vexview.chat.ChatInGui;
import lk.vexview.chat.ChatString;
import lk.vexview.chat.CustomChatGui;
import lk.vexview.chat.DefaultChatGui;
import lk.vexview.chat.VexChatGui;
import lk.vexview.hud.HUDViewer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSleepMP;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.json.JSONObject;
import org.lwjgl.input.Keyboard;

public class ChatTools {
    public static String s1 = ChatTools.m6();
    public static ChatInGui s2;
    public static Map<String, LinkedList<ChatString>> s3;
    public boolean f1 = false;

    @SubscribeEvent
    public void keyOpen(GuiOpenEvent guiOpenEvent) {
        if (guiOpenEvent.getGui() != null && guiOpenEvent.getGui() instanceof GuiChat && !(guiOpenEvent.getGui() instanceof GuiSleepMP)) {
            if (!s3.isEmpty()) {
                if (this.f1) {
                    this.f1 = false;
                    guiOpenEvent.setGui((GuiScreen)new DefaultChatGui("/"));
                    return;
                }
                guiOpenEvent.setGui((GuiScreen)new DefaultChatGui());
                return;
            }
            guiOpenEvent.setGui((GuiScreen)new VexChatGui(this.f1 ? "/" : ""));
            this.f1 = false;
        }
    }

    public static void m2(String string) {
        if (!s3.containsKey(string)) {
            s3.put(string, new LinkedList());
        }
    }

    @SubscribeEvent
    public void keyListener(InputEvent.KeyInputEvent keyInputEvent) {
        if (Keyboard.getEventKey() == 53 && Keyboard.getEventKeyState()) {
            this.f1 = true;
        }
    }

    public static LinkedList<ChatString> m3() {
        return s3.get(s1);
    }

    static {
        s3 = new ConcurrentHashMap<String, LinkedList<ChatString>>();
        s2 = new ChatInGui();
    }

    public static String m6() {
        return "\u516c\u5171";
    }

    public static boolean m7() {
        return s1.equals(ChatTools.m6());
    }

    public static void m8(String string, String string3) {
        if (s3.containsKey(string)) {
            ChatTools.m11(string3).forEach(string2 -> {
                s3.get(string).addFirst(new ChatString((String)string2));
                ++ChatTools.s2.f3;
                ChatTools.s2.f2.schedule(() -> --ChatTools.s2.f3, 8L, TimeUnit.SECONDS);
            });
        }
    }

    @SubscribeEvent
    public void onClientPacket(FMLNetworkEvent.ClientCustomPacketEvent object) throws IOException {
        FMLNetworkEvent.ClientCustomPacketEvent clientCustomPacketEvent = object;
        byte[] byArray = new byte[clientCustomPacketEvent.getPacket().payload().readableBytes()];
        clientCustomPacketEvent.getPacket().payload().getBytes(0, byArray);
        ChatTools.m13(ChatTools.m9(byArray));
    }

    private static /* synthetic */ String m9(byte[] byArray) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byArray);
        GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
        byte[] byArray2 = new byte[256];
        int n = 0;
        GZIPInputStream gZIPInputStream2 = gZIPInputStream;
        while ((n = gZIPInputStream2.read(byArray2)) >= 0) {
            gZIPInputStream2 = gZIPInputStream;
            byteArrayOutputStream.write(byArray2, 0, n);
        }
        return byteArrayOutputStream.toString("utf-8");
    }

    public static List<String> m11(String string) {
        LinkedList<String> linkedList = new LinkedList<String>();
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        int n = GuiNewChat.calculateChatboxWidth((float)Minecraft.getMinecraft().gameSettings.chatWidth);
        StringBuilder stringBuilder = new StringBuilder();
        int n2 = 0;
        FontRenderer fontRenderer2 = fontRenderer;
        while (true) {
            if (fontRenderer2.getStringWidth(stringBuilder.toString()) >= n) {
                linkedList.addFirst(stringBuilder.toString());
                stringBuilder = new StringBuilder();
                fontRenderer2 = fontRenderer;
                continue;
            }
            if (n2 == string.length()) {
                LinkedList<String> linkedList2 = linkedList;
                linkedList2.addFirst(stringBuilder.toString());
                return linkedList2;
            }
            stringBuilder.append(string.charAt(n2++));
            fontRenderer2 = fontRenderer;
        }
    }

    public static String m12() {
        return s1;
    }

    @SubscribeEvent(priority=EventPriority.HIGHEST)
    public void onHUD(RenderGameOverlayEvent.Pre pre) {
        if (!s3.isEmpty()) {
            if (pre.getType() == RenderGameOverlayEvent.ElementType.CHAT && Minecraft.getMinecraft().currentScreen != null && Minecraft.getMinecraft().currentScreen instanceof CustomChatGui) {
                pre.setCanceled(true);
                return;
            }
            if (pre.getType() == RenderGameOverlayEvent.ElementType.CHAT && !ChatTools.m7()) {
                s2.m7();
                pre.setCanceled(true);
            }
        }
    }

    public static void m13(String string) {
        VexView.m1(() -> {
            JSONObject jSONObject = new JSONObject(string);
            if (jSONObject.getString("packet_sub_type").equals("register_channel")) {
                ChatTools.m2(jSONObject.getString("packet_data"));
                return;
            }
            ChatTools.m8(jSONObject.getString("packet_sub_type"), jSONObject.getString("packet_data"));
        });
    }

    @SubscribeEvent
    public void renderHudButton(GuiScreenEvent.DrawScreenEvent drawScreenEvent) {
        if (drawScreenEvent.getGui() != null && drawScreenEvent.getGui() instanceof GuiChat) {
            try {
                HUDViewer.s2.values().forEach(vexButtonShow -> vexButtonShow.m2().drawButton(Minecraft.getMinecraft(), drawScreenEvent.getMouseX(), drawScreenEvent.getMouseY(), drawScreenEvent.getRenderPartialTicks()));
                HUDViewer.s2.values().forEach(vexButtonShow -> vexButtonShow.m2().drawButtonForegroundLayer(drawScreenEvent.getMouseX(), drawScreenEvent.getMouseY()));
                return;
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }
}

