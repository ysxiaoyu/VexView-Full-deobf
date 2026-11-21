/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.zip.GZIPInputStream;
import lk.vexview.VexConfig;
import lk.vexview.announcement.AnnGui;
import lk.vexview.auto.connect.AutoConnect;
import lk.vexview.chat.ChatTools;
import lk.vexview.effect.CustomRender;
import lk.vexview.flowview.FlowTools;
import lk.vexview.gui.tools.Base64Tools;
import lk.vexview.hud.HUDViewer;
import lk.vexview.hud.MinecraftHUD;
import lk.vexview.hud.util.Base64HudTools;
import lk.vexview.inventory.InventoryTools;
import lk.vexview.net.NetPacketManager;
import lk.vexview.net.packet.PacketKeyPress;
import lk.vexview.scoreboard.NewScoreBroad;
import lk.vexview.tag.TagView;
import lk.vexview.tooltip.ToolTipRender;
import lk.vexview.tutorial.GuideListener;
import lk.vexview.ui.menu.VexGameMenu;
import lk.vexview.ui.verification.ConnectGui;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.FMLEventChannel;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;

@Mod(modid="vexview", name="VexView", version="2.7.3", acceptedMinecraftVersions="[1.8,1.12.2]")
public class VexView {
    static ThreadPoolExecutor s1;
    public static final String s2 = "vexview";
    public static final KeyBinding s3;
    public static final String s4 = "2.7.3";
    static ScheduledExecutorService s5;
    static ThreadPoolExecutor s6;
    private static Logger s7;
    public static boolean s8;
    public static FMLEventChannel s9;
    public static FMLEventChannel s10;
    @Mod.Instance(value="vexview")
    public static VexView s11;
    public static FMLEventChannel s12;
    public static final String s13 = "VexView";
    public static FMLEventChannel s14;

    static {
        s8 = false;
        s5 = Executors.newScheduledThreadPool(10);
        s1 = (ThreadPoolExecutor)Executors.newFixedThreadPool(10);
        s6 = (ThreadPoolExecutor)Executors.newFixedThreadPool(10);
        s3 = new KeyBinding(I18n.format((String)"key.scoreboard", (Object[])new Object[0]), 49, I18n.format((String)"key.scoreboard.title", (Object[])new Object[0]));
    }

    public static void m1(Runnable runnable) {
        try {
            s1.execute(runnable);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    @SubscribeEvent
    public void onClientPacket(FMLNetworkEvent.ClientCustomPacketEvent clientCustomPacketEvent) throws IOException {
        byte[] data = new byte[clientCustomPacketEvent.getPacket().payload().readableBytes()];
        clientCustomPacketEvent.getPacket().payload().getBytes(0, data);
        NetPacketManager.m5(VexView.m3(data));
    }

    public static Future<Boolean> m2(Callable<Boolean> callable) {
        try {
            return s6.submit(callable);
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Mod.EventHandler
    public void preload(FMLPreInitializationEvent fMLPreInitializationEvent) {
        MinecraftForge.EVENT_BUS.register((Object)this);
        FMLCommonHandler.instance().bus().register((Object)this);
        MinecraftForge.EVENT_BUS.register((Object)new HUDViewer());
        FMLCommonHandler.instance().bus().register((Object)new HUDViewer());
        MinecraftForge.EVENT_BUS.register((Object)new InventoryTools());
        FMLCommonHandler.instance().bus().register((Object)new InventoryTools());
        MinecraftForge.EVENT_BUS.register((Object)new ConnectGui());
        FMLCommonHandler.instance().bus().register((Object)new ConnectGui());
        MinecraftForge.EVENT_BUS.register((Object)new VexGameMenu());
        FMLCommonHandler.instance().bus().register((Object)new VexGameMenu());
        MinecraftForge.EVENT_BUS.register((Object)new AutoConnect());
        FMLCommonHandler.instance().bus().register((Object)new AutoConnect());
        MinecraftForge.EVENT_BUS.register((Object)new FlowTools());
        FMLCommonHandler.instance().bus().register((Object)new FlowTools());
        MinecraftForge.EVENT_BUS.register((Object)new AnnGui());
        FMLCommonHandler.instance().bus().register((Object)new AnnGui());
        MinecraftForge.EVENT_BUS.register((Object)new NewScoreBroad());
        FMLCommonHandler.instance().bus().register((Object)new NewScoreBroad());
        MinecraftForge.EVENT_BUS.register((Object)new MinecraftHUD());
        FMLCommonHandler.instance().bus().register((Object)new MinecraftHUD());
        MinecraftForge.EVENT_BUS.register((Object)new GuideListener());
        FMLCommonHandler.instance().bus().register((Object)new GuideListener());
        MinecraftForge.EVENT_BUS.register((Object)new TagView());
        FMLCommonHandler.instance().bus().register((Object)new TagView());
        MinecraftForge.EVENT_BUS.register((Object)new CustomRender());
        FMLCommonHandler.instance().bus().register((Object)new CustomRender());
        MinecraftForge.EVENT_BUS.register((Object)new ChatTools());
        FMLCommonHandler.instance().bus().register((Object)new ChatTools());
        MinecraftForge.EVENT_BUS.register((Object)new ToolTipRender());
        FMLCommonHandler.instance().bus().register((Object)new ToolTipRender());
        s9 = NetworkRegistry.INSTANCE.newEventDrivenChannel(s13);
        s9.register((Object)this);
        s10 = NetworkRegistry.INSTANCE.newEventDrivenChannel("Base64VexView");
        s10.register((Object)new Base64Tools());
        s14 = NetworkRegistry.INSTANCE.newEventDrivenChannel("HudBase64VexView");
        s14.register((Object)new Base64HudTools());
        s12 = NetworkRegistry.INSTANCE.newEventDrivenChannel("ChatVexView");
        s12.register((Object)new ChatTools());
        FMLPreInitializationEvent fMLPreInitializationEvent2 = fMLPreInitializationEvent;
        s7 = fMLPreInitializationEvent2.getModLog();
        VexConfig.m1(fMLPreInitializationEvent2);
        File file = new File(VexConfig.s1);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    private static /* synthetic */ String m3(byte[] byArray) throws IOException {
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

    @SubscribeEvent
    public void keyListener(InputEvent.KeyInputEvent keyInputEvent) {
        if (Keyboard.getEventKey() == 30 || Keyboard.getEventKey() == 31 || Keyboard.getEventKey() == 32 || Keyboard.getEventKey() == 17 || Keyboard.getEventKey() == 57) {
            return;
        }
        String string = new StringBuilder().insert(0, "KeyBoardPress:").append(Keyboard.getEventKey()).append(":").append(Keyboard.getEventKeyState() ? 1 : 0).append(":NOGUI").toString();
        NetPacketManager.m4(new PacketKeyPress(string));
    }

    public static void m4(Runnable runnable, long l, long l2, TimeUnit timeUnit) {
        try {
            s5.scheduleAtFixedRate(runnable, l, l2, timeUnit);
            return;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return;
        }
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent fMLInitializationEvent) {
        ClientRegistry.registerKeyBinding((KeyBinding)s3);
    }
}

