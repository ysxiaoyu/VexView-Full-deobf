package lk.vexview.chat;

import java.io.IOException;
import java.util.ArrayList;
import lk.vexview.announcement.AnnGui;
import lk.vexview.announcement.Announcement;
import lk.vexview.announcement.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;

public class DefaultChatGui extends VexChatGui {
   public DefaultChatGui() {
      super("");
   }

   public void drawScreen(int var1, int var2, float var3) {
      super.drawScreen(var1, var2, var3);
   }

   public DefaultChatGui(String var1) {
      super(var1);
   }

   public void initGui() {
      if (!ChatTools.m7()) {
         Minecraft.getMinecraft().displayGuiScreen(new CustomChatGui());
      }

      this.buttonList.clear();
      final FontRenderer var7 = this.fontRenderer;
      ArrayList<String> var8;
      (var8 = new ArrayList<String>()).add(ChatTools.m6());
      var8.addAll(ChatTools.s3.keySet());
      int var9 = 0;

      for (String var11 : var8) {
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
               GuiButton var10000;
               if (!this.enabled) {
                  var2 = 10526880;
                  var10000 = this;
               } else {
                  if (this.hovered) {
                     var2 = 16777120;
                  }

                  var10000 = this;
               }

               var10000.drawCenteredString(var7, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, var2);
            }
         };
         this.buttonList.add(var13);
         if (ChatTools.s1.equals(var11)) {
            var13.enabled = false;
         }
      }

      super.initGui();
   }

   protected void actionPerformed(GuiButton var1) throws IOException {
      if (var1.id != 0) {
         ChatTools.s1 = var1.displayString;
         Minecraft.getMinecraft().displayGuiScreen(new CustomChatGui());
         StringBuilder var10002 = new StringBuilder();
         Object[] var10004 = new Object[0];
         boolean var10006 = true;
         AnnGui.m1(new Announcement(var10002.append(I18n.format("switch.channel", var10004)).append(var1.displayString).toString(), Type.s1));
      }
   }
}
