/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.hud.util;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import javax.imageio.ImageIO;
import lk.vexview.VexView;
import lk.vexview.engine.BufferImageLib;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.json.JSONObject;

public class Base64HudTools {
    private static String s1 = "";

    public static void m1(String string) {
        VexView.m1(() -> {
            try {
                if (!s1.equals("")) {
                    Base64.Decoder decoder = Base64.getDecoder();
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decoder.decode(string.getBytes("utf-8")));
                    BufferImageLib.m5(s1, ImageIO.read(byteArrayInputStream));
                    s1 = "";
                    return;
                }
                JSONObject jSONObject = new JSONObject(string);
                s1 = jSONObject.getString("id");
            }
            catch (Exception exception) {
                s1 = "";
            }
        });
    }

    @SubscribeEvent
    public void onClientPacket(FMLNetworkEvent.ClientCustomPacketEvent object) throws UnsupportedEncodingException {
        FMLNetworkEvent.ClientCustomPacketEvent clientCustomPacketEvent = object;
        byte[] bytes = new byte[clientCustomPacketEvent.getPacket().payload().readableBytes()];
        clientCustomPacketEvent.getPacket().payload().getBytes(0, bytes);
        Base64HudTools.m1(new String(bytes, "utf-8"));
    }
}


