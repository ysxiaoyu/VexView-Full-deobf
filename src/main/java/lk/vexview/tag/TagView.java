package lk.vexview.tag;

import java.util.Iterator;
import lk.vexview.tag.core.VexTag;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TagView {
   @SubscribeEvent
   public void onWorld(RenderWorldLastEvent var1) {
      try {
         for (EntityPlayer var6 : Minecraft.getMinecraft().world.playerEntities) {
            if (Minecraft.getMinecraft().player.getName().equals(var6.getName())) {
               if (TagBuilder.s3.containsKey(var6.getName())) {
                  for (String var14 : TagBuilder.s3.get(var6.getName())) {
                     if (TagBuilder.s2.get(var14).f4) {
                        TagBuilder.s2.get(var14).m3(var6);
                     } else if (Minecraft.getMinecraft().getRenderManager().options != null
                        && Minecraft.getMinecraft().getRenderManager().options.thirdPersonView != 0) {
                        TagBuilder.s2.get(var14).m3(var6);
                     }
                  }
               }
            } else {
               Iterator var7;
               if (TagBuilder.s3.containsKey(var6.getName())) {
                  for (Iterator var10000 = var7 = TagBuilder.s3.get(var6.getName()).iterator(); var10000.hasNext(); var10000 = var7) {
                     String var8 = (String)var7.next();
                     TagBuilder.s2.get(var8).m3(var6);
                  }
               }
            }
         }

         Iterator var12 = TagBuilder.s4.values().iterator();

         while (var12.hasNext()) {
            ((VexTag)var12.next()).m2();
         }
      } catch (Exception var11) {
      }
   }
}
