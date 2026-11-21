/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lk.vexview.engine.BufferImageLib;
import lk.vexview.gui.core.VexImage;

public class VexGifImage
extends VexImage {
    long f1 = System.currentTimeMillis();
    public static Map<String, Integer> s1 = new ConcurrentHashMap<String, Integer>();
    public int f2;
    private int f3 = 0;

    @Override
    public void m2() {
        VexGifImage vexGifImage = this;
        vexGifImage.m3(BufferImageLib.m9(this.f11, vexGifImage));
    }

    public VexGifImage(int n, int n2, int n3, int n4, int n5, int n6, String string, int n7) {
        super(n, n2, n3, n4, n5, n6, string);
        if (s1.containsKey(string)) {
            this.f2 = s1.get(string);
            return;
        }
        this.f2 = n7;
    }

    public int m7() {
        VexGifImage vexGifImage;
        if (System.currentTimeMillis() - this.f1 < (long)this.f2) {
            return this.f3;
        }
        this.f1 = System.currentTimeMillis();
        if (this.f3 < BufferImageLib.s2.get(this.f11).size() - 1) {
            VexGifImage vexGifImage2 = this;
            vexGifImage = vexGifImage2;
            ++vexGifImage2.f3;
        } else {
            vexGifImage = this;
            this.f3 = 0;
        }
        return vexGifImage.f3;
    }
}

