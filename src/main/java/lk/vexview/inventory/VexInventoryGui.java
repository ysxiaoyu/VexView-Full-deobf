package lk.vexview.inventory;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.IOException;
import lk.vexview.engine.BufferImageLib;
import lk.vexview.gui.core.VexButton;
import lk.vexview.gui.core.VexComponent;
import lk.vexview.gui.core.VexGifImage;
import lk.vexview.gui.core.VexImage;
import lk.vexview.gui.core.VexSlot;
import lk.vexview.gui.core.VexText;
import lk.vexview.net.NetPacketManager;
import lk.vexview.net.packet.PacketInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import org.lwjgl.opengl.GL11;

public class VexInventoryGui extends GuiInventory {
   public void initGui() {
      super.initGui();
      this.m7();
   }

   public VexInventoryGui(EntityPlayer var1) {
      super(var1);
      NetPacketManager.m4(new PacketInventory("", "open"));
      InventoryTools.s1.forEach(VexComponent::m2);
   }

   private void m1(int var1, int var2, VexComponent var3) {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      if (var3 instanceof VexImage) {
         VexImage var4 = (VexImage)var3;
         this.m6(var4, this.guiLeft + this.xSize, this.guiTop, this.width, this.height);
      } else if (var3 instanceof VexButton) {
         VexButton var5 = (VexButton)var3;
         this.m3(var5, this.guiLeft + this.xSize, this.guiTop, var1, var2, this.width, this.height);
      } else if (var3 instanceof VexText) {
         VexText var6 = (VexText)var3;
         this.m8(var6, this.guiLeft + this.xSize, this.guiTop, this.width, this.height);
      } else if (var3 instanceof VexSlot) {
         VexSlot var7 = (VexSlot)var3;
         ByteBuf var8 = Unpooled.wrappedBuffer(var7.m1());
         PacketBuffer var9 = new PacketBuffer(var8);

         try {
            this.m4(var9.readItemStack(), this.guiLeft + this.xSize + var7.f2, this.guiTop + var7.f4, var1, var2);
         } catch (IOException var11) {
         }
      }
   }

   private void m2(int var1, int var2, VexComponent var3) {
      if (var3 instanceof VexSlot) {
         VexSlot var4 = (VexSlot)var3;
         if (this.m5(this.guiLeft + this.xSize + var4.f2, this.guiTop + var4.f4, var1, var2)) {
            NetPacketManager.m4(new PacketInventory(var4.m7() + "", "click"));
         }
      }
   }

   private void m3(VexButton var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      int var8 = var1.f3;
      int var9 = var1.f7;
      boolean var10 = var4 >= var2 + var8 && var5 >= var3 + var9 && var4 <= var2 + var8 + var1.f11 && var5 <= var3 + var9 + var1.f8;
      if (var1.f10) {
         if (BufferImageLib.s4.containsKey(var1.f5) && BufferImageLib.s4.containsKey(var1.f13) && BufferImageLib.s4.containsKey(var1.f1)) {
            if (!var1.f2) {
               this.mc.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f1));
            } else if (var10) {
               this.mc.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f13));
            } else {
               this.mc.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f5));
            }
         }
      } else if (BufferImageLib.s4.containsKey(var1.f5) && BufferImageLib.s4.containsKey(var1.f13)) {
         if (var10) {
            this.mc.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f13));
         } else {
            this.mc.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f5));
         }
      }

      drawScaledCustomSizeModalRect(var2 + var8, var3 + var9, 0.0F, 0.0F, var1.f11, var1.f8, var1.f11, var1.f8, (float)var1.f11, (float)var1.f8);
      this.drawCenteredString(this.fontRenderer, new StringBuilder().insert(0, var1.f9).append("\u00a7r").toString(), var2 + var8 + var1.f11 / 2, var3 + var9 + (var1.f8 - 8) / 2, 16777215);
   }

   private void m4(ItemStack var1, int var2, int var3, int var4, int var5) {
      if (this.m5(var2, var3, var4, var5)) {
         drawRect(var2, var3, var2 + 16, var3 + 16, -2130706433);
      }

      RenderHelper.enableGUIStandardItemLighting();
      this.itemRender.renderItemAndEffectIntoGUI(this.mc.player, var1, var2, var3);
      if (var1.getCount() != 1) {
         this.itemRender.renderItemOverlayIntoGUI(this.fontRenderer, var1, var2, var3, "" + var1.getCount());
      }

      if (this.m5(var2, var3, var4, var5)) {
         this.renderToolTip(var1, var4, var5);
      }
   }

   private boolean m5(int var1, int var2, int var3, int var4) {
      return var3 >= var1 && var3 <= var1 + 16 && var4 >= var2 && var4 <= var2 + 16;
   }

   private void m6(VexImage var1, int var2, int var3, int var4, int var5) {
      if (var1 instanceof VexGifImage) {
         VexGifImage var6 = (VexGifImage)var1;
         if (BufferImageLib.s2.containsKey(var6.f11)) {
            this.mc.getTextureManager().bindTexture(BufferImageLib.s2.get(var6.f11).get(var6.m7()));
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            drawScaledCustomSizeModalRect(var2 + var1.f1, var3 + var1.f2, 0.0F, 0.0F, var1.f10, var1.f7, var1.f6, var1.f12, (float)var1.f10, (float)var1.f7);
         }
      } else if (BufferImageLib.s4.containsKey(var1.f11)) {
         this.mc.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f11));
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         drawScaledCustomSizeModalRect(var2 + var1.f1, var3 + var1.f2, (float)var1.f9, (float)var1.f5, var1.f3, var1.f4, var1.f6, var1.f12, (float)var1.f10, (float)var1.f7);
      }
   }

   private void m7() {
      InventoryTools.s1.forEach(this::m9);
   }

   private void m8(VexText var1, int var2, int var3, int var4, int var5) {
      GL11.glScaled(var1.f2, var1.f2, var1.f2);
      int var6 = 0;

      for (String var7 : var1.f1) {
         this.drawString(this.fontRenderer, new StringBuilder().insert(0, var7).append("\u00a7r").toString(), (int)((double)(var2 + var1.f3) / var1.f2), (int)(((double)var3 + (double)var6 * (2.0D + 8.0D * var1.f2) + (double)var1.f4) / var1.f2), 16777215);
         ++var6;
      }

      GL11.glScaled(1.0D / var1.f2, 1.0D / var1.f2, 1.0D / var1.f2);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void m9(VexComponent var1) {
      if (var1 instanceof VexButton) {
         VexButton var2 = (VexButton)var1;
         this.buttonList.add(new Anon1(this, var2.f12, var2.f3 + this.guiLeft + this.xSize, var2.f7 + this.guiTop, var2.f11, var2.f8, var2.f9));
      }
   }

   public void drawScreen(int var1, int var2, float var3) {
      super.drawScreen(var1, var2, var3);
      GlStateManager.enableAlpha();
      GlStateManager.enableBlend();
      GlStateManager.disableLighting();
      InventoryTools.s1.forEach(var3x -> this.m1(var1, var2, var3x));
   }

   public void onGuiClosed() {
      super.onGuiClosed();
      NetPacketManager.m4(new PacketInventory("", "close"));
   }

   protected void actionPerformed(GuiButton var1) {
      if (var1.id == 10) {
         super.actionPerformed(var1);
         this.buttonList.clear();
         this.buttonList.add(var1);
         this.m7();
      } else {
         NetPacketManager.m4(new PacketInventory(new StringBuilder().insert(0, "").append(var1.id).toString(), "button"));
      }
   }

   protected void mouseClicked(int var1, int var2, int var3) {
      super.mouseClicked(var1, var2, var3);
      InventoryTools.s1.forEach(var3x -> this.m2(var1, var2, var3x));
   }

   static class Anon1 extends GuiButton {
      final VexInventoryGui f1;

      Anon1(VexInventoryGui var1, int var2, int var3, int var4, int var5, int var6, String var7) {
         super(var2, var3, var4, var5, var6, var7);
         this.f1 = var1;
      }

      public void drawButton(Minecraft var1, int var2, int var3, float var4) {
      }
   }
}
