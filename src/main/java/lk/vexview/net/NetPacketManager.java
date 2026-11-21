/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPOutputStream;
import lk.vexview.VexView;
import lk.vexview.announcement.AnnGui;
import lk.vexview.announcement.Announcement;
import lk.vexview.announcement.Type;
import lk.vexview.effect.VexEffect;
import lk.vexview.flowview.FlowTools;
import lk.vexview.gui.VexCustomGui;
import lk.vexview.gui.tools.GuiPacketTools;
import lk.vexview.gui.tools.VexDynamicTools;
import lk.vexview.hud.util.HudNetManager;
import lk.vexview.inventory.InventoryTools;
import lk.vexview.net.packet.Packet;
import lk.vexview.net.packet.PacketChannelChat;
import lk.vexview.potion.PotionTools;
import lk.vexview.scoreboard.SBPacketBuffer;
import lk.vexview.tag.TagBuilder;
import lk.vexview.tutorial.GuideListener;
import lk.vexview.ui.menu.VexGameMenu;
import lk.vexview.ui.verification.VerNetManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import org.json.JSONObject;

public class NetPacketManager {
    public static void m1(PacketChannelChat packetChannelChat) {
        byte[] byArray;
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("packet_type", packetChannelChat.f2);
        jSONObject.put("packet_sub_type", packetChannelChat.f1);
        jSONObject.put("packet_data", packetChannelChat.f3);
        byte[] byArray2 = null;
        try {
            byArray = byArray2 = NetPacketManager.m3(jSONObject.toString());
        }
        catch (Exception exception) {
            byArray = byArray2;
        }
        ByteBuf byteBuf = Unpooled.wrappedBuffer((byte[])byArray);
        FMLProxyPacket fMLProxyPacket = new FMLProxyPacket(new PacketBuffer(byteBuf), "ChatVexView");
        VexView.s12.sendToServer(fMLProxyPacket);
    }

    public static String m2(String string) {
        int n;
        StackTraceElement stackTraceElement = new RuntimeException().getStackTrace()[1];
        String string2 = new StringBuffer(stackTraceElement.getClassName()).append(stackTraceElement.getMethodName()).toString();
        int n2 = string.length();
        int n3 = n2 - 1;
        char[] cArray = new char[n2];
        int n4 = 5 << 4 ^ (3 << 2 ^ 1);
        int cfr_ignored_0 = (3 ^ 5) << 3 ^ 2;
        int n5 = 5 << 4 ^ 1 << 1;
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

    private static /* synthetic */ byte[] m3(String string) throws IOException {
        GZIPOutputStream gZIPOutputStream;
        if (string == null || string.length() <= 0) {
            return string.getBytes("utf-8");
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GZIPOutputStream gZIPOutputStream2 = gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
        gZIPOutputStream2.write(string.getBytes("utf-8"));
        gZIPOutputStream2.close();
        return byteArrayOutputStream.toByteArray();
    }

    public static void m4(Packet packet) {
        byte[] byArray;
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("packet_type", packet.f2);
        jSONObject.put("packet_sub_type", packet.f1);
        jSONObject.put("packet_data", packet.f3);
        byte[] byArray2 = null;
        try {
            byArray = byArray2 = NetPacketManager.m3(jSONObject.toString());
        }
        catch (Exception exception) {
            byArray = byArray2;
        }
        if (byArray != null) {
            ByteBuf byteBuf = Unpooled.wrappedBuffer((byte[])byArray2);
            FMLProxyPacket fMLProxyPacket = new FMLProxyPacket(new PacketBuffer(byteBuf), "VexView");
            VexView.s9.sendToServer(fMLProxyPacket);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void m5(String string) throws UnsupportedEncodingException {
        try {
            int n;
            JSONObject jSONObject;
            block39: {
                jSONObject = new JSONObject(string);
                String string2 = jSONObject.getString("packet_type");
                int n2 = -1;
                switch (string2.hashCode()) {
                    case 102715: {
                        if (!string2.equals("gui")) break;
                        n = n2 = 0;
                        break block39;
                    }
                    case 1586596656: {
                        if (!string2.equals("scorebroad")) break;
                        n = n2 = 1;
                        break block39;
                    }
                    case -1768417199: {
                        if (!string2.equals("gamemenu")) break;
                        n = n2 = 2;
                        break block39;
                    }
                    case 2030499027: {
                        if (!string2.equals("flowview")) break;
                        n = n2 = 3;
                        break block39;
                    }
                    case 98712316: {
                        if (!string2.equals("guide")) break;
                        n = n2 = 4;
                        break block39;
                    }
                    case 103671: {
                        if (!string2.equals("hud")) break;
                        n = n2 = 5;
                        break block39;
                    }
                    case 116643: {
                        if (!string2.equals("ver")) break;
                        n = n2 = 6;
                        break block39;
                    }
                    case 2124767295: {
                        if (!string2.equals("dynamic")) break;
                        n = n2 = 7;
                        break block39;
                    }
                    case 114586: {
                        if (!string2.equals("tag")) break;
                        n = n2 = 8;
                        break block39;
                    }
                    case 36250312: {
                        if (!string2.equals("worldtag")) break;
                        n = n2 = 9;
                        break block39;
                    }
                    case -982431341: {
                        if (!string2.equals("potion")) break;
                        n = n2 = 10;
                        break block39;
                    }
                    case -1833928446: {
                        if (!string2.equals("effects")) break;
                        n = n2 = 11;
                        break block39;
                    }
                    case -649620375: {
                        if (!string2.equals("announce")) break;
                        n = n2 = 12;
                        break block39;
                    }
                    case -1909514487: {
                        if (!string2.equals("itemaction")) break;
                        n = n2 = 13;
                        break block39;
                    }
                    case 96965648: {
                        if (!string2.equals("extra")) break;
                        n2 = 14;
                    }
                }
                n = n2;
            }
            switch (n) {
                case 0: {
                    GuiPacketTools.m1(jSONObject);
                    return;
                }
                case 1: {
                    SBPacketBuffer.m2(jSONObject);
                    return;
                }
                case 2: {
                    VexGameMenu.m4(jSONObject.getString("packet_data"));
                    return;
                }
                case 3: {
                    FlowTools.m3(jSONObject.getString("packet_data"));
                    return;
                }
                case 4: {
                    GuideListener.m1();
                    return;
                }
                case 5: {
                    HudNetManager.m2(jSONObject);
                    return;
                }
                case 6: {
                    VerNetManager.m1(jSONObject);
                    return;
                }
                case 7: {
                    VexDynamicTools.m9(jSONObject);
                    return;
                }
                case 8: {
                    TagBuilder.m1(jSONObject);
                    return;
                }
                case 9: {
                    TagBuilder.m2(jSONObject);
                    return;
                }
                case 10: {
                    PotionTools.m3(jSONObject.getString("packet_data"));
                    return;
                }
                case 11: {
                    if (!jSONObject.getString("packet_sub_type").equals("lightning")) return;
                    VexEffect.m1(jSONObject.getString("packet_data"));
                    return;
                }
                case 12: {
                    AnnGui.m1(new Announcement(jSONObject.getString("packet_data"), Type.valueOf(jSONObject.getString("packet_sub_type"))));
                    return;
                }
                case 13: {
                    if (Minecraft.getMinecraft().currentScreen == null) return;
                    if (!(Minecraft.getMinecraft().currentScreen instanceof VexCustomGui)) return;
                    if (jSONObject.getString("packet_data").equals("accept")) {
                        ((VexCustomGui)Minecraft.getMinecraft().currentScreen).runAction();
                        return;
                    }
                    ((VexCustomGui)Minecraft.getMinecraft().currentScreen).cancelAction();
                    return;
                }
                case 14: {
                    InventoryTools.m1(jSONObject.getString("packet_data"));
                }
            }
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}

