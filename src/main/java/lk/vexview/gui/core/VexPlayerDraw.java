/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.core;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import lk.vexview.entity.VexEntityGeter;
import lk.vexview.gui.core.VexComponent;
import net.minecraft.client.entity.AbstractClientPlayer;
import org.json.JSONObject;

public class VexPlayerDraw
extends VexComponent {
    public int f1;
    public int f2;
    public boolean f3;
    private GameProfile f4;
    public AbstractClientPlayer f5;
    public int f6;

    @Override
    public void m2() {
    }

    public VexPlayerDraw(int n, int n2, int n3, boolean bl, String string2, String string3, String string4) {
        VexPlayerDraw vexPlayerDraw = this;
        VexPlayerDraw vexPlayerDraw2 = this;
        VexPlayerDraw vexPlayerDraw3 = this;
        this.f2 = 0;
        vexPlayerDraw3.f6 = 0;
        vexPlayerDraw3.f1 = 30;
        vexPlayerDraw2.f2 = n;
        vexPlayerDraw2.f6 = n2;
        vexPlayerDraw.f1 = n3;
        vexPlayerDraw.f3 = bl;
        GameProfile gameProfile = new GameProfile(UUID.fromString(string2), string3);
        Map<String, Collection<Property>> map = (Map)new JSONObject(string4).toMap();
        map.forEach((string, collection) -> collection.forEach(property -> gameProfile.getProperties().put(string, property)));
        VexPlayerDraw vexPlayerDraw4 = this;
        vexPlayerDraw4.f4 = gameProfile;
        vexPlayerDraw4.f5 = VexEntityGeter.m1(gameProfile);
    }
}

