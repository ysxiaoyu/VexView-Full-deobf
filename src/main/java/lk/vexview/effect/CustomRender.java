/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.effect;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lk.vexview.effect.Lighting;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CustomRender {
    public static List<Lighting> s1 = new CopyOnWriteArrayList<Lighting>();

    @SubscribeEvent
    public void onWorld(RenderWorldLastEvent renderWorldLastEvent) {
        s1.forEach(lighting -> {
            Lighting lighting2 = lighting;
            lighting2.m2();
            if (lighting2.m1()) {
                s1.remove(lighting);
            }
        });
    }
}

