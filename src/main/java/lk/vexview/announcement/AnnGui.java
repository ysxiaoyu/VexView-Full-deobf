/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.announcement;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lk.vexview.announcement.Announcement;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AnnGui {
    static final ResourceLocation s1;
    static List<Announcement> s2;
    static final ResourceLocation s3;
    static final ResourceLocation s4;

    @SubscribeEvent
    public void onGame(RenderGameOverlayEvent.Pre pre) {
        if (pre.getType() == RenderGameOverlayEvent.ElementType.CROSSHAIRS) {
            Iterator<Announcement> iterator;
            int n = 0;
            Iterator<Announcement> iterator2 = iterator = s2.iterator();
            while (iterator2.hasNext()) {
                Announcement announcement = iterator.next();
                int n2 = pre.getResolution().getScaledWidth();
                announcement.m1(n2, n * 12);
                if (announcement.f3 >= announcement.m3()) {
                    s2.remove(announcement);
                }
                ++n;
                iterator2 = iterator;
            }
        }
    }

    static {
        s4 = new ResourceLocation("vexview", "textures/download.png");
        s3 = new ResourceLocation("vexview", "textures/error.png");
        s1 = new ResourceLocation("vexview", "textures/normal.png");
        s2 = new CopyOnWriteArrayList<Announcement>();
    }

    public static void m1(Announcement announcement) {
        s2.add(announcement);
    }
}

