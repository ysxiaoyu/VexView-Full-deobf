package lk.vexview.gui.core;

import lk.vexview.engine.BufferImageLib;
import lk.vexview.engine.base.Element;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class VexButton extends VexComponent implements Downable {
   public String f1 = null;
   public boolean f2;
   public int f3;
   boolean f4;
   public String f5;
   boolean f6;
   public int f7;
   public int f8;
   public String f9;
   public boolean f10;
   public int f11;
   public int f12;
   public String f13;
   public String f14;
   boolean f15 = false;

   @Override
   public boolean equals(Object var1) {
      return var1 instanceof VexButton ? ((VexButton)var1).f12 == this.f12 : false;
   }

   public boolean m1() {
      return this.f4;
   }

   public boolean m3() {
      return this.f15;
   }

   public boolean m7() {
      return this.f6;
   }

   public VexButton(int var1, int var2, int var3, int var4, int var5, String var6, String var7, String var8, String var9, String var10, boolean var11) {
      this.f4 = false;
      this.f6 = false;
      this.f14 = "";
      this.f2 = true;
      this.f10 = false;
      this.f3 = var2;
      this.f7 = var3;
      this.f11 = var4;
      this.f8 = var5;
      this.f12 = var1;
      this.f5 = var6;
      this.f13 = var7;
      this.f1 = var8;
      this.f9 = var9;
      if (var6.contains("[local]")) {
         this.f15 = true;
      }

      if (var7.contains("[local]")) {
         this.f4 = true;
      }

      if (var8.contains("[local]")) {
         this.f6 = true;
      }

      this.f14 = var10;
      this.f2 = var11;
      this.f10 = true;
   }

   public GuiButton m5(int var1, int var2, int var3, int var4, Minecraft var5) {
      int var6 = this.f3 == -1 ? var3 / 2 - this.f11 / 2 : this.f3;
      var3 = this.f7 == -1 ? var4 / 2 - this.f8 / 2 : this.f7;
      GuiButton var10000 = new GuiButton(this.f12, var6 + var1, var3 + var2, this.f11, this.f8, this.f9) {
         public void drawButton(Minecraft var1, int var2x, int var3x, float var4x) {
         }
      };
      var10000.enabled = this.f2;
      return var10000;
   }

   @Override
   public void m2() {
      this.m3(BufferImageLib.m4(this.f5, this, "BUTTON"));
      this.m3(BufferImageLib.m4(this.f13, this, "BUTTON"));
      if (this.f10) {
         this.m3(BufferImageLib.m4(this.f1, this, "BUTTON"));
      }
   }

   public VexButton(int var1, int var2, int var3, int var4, int var5, String var6, String var7, String var8, String var9) {
      this.f4 = false;
      this.f6 = false;
      this.f14 = "";
      this.f2 = true;
      this.f10 = false;
      this.f3 = var2;
      this.f7 = var3;
      this.f11 = var4;
      this.f8 = var5;
      this.f12 = var1;
      this.f5 = var6;
      this.f13 = var7;
      this.f9 = var8;
      if (var6.contains("[local]")) {
         this.f15 = true;
      }

      if (var7.contains("[local]")) {
         this.f4 = true;
      }

      this.f14 = var9;
      this.f10 = false;
   }
}
