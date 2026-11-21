/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.entity;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.world.World;

public class VexPlayerEntity
extends AbstractClientPlayer {
    public boolean isCreative() {
        return false;
    }

    public VexPlayerEntity(WorldClient worldClient, GameProfile gameProfile) {
        super((World)worldClient, gameProfile);
    }

    public boolean isSpectator() {
        return false;
    }

    public VexPlayerEntity(World world) {
        super((World)Minecraft.getMinecraft().world, new GameProfile(UUID.randomUUID(), ""));
    }
}

