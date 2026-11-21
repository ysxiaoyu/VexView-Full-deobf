/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.core;

import java.util.ArrayList;
import java.util.List;
import lk.vexview.gui.core.VexComponent;

public class VexText
extends VexComponent {
    public List<String> f1;
    public double f2;
    public int f3;
    public int f4;

    public VexText(int n, int n2, double d, List<String> list) {
        VexText vexText = this;
        VexText vexText2 = this;
        vexText2.f2 = 1.0;
        VexText vexText3 = this;
        vexText2.f1 = new ArrayList<String>();
        vexText2.f3 = n;
        vexText.f4 = n2;
        vexText.f2 = d;
        list.stream().forEach(string -> {
            int n4;
            String[] stringArray = string.replace("\\n", "<Enter_Next>").split("<Enter_Next>");
            int n5 = stringArray.length;
            int n6 = n4 = 0;
            while (n6 < n5) {
                String string2 = stringArray[n4];
                this.f1.add(string2);
                n6 = ++n4;
            }
        });
    }

    public boolean equals(Object object) {
        if (object instanceof VexText) {
            VexText vexText = (VexText)object;
            if (vexText.f3 == this.f3 && vexText.f4 == this.f4 && this.f1.equals(vexText.f1)) {
                return true;
            }
        }
        return false;
    }
}

