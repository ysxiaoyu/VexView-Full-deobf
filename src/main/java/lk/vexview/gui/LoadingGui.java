/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import lk.vexview.VexConfig;
import lk.vexview.gui.VexCustomGui;
import lk.vexview.gui.core.VexButton;
import lk.vexview.gui.core.VexCheckBox;
import lk.vexview.gui.core.VexComponent;
import lk.vexview.gui.core.VexImage;
import lk.vexview.net.NetPacketManager;
import lk.vexview.net.packet.PacketGuiClose;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class LoadingGui
extends GuiScreen {
    String f1;
    Map<Integer, String> f2;
    private static final ResourceLocation s1 = new ResourceLocation("vexview", "textures/loading.png");
    public int f3;
    public int f4;
    long f5;
    public int f6;
    VexCustomGui f7;
    List<VexComponent> f8;

    public void renderError(int n, int n2, float f) {
        if (!this.f2.isEmpty()) {
            Iterator<String> iterator;
            LoadingGui loadingGui = this;
            LoadingGui.drawRect((int)0, (int)(this.height / 2 + 6), (int)loadingGui.width, (int)(loadingGui.height / 2 + 6 + 10 * this.f2.size()), (int)Integer.MIN_VALUE);
            n = 0;
            Iterator<String> iterator2 = iterator = this.f2.values().iterator();
            while (iterator2.hasNext()) {
                String string = iterator.next();
                iterator2 = iterator;
                LoadingGui loadingGui2 = this;
                int n3 = 10 * n;
                ++n;
                loadingGui2.drawString(this.fontRenderer, string, (loadingGui2.width - this.fontRenderer.getStringWidth(string)) / 2, this.height / 2 + 7 + n3, -1);
            }
        }
    }

    public void renderText(int n, int n2, float f) {
        if (System.currentTimeMillis() - this.f5 >= 500L) {
            LoadingGui loadingGui;
            if (this.f1.equals("oOoOo")) {
                loadingGui = this;
                this.f1 = "OoOoO";
            } else {
                loadingGui = this;
                this.f1 = "oOoOo";
            }
            loadingGui.f5 = System.currentTimeMillis();
        }
        LoadingGui loadingGui = this;
        loadingGui.drawString(loadingGui.fontRenderer, loadingGui.f1, this.width / 2 - 6, this.height / 2 - 8, 0xFFFFFF);
    }

    public void onGuiClosed() {
        if (!this.isDone()) {
            NetPacketManager.m4(new PacketGuiClose());
        }
    }

    public LoadingGui(VexCustomGui vexCustomGui) {
        LoadingGui loadingGui = this;
        LoadingGui loadingGui2 = this;
        LoadingGui loadingGui3 = this;
        this.f8 = new ArrayList<VexComponent>();
        this.f3 = 0;
        loadingGui2.f4 = 0;
        loadingGui.f6 = 0;
        loadingGui2.f2 = new ConcurrentHashMap<Integer, String>();
        loadingGui.f5 = System.currentTimeMillis();
        this.f1 = "oOoOo";
        this.f7 = vexCustomGui;
        LoadingGui loadingGui4 = this;
        this.f8.addAll(loadingGui4.f7.f3.m7());
        if (loadingGui4.f7.f3.f13 != null) {
            LoadingGui loadingGui5 = this;
            loadingGui5.f8.addAll(loadingGui5.f7.f3.f13.f6);
        }
        this.f3 = this.f8.size();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setFailed(VexComponent vexComponent, int n) {
        LoadingGui loadingGui;
        LoadingGui loadingGui2;
        if (this.f2.containsKey(vexComponent.hashCode())) return;
        if (vexComponent instanceof VexImage) {
            VexImage vexImage = (VexImage)vexComponent;
            if (vexImage.m1()) {
                this.f2.put(vexComponent.hashCode(), new StringBuilder().insert(0, "> ").append(I18n.format((String)"image.dowmloadfailed.local.a", (Object[])new Object[0])).append(" .minecraft/").append(VexConfig.s1).append("/").append(vexImage.f11.replace("[local]", "")).append(I18n.format((String)"image.dowmloadfailed.type.image", (Object[])new Object[0])).toString());
                return;
            }
            this.f2.put(vexComponent.hashCode(), new StringBuilder().insert(0, "> ").append(I18n.format((String)"image.dowmloadfailed.local.c", (Object[])new Object[0])).append(" ").append(vexImage.f11).append(I18n.format((String)"image.dowmloadfailed.type.image", (Object[])new Object[0])).toString());
            return;
        }
        if (vexComponent instanceof VexButton) {
            LoadingGui loadingGui3;
            LoadingGui loadingGui4;
            LoadingGui loadingGui5;
            VexButton vexButton = (VexButton)vexComponent;
            switch (n) {
                case 0: {
                    loadingGui5 = this;
                    if (vexButton.m3()) {
                        loadingGui5.f2.put(vexComponent.hashCode(), new StringBuilder().insert(0, "> ").append(I18n.format((String)"image.dowmloadfailed.local.a", (Object[])new Object[0])).append(" .minecraft/").append(VexConfig.s1).append("/").append(vexButton.f5.replace("[local]", "")).append(I18n.format((String)"image.dowmloadfailed.type.button", (Object[])new Object[0])).toString());
                        return;
                    }
                }
                loadingGui5.f2.put(vexComponent.hashCode(), new StringBuilder().insert(0, "> ").append(I18n.format((String)"image.dowmloadfailed.local.c", (Object[])new Object[0])).append(" ").append(vexButton.f5).append(I18n.format((String)"image.dowmloadfailed.type.button", (Object[])new Object[0])).toString());
                return;
                case 1: {
                    loadingGui4 = this;
                    if (vexButton.m1()) {
                        loadingGui4.f2.put(vexComponent.hashCode(), new StringBuilder().insert(0, "> ").append(I18n.format((String)"image.dowmloadfailed.local.a", (Object[])new Object[0])).append(" .minecraft/").append(VexConfig.s1).append("/").append(vexButton.f13.replace("[local]", "")).append(I18n.format((String)"image.dowmloadfailed.type.button", (Object[])new Object[0])).toString());
                        return;
                    }
                }
                loadingGui4.f2.put(vexComponent.hashCode(), new StringBuilder().insert(0, "> ").append(I18n.format((String)"image.dowmloadfailed.local.c", (Object[])new Object[0])).append(" ").append(vexButton.f13).append(I18n.format((String)"image.dowmloadfailed.type.button", (Object[])new Object[0])).toString());
                return;
                case 2: {
                    loadingGui3 = this;
                    if (!vexButton.m7()) break;
                    loadingGui3.f2.put(vexComponent.hashCode(), new StringBuilder().insert(0, "> ").append(I18n.format((String)"image.dowmloadfailed.local.a", (Object[])new Object[0])).append(" .minecraft/").append(VexConfig.s1).append("/").append(vexButton.f1.replace("[local]", "")).append(I18n.format((String)"image.dowmloadfailed.type.button", (Object[])new Object[0])).toString());
                    return;
                }
                default: {
                    return;
                }
            }
            loadingGui3.f2.put(vexComponent.hashCode(), new StringBuilder().insert(0, "> ").append(I18n.format((String)"image.dowmloadfailed.local.c", (Object[])new Object[0])).append(" ").append(vexButton.f1).append(I18n.format((String)"image.dowmloadfailed.type.button", (Object[])new Object[0])).toString());
            return;
        }
        if (!(vexComponent instanceof VexCheckBox)) return;
        VexCheckBox vexCheckBox = (VexCheckBox)vexComponent;
        switch (n) {
            case 0: {
                loadingGui2 = this;
                if (vexCheckBox.f5.contains("[local]")) {
                    loadingGui2.f2.put(vexComponent.hashCode(), new StringBuilder().insert(0, "> ").append(I18n.format((String)"image.dowmloadfailed.local.a", (Object[])new Object[0])).append(" .minecraft/").append(VexConfig.s1).append("/").append(vexCheckBox.f5.replace("[local]", "")).append(I18n.format((String)"image.dowmloadfailed.type.clickbox", (Object[])new Object[0])).toString());
                    return;
                }
            }
            loadingGui2.f2.put(vexComponent.hashCode(), new StringBuilder().insert(0, "> ").append(I18n.format((String)"image.dowmloadfailed.local.c", (Object[])new Object[0])).append(" ").append(vexCheckBox.f5).append(I18n.format((String)"image.dowmloadfailed.type.clickbox", (Object[])new Object[0])).toString());
            return;
            case 1: {
                loadingGui = this;
                if (!vexCheckBox.f8.contains("[local]")) break;
                loadingGui.f2.put(vexComponent.hashCode(), new StringBuilder().insert(0, "> ").append(I18n.format((String)"image.dowmloadfailed.local.a", (Object[])new Object[0])).append(" .minecraft/").append(VexConfig.s1).append("/").append(vexCheckBox.f8.replace("[local]", "")).append(I18n.format((String)"image.dowmloadfailed.type.clickbox", (Object[])new Object[0])).toString());
                return;
            }
            default: {
                return;
            }
        }
        loadingGui.f2.put(vexComponent.hashCode(), new StringBuilder().insert(0, "> ").append(I18n.format((String)"image.dowmloadfailed.local.c", (Object[])new Object[0])).append(" ").append(vexCheckBox.f8).append(I18n.format((String)"image.dowmloadfailed.type.clickbox", (Object[])new Object[0])).toString());
    }

    public void drawScreen(int n, int n2, float f) {
        LoadingGui loadingGui = this;
        loadingGui.mc.getTextureManager().bindTexture(s1);
        LoadingGui loadingGui2 = this;
        loadingGui2.drawTexturedModalRect(loadingGui.width / 2 - 73, loadingGui2.height / 2, 0, 0, 148, 5);
        loadingGui.drawHorizontalLine(loadingGui.width / 2 - 74 + 2, this.width / 2 - 74 + 2 + this.f6, this.height / 2 + 2, this.f2.isEmpty() ? -14513374 : -65536);
        LoadingGui loadingGui3 = this;
        loadingGui3.renderText(n, n2, f);
        loadingGui3.renderError(n, n2, f);
        if ((double)loadingGui3.f6 <= 144.0 * ((double)this.f4 / (double)this.f3)) {
            ++this.f6;
        }
        if (this.isDone()) {
            Minecraft.getMinecraft().displayGuiScreen((GuiScreen)this.f7);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isDone() {
        int n = 0;
        if (this.f7.f3.f7.isDone()) {
            try {
                if (!this.f7.f3.f7.get().booleanValue()) {
                    if (!this.f2.containsKey(this.f7.f3.hashCode())) {
                        if (this.f7.f3.m1()) {
                            this.f2.put(this.f7.f3.hashCode(), new StringBuilder().insert(0, "> ").append(I18n.format((String)"image.dowmloadfailed.local.a", (Object[])new Object[0])).append(" .minecraft/").append(VexConfig.s1).append("/").append(this.f7.f3.f9.replace("[local]", "")).append(" ").append(I18n.format((String)"image.dowmloadfailed.local.b", (Object[])new Object[0])).toString());
                        } else {
                            this.f2.put(this.f7.f3.hashCode(), new StringBuilder().insert(0, "> ").append(I18n.format((String)"image.dowmloadfailed.local.c", (Object[])new Object[0])).append(" ").append(this.f7.f3.f9).append(" ").append(I18n.format((String)"image.dowmloadfailed.local.b", (Object[])new Object[0])).toString());
                        }
                        return false;
                    }
                    return false;
                }
            }
            catch (InterruptedException | ExecutionException exception) {
                exception.printStackTrace();
            }
        }
        for (VexComponent vexComponent : this.f8) {
            if (vexComponent.m4().isEmpty()) {
                ++n;
                continue;
            }
            VexComponent vexComponent2 = vexComponent;
            int n2 = vexComponent2.m4().size();
            int n3 = 0;
            for (Future<Boolean> future : vexComponent2.m4()) {
                if (!future.isDone()) continue;
                try {
                    if (future.get().booleanValue()) {
                        ++n3;
                        continue;
                    }
                    this.setFailed(vexComponent, n3);
                }
                catch (InterruptedException | ExecutionException exception) {
                    exception.printStackTrace();
                }
            }
            if (n2 != n3) continue;
            ++n;
        }
        this.f4 = n;
        return n == this.f3;
    }
}

