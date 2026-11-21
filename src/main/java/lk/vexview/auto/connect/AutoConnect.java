/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.auto.connect;

import com.mojang.authlib.GameProfile;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import lk.vexview.VexConfig;
import lk.vexview.auto.connect.DisConnectGui;
import lk.vexview.auto.connect.VexPerpareGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.INetHandler;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.ServerStatusResponse;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.status.INetHandlerStatusClient;
import net.minecraft.network.status.client.CPacketPing;
import net.minecraft.network.status.client.CPacketServerQuery;
import net.minecraft.network.status.server.SPacketPong;
import net.minecraft.network.status.server.SPacketServerInfo;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.ArrayUtils;

public class AutoConnect {
    public static boolean s1 = false;

    @SubscribeEvent
    public void onOpen(GuiOpenEvent guiOpenEvent) {
        if (VexConfig.s8 && guiOpenEvent.getGui() instanceof GuiMainMenu) {
            guiOpenEvent.setGui((GuiScreen)new VexPerpareGui());
        }
        if (VexConfig.s8 && guiOpenEvent.getGui() instanceof GuiMultiplayer) {
            guiOpenEvent.setGui((GuiScreen)new DisConnectGui());
        }
        if (VexConfig.s8 && guiOpenEvent.getGui() instanceof GuiDisconnected) {
            guiOpenEvent.setGui((GuiScreen)new DisConnectGui());
        }
    }

    public static void m1() {
        new Timer("").schedule(new TimerTask(){

            @Override
            public void run() {
                if (VexConfig.s8) {
                    if (VexConfig.s12.populationInfo == null) {
                        try {
                            AutoConnect.m2(VexConfig.s12);
                            return;
                        }
                        catch (Exception exception) {
                            VexConfig.s12.populationInfo = "No";
                            return;
                        }
                        finally {
                            AutoConnect.m1();
                        }
                    }
                    Minecraft.getMinecraft().addScheduledTask(() -> {
                        FMLClientHandler.instance().setupServerList();
                        FMLClientHandler.instance().connectToServer((GuiScreen)new DisConnectGui(), VexConfig.s12);
                    });
                }
            }
        }, 1000L);
    }

    public static void m2(final ServerData serverData) throws UnknownHostException {
        ServerAddress serverAddress = ServerAddress.fromString((String)serverData.serverIP);
        NetworkManager networkManager = NetworkManager.createNetworkManagerAndConnect((InetAddress)InetAddress.getByName(serverAddress.getIP()), (int)serverAddress.getPort(), (boolean)false);
        serverData.serverMOTD = I18n.format((String)"multiplayer.status.pinging", (Object[])new Object[0]);
        ServerData serverData2 = serverData;
        serverData2.pingToServer = -1L;
        serverData2.playerList = null;
        NetworkManager networkManager2 = networkManager;
        final NetworkManager networkManager3 = networkManager;
        networkManager3.setNetHandler((INetHandler)new INetHandlerStatusClient(){
            private long f1;
            private boolean f2;
            private boolean f3;

            public void onDisconnect(ITextComponent iTextComponent) {
                if (!this.f2) {
                    serverData.serverMOTD = TextFormatting.DARK_RED + I18n.format((String)"multiplayer.status.cannot_connect", (Object[])new Object[0]);
                    serverData.populationInfo = "";
                }
            }

            public void handleServerInfo(SPacketServerInfo sPacketServerInfo) {
                ServerStatusResponse serverStatusResponse;
                ServerStatusResponse serverStatusResponse2;
                block14: {
                    block13: {
                        block12: {
                            int n;
                            ServerStatusResponse serverStatusResponse3;
                            ServerStatusResponse serverStatusResponse4;
                            if (this.f3) {
                                networkManager3.closeChannel((ITextComponent)new TextComponentTranslation("multiplayer.status.unrequested", new Object[0]));
                                return;
                            }
                            this.f3 = true;
                            serverStatusResponse2 = sPacketServerInfo.getResponse();
                            if (serverStatusResponse2.getServerDescription() != null) {
                                ServerStatusResponse serverStatusResponse5 = serverStatusResponse2;
                                serverStatusResponse4 = serverStatusResponse5;
                                serverData.serverMOTD = serverStatusResponse5.getServerDescription().getFormattedText();
                            } else {
                                serverData.serverMOTD = "";
                                serverStatusResponse4 = serverStatusResponse2;
                            }
                            if (serverStatusResponse4.getVersion() != null) {
                                serverData.gameVersion = serverStatusResponse2.getVersion().getName();
                                ServerStatusResponse serverStatusResponse6 = serverStatusResponse2;
                                serverStatusResponse3 = serverStatusResponse6;
                                serverData.version = serverStatusResponse6.getVersion().getProtocol();
                            } else {
                                serverData.gameVersion = I18n.format((String)"multiplayer.status.old", (Object[])new Object[0]);
                                serverStatusResponse3 = serverStatusResponse2;
                                serverData.version = 0;
                            }
                            if (serverStatusResponse3.getPlayers() == null) break block12;
                            ServerStatusResponse serverStatusResponse7 = serverStatusResponse2;
                            serverData.populationInfo = TextFormatting.GRAY + "" + serverStatusResponse7.getPlayers().getOnlinePlayerCount() + "" + TextFormatting.DARK_GRAY + "/" + TextFormatting.GRAY + serverStatusResponse2.getPlayers().getMaxPlayers();
                            if (!ArrayUtils.isNotEmpty((Object[])serverStatusResponse7.getPlayers().getPlayers())) break block13;
                            StringBuilder stringBuilder = new StringBuilder();
                            GameProfile[] gameProfileArray = serverStatusResponse2.getPlayers().getPlayers();
                            int n2 = gameProfileArray.length;
                            int n3 = n = 0;
                            while (n3 < n2) {
                                GameProfile gameProfile = gameProfileArray[n];
                                if (stringBuilder.length() > 0) {
                                    stringBuilder.append("\n");
                                }
                                stringBuilder.append(gameProfile.getName());
                                n3 = ++n;
                            }
                            if (serverStatusResponse2.getPlayers().getPlayers().length < serverStatusResponse2.getPlayers().getOnlinePlayerCount()) {
                                if (stringBuilder.length() > 0) {
                                    stringBuilder.append("\n");
                                }
                                Object[] objectArray = new Object[1];
                                objectArray[0] = serverStatusResponse2.getPlayers().getOnlinePlayerCount() - serverStatusResponse2.getPlayers().getPlayers().length;
                                stringBuilder.append(I18n.format((String)"multiplayer.status.and_more", (Object[])objectArray));
                            }
                            serverData.playerList = stringBuilder.toString();
                            serverStatusResponse = serverStatusResponse2;
                            break block14;
                        }
                        serverData.populationInfo = TextFormatting.DARK_GRAY + I18n.format((String)"multiplayer.status.unknown", (Object[])new Object[0]);
                    }
                    serverStatusResponse = serverStatusResponse2;
                }
                if (serverStatusResponse.getFavicon() != null) {
                    String string = serverStatusResponse2.getFavicon();
                    if (string.startsWith("data:image/png;base64,")) {
                        serverData.setBase64EncodedIconData(string.substring("data:image/png;base64,".length()));
                    }
                } else {
                    serverData.setBase64EncodedIconData((String)null);
                }
                FMLClientHandler.instance().bindServerListData(serverData, serverStatusResponse2);
                this.f1 = Minecraft.getSystemTime();
                networkManager3.sendPacket((Packet)new CPacketPing(this.f1));
                this.f2 = true;
            }

            public void handlePong(SPacketPong sPacketPong) {
                long l = this.f1;
                long l2 = Minecraft.getSystemTime();
                serverData.pingToServer = l2 - l;
                networkManager3.closeChannel((ITextComponent)new TextComponentString("Finished"));
            }
        });
        try {
            NetworkManager networkManager4 = networkManager;
            networkManager4.sendPacket((Packet)new C00Handshake(serverAddress.getIP(), serverAddress.getPort(), EnumConnectionState.STATUS, true));
            networkManager4.sendPacket((Packet)new CPacketServerQuery());
            return;
        }
        catch (Throwable throwable) {
            throwable.printStackTrace();
            return;
        }
    }
}

