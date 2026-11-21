/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.core;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;
import lk.vexview.gui.core.VexComponent;
import org.json.JSONArray;

public class VexSlot
extends VexComponent {
    int f1;
    public int f2;
    private byte[] f3;
    public int f4;

    public int hashCode() {
        Object[] objectArray = new Object[3];
        objectArray[0] = this.f2;
        objectArray[1] = this.f4;
        objectArray[2] = this.f1;
        int n = Objects.hash(objectArray);
        n = 31 * n + Arrays.hashCode(this.f3);
        return n;
    }

    public byte[] m1() {
        return this.f3;
    }

    private /* synthetic */ byte[] m2(String object) {
        Iterator<Object> iterator;
        JSONArray jSONArray = new JSONArray((String)object);
        byte[] byArray = new byte[jSONArray.length()];
        int n = 0;
        Iterator<Object> iterator2 = iterator = jSONArray.toList().iterator();
        while (iterator2.hasNext()) {
            Object object2 = iterator.next();
            byArray[n++] = ((Integer)object2).byteValue();
            iterator2 = iterator;
        }
        return byArray;
    }

    public VexSlot m3(String string) {
        VexSlot vexSlot = this;
        vexSlot.f3 = vexSlot.m2(string);
        return vexSlot;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        VexSlot vexSlot = (VexSlot)object;
        return this.f2 == vexSlot.f2 && this.f4 == vexSlot.f4 && this.f1 == vexSlot.f1 && Arrays.equals(this.f3, vexSlot.f3);
    }

    public int m7() {
        return this.f1;
    }

    public VexSlot(int n, int n2, int n3) {
        VexSlot vexSlot = this;
        this.f2 = n2;
        vexSlot.f4 = n3;
        vexSlot.f1 = n;
    }
}

