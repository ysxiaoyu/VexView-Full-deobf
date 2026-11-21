/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.potion;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.ImageIO;
import lk.vexview.VexConfig;
import lk.vexview.VexView;
import lk.vexview.engine.BufferImageLib;
import lk.vexview.potion.CustomPotion;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class PotionTools {
    public static void m1(String string) {
        VexView.m2(() -> {
            InputStream inputStream;
            HttpURLConnection httpURLConnection;
            block4: {
                FileInputStream fileInputStream;
                try {
                    if (BufferImageLib.s4.containsKey(string)) return true;
                }
                catch (Exception exception) {
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

    private static /* synthetic */ void m2(String string, String string2, int n, int n2, boolean bl) {
        PotionEffect potionEffect;
        PotionEffect potionEffect2;
        block2: {
            PotionTools.m1(string);
            potionEffect2 = null;
            for (PotionEffect potionEffect3 : Minecraft.getMinecraft().player.getActivePotionEffects()) {
                if (!(potionEffect3.getPotion() instanceof CustomPotion) || !((CustomPotion)potionEffect3.getPotion()).f1.equals(string2)) continue;
                potionEffect = potionEffect2 = potionEffect3;
                break block2;
            }
            potionEffect = potionEffect2;
        }
        if (potionEffect != null) {
            Minecraft.getMinecraft().player.removeActivePotionEffect(potionEffect2.getPotion());
        }
        Minecraft.getMinecraft().player.addPotionEffect(new PotionEffect((Potion)new CustomPotion(string, string2, bl, 0xFFFFFF), n, n2));
    }

    public static void m3(String string) {
        String[] stringArray = string.split("<&>");
        PotionTools.m2(stringArray[0], stringArray[1], Integer.valueOf(stringArray[2]), Integer.valueOf(stringArray[3]), Boolean.valueOf(stringArray[4]));
    }
}

