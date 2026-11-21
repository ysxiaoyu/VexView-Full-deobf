/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.tag;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import lk.vexview.engine.ComponentFactory;
import lk.vexview.engine.collection.ComponentElementCollection;
import lk.vexview.engine.collection.TagElementCollection;
import lk.vexview.tag.core.VexTag;
import org.json.JSONObject;

public class TagBuilder {
    static String s1;
    static Map<String, VexTag> s2;
    static Map<String, List<String>> s3;
    static Map<String, VexTag> s4;

    public static void m1(JSONObject jSONObject) {
        String string = jSONObject.getString("packet_sub_type");
        if (string.equals("remove")) {
            ComponentElementCollection componentElementCollection = new ComponentElementCollection(jSONObject.getString("packet_data"), "<&>");
            if (s3.containsKey(componentElementCollection.m8(0))) {
                s2.remove(componentElementCollection.m8(1));
                s3.get(componentElementCollection.m8(0)).remove(componentElementCollection.m8(1));
                return;
            }
        } else {
            if (string.equals("clear")) {
                s3.clear();
                s2.clear();
                return;
            }
            TagElementCollection tagElementCollection = new TagElementCollection(jSONObject.getString("packet_data"), "<&>", string);
            VexTag vexTag = ComponentFactory.m3(tagElementCollection);
            vexTag.m1();
            if (!s3.containsKey(tagElementCollection.m1())) {
                s3.put(tagElementCollection.m1(), new CopyOnWriteArrayList());
            }
            if (!s3.get(tagElementCollection.m1()).contains(tagElementCollection.m2())) {
                s3.get(tagElementCollection.m1()).add(tagElementCollection.m2());
            }
            s2.put(tagElementCollection.m2(), vexTag);
        }
    }

    public static void m2(JSONObject jSONObject) {
        JSONObject jSONObject2 = jSONObject;
        String string = jSONObject2.getString("packet_sub_type");
        String string2 = jSONObject2.getString("packet_data");
        ComponentElementCollection componentElementCollection = new ComponentElementCollection(string2, "<&>");
        if (componentElementCollection.m8(0).equals(s1)) {
            s4.clear();
            s1 = componentElementCollection.m8(0);
        }
        if (string.equals("remove")) {
            if (s4.containsKey(componentElementCollection.m8(1))) {
                s4.remove(componentElementCollection.m8(1));
                return;
            }
        } else {
            if (string.equals("clear")) {
                s4.clear();
                return;
            }
            TagElementCollection tagElementCollection = new TagElementCollection(string2, "<&>", string);
            VexTag vexTag = ComponentFactory.m3(tagElementCollection);
            vexTag.m1();
            s4.put(tagElementCollection.m2(), vexTag);
        }
    }

    static {
        s2 = new ConcurrentHashMap<String, VexTag>();
        s3 = new ConcurrentHashMap<String, List<String>>();
        s1 = "";
        s4 = new ConcurrentHashMap<String, VexTag>();
    }
}

