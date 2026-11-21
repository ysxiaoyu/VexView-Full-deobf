/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.tools;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import javax.imageio.ImageIO;
import lk.vexview.VexView;
import lk.vexview.engine.BufferImageLib;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import org.json.JSONObject;

public class Base64Tools {
    private static String s1 = "";

    public static void m2(String string) {
        VexView.m1(() -> {
            try {
                if (!s1.equals("")) {
                    Base64.Decoder decoder = Base64.getDecoder();
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decoder.decode(string.getBytes("utf-8")));
                    BufferImageLib.m2(s1, ImageIO.read(byteArrayInputStream));
                    s1 = "";
                    return;
                }
                JSONObject jSONObject = new JSONObject(string);
                s1 = jSONObject.getString("id");
                return;
            }
            catch (Exception exception) {
                s1 = "";
            }
        });
    }

    @SubscribeEvent
    public void onClientPacket(FMLNetworkEvent.ClientCustomPacketEvent clientCustomPacketEvent) throws UnsupportedEncodingException {
        byte[] data = new byte[clientCustomPacketEvent.getPacket().payload().readableBytes()];
        clientCustomPacketEvent.getPacket().payload().getBytes(0, data);
        Base64Tools.m2(new String(data, "utf-8").replace("<Base64_image>", ""));
    }
}

