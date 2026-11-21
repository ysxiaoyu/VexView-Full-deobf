package lk.vexview.gui;

import java.util.Iterator;
import lk.vexview.engine.BufferImageLib;
import lk.vexview.gui.core.VexBase64Image;
import lk.vexview.gui.core.VexButton;
import lk.vexview.gui.core.VexCheckBox;
import lk.vexview.gui.core.VexGifImage;
import lk.vexview.gui.core.VexImage;
import lk.vexview.gui.core.VexMcImage;
import lk.vexview.gui.core.VexText;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;
import org.lwjgl.opengl.GL11;

public class AbstractVexCustomGui extends GuiContainer {
   protected void drawButtons(VexButton var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      int var9;
      int var10000;
      label69: {
         var9 = var1.f3 == -1 ? var6 / 2 - var1.f11 / 2 : var1.f3;
         var6 = var1.f7 == -1 ? var7 / 2 - var1.f8 / 2 : var1.f7;
         if (var1.f10) {
            if (BufferImageLib.s4.containsKey(var1.f5) && BufferImageLib.s4.containsKey(var1.f13) && BufferImageLib.s4.containsKey(var1.f1)) {
               if (!var1.f2) {
                  this.mc.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f1));
                  var10000 = var2;
                  break label69;
               }

               boolean var11 = var4 >= var2 + var9 && var5 >= var3 + var6 && var4 <= var2 + var9 + var1.f11 && var5 <= var3 + var6 + var1.f8;
               if (var11) {
                  this.mc.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f13));
               } else {
                  this.mc.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f5));
               }
            }
         } else if (BufferImageLib.s4.containsKey(var1.f5) && BufferImageLib.s4.containsKey(var1.f13)) {
            boolean var12 = var4 >= var2 + var9 && var5 >= var3 + var6 && var4 <= var2 + var9 + var1.f11 && var5 <= var3 + var6 + var1.f8;
            if (var12) {
               this.mc.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f13));
               var10000 = var2;
               break label69;
            }

            this.mc.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f5));
         }

         var10000 = var2;
      }

      drawScaledCustomSizeModalRect(var10000 + var9, var3 + var6, 0.0F, 0.0F, var1.f11, var1.f8, var1.f11, var1.f8, var1.f11, var1.f8);
      this.drawCenteredString(
         this.fontRenderer,
         new StringBuilder().insert(0, var1.f9).append("§r").toString(),
         var2 + var9 + var1.f11 / 2,
         var3 + var6 + (var1.f8 - 8) / 2,
         16777215
      );
   }

   void drawText(VexText var1, int var2, int var3, int var4, int var5, int var6) {
      GL11.glScaled(var1.f2, var1.f2, var1.f2);

      Iterator var9;
      for (Iterator var10000 = var9 = var1.f1.iterator(); var10000.hasNext(); var10000 = var9) {
         String var10 = (String)var9.next();
         FontRenderer var10001 = this.fontRenderer;
         String var10002 = new StringBuilder().insert(0, var10).append("§r").toString();
         int var10003 = (int)((var3 + (var1.f3 == -1 ? var5 / 2 - this.fontRenderer.getStringWidth(var10) / 2 : var1.f3)) / var1.f2);
         double var10004 = (
               var4 + var2 * (2.0 + 8.0 * var1.f2) + (int)(var1.f4 == -1 ? var6 / 2 - (2.0 + 8.0 * var1.f2) * var1.f1.size() / (2.0 * var1.f2) : var1.f4)
            )
            / var1.f2;
         var2++;
         this.drawString(var10001, var10002, var10003, (int)var10004, 16777215);
      }

      GL11.glScaled(1.0 / var1.f2, 1.0 / var1.f2, 1.0 / var1.f2);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   void drawImage(VexImage var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      if (var1 instanceof VexGifImage) {
         VexGifImage var8 = (VexGifImage)var1;
         if (BufferImageLib.s2.containsKey(var8.f11)) {
            this.mc.getTextureManager().bindTexture(BufferImageLib.s2.get(var8.f11).get(var8.m7()));
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            int var10000;
            VexImage var10001;
            if (var1.f1 != -1) {
               var10000 = var2 + var1.f1;
               var10001 = var1;
            } else {
               var10000 = var2 + var6 / 2 - var1.f6 / 2;
               var10001 = var1;
            }

            drawScaledCustomSizeModalRect(
               var10000,
               var10001.f2 != -1 ? var3 + var1.f2 : var3 + var7 / 2 - var1.f12 / 2,
               0.0F,
               0.0F,
               var1.f10,
               var1.f7,
               var1.f6,
               var1.f12,
               var1.f10,
               var1.f7
            );
            return;
         }
      } else if (BufferImageLib.s4.containsKey(var1.f11)) {
         this.mc.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f11));
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         int var9;
         VexImage var10;
         if (var1.f1 != -1) {
            var9 = var2 + var1.f1;
            var10 = var1;
         } else {
            var9 = var2 + var6 / 2 - var1.f6 / 2;
            var10 = var1;
         }

         int var11;
         VexImage var10002;
         if (var10.f2 != -1) {
            var11 = var3 + var1.f2;
            var10002 = var1;
         } else {
            var11 = var3 + var7 / 2 - var1.f12 / 2;
            var10002 = var1;
         }

         drawScaledCustomSizeModalRect(var9, var11, var10002.f9, var1.f5, var1.f3, var1.f4, var1.f6, var1.f12, var1.f10, var1.f7);
      }
   }

   void drawBase64Image(VexBase64Image var1, int var2, int var3, int var4, int var5) {
      if (BufferImageLib.s5.containsKey(var1.f2)) {
         this.mc.getTextureManager().bindTexture(BufferImageLib.s5.get(var1.f2));
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         int var10000;
         VexBase64Image var10001;
         if (var1.f3 != -1) {
            var10000 = var2 + var1.f3;
            var10001 = var1;
         } else {
            var10000 = var2 + var4 / 2 - var1.f5 / 2;
            var10001 = var1;
         }

         drawScaledCustomSizeModalRect(
            var10000, var10001.f4 != -1 ? var3 + var1.f4 : var3 + var5 / 2 - var1.f1 / 2, 0.0F, 0.0F, var1.f5, var1.f1, var1.f5, var1.f1, var1.f5, var1.f1
         );
      }
   }

   void drawMCImage(VexMcImage var1, int var2, int var3, int var4, int var5) {
      if (BufferImageLib.s1.containsKey(var1.f12)) {
         this.mc.getTextureManager().bindTexture(BufferImageLib.s1.get(var1.f12));
         int var10000;
         VexMcImage var10001;
         if (var1.f1 != -1) {
            var10000 = var2 + var1.f1;
            var10001 = var1;
         } else {
            var10000 = var2 + var4 / 2 - var1.f9 / 2;
            var10001 = var1;
         }

         int var6;
         VexMcImage var10002;
         if (var10001.f10 != -1) {
            var6 = var3 + var1.f10;
            var10002 = var1;
         } else {
            var6 = var3 + var5 / 2 - var1.f7 / 2;
            var10002 = var1;
         }

         drawScaledCustomSizeModalRect(var10000, var6, var10002.f2, var1.f5, var1.f4, var1.f6, var1.f9, var1.f7, var1.f3, var1.f8);
      }
   }

   protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
   }

   public AbstractVexCustomGui(Container var1) {
      super(var1);
   }

   void drawCheckBox(VexCheckBox var1, int var2, int var3, int var4, int var5) {
      if (BufferImageLib.s4.containsKey(var1.f5) && BufferImageLib.s4.containsKey(var1.f8)) {
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         if (var1.f3) {
            this.mc.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f8));
            int var6;
            VexCheckBox var7;
            if (var1.f1 != -1) {
               var6 = var2 + var1.f1;
               var7 = var1;
            } else {
               var6 = var2 + var4 / 2 - var1.f2 / 2;
               var7 = var1;
            }

            drawScaledCustomSizeModalRect(
               var6, var7.f7 != -1 ? var3 + var1.f7 : var3 + var5 / 2 - var1.f4 / 2, 0.0F, 0.0F, var1.f2, var1.f4, var1.f2, var1.f4, var1.f2, var1.f4
            );
            return;
         }

         this.mc.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f5));
         int var10000;
         VexCheckBox var10001;
         if (var1.f1 != -1) {
            var10000 = var2 + var1.f1;
            var10001 = var1;
         } else {
            var10000 = var2 + var4 / 2 - var1.f2 / 2;
            var10001 = var1;
         }

         drawScaledCustomSizeModalRect(
            var10000, var10001.f7 != -1 ? var3 + var1.f7 : var3 + var5 / 2 - var1.f4 / 2, 0.0F, 0.0F, var1.f2, var1.f4, var1.f2, var1.f4, var1.f2, var1.f4
         );
      }
   }
}
