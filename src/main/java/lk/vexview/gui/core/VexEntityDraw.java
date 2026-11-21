/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.gui.core;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.Iterator;
import lk.vexview.entity.VexEntityGeter;
import lk.vexview.gui.core.VexComponent;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import org.json.JSONArray;

public class VexEntityDraw
extends VexComponent {
    public int f1;
    public int f2;
    public Entity f3;
    public boolean f4;
    public int f5;

    public VexEntityDraw(int n, int n2, int n3, boolean bl, String string, String string2) {
        VexEntityDraw vexEntityDraw = this;
        VexEntityDraw vexEntityDraw2 = this;
        VexEntityDraw vexEntityDraw3 = this;
        vexEntityDraw3.f1 = 0;
        vexEntityDraw3.f5 = 0;
        vexEntityDraw2.f2 = 30;
        vexEntityDraw2.f1 = n;
        vexEntityDraw.f5 = n2;
        vexEntityDraw.f2 = n3;
        this.f4 = bl;
        this.f3 = VexEntityGeter.m2(string);
        if (this.f3 != null) {
            Iterator<Object> iterator;
            JSONArray jSONArray = new JSONArray(string2);
            byte[] byArray = new byte[jSONArray.length()];
            n2 = 0;
            Iterator<Object> iterator2 = iterator = jSONArray.toList().iterator();
            while (iterator2.hasNext()) {
                Object object = iterator.next();
                byArray[n2++] = ((Integer)object).byteValue();
                iterator2 = iterator;
            }
            iterator = null;
            try {
                NBTTagCompound nBTTagCompound = CompressedStreamTools.read((DataInputStream)new DataInputStream(new ByteArrayInputStream(byArray)));
                this.f3.readFromNBT(nBTTagCompound);
                return;
            }
            catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
    }
}

