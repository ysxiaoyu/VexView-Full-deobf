/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.ui.menu;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import lk.vexview.VexView;
import lk.vexview.engine.base.Element;
import lk.vexview.net.NetPacketManager;
import lk.vexview.net.packet.PacketGameMenu;
import lk.vexview.scoreboard.SBPacketBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiUtilRenderComponents;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.fml.client.GuiScrollingList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.input.Mouse;

public class VexGameMenu
extends GuiIngameMenu {
    static BoradCast s1;
    static boolean s2;
    static String s3;
    static String s4;
    static boolean s5;
    static List<String> s6;
    private static final ResourceLocation s7;

    public void initGui() {
        VexGameMenu vexGameMenu = this;
        super.initGui();
        int n = vexGameMenu.width / 2 - 110;
        int n2 = vexGameMenu.height / 2 - 55;
        vexGameMenu.buttonList.clear();
        if (VexView.s8) {
            this.buttonList.add(new GuiButton(61, n, n2 - 22, 100, 20, "\u00a7lVexView Store \u00a7a\u2714"));
        }
        this.buttonList.add(new GuiButton(4, n, n2, 100, 20, I18n.format((String)"menu.returnToGame", (Object[])new Object[0])));
        this.buttonList.add(new GuiButton(22, n, n2 + 22, 100, 20, I18n.format((String)"goto.server.website", (Object[])new Object[0])));
        if (s3.equals("none")) {
            ((GuiButton)this.buttonList.get((int)(VexView.s8 ? 2 : 1))).enabled = false;
        }
        this.buttonList.add(new GuiButton(0, n, n2 + 44, 100, 20, I18n.format((String)"menu.options", (Object[])new Object[0])));
        this.buttonList.add(new GuiButton(12, n, n2 + 66, 100, 20, I18n.format((String)"fml.menu.modoptions", (Object[])new Object[0])));
        this.buttonList.add(new GuiButton(5, n, n2 + 88, 100, 20, I18n.format((String)"gui.advancements", (Object[])new Object[0])));
        this.buttonList.add(new GuiButton(6, n, n2 + 110, 100, 20, I18n.format((String)"gui.stats", (Object[])new Object[0])));
        this.buttonList.add(new GuiButton(1, n, n2 + 132, 100, 20, I18n.format((String)"disconnect.server", (Object[])new Object[0])));
        s1 = new BoradCast(this, s6);
    }

    public void drawScreen(int n, int n2, float f) {
        VexGameMenu vexGameMenu = this;
        super.drawScreen(n, n2, f);
        VexGameMenu vexGameMenu2 = this;
        vexGameMenu2.drawCenteredString(vexGameMenu.fontRenderer, new StringBuilder().insert(0, s4).append("\u00a7f").toString(), vexGameMenu2.width / 2, 28, 0xFFFFFF);
        s1.drawScreen(n, n2, f);
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        Minecraft.getMinecraft().getTextureManager().bindTexture(s7);
        VexGameMenu.drawScaledCustomSizeModalRect((int)(vexGameMenu.width / 2 + 10), (int)(this.height / 2 - 55), (float)0.0f, (float)0.0f, (int)150, (int)152, (int)150, (int)152, (float)150.0f, (float)152.0f);
    }

    static {
        s4 = "";
        s3 = "";
        s6 = new ArrayList<String>();
        s7 = new ResourceLocation("vexview", "textures/broad.png");
        s2 = false;
        s5 = false;
    }

    @SubscribeEvent
    public void onOpen(GuiOpenEvent guiOpenEvent) {
        if (s5 && guiOpenEvent.getGui() instanceof GuiIngameMenu) {
            guiOpenEvent.setGui((GuiScreen)this);
            if (!s2) {
                s2 = true;
                NetPacketManager.m4(new PacketGameMenu());
                new Timer("").schedule(new TimerTask(){

                    @Override
                    public void run() {
                        s2 = false;
                    }
                }, 3000L);
            }
        }
    }

    protected void actionPerformed(GuiButton guiButton) throws IOException {
        GuiButton guiButton2 = guiButton;
        super.actionPerformed(guiButton2);
        if (guiButton2.id == 22) {
            Desktop desktop;
            Desktop desktop2 = Desktop.getDesktop();
            URI uRI = null;
            try {
                uRI = new URI(s3);
                desktop = desktop2;
            }
            catch (URISyntaxException uRISyntaxException) {
                desktop = desktop2;
            }
            desktop.browse(uRI);
        }
    }

    public static void m4(String string) {
        if (string.equals("NO")) {
            s5 = false;
            return;
        }
        s5 = true;
        String string2 = string;
        s3 = string.split("<##>")[1];
        s4 = string2.split("<##>")[2];
        s6 = Arrays.asList(string2.split(SBPacketBuffer.m1("\"^2L"))[3].split("<#>"));
    }

    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
        if (s1 != null) {
            int n = Mouse.getEventX() * this.width / this.mc.displayWidth;
            int n2 = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
            s1.handleMouseInput(n, n2);
        }
    }

    private class BoradCast
    extends GuiScrollingList {
        private List<ITextComponent> f1;
        final /* synthetic */ VexGameMenu f2;

        protected boolean isSelected(int n) {
            return false;
        }

        protected void elementClicked(int n, boolean bl) {
        }

        protected int getSize() {
            return 0;
        }

        public BoradCast(VexGameMenu vexGameMenu, List<String> list) {
            super(Minecraft.getMinecraft(), 155, vexGameMenu.height, vexGameMenu.height / 2 - 55, vexGameMenu.height / 2 + 97, vexGameMenu.width / 2 + 10, 60, vexGameMenu.width, vexGameMenu.height);
            this.f2 = vexGameMenu;
            this.f1 = null;
            this.f1 = this.m2(list);
            this.setHeaderInfo(true, this.m1());
        }

        private /* synthetic */ int m1() {
            int n = 0;
            n = 0 + this.f1.size() * 10;
            BoradCast boradCast = this;
            if (n < boradCast.bottom - boradCast.top - 8) {
                BoradCast boradCast2 = this;
                n = boradCast2.bottom - boradCast2.top - 8;
            }
            return n;
        }

        protected void drawHeader(int n, int n2, Tessellator tessellator) {
            Iterator<ITextComponent> iterator;
            Iterator<ITextComponent> iterator2 = iterator = this.f1.iterator();
            while (iterator2.hasNext()) {
                ITextComponent iTextComponent = iterator.next();
                if (iTextComponent != null) {
                    GlStateManager.enableBlend();
                    this.f2.fontRenderer.drawStringWithShadow(iTextComponent.getFormattedText(), (float)(this.left + 4), (float)n2, 0xFFFFFF);
                    GlStateManager.disableAlpha();
                    GlStateManager.disableBlend();
                }
                n2 += 10;
                iterator2 = iterator;
            }
        }

        protected void drawSlot(int n, int n2, int n3, int n4, Tessellator tessellator) {
        }

        protected void drawBackground() {
        }

        /*
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        protected void clickHeader(int n, int n2) {
            ITextComponent iTextComponent;
            if (n2 <= 0) {
                return;
            }
            if ((n2 /= 10) >= this.f1.size()) {
                return;
            }
            ITextComponent iTextComponent2 = this.f1.get(n2);
            if (iTextComponent2 == null) return;
            int n3 = -4;
            Iterator iterator = iTextComponent2.iterator();
            block0: do {
                Iterator iterator2 = iterator;
                while (iterator2.hasNext()) {
                    iTextComponent = (ITextComponent)iterator.next();
                    if (iTextComponent instanceof TextComponentString) continue block0;
                    iterator2 = iterator;
                }
                return;
            } while ((n3 += this.f2.fontRenderer.getStringWidth(((TextComponentString)iTextComponent).getText())) < n);
            this.f2.handleComponentClick(iTextComponent);
        }

        private /* synthetic */ List<ITextComponent> m2(List<String> list) {
            ArrayList<ITextComponent> arrayList = new ArrayList<ITextComponent>();
            Iterator<String> iterator = list.iterator();
            block0: while (true) {
                Iterator<String> iterator2 = iterator;
                while (iterator2.hasNext()) {
                    String string = iterator.next();
                    if (string == null) {
                        iterator2 = iterator;
                        arrayList.add(null);
                        continue;
                    }
                    ITextComponent iTextComponent = ForgeHooks.newChatWithLinks((String)string, (boolean)false);
                    int n = this.listWidth - 8;
                    if (n < 0) continue block0;
                    arrayList.addAll(GuiUtilRenderComponents.splitText((ITextComponent)iTextComponent, (int)n, (FontRenderer)this.f2.fontRenderer, (boolean)false, (boolean)true));
                    continue block0;
                }
                break;
            }
            return arrayList;
        }
    }
}

