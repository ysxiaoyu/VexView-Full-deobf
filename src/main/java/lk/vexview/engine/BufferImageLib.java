/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.engine;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import javax.imageio.ImageIO;
import lk.vexview.VexConfig;
import lk.vexview.VexView;
import lk.vexview.gui.core.Downable;
import lk.vexview.gui.core.GifDecoder;
import lk.vexview.gui.core.VexGifImage;
import lk.vexview.net.NetPacketManager;
import lk.vexview.net.packet.PacketDownload;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.util.ResourceLocation;

public class BufferImageLib {
    public static Map<String, ResourceLocation> s1 = new ConcurrentHashMap<String, ResourceLocation>();
    public static Map<String, List<ResourceLocation>> s2;
    public static Map<String, ResourceLocation> s3;
    public static Map<String, ResourceLocation> s4;
    public static Map<String, ResourceLocation> s5;

    static {
        s4 = new ConcurrentHashMap<String, ResourceLocation>();
        s5 = new ConcurrentHashMap<String, ResourceLocation>();
        s3 = new ConcurrentHashMap<String, ResourceLocation>();
        s2 = new ConcurrentHashMap<String, List<ResourceLocation>>();
    }

    public static void m2(String string, BufferedImage bufferedImage) {
        if (!s5.containsKey(string)) {
            Minecraft.getMinecraft().addScheduledTask(() -> s5.put(string, Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("normal", new DynamicTexture(bufferedImage))));
        }
    }

    public static Future<Boolean> m4(String string, Downable downable, String string2) {
        return VexView.m2(() -> {
            InputStream inputStream;
            HttpURLConnection httpURLConnection;
            block4: {
                FileInputStream fileInputStream;
                try {
                    if (s4.containsKey(string)) return true;
                }
                catch (Exception exception) {
                    NetPacketManager.m4(new PacketDownload(string2));
                    return false;
                }
                if (!string.contains("[local]")) break block4;
                File file = new File(VexConfig.s1, string.replace("[local]", ""));
                FileInputStream fileInputStream2 = fileInputStream = new FileInputStream(file);
                BufferImageLib.m8(string, ImageIO.read(fileInputStream2));
                ((InputStream)fileInputStream2).close();
                return true;
            }
            URL uRL = new URL(string);
            HttpURLConnection httpURLConnection2 = httpURLConnection = (HttpURLConnection)uRL.openConnection();
            httpURLConnection2.setRequestMethod("GET");
            httpURLConnection2.setConnectTimeout(3000);
            InputStream inputStream2 = inputStream = httpURLConnection2.getInputStream();
            BufferImageLib.m8(string, ImageIO.read(inputStream2));
            inputStream2.close();
            return true;
        });
    }

    public static void m5(String string, BufferedImage bufferedImage) {
        if (!s3.containsKey(string)) {
            Minecraft.getMinecraft().addScheduledTask(() -> s3.put(string, Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("base64", new DynamicTexture(bufferedImage))));
        }
    }

    public static void m8(String string, BufferedImage bufferedImage) {
        if (!s4.containsKey(string)) {
            Minecraft.getMinecraft().addScheduledTask(() -> s4.put(string, Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("normal", new DynamicTexture(bufferedImage))));
        }
    }

    public static Future<Boolean> m9(String string, VexGifImage vexGifImage) {
        return VexView.m2(() -> {
            try {
                HttpURLConnection httpURLConnection;
                if (s2.containsKey(string)) {
                    return true;
                }
                if (string.contains("[local]")) {
                    File file = new File(VexConfig.s1, string.replace("[local]", ""));
                    FileInputStream fileInputStream = new FileInputStream(file);
                    GifDecoder gifDecoder = new GifDecoder();
                    gifDecoder.m12(fileInputStream);
                    int n = gifDecoder.m21();
                    if (vexGifImage.f2 == 0) {
                        vexGifImage.f2 = gifDecoder.f1;
                    }
                    VexGifImage.s1.put(string, vexGifImage.f2);
                    ArrayList<BufferedImage> arrayList = new ArrayList<BufferedImage>();
                    int n2 = 0;
                    int n3 = n2;
                    while (n3 < n) {
                        BufferedImage bufferedImage = gifDecoder.m9(n2);
                        arrayList.add(bufferedImage);
                        n3 = ++n2;
                    }
                    BufferImageLib.m13(string, arrayList);
                    ((InputStream)fileInputStream).close();
                    return true;
                }
                GifDecoder gifDecoder = new GifDecoder();
                URL uRL = new URL(string);
                HttpURLConnection httpURLConnection2 = httpURLConnection = (HttpURLConnection)uRL.openConnection();
                httpURLConnection2.setRequestMethod("GET");
                httpURLConnection2.setConnectTimeout(3000);
                InputStream inputStream = httpURLConnection2.getInputStream();
                gifDecoder.m12(inputStream);
                int n = gifDecoder.m21();
                if (vexGifImage.f2 == 0) {
                    vexGifImage.f2 = gifDecoder.f1;
                }
                VexGifImage.s1.put(string, vexGifImage.f2);
                ArrayList<BufferedImage> arrayList = new ArrayList<BufferedImage>();
                int n4 = 0;
                int n5 = n4;
                while (true) {
                    if (n5 >= n) {
                        BufferImageLib.m13(string, arrayList);
                        inputStream.close();
                        return true;
                    }
                    BufferedImage bufferedImage = gifDecoder.m9(n4);
                    arrayList.add(bufferedImage);
                    n5 = ++n4;
                }
            }
            catch (Exception exception) {
                NetPacketManager.m4(new PacketDownload("GIFIMAGE"));
                return false;
            }
        });
    }

    public static void m13(String string, List<BufferedImage> list) {
        if (!s2.containsKey(string)) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                ArrayList arrayList = new ArrayList();
                list.forEach(bufferedImage -> arrayList.add(Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation("gif", new DynamicTexture(bufferedImage))));
                s2.put(string, arrayList);
            });
        }
    }

    public static void m14(String string) {
        if (!s1.containsKey(string)) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                String string2 = string;
                String string3 = string;
                s1.put(string3, new ResourceLocation(string3));
            });
        }
    }
}

