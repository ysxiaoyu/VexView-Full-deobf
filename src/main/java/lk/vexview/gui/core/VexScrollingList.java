package lk.vexview.gui.core;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import lk.vexview.engine.BufferImageLib;
import lk.vexview.gui.VexCustomGui;
import lk.vexview.net.NetPacketManager;
import lk.vexview.net.packet.PacketSlotClick;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.client.GuiScrollingList;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class VexScrollingList extends GuiScrollingList {
   int f1;
   public static Map<Integer, Integer> s1 = new HashMap<>();
   private final Minecraft f2;
   public List<VexSlot> f3 = new CopyOnWriteArrayList<>();
   public List<GuiButton> f4;
   int f5;
   public VexList f6;
   GuiScreen f7;
   Map<Integer, ItemStack> f8 = new HashMap<>();

   protected void drawBackground() {
   }

   protected void drawSlot(int var1, int var2, int var3, int var4, Tessellator var5) {
   }

   public VexScrollingList(List<VexSlot> var1, VexList var2, GuiScreen var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11) {
      super(Minecraft.getMinecraft(), var4, var5, var6, var7, var8, var9, var10, var11);
      this.f4 = new ArrayList<>();
      this.f3.addAll(var1);
      this.f6 = var2;
      this.f2 = Minecraft.getMinecraft();
      this.f5 = var5;
      this.setHeaderInfo(true, 0);
      this.f7 = var3;
      this.initButton();
   }

   protected void drawHeader(int var1, int var2, Tessellator var3) {
      Iterator var5;
      for (Iterator var10000 = var5 = this.f6.f6.iterator(); var10000.hasNext(); var10000 = var5) {
         VexComponent var6 = (VexComponent)var5.next();
         GlStateManager.enableAlpha();
         GlStateManager.enableBlend();
         if (var6 instanceof VexText) {
            this.drawText(var2, (VexText)var6);
         } else if (var6 instanceof VexGifImage) {
            this.m4((VexGifImage)var6, var3, var2);
         } else if (var6 instanceof VexMcImage) {
            this.m7((VexMcImage)var6, var3, var2);
         } else if (var6 instanceof VexImage) {
            this.m9((VexImage)var6, var3, var2);
         } else if (var6 instanceof VexButton) {
            this.m3((VexButton)var6, var3, var2);
         } else if (var6 instanceof VexCheckBox) {
            this.m2((VexCheckBox)var6, var3, var2);
         }

         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      }

      this.m10(var3, this.f1, this.mouseX, this.mouseY);
      this.f1 = var2;
   }

   public void drawScreen(int var1, int var2, float var3) {
      GlStateManager.disableLighting();
      GlStateManager.disableFog();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableAlpha();
      GlStateManager.enableBlend();
      super.drawScreen(var1, var2, var3);
      this.m8(Tessellator.getInstance(), this.f1 + 8, var1, var2);
      Iterator var6;
      Iterator var10000 = var6 = this.f4.iterator();

      while (var10000.hasNext()) {
         GuiButton var7 = (GuiButton)var6.next();
         var10000 = var6;
         var7.drawButton(this.f2, var1, var2, var3);
      }

      var6 = this.f6.f6.iterator();

      while (var6.hasNext()) {
         VexComponent var8;
         if ((var8 = (VexComponent)var6.next()).f1 != null
            && this.isMouseInList(var1, var2)
            && !var8.f1.f3.isEmpty()
            && var1 >= this.left + var8.f1.f1
            && var1 <= this.left + var8.f1.f1 + var8.f1.f4
            && var2 >= this.f1 + var8.f1.f5
            && var2 <= this.f1 + var8.f1.f5 + var8.f1.f2) {
            this.f7.drawHoveringText(var8.f1.f3, var1, var2);
         }
      }
   }

   public void initButton() {
      Iterator var3 = this.f6.f6.iterator();

      while (var3.hasNext()) {
         VexComponent var4;
         if ((var4 = (VexComponent)var3.next()) instanceof VexButton) {
            final VexButton var5 = (VexButton)var4;
            this.f4
               .add(
                  new GuiButton(var5.f12, var5.f3, var5.f7, var5.f11, var5.f8, var5.f9) {
                     public boolean mousePressed(Minecraft var1, int var2, int var3x) {
                        return var5.f2
                           ? var2 >= VexScrollingList.this.left + var5.f3
                              && var3x >= VexScrollingList.this.f1 + var5.f7
                              && var2 < VexScrollingList.this.left + var5.f3 + var5.f11
                              && var3x < VexScrollingList.this.f1 + var5.f7 + var5.f8
                           : false;
                     }

                     public void drawButton(Minecraft var1, int var2, int var3x, float var4x) {
                     }
                  }
               );
         }
      }
   }

   public boolean isMouseInList(double var1, double var3) {
      return var3 >= this.top && var3 <= this.bottom && var1 >= this.left && var1 <= this.right;
   }

   protected int getContentHeight() {
      return this.f5;
   }

   protected void drawText(int var1, VexText var2) {
      var1 = var1;

      Iterator var5;
      for (Iterator var10000 = var5 = this.m6(var2.f1).iterator(); var10000.hasNext(); var10000 = var5) {
         ITextComponent var6;
         if ((var6 = (ITextComponent)var5.next()) != null) {
            GlStateManager.enableBlend();
            GL11.glScaled(var2.f2, var2.f2, var2.f2);
            Minecraft.getMinecraft()
               .fontRenderer
               .drawStringWithShadow(var6.getFormattedText(), (float)((this.left + 4 + var2.f3) / var2.f2), (float)((var1 + var2.f4) / var2.f2), 16777215);
            GL11.glScaled(1.0 / var2.f2, 1.0 / var2.f2, 1.0 / var2.f2);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.disableAlpha();
            GlStateManager.disableBlend();
         }

         var1 += 10;
      }
   }

   protected void elementClicked(int var1, boolean var2) {
   }

   public int getAmountScrolled() {
      return this.top - this.f1;
   }

   public void handleMouseInput(int var1, int var2) throws IOException {
      if (this.isMouseInList(var1, var2) && this.f7 instanceof VexCustomGui) {
         for (VexSlot var8 : this.f3) {
            if (this.f8.containsKey(var8.f1) && this.f8.get(var8.f1) != null && this.f8.get(var8.f1) != null) {
               int var9 = var8.f4;
               int var10 = var8.f2;
               if (var1 >= this.left + var10 && var1 <= this.left + var10 + 16 && var2 >= this.f1 + var9 && var2 <= this.f1 + 16 + var9) {
                  String var11;
                  String var10000;
                  switch (Mouse.getEventButton()) {
                     case 0:
                        var10000 = var11 = new StringBuilder().insert(0, "VexSlotClick:LEFT_CLICK:").append(var8.f1).toString();
                        break;
                     case 1:
                        var10000 = var11 = new StringBuilder().insert(0, "VexSlotClick:RIGHT_CLICK:").append(var8.f1).toString();
                        break;
                     case 2:
                        var10000 = var11 = new StringBuilder().insert(0, "VexSlotClick:MIDDLE_CLICK:").append(var8.f1).toString();
                        break;
                     default:
                        var10000 = var11 = null;
                  }

                  if (var10000 != null) {
                     NetPacketManager.m4(new PacketSlotClick(var11));
                  }
               }
            }
         }
      }

      super.handleMouseInput(var1, var2);
   }

   protected int getSize() {
      return 0;
   }

   protected boolean isSelected(int var1) {
      return false;
   }

   private void m2(VexCheckBox var1, Tessellator var2, int var3) {
      if (BufferImageLib.s4.containsKey(var1.f5) && BufferImageLib.s4.containsKey(var1.f8)) {
         GlStateManager.enableBlend();
         if (var1.f3) {
            this.f2.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f8));
         } else {
            this.f2.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f5));
         }

         Dimension var7 = new Dimension(var1.f2, var1.f4);
         BufferBuilder var8 = var2.getBuffer();
         int var9 = this.left + var1.f1;
         s1.put(var1.f6, var3 + var1.f7);
         var8.begin(7, DefaultVertexFormats.POSITION_TEX);
         var8.pos((double)var9, (double)(var3 + var1.f7 + var7.height), 0.0).tex(0.0, 1.0).endVertex();
         var8.pos((double)(var9 + var7.width), (double)(var3 + var1.f7 + var7.height), 0.0).tex(1.0, 1.0).endVertex();
         var8.pos((double)(var9 + var7.width), (double)(var3 + var1.f7), 0.0).tex(1.0, 0.0).endVertex();
         var8.pos((double)var9, (double)(var3 + var1.f7), 0.0).tex(0.0, 0.0).endVertex();
         var2.draw();
         GlStateManager.disableBlend();
         var3 += var7.height + 10;
      }
   }

   private void m3(VexButton var1, Tessellator var2, int var3) {
      if (var1.f10) {
         if (BufferImageLib.s4.containsKey(var1.f5) && BufferImageLib.s4.containsKey(var1.f13) && BufferImageLib.s4.containsKey(var1.f1)) {
            if (!var1.f2) {
               this.f2.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f1));
            } else if (this.mouseX >= this.left + var1.f3
               && this.mouseY >= var3 + var1.f7
               && this.mouseX < this.left + var1.f3 + var1.f11
               && this.mouseY < var3 + var1.f7 + var1.f8) {
               this.f2.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f13));
            } else {
               this.f2.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f5));
            }
         }
      } else if (BufferImageLib.s4.containsKey(var1.f5) && BufferImageLib.s4.containsKey(var1.f13)) {
         if (this.mouseX >= this.left + var1.f3
            && this.mouseY >= var3 + var1.f7
            && this.mouseX < this.left + var1.f3 + var1.f11
            && this.mouseY < var3 + var1.f7 + var1.f8) {
            this.f2.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f13));
         } else {
            this.f2.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f5));
         }
      }

      Dimension var7 = new Dimension(var1.f11, var1.f8);
      BufferBuilder var8 = var2.getBuffer();
      int var9 = this.left + var1.f3;
      var8.begin(7, DefaultVertexFormats.POSITION_TEX);
      var8.pos((double)var9, (double)(var3 + var1.f7 + var7.height), 0.0).tex(0.0, 1.0).endVertex();
      var8.pos((double)(var9 + var7.width), (double)(var3 + var1.f7 + var7.height), 0.0).tex(1.0, 1.0).endVertex();
      var8.pos((double)(var9 + var7.width), (double)(var3 + var1.f7), 0.0).tex(1.0, 0.0).endVertex();
      var8.pos((double)var9, (double)(var3 + var1.f7), 0.0).tex(0.0, 0.0).endVertex();
      var2.draw();
      GlStateManager.disableBlend();
      this.f2.fontRenderer.drawString(var1.f9, var9 + (var1.f11 - this.f2.fontRenderer.getStringWidth(var1.f9)) / 2, var3 + var1.f7 + (var1.f8 - 10) / 2, 16777215);
      var3 += var7.height + 10;
   }

   private void m4(VexGifImage var1, Tessellator var2, int var3) {
      if (BufferImageLib.s2.containsKey(var1.f11)) {
         this.f2.getTextureManager().bindTexture(BufferImageLib.s2.get(var1.f11).get(var1.m7()));
         Dimension var6 = new Dimension(var1.f6, var1.f12);
         BufferBuilder var7 = var2.getBuffer();
         int var8 = this.left + ((VexImage)var1).f1;
         var7.begin(7, DefaultVertexFormats.POSITION_TEX);
         var7.pos((double)var8, (double)(var3 + var1.f2 + var6.height), 0.0).tex(0.0, 1.0).endVertex();
         var7.pos((double)(var8 + var6.width), (double)(var3 + var1.f2 + var6.height), 0.0).tex(1.0, 1.0).endVertex();
         var7.pos((double)(var8 + var6.width), (double)(var3 + var1.f2), 0.0).tex(1.0, 0.0).endVertex();
         var7.pos((double)var8, (double)(var3 + var1.f2), 0.0).tex(0.0, 0.0).endVertex();
         var2.draw();
         GlStateManager.disableBlend();
         var3 += var6.height + 10;
      }
   }

   private List<ITextComponent> m6(List<String> var1) {
      ArrayList<ITextComponent> var5 = new ArrayList<ITextComponent>();
      Iterator<String> var6 = var1.iterator();

      while (var6.hasNext()) {
         String var7 = var6.next();
         if (var7 == null) {
            var5.add(null);
            continue;
         }

         ITextComponent var8 = ForgeHooks.newChatWithLinks(var7, false);
         int var9 = this.listWidth - 8;
         if (var9 < 0) {
            continue;
         }

         var5.addAll(GuiUtilRenderComponents.splitText(var8, var9, Minecraft.getMinecraft().fontRenderer, false, true));
      }

      return var5;
   }

   private void m7(VexMcImage var1, Tessellator var2, int var3) {
      if (BufferImageLib.s1.containsKey(var1.f12)) {
         GlStateManager.enableBlend();
         this.f2.getTextureManager().bindTexture(BufferImageLib.s1.get(var1.f12));
         Dimension var8 = new Dimension(var1.f9, var1.f7);
         BufferBuilder var9 = var2.getBuffer();
         int var10 = this.left + var1.f1;
         float var11 = 1.0F / (float)var1.f3;
         float var12 = 1.0F / (float)var1.f8;
         var9.begin(7, DefaultVertexFormats.POSITION_TEX);
         var9.pos((double)var10, (double)(var3 + var1.f10 + var8.height), 0.0)
            .tex((float)var1.f2 * var11, ((float)var1.f5 + (float)var1.f6) * var12)
            .endVertex();
         var9.pos((double)(var10 + var8.width), (double)(var3 + var1.f10 + var8.height), 0.0)
            .tex(((float)var1.f2 + (float)var1.f4) * var11, ((float)var1.f5 + (float)var1.f6) * var12)
            .endVertex();
         var9.pos((double)(var10 + var8.width), (double)(var3 + var1.f10), 0.0)
            .tex(((float)var1.f2 + (float)var1.f4) * var11, (float)var1.f5 * var12)
            .endVertex();
         var9.pos((double)var10, (double)(var3 + var1.f10), 0.0)
            .tex((float)var1.f2 * var11, (float)var1.f5 * var12)
            .endVertex();
         var2.draw();
         GlStateManager.disableBlend();
         var3 += var8.height + 10;
      }
   }

   private void m8(Tessellator var1, int var2, int var3, int var4) {
      if (this.f7 instanceof VexCustomGui) {
         for (VexSlot var9 : this.f3) {
            if (this.f8.get(var9.f1) != null) {
               int var10 = var9.f4;
               int var11 = var9.f2;
               if (this.isMouseInList((double)var3, (double)var4)
                  && var3 >= this.left + var11
                  && var3 <= this.left + var11 + 16
                  && var4 >= var2 + var10 - 8
                  && var4 <= var2 + var10 + 8) {
                  Gui.drawRect(this.left + var11, var2 + var10 - 8, this.left + var11 + 16, var2 + var10 + 8, -2130706433);
                  if (!this.f8.get(var9.f1).equals(ItemStack.EMPTY)) {
                     ((VexCustomGui)this.f7).renderToolTip(this.f8.get(var9.f1), var3, var4);
                  }
               }
            }
         }
      }
   }

   private void m9(VexImage var1, Tessellator var2, int var3) {
      if (BufferImageLib.s4.containsKey(var1.f11)) {
         GlStateManager.enableBlend();
         this.f2.getTextureManager().bindTexture(BufferImageLib.s4.get(var1.f11));
         Dimension var8 = new Dimension(var1.f6, var1.f12);
         BufferBuilder var9 = var2.getBuffer();
         int var10 = this.left + var1.f1;
         float var11 = 1.0F / (float)var1.f10;
         float var12 = 1.0F / (float)var1.f7;
         var9.begin(7, DefaultVertexFormats.POSITION_TEX);
         var9.pos((double)var10, (double)(var3 + var1.f2 + var8.height), 0.0)
            .tex((float)var1.f9 * var11, ((float)var1.f5 + (float)var1.f4) * var12)
            .endVertex();
         var9.pos((double)(var10 + var8.width), (double)(var3 + var1.f2 + var8.height), 0.0)
            .tex(((float)var1.f9 + (float)var1.f3) * var11, ((float)var1.f5 + (float)var1.f4) * var12)
            .endVertex();
         var9.pos((double)(var10 + var8.width), (double)(var3 + var1.f2), 0.0)
            .tex(((float)var1.f9 + (float)var1.f3) * var11, (float)var1.f5 * var12)
            .endVertex();
         var9.pos((double)var10, (double)(var3 + var1.f2), 0.0)
            .tex((float)var1.f9 * var11, (float)var1.f5 * var12)
            .endVertex();
         var2.draw();
         GlStateManager.disableBlend();
         var3 += var8.height + 10;
      }
   }

   private void m10(Tessellator var1, int var2, int var3, int var4) {
      if (this.f7 instanceof VexCustomGui) {
         for (VexSlot var7 : this.f3) {
            if (!this.f8.containsKey(var7.f1)) {
               try {
                  ByteBuf var8 = Unpooled.wrappedBuffer(var7.m1());
                  PacketBuffer var9 = new PacketBuffer(var8);
                  this.f8.put(var7.f1, var9.readItemStack());
               } catch (Exception var10) {
                  var10.printStackTrace();
               }
            }

            if (this.f8.get(var7.f1) == null) {
               continue;
            }

            int var11 = var7.f4;
            int var12 = var7.f2;
            ((VexCustomGui)this.f7).drawpickedItem(this.left + var12 + 8, var2 + 8 + var11, this.f8.get(var7.f1));
         }
      }
   }
}
