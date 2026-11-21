/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview;

import lk.vexview.hud.util.HudNetManager;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.lwjgl.opengl.Display;

public class VexConfig {
    public static String s1;
    public static double s2;
    public static boolean s3;
    public static double s4;
    public static int s5;
    public static boolean s6;
    public static String s7;
    public static boolean s8;
    public static String s9;
    static String s10;
    public static boolean s11;
    public static ServerData s12;
    public static boolean s13;
    public static boolean s14;

    static {
        s8 = false;
        s11 = false;
    }

    public static void m1(FMLPreInitializationEvent fMLPreInitializationEvent) {
        Configuration configuration;
        Configuration configuration2 = configuration = new Configuration(fMLPreInitializationEvent.getSuggestedConfigurationFile());
        Configuration configuration3 = configuration;
        Configuration configuration4 = configuration;
        Configuration configuration5 = configuration;
        Configuration configuration6 = configuration;
        Configuration configuration7 = configuration;
        Configuration configuration8 = configuration;
        configuration8.load();
        s6 = configuration8.get("Update", "close", false).getBoolean();
        s3 = configuration7.get("Update", "enable", false).getBoolean();
        s7 = configuration7.get("Update", "Textures", "YourName").getString();
        s10 = configuration6.get("Client", "Title", "").getString();
        s8 = configuration6.get("AutoConnect", "Enable", false).getBoolean();
        s9 = configuration5.get("AutoConnect", "IP", "localhost:25565").getString();
        s1 = configuration5.get("ImagePath", "local_path", "vexview/textures").getString();
        s11 = configuration4.get("RelativeLayout", "Enable", false).getBoolean();
        s2 = configuration4.get("RelativeLayout", "Height", 0.2).getDouble();
        s4 = configuration3.get("RelativeLayout", "Width", 0.9).getDouble();
        s5 = configuration3.get("Connect", "Delay", 3).getInt();
        s13 = configuration2.get("ConnectGui", "Show", true).getBoolean();
        s14 = configuration2.get("ConnectGui", "RetryGui", true).getBoolean();
        configuration2.save();
        if (s10 != null && !s10.equals("")) {
            Display.setTitle((String)s10);
        }
        if (s8) {
            s12 = new ServerData("Minecraft Server", s9, true);
        }
    }
}

