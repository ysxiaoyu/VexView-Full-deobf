package lk.vexview.flowview;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class VexFlowView {
   String f1;
   boolean f2;
   long f3 = 0L;
   public boolean f4;
   public int f5;
   int f6;
   int f7 = 0;

   public void m1(Pre var1) {
      FontRenderer var4;
      int var5;
      int var6;
      VexFlowView var10000;
      label28: {
         var4 = Minecraft.getMinecraft().fontRenderer;
         var5 = var1.getResolution().getScaledWidth();
         var6 = var1.getResolution().getScaledHeight();
         if (this.m2()) {
            if (this.f6 <= var5 + var4.getStringWidth(this.f1)) {
               if (this.f7 < 40) {
                  this.f7 += 4;
               }

               var10000 = this;
               this.f6++;
               break label28;
            }

            if (this.f7 > 0) {
               var10000 = this;
               this.f7 -= 4;
               break label28;
            }

            this.f4 = true;
         }

         var10000 = this;
      }

      if (var10000.f2) {
         Gui.drawRect(0, (int)(var6 * (this.f5 * 0.01)), var5, (int)(var6 * (this.f5 * 0.01)) + 14, FlowTools.m1(this.f7));
      }

      var4.drawString(this.f1, var5 - this.f6, (int)(var6 * (this.f5 * 0.01)) + 2, 16777215);
   }

   public VexFlowView(String var1, int var2, boolean var3) {
      this.f6 = 0;
      this.f4 = false;
      this.f1 = var1;
      this.f5 = var2;
      this.f2 = var3;
   }

   private boolean m2() {
      if (this.f3 == 0L) {
         this.f3 = System.currentTimeMillis();
         return true;
      } else if (System.currentTimeMillis() - this.f3 >= 17L) {
         this.f3 = System.currentTimeMillis();
         return true;
      } else {
         return false;
      }
   }
}
