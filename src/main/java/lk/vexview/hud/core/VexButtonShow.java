/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.hud.core;

import lk.vexview.engine.BufferImageLib;
import lk.vexview.engine.collection.SimpleElementCollection;
import lk.vexview.gui.core.VexButton;
import lk.vexview.hud.HUDViewer;
import lk.vexview.hud.core.VexShow;
import lk.vexview.net.NetPacketManager;
import lk.vexview.net.packet.PacketHudButtonClick;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

public class VexButtonShow
extends VexShow {
    public VexButtonShow f1;
    public VexButton f2;
    private GuiButton f3;

    public VexButtonShow(String string, int n, int n2, int n3, int n4, String string2, String string3, String string4, String string5, int n5, int n6) {
        VexButtonShow vexButtonShow = this;
        VexButtonShow vexButtonShow2 = this;
        VexButtonShow vexButtonShow3 = this;
        vexButtonShow2.f1 = vexButtonShow3;
        VexButtonShow vexButtonShow4 = this;
        vexButtonShow2.f2 = new VexButton(0, n, n2, n3, n4, string2, string3, string4, string5);
        vexButtonShow2.f7 = string;
        vexButtonShow.f6 = n6;
        super.f2 = n5;
        vexButtonShow.f3 = new GuiButton(0, n, n2, n3, n4, string4){
            final /* synthetic */ int f1 = n3;
            final /* synthetic */ VexButtonShow f2 = VexButtonShow.this;
            final /* synthetic */ int f3 = n4;

            public boolean mousePressed(Minecraft minecraft, int n, int n2) {
                ScaledResolution scaledResolution = new ScaledResolution(minecraft);
                int n3 = scaledResolution.getScaledWidth();
                int n4 = scaledResolution.getScaledHeight();
                n3 = HUDViewer.m8(this.f2.f1, this.f1, n3, this.x);
                n4 = HUDViewer.m3(this.f2.f1, this.f3, n4, this.y);
                boolean bl = this.enabled && this.visible && n >= n3 && n2 >= n4 && (double)n < (double)(n3 + this.width) && (double)n2 < (double)(n4 + this.height);
                return bl;
            }

            public void drawButton(Minecraft minecraft, int n, int n2, float f) {
                FontRenderer fontRenderer;
                FontRenderer fontRenderer2;
                int n3;
                int n4;
                block3: {
                    block2: {
                        block1: {
                            GlStateManager.disableLighting();
                            GlStateManager.color((float)1.0f, (float)1.0f, (float)1.0f, (float)1.0f);
                            GlStateManager.enableAlpha();
                            GlStateManager.enableBlend();
                            ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
                            n4 = scaledResolution.getScaledWidth();
                            n3 = scaledResolution.getScaledHeight();
                            n4 = HUDViewer.m8(this.f2.f1, this.f1, n4, this.x);
                            n3 = HUDViewer.m3(this.f2.f1, this.f3, n3, this.y);
                            this.hovered = n >= n4 && n2 >= n3 && n < n4 + this.width && n2 < n3 + this.height;
                            fontRenderer2 = Minecraft.getMinecraft().fontRenderer;
                            if (!this.hovered) break block1;
                            if (!BufferImageLib.s4.containsKey(this.f2.f2.f13)) break block2;
                            Minecraft.getMinecraft().getTextureManager().bindTexture(BufferImageLib.s4.get(this.f2.f2.f13));
                            drawScaledCustomSizeModalRect((int)n4, (int)n3, (float)0.0f, (float)0.0f, (int)this.f1, (int)this.f3, (int)this.f1, (int)this.f3, (float)this.f1, (float)this.f3);
                            fontRenderer = fontRenderer2;
                            break block3;
                        }
                        if (BufferImageLib.s4.containsKey(this.f2.f2.f5)) {
                            Minecraft.getMinecraft().getTextureManager().bindTexture(BufferImageLib.s4.get(this.f2.f2.f5));
                            drawScaledCustomSizeModalRect((int)n4, (int)n3, (float)0.0f, (float)0.0f, (int)this.f1, (int)this.f3, (int)this.f1, (int)this.f3, (float)this.f1, (float)this.f3);
                        }
                    }
                    fontRenderer = fontRenderer2;
                }
                fontRenderer.drawString(this.f2.f2.f9, n4 + this.f2.f2.f11 / 2 - fontRenderer2.getStringWidth(this.f2.f2.f9) / 2, n3 + this.f2.f2.f8 / 2 - 4, -1);
            }

            public void mouseReleased(int n, int n2) {
                if (this.mousePressed(Minecraft.getMinecraft(), n, n2)) {
                    this.playPressSound(Minecraft.getMinecraft().getSoundHandler());
                    NetPacketManager.m4(new PacketHudButtonClick(this.f2.f1.f7));
                }
            }

            public void drawButtonForegroundLayer(int n, int n2) {
                ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
                int n3 = scaledResolution.getScaledWidth();
                int n4 = scaledResolution.getScaledHeight();
                n3 = HUDViewer.m8(this.f2.f1, this.f1, n3, this.x);
                n4 = HUDViewer.m3(this.f2.f1, this.f3, n4, this.y);
                boolean bl = this.hovered = n >= n3 && n2 >= n4 && n < n3 + this.width && n2 < n4 + this.height;
                if (this.hovered && this.f2.f2.m5() != null && !this.f2.f2.m5().f3.isEmpty()) {
                    Minecraft.getMinecraft().currentScreen.drawHoveringText(this.f2.f2.m5().f3, n, n2);
                }
            }
        };
    }

    @Override
    public void m4() {
        this.f2.m2();
    }

    public VexShow m1(SimpleElementCollection simpleElementCollection) {
        VexButtonShow vexButtonShow = this;
        vexButtonShow.f2.m1(simpleElementCollection);
        return vexButtonShow;
    }

    public GuiButton m2() {
        return this.f3;
    }
}

