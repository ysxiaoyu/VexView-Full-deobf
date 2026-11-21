/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.chat;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;
import lk.vexview.chat.ChatString;
import lk.vexview.chat.ChatTools;
import lk.vexview.chat.CustomChatGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraft.client.gui.ScaledResolution;

public class ChatInGui {
    public int f1;
    public ScheduledExecutorService f2;
    public int f3;

    public ChatInGui() {
        ChatInGui chatInGui = this;
        chatInGui.f2 = Executors.newScheduledThreadPool(50);
        chatInGui.f3 = 0;
    }

    public static String m1(String string) {
        int n;
        StackTraceElement stackTraceElement = new RuntimeException().getStackTrace()[1];
        String string2 = new StringBuffer(stackTraceElement.getMethodName()).append(stackTraceElement.getClassName()).toString();
        int n2 = string.length();
        int n3 = n2 - 1;
        char[] cArray = new char[n2];
        int n4 = 2 << 3 ^ 4;
        int cfr_ignored_0 = (2 ^ 5) << 3 ^ 4;
        int n5 = 4 << 4 ^ (3 ^ 5) << 1;
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

    private boolean m2(int n, ChatString chatString) {
        int n2 = ChatTools.m3().indexOf(chatString);
        return -this.f1 <= n2 && n2 < -this.f1 + n / 10;
    }

    private static void m3(List<ChatString> list, int n, int n2, ChatString chatString) {
        int n3 = list.indexOf(chatString);
        Gui.drawRect(0, n - 2 - 10 * n3, n2, n - 2 - 10 * (n3 - 1), 0x70000000);
        Minecraft.getMinecraft().fontRenderer.drawString(chatString.toString(), 2, n - 10 * n3, -1);
    }

    public boolean m4(double d) {
        Minecraft minecraft = Minecraft.getMinecraft();
        int n = GuiNewChat.calculateChatboxHeight((float)(this.m6() ? minecraft.gameSettings.chatHeightFocused : minecraft.gameSettings.chatHeightUnfocused));
        if ((double)this.f1 + d > 0.0) {
            this.f1 = 0;
        } else {
            ChatInGui chatInGui = this;
            chatInGui.f1 = (double)this.f1 + d <= (double)(-ChatTools.m3().size() + n / 10) ? -ChatTools.m3().size() + n / 10 : (int)((double)this.f1 + d);
        }
        return true;
    }

    private boolean m5(int n, ChatString chatString) {
        int n2 = ChatTools.m3().indexOf(chatString);
        return n2 >= 0 && n2 < (this.f3 > n ? n : this.f3);
    }

    public boolean m6() {
        return Minecraft.getMinecraft().currentScreen != null && Minecraft.getMinecraft().currentScreen instanceof CustomChatGui;
    }

    public void m7() {
        Minecraft minecraft = Minecraft.getMinecraft();
        int n = GuiNewChat.calculateChatboxWidth((float)Minecraft.getMinecraft().gameSettings.chatWidth);
        int n2 = GuiNewChat.calculateChatboxHeight((float)(this.m6() ? minecraft.gameSettings.chatHeightFocused : minecraft.gameSettings.chatHeightUnfocused));
        int n3 = new ScaledResolution(Minecraft.getMinecraft()).getScaledHeight() - 50;
        List<ChatString> list = this.m6() ? new ArrayList<ChatString>(ChatTools.m3()).stream().filter(chatString -> this.m2(n2, chatString)).collect(Collectors.toList()) : new ArrayList<ChatString>(ChatTools.m3()).stream().filter(chatString -> this.m5(n2, chatString)).collect(Collectors.toList());
        list.forEach(chatString -> m3(list, n3, n, chatString));
    }
}

