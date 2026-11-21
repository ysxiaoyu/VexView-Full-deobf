/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.scoreboard;

import java.util.concurrent.ExecutionException;
import lk.vexview.VexView;
import lk.vexview.announcement.AnnGui;
import lk.vexview.announcement.Announcement;
import lk.vexview.announcement.Type;
import lk.vexview.engine.ComponentFactory;
import lk.vexview.engine.collection.ComponentElementCollection;
import lk.vexview.engine.collection.HoverTextElementCollection;
import lk.vexview.gui.core.VexImage;
import lk.vexview.gui.core.VexText;
import lk.vexview.scoreboard.NewScoreBroad;
import net.minecraft.client.resources.I18n;
import org.json.JSONObject;

public class ScoreBroadBuilder {
    public static String m2(String string) {
        int n;
        StackTraceElement stackTraceElement = new RuntimeException().getStackTrace()[1];
        String string2 = new StringBuffer(stackTraceElement.getClassName()).insert(0, stackTraceElement.getMethodName()).toString();
        int n2 = string.length();
        int n3 = n2 - 1;
        char[] cArray = new char[n2];
        int n4 = 2 << 3 ^ 1;
        int cfr_ignored_0 = 5 << 3 ^ 5;
        int n5 = (2 ^ 5) << 4 ^ (3 << 2 ^ 1);
        int n6 = n = string2.length() - 1;
        int n7 = n3;
        String string3 = string2;
        while (n7 >= 0) {
            int n8 = n3--;
            cArray[n8] = (char)(n5 ^ (string.charAt(n8) ^ string3.charAt(n)));
            if (n3 < 0) break;
            int n9 = n3--;
            char c = cArray[n9] = (char)(n4 ^ (string.charAt(n9) ^ string3.charAt(n)));
            if (--n < 0) {
                n = n6;
            }
            n7 = n3;
        }
        return new String(cArray);
    }

    public static void m3(String string) {
        VexView.m1(() -> {
            try {
                JSONObject jSONObject = new JSONObject(string);
                ComponentElementCollection componentElementCollection = new ComponentElementCollection(jSONObject.getString("image"), "<&>");
                VexImage vexImage = new VexImage(0, 0, componentElementCollection.m3(1), componentElementCollection.m3(2), componentElementCollection.m3(3), componentElementCollection.m3(4), componentElementCollection.m8(0));
                VexText vexText = (VexText)ComponentFactory.m4(new HoverTextElementCollection(jSONObject.getString("pages"), "<&>", "<#&>"));
                if (!vexImage.m1()) {
                    AnnGui.m1(new Announcement(I18n.format((String)"scorebroad.begin", (Object[])new Object[0]), Type.s4));
                    NewScoreBroad.s5 = 1;
                }
                vexImage.m2();
                if (!vexImage.m4().get(0).get().booleanValue()) {
                    AnnGui.m1(new Announcement(new StringBuilder().insert(0, "\ufffd\ufffdc").append(I18n.format((String)"scorebroad.failed", (Object[])new Object[0])).append("\ufffd\ufffdf").toString(), Type.s3));
                    NewScoreBroad.s5 = 0;
                    return;
                }
                NewScoreBroad.s6 = vexImage;
                NewScoreBroad.s4 = vexText;
                NewScoreBroad.s5 = 2;
                if (!NewScoreBroad.s6.m1()) {
                    AnnGui.m1(new Announcement(new StringBuilder().insert(0, "\ufffd\ufffda").append(I18n.format((String)"scorebroad.done", (Object[])new Object[0])).append("\ufffd\ufffdf").toString(), Type.s1));
                }
            }
            catch (InterruptedException | ExecutionException exception) {
                exception.printStackTrace();
            }
        });
    }

    public static void m4(String string) {
        JSONObject jSONObject = new JSONObject(string);
        NewScoreBroad.s4 = (VexText)ComponentFactory.m4(new HoverTextElementCollection(jSONObject.getString("pages"), "<&>", "<#&>"));
    }
}

