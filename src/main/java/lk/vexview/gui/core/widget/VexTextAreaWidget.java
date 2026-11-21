package lk.vexview.gui.core.widget;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import lk.vexview.gui.core.VexTextArea;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.math.MathHelper;

public class VexTextAreaWidget extends GuiTextField {
   private int f1;
   private int f2;
   private int f3;
   private StringLines f4;
   private int f5;
   public int f6;
   private VexTextArea f7;
   private FontRenderer f8 = Minecraft.getMinecraft().fontRenderer;
   private int f9;

   public void setFocused(boolean var1) {
      super.setFocused(var1);
   }

   public void init(List<String> var1) {
      this.f4 = new StringLines(var1);
   }

   public void drawTextBox() {
      if (this.f9 == -6250336 && this.f1 == -16777216) {
         drawRect(this.x - 1, this.y - 1, this.x + this.width + 1, this.y + this.height + 1, -6250336);
         drawRect(this.x, this.y, this.x + this.width, this.y + this.height, -16777216);
      } else {
         drawRect(this.x, this.y, this.x + this.width, this.y + 1, this.f9);
         drawRect(this.x, this.y + this.height - 1, this.x + this.width, this.y + this.height, this.f9);
         drawRect(this.x, this.y, this.x + 1, this.y + this.height, this.f9);
         drawRect(this.x + this.width - 1, this.y, this.x + this.width, this.y + this.height, this.f9);
         drawRect(this.x + 1, this.y + 1, this.x + this.width - 1, this.y + this.height - 1, this.f1);
      }

      AtomicInteger var3 = new AtomicInteger(0);
      this.f4.m22().forEach(var2 -> {
         this.f8.drawStringWithShadow(var2, this.x + 2, this.y + 3 + var3.get() * 10, 16777215);
         var3.getAndIncrement();
      });
      if (this.isFocused()) {
         boolean var4 = this.f3 < this.f4.m16(this.f2).length();
         if (var4) {
            int var6 = this.x + 2 + this.f8.getStringWidth(this.f4.m18(this.f2, this.f3));
            int var5;
            drawRect(var6, var5 = this.y + 3 + this.f2 * 10, var6 + 1, var5 + 9, -3092272);
            return;
         }

         int var7 = this.x + 3 + this.f8.getStringWidth(this.f4.m16(this.f2));
         this.f8.drawStringWithShadow("_", var7, this.y + this.f2 * 10 + 3, 14737632);
      }
   }

   public boolean writeText(char var1, int var2) {
      if (this.f4.m7(this.f5)) {
         boolean var5 = this.f8.getStringWidth(this.f4.m16(this.f2)) >= this.width - 10;
         boolean var4 = this.f3 == this.f4.m16(this.f2).length();
         if (var5) {
            if (var4) {
               if (this.f4.m1(this.f2)) {
                  this.f4.m15(this.f2, this.f3, var1, this.f8, this.width);
               }

               this.f3 = 1;
               this.f2++;
            } else {
               this.f4.m8(this.f2, this.f3, var1, this.f8, this.width);
               if (this.f3 == this.f4.m16(this.f2).length()) {
                  this.f2++;
                  this.f3 = 1;
               } else {
                  this.f3++;
               }
            }
         } else {
            this.f4.m20(this.f2, this.f3, var1);
            this.f3++;
         }

         return true;
      } else {
         return false;
      }
   }

   public void resetPosition(int var1, int var2, int var3, int var4) {
      this.x = var1 + (this.f7.f2 == -1 ? this.width / 2 - this.f7.f5 / 2 : this.f7.f2);
      this.y = var2 + (this.f7.f7 == -1 ? this.height / 2 - this.f7.f1 / 2 : this.f7.f7);
   }

   public List<String> getTextLines() {
      return this.f4.m19();
   }

   public boolean mouseClicked(int var1, int var2, int var3) {
      boolean var5 = var1 >= this.x && var1 < this.x + this.width && var2 >= this.y && var2 < this.y + this.height;
      this.setFocused(var5);
      if (this.isFocused() && var5 && var3 == 0) {
         var1 = MathHelper.floor(var1) - this.x;
         if (this.isFocused()) {
            var1 -= 4;
         }

         if ((var2 = (var2 - this.y - 3) / 10) < this.f4.m17()) {
            this.f3 = MathHelper.clamp(this.f8.trimStringToWidth(this.f4.m16(var2), var1).length(), 0, this.f4.m16(var2).length());
            this.f2 = var2;
         } else {
            this.f3 = 0;
            this.f2 = this.f4.m17() > 0 ? this.f4.m17() - 1 : 0;
         }

         return true;
      } else {
         return false;
      }
   }

   public VexTextAreaWidget(int var1, FontRenderer var2, int var3, int var4, int var5, int var6, int var7, int var8, VexTextArea var9) {
      super(var1, var2, var3, var4, var5, var6);
      this.f6 = var1;
      this.f1 = var7;
      this.f9 = var8;
      this.f7 = var9;
      this.init(var9.f9);
      this.setMaxStringLength(var9.f8);
      this.setFocused(false);
   }

   public void setTextLines(List<Object> var1) {
      this.f4.m6(var1);
   }

   public void setMaxStringLength(int var1) {
      this.f5 = var1;
      super.setMaxStringLength(var1);
   }

   public boolean textboxKeyTyped(char var1, int var2) {
      switch (var2) {
         case 14:
            if (this.f4.m3(this.f2, this.f3)) {
               this.f3--;
            } else if (this.f2 > 0) {
               if (this.f4.m16(this.f2).length() > 0) {
                  boolean var4 = this.f8.getStringWidth(this.f4.m16(this.f2 - 1)) >= this.width - 10;
                  this.f3 = this.f4.m16(this.f2 - 1).length() - (var4 ? 1 : 0);
                  this.f4.m10(this.f2, var4, this.f8, this.width);
                  this.f2--;
               } else {
                  this.f4.m2(this.f2--);
                  this.f3 = this.f4.m16(this.f2).length();
               }
            }

            return true;
         case 28:
            this.f4.m4(this.f2, this.f3);
            this.f2++;
            this.f3 = 0;
            return true;
         case 200:
            if (this.f2 > 0) {
               this.f2--;
               if (this.f3 > this.f4.m16(this.f2).length()) {
                  this.f3 = this.f4.m16(this.f2).length();
               }
            }

            return true;
         case 203:
            if (this.f3 > 0) {
               this.f3--;
            }

            return true;
         case 205:
            if (this.f3 < this.f4.m16(this.f2).length()) {
               this.f3++;
            }

            return true;
         case 208:
            if (this.f2 < this.f4.m17() - 1) {
               this.f2++;
               if (this.f3 > this.f4.m16(this.f2).length()) {
                  this.f3 = this.f4.m16(this.f2).length();
               }
            }

            return true;
         default:
            if (ChatAllowedCharacters.isAllowedCharacter(var1)) {
               this.writeText(var1, var2);
               return true;
            } else {
               return true;
            }
      }
   }
}
