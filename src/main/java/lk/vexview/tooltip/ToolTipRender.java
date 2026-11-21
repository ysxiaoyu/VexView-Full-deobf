/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.tooltip;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import lk.vexview.VexConfig;
import lk.vexview.VexView;
import lk.vexview.engine.BufferImageLib;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.GL11;

public class ToolTipRender {
    public static double s1;
    public static double s2;
    public static boolean s3;
    public static List<String> s4;
    private static final ResourceLocation s5;

    public static void m1(ItemStack itemStack, int n, int n2) {
        if (Minecraft.getMinecraft().currentScreen != null) {
            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            ItemStack itemStack2 = itemStack;
            List<String> list = ToolTipRender.m6(itemStack2);
            GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            int n3 = 0;
            int n4 = list.size() * 10 + 2;
            if (itemStack2.getTagCompound().hasKey("showheight")) {
                int n5;
                n4 = n5 = Integer.valueOf(itemStack.getTagCompound().getString("showheight")).intValue();
            }
            for (String string : list) {
                if (n3 >= fontRenderer.getStringWidth(string)) continue;
                n3 = fontRenderer.getStringWidth(string);
            }
            int n6 = n;
            int n7 = n2 - 4;
            if (n6 + n3 > guiScreen.width) {
                n6 -= 28 + n3;
            }
            if (n7 + n4 + 6 > guiScreen.height) {
                n7 = guiScreen.height - n4 - 6;
            }
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            Minecraft.getMinecraft().getTextureManager().bindTexture(s5);
            Gui.drawModalRectWithCustomSizedTexture((int)(n6 + (n3 += 4)), (int)n7, (float)0.0f, (float)0.0f, (int)40, (int)40, (float)40.0f, (float)40.0f);
            RenderHelper.enableGUIStandardItemLighting();
            GlStateManager.enableDepth();
            GlStateManager.translate((float)((float)n6 + (float)n3 + 12.0f), (float)((float)n7 + 20.0f), (float)500.0f);
            GlStateManager.translate((float)8.0f, (float)8.0f, (float)0.0f);
            GlStateManager.scale((float)1.0f, (float)-1.0f, (float)1.0f);
            GlStateManager.scale((float)60.0f, (float)60.0f, (float)60.0f);
            GlStateManager.rotate((float)10.0f, (float)1.0f, (float)0.0f, (float)0.0f);
            GlStateManager.rotate((float)((float)s2), (float)0.0f, (float)1.0f, (float)0.0f);
            Minecraft.getMinecraft().getItemRenderer().renderItem((EntityLivingBase)Minecraft.getMinecraft().player, itemStack, ItemCameraTransforms.TransformType.GROUND);
            s2 += 0.4;
            if (s2 >= 360.0) {
                s2 = 0.0;
            }
            GlStateManager.enableLighting();
            GlStateManager.enableRescaleNormal();
        }
    }

    public static void m3(ItemStack itemStack, int n, int n2) {
        if (Minecraft.getMinecraft().currentScreen != null) {
            ItemStack itemStack2 = itemStack;
            List<String> list = ToolTipRender.m6(itemStack2);
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            GuiScreen guiScreen = Minecraft.getMinecraft().currentScreen;
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            int n3 = 0;
            int n4 = list.size() * 10 + 2;
            if (itemStack2.getTagCompound().hasKey("showheight")) {
                int n5;
                n4 = n5 = Integer.valueOf(itemStack.getTagCompound().getString("showheight")).intValue();
            }
            for (String string : list) {
                if (n3 >= fontRenderer.getStringWidth(string)) continue;
                n3 = fontRenderer.getStringWidth(string);
            }
            int n6 = n;
            int n7 = n2 - 4;
            if (n6 + n3 > guiScreen.width) {
                n6 -= 28 + n3;
            }
            if (n7 + n4 + 6 > guiScreen.height) {
                n7 = guiScreen.height - n4 - 6;
            }
            n3 += 4;
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            String string = itemStack.getTagCompound().getString("backimage");
            if (!BufferImageLib.s4.containsKey(string)) {
                if (!list.contains(string)) {
                    final int tooltipX = n;
                    final int tooltipY = n2;
                    VexView.m1(() -> {
                        if (string.contains("[local]")) {
                            try {
                                FileInputStream fileInputStream;
                                File file = new File(VexConfig.s1, string.replace("[local]", ""));
                                FileInputStream fileInputStream2 = fileInputStream = new FileInputStream(file);
                                BufferImageLib.m8(string, ImageIO.read(fileInputStream2));
                                ((InputStream)fileInputStream2).close();
                                return;
                            }
                            catch (Exception exception) {
                                fontRenderer.drawStringWithShadow("Error Image!", (float)(tooltipX + 2), (float)(tooltipY + 2), -1);
                            }
                        }
                    });
                    list.add(string);
                }
                return;
            }
            Minecraft.getMinecraft().getTextureManager().bindTexture(BufferImageLib.s4.get(string));
            if (itemStack.getTagCompound().hasKey("showheight")) {
                Iterator<String> iterator;
                int n8 = n3;
                Gui.drawModalRectWithCustomSizedTexture((int)n6, (int)n7, (float)0.0f, (float)0.0f, (int)n8, (int)(n4 + 2), (float)n8, (float)(n4 + 2));
                GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
                double d = (double)Minecraft.getMinecraft().displayWidth / scaledResolution.getScaledWidth_double();
                double d2 = (double)Minecraft.getMinecraft().displayHeight / scaledResolution.getScaledHeight_double();
                GL11.glEnable((int)3089);
                GL11.glScissor((int)((int)((double)n6 * d)), (int)((int)((double)Minecraft.getMinecraft().displayHeight - (double)(n7 + n4) * d2)), (int)((int)((double)n3 * d)), (int)((int)((double)n4 * d2)));
                n2 = 0;
                Iterator<String> iterator2 = iterator = list.iterator();
                while (iterator2.hasNext()) {
                    String string2 = iterator.next();
                    iterator2 = iterator;
                    int n9 = n7 + 2 + n2 * 10 - (int)s1;
                    ++n2;
                    fontRenderer.drawStringWithShadow(string2, (float)(n6 + 2), (float)n9, -1);
                }
                GL11.glDisable((int)3089);
                if (s3) {
                    if (s1 + (double)n4 < (double)(list.size() * 10 + 2)) {
                        s1 += 0.05;
                    } else {
                        s3 = false;
                    }
                } else if (s1 > 0.0) {
                    s1 -= 0.05;
                } else {
                    s3 = true;
                }
            } else {
                Iterator<String> iterator;
                int n10 = n3;
                Gui.drawModalRectWithCustomSizedTexture((int)n6, (int)n7, (float)0.0f, (float)0.0f, (int)n10, (int)n4, (float)n10, (float)n4);
                GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                int n11 = 0;
                Iterator<String> iterator3 = iterator = list.iterator();
                while (iterator3.hasNext()) {
                    String string3 = iterator.next();
                    iterator3 = iterator;
                    int n12 = n7 + 2 + n11 * 10;
                    ++n11;
                    fontRenderer.drawStringWithShadow(string3, (float)(n6 + 2), (float)n12, -1);
                }
            }
            GlStateManager.enableLighting();
            GlStateManager.enableRescaleNormal();
        }
    }

    static {
        s2 = 0.0;
        s5 = new ResourceLocation("vexview", "textures/itemshow.png");
        s3 = true;
    }

    public static boolean m4(ItemStack itemStack) {
        if (itemStack.hasTagCompound()) {
            return itemStack.getTagCompound().hasKey("preview");
        }
        return false;
    }

    public static boolean m5(ItemStack itemStack) {
        if (itemStack.hasTagCompound()) {
            return itemStack.getTagCompound().hasKey("backimage");
        }
        return false;
    }

    @SubscribeEvent
    public void onRenderEnd(RenderTooltipEvent.PostText postText) {
        if (ToolTipRender.m4(postText.getStack())) {
            ToolTipRender.m1(postText.getStack(), postText.getX(), postText.getY());
        }
    }

    @SubscribeEvent
    public void onRender(RenderTooltipEvent.Pre pre) {
        if (ToolTipRender.m5(pre.getStack())) {
            RenderTooltipEvent.Pre pre2 = pre;
            ToolTipRender.m3(pre2.getStack(), pre.getX() + 8, pre.getY());
            pre2.setCanceled(true);
            if (ToolTipRender.m4(pre2.getStack())) {
                ToolTipRender.m1(pre.getStack(), pre.getX() + 8, pre.getY());
            }
        }
    }

    public static List<String> m6(ItemStack itemStack) {
        Minecraft minecraft;
        Minecraft minecraft2 = minecraft = Minecraft.getMinecraft();
        return itemStack.getTooltip((EntityPlayer)minecraft2.player, (ITooltipFlag)(minecraft2.gameSettings.advancedItemTooltips ? ITooltipFlag.TooltipFlags.ADVANCED : ITooltipFlag.TooltipFlags.NORMAL));
    }
}


