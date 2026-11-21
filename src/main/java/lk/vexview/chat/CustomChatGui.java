package lk.vexview.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import lk.vexview.announcement.AnnGui;
import lk.vexview.announcement.Announcement;
import lk.vexview.announcement.Type;
import lk.vexview.hud.HUDViewer;
import lk.vexview.net.NetPacketManager;
import lk.vexview.net.packet.PacketChannelChat;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

public class CustomChatGui extends GuiScreen {
   protected GuiTextField f1;
   private String f2 = "";

   protected void setText(String var1, boolean var2) {
      if (var2) {
         this.f1.setText(var1);
      } else {
         this.f1.writeText(var1);
      }
   }

   public void sendChatMessage(String var1) {
      NetPacketManager.m1(new PacketChannelChat(var1, ChatTools.m12()));
   }

   public void drawScreen(int var1, int var2, float var3) {
      drawRect(2, this.height - 14, this.width - 2, this.height - 2, Integer.MIN_VALUE);
      this.f1.drawTextBox();
      ChatTools.s2.m7();
      super.drawScreen(var1, var2, var3);
   }

   protected void keyTyped(char var1, int var2) throws IOException {
      if (var2 == 28) {
         if (!this.f1.getText().isEmpty()) {
            this.sendChatMessage(this.f1.getText());
         }

         this.mc.player.closeScreen();
      } else {
         this.f1.textboxKeyTyped(var1, var2);
         super.keyTyped(var1, var2);
      }
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      this.f1 = new GuiTextField(0, this.fontRenderer, 4, this.height - 12, this.width - 4, 12);
      this.f1.setMaxStringLength(256);
      this.f1.setEnableBackgroundDrawing(false);
      this.f1.setFocused(true);
      this.f1.setText(this.f2);
      this.f1.setCanLoseFocus(false);
      this.buttonList.clear();
      final FontRenderer var7 = this.fontRenderer;
      ArrayList var8;
      (var8 = new ArrayList()).add(ChatTools.m6());
      var8.addAll(ChatTools.s3.keySet());
      int var9 = 0;
      Iterator var10 = var8.iterator();

      for (Iterator var10000 = var10; var10000.hasNext(); var10000 = var10) {
         String var11 = (String)var10.next();
         int var12;
         if ((var12 = var8.indexOf(var11)) == 0) {
            var9 = 0;
         } else {
            var9 = var9 + var7.getStringWidth((String)var8.get(var12 - 1)) + 12;
         }

         GuiButton var13 = new GuiButton(-100 + var12, 2 + var9, this.height - 35, var7.getStringWidth(var11) + 8, 16, var11) {
            public void drawButton(Minecraft var1, int var2, int var3, float var4) {
               this.hovered = var2 >= this.x && var3 >= this.y && var2 < this.x + this.width && var3 < this.y + this.height;
               drawRect(this.x + 1, this.y + 1, this.x + this.width - 1, this.y + this.height - 1, this.hovered && this.enabled ? -1603642675 : -1610610688);
               drawRect(this.x, this.y, this.x + this.width, this.y + 1, this.enabled ? -1603704471 : -1601557534);
               drawRect(this.x, this.y + this.height - 1, this.x + this.width, this.y + this.height, this.enabled ? -1603704471 : -1601557534);
               drawRect(this.x, this.y, this.x + 1, this.y + this.height, this.enabled ? -1603704471 : -1601557534);
               drawRect(this.x + this.width - 1, this.y, this.x + this.width, this.y + this.height, this.enabled ? -1603704471 : -1601557534);
               var2 = 14737632;
               GuiButton var10000x;
               if (!this.enabled) {
                  var2 = 10526880;
                  var10000x = this;
               } else {
                  if (this.hovered) {
                     var2 = 16777120;
                  }

                  var10000x = this;
               }

               var10000x.drawCenteredString(var7, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, var2);
            }
         };
         this.buttonList.add(var13);
         if (ChatTools.s1.equals(var11)) {
            var13.enabled = false;
         }

         HUDViewer.s2.values().forEach(var1 -> this.buttonList.add(var1.m2()));
      }
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
      ChatTools.s2.f1 = 0;
   }

   public boolean doesGuiPauseGame() {
      return false;
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if (var1.id != 0) {
         if (var1.displayString.equals(ChatTools.m6())) {
            ChatTools.s1 = var1.displayString;
            Minecraft.getMinecraft().displayGuiScreen(new DefaultChatGui());
            StringBuilder var2 = new StringBuilder();
            Object[] var3 = new Object[0];
            boolean var4 = true;
            AnnGui.m1(new Announcement(var2.append(I18n.format("switch.channel", var3)).append(var1.displayString).toString(), Type.s1));
         } else {
            ChatTools.s1 = var1.displayString;
            var1.enabled = false;
            ChatTools.s2.f1 = 0;
            this.buttonList.stream().filter(var1x -> !var1x.displayString.equals(var1.displayString)).forEach(var0 -> var0.enabled = true);
            StringBuilder var10002 = new StringBuilder();
            Object[] var10004 = new Object[0];
            boolean var10006 = true;
            AnnGui.m1(new Announcement(var10002.append(I18n.format("switch.channel", var10004)).append(var1.displayString).toString(), Type.s1));
         }
      }
   }
}
