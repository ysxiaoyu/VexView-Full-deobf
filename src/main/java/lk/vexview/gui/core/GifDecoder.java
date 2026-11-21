package lk.vexview.gui.core;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

public class GifDecoder {
   public int f1;
   protected int f2;
   protected int f3;
   protected int f4;
   protected int f5;
   protected int[] f6;
   protected short[] f7;
   protected static final int s1 = 4096;
   protected int f8;
   protected int f9;
   protected BufferedInputStream f10;
   protected int f11;
   protected int f12;
   protected int f13 = 1;
   protected int f14;
   protected int f15;
   protected byte[] f16;
   protected BufferedImage f17;
   protected ArrayList f18;
   protected int[] f19;
   protected int f20;
   protected byte[] f21;
   protected boolean f22;
   public static final int s2 = 1;
   protected int f23;
   public static final int s3 = 0;
   protected boolean f24;
   protected int f25;
   protected int f26;
   protected byte[] f27;
   protected Rectangle f28;
   protected boolean f29;
   public static final int s4 = 2;
   protected BufferedImage f30;
   protected boolean f31;
   protected int[] f32;
   protected int f33;
   protected int f34;
   protected int f35;
   protected byte[] f36;
   protected int f37;

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public int m1(BufferedInputStream var1) {
      this.m5();
      if (var1 != null) {
         this.f10 = var1;
         this.m17();
         if (!this.m16()) {
            this.m2();
            if (this.f25 < 0) {
               this.f33 = 1;
            }
         }
      } else {
         this.f33 = 2;
      }

      try {
         var1.close();
      } catch (IOException var3) {
         return this.f33;
      }

      return this.f33;
   }

   protected void m2() {
      boolean var4 = false;

      while (!var4 && !this.m16()) {
         switch (this.m7()) {
            case 0:
               break;
            case 33:
               switch (this.m7()) {
                  case 249:
                     this.m22();
                     continue;
                  case 255:
                     this.m24();
                     String var6 = "";
                     int var7 = 0;

                     for (int var10000 = var7; var10000 < 11; var10000 = var7) {
                        StringBuilder var8 = new StringBuilder().insert(0, var6);
                        char var10001 = (char)this.f16[var7];
                        var7++;
                        var6 = var8.append(var10001).toString();
                     }

                     if (var6.equals("NETSCAPE2.0")) {
                        this.m4();
                     } else {
                        this.m20();
                     }
                     continue;
                  default:
                     this.m20();
                     continue;
               }
            case 44:
               this.m8();
               break;
            case 59:
               var4 = true;
               break;
            default:
               this.f33 = 1;
         }
      }
   }

   protected void m3() {
      byte var1 = -1;
      int var2 = this.f11 * this.f2;
      if (this.f36 == null || this.f36.length < var2) {
         byte[] var10001 = new byte[var2];
         boolean var10003 = true;
         this.f36 = var10001;
      }

      if (this.f7 == null) {
         short[] var27 = new short[4096];
         boolean var43 = true;
         this.f7 = var27;
      }

      if (this.f27 == null) {
         byte[] var28 = new byte[4096];
         boolean var44 = true;
         this.f27 = var28;
      }

      if (this.f21 == null) {
         byte[] var29 = new byte[4097];
         boolean var45 = true;
         this.f21 = var29;
      }

      int var15 = this.m7();
      int var4;
      int var7 = (var4 = 1 << var15) + 1;
      int var3 = var4 + 2;
      int var9 = var1;
      int var6 = var15 + 1;
      int var5 = (1 << var6) - 1;

      int var11;
      for (int var10000 = var11 = 0; var10000 < var4; var10000 = var11) {
         this.f7[var11] = 0;
         int var30 = var11;
         byte var10002 = (byte)var11;
         var11++;
         this.f27[var30] = var10002;
      }

      int var18 = 0;
      int var19 = 0;
      int var17 = 0;
      int var16 = 0;
      int var12 = 0;
      int var10 = 0;
      int var14 = 0;
      int var13;
      int var22 = var13 = 0;

      while (true) {
         label94:
         if (var22 < var2) {
            if (var17 == 0) {
               if (var10 < var6) {
                  if (var12 == 0) {
                     if ((var12 = this.m24()) <= 0) {
                        var22 = var19;
                        break;
                     }

                     var18 = 0;
                  }

                  int var35 = this.f16[var18] & 255;
                  int var42 = var10;
                  var10 += 8;
                  var35 <<= var42;
                  var12--;
                  var14 += var35;
                  var18++;
                  var22 = var13;
                  continue;
               }

               var11 = var14 & var5;
               var14 >>= var6;
               var10 -= var6;
               if (var11 > var3) {
                  break label94;
               }

               if (var11 == var7) {
                  var22 = var19;
                  break;
               }

               if (var11 == var4) {
                  var6 = var15 + 1;
                  var5 = (1 << var6) - 1;
                  var3 = var4 + 2;
                  var9 = var1;
                  var22 = var13;
                  continue;
               }

               if (var9 == var1) {
                  this.f21[var17++] = this.f27[var11];
                  var9 = var11;
                  var16 = var11;
                  var22 = var13;
                  continue;
               }

               int var8 = var11;
               if (var11 == var3) {
                  int var31 = var17;
                  byte var38 = (byte)var16;
                  var17++;
                  this.f21[var31] = var38;
                  var11 = var9;
               }

               for (int var23 = var11; var23 > var4; var23 = var11 = this.f7[var11]) {
                  this.f21[var17++] = this.f27[var11];
               }

               var16 = this.f27[var11] & 255;
               if (var3 >= 4096) {
                  var22 = var19;
                  break;
               }

               int var46 = var17;
               byte var10004 = (byte)var16;
               var17++;
               this.f21[var46] = var10004;
               this.f7[var3] = (short)var9;
               int var32 = var3;
               byte var40 = (byte)var16;
               var3++;
               this.f27[var32] = var40;
               if ((var3 & var5) == 0 && var3 < 4096) {
                  var22 = var5 + var3;
                  var6++;
                  var5 = var22;
               }

               var9 = var8;
            }

            var17--;
            int var33 = var19++;
            byte var41 = this.f21[var17];
            var13++;
            this.f36[var33] = var41;
            var22 = var13;
            continue;
         }

         var22 = var19;
         break;
      }

      var13 = var22;

      for (int var26 = var13; var26 < var2; var26 = var13) {
         this.f36[var13++] = 0;
      }
   }

   protected void m4() {
      do {
         this.m24();
         if (this.f16[0] == 1) {
            int var2 = this.f16[1] & 255;
            this.f13 = (this.f16[2] & 255) << 8 | var2;
         }
      } while (this.f26 > 0 && !this.m16());
   }

   protected void m5() {
      this.f33 = 0;
      this.f25 = 0;
      this.f18 = new ArrayList();
      this.f32 = null;
      this.f19 = null;
   }

   protected int m6() {
      return this.m7() | this.m7() << 8;
   }

   protected int m7() {
      byte var3 = 0;

      try {
         return this.f10.read();
      } catch (IOException var5) {
         this.f33 = 1;
         return var3;
      }
   }

   protected void m8() {
      this.f4 = this.m6();
      this.f15 = this.m6();
      this.f11 = this.m6();
      this.f2 = this.m6();
      int var2;
      this.f24 = ((var2 = this.m7()) & 128) != 0;
      this.f31 = (var2 & 64) != 0;
      this.f8 = 2 << (var2 & 7);
      if (this.f24) {
         this.f19 = this.m11(this.f8);
         this.f6 = this.f19;
      } else {
         this.f6 = this.f32;
         if (this.f9 == this.f3) {
            this.f5 = 0;
         }
      }

      var2 = 0;
      if (this.f22) {
         var2 = this.f6[this.f3];
         this.f6[this.f3] = 0;
      }

      if (this.f6 == null) {
         this.f33 = 1;
      }

      if (!this.m16()) {
         this.m3();
         this.m20();
         if (!this.m16()) {
            this.f25++;
            this.f17 = new BufferedImage(this.f34, this.f12, 3);
            this.m23();
            this.f18.add(new GifDecoder$GifFrame(this.f17, this.f1));
            if (this.f22) {
               this.f6[this.f3] = var2;
            }

            this.m15();
         }
      }
   }

   public BufferedImage m9(int var1) {
      BufferedImage var3 = null;
      if (var1 >= 0 && var1 < this.f25) {
         var3 = ((GifDecoder$GifFrame)this.f18.get(var1)).f1;
      }

      return var3;
   }

   protected void m10() {
      this.f34 = this.m6();
      this.f12 = this.m6();
      int var2;
      this.f29 = ((var2 = this.m7()) & 128) != 0;
      this.f37 = 2 << (var2 & 7);
      this.f9 = this.m7();
      this.f20 = this.m7();
   }

   protected int[] m11(int var1) {
      int var9 = 3 * var1;
      int[] var10 = null;
      byte[] var11 = new byte[var9];
      int var12 = 0;

      try {
         var12 = this.f10.read(var11);
      } catch (IOException var17) {
      }

      if (var12 < var9) {
         this.f33 = 1;
         return var10;
      } else {
         var10 = new int[256];
         int var14 = 0;
         var9 = 0;

         while (var14 < var1) {
            var10[var14++] = 0xFF000000
               | (var11[var9++] & 255) << 16
               | (var11[var9++] & 255) << 8
               | (var11[var9++] & 255);
         }

         return var10;
      }
   }

   public GifDecoder() {
      byte[] var10011 = new byte[256];
      boolean var10013 = true;
      this.f16 = var10011;
      this.f26 = 0;
      this.f35 = 0;
      this.f23 = 0;
      this.f22 = false;
      this.f1 = 0;
   }

   // $VF: Handled exception range with multiple entry points by splitting it
   // $VF: Duplicated exception handlers to handle obfuscated exceptions
   public int m12(InputStream var1) {
      this.m5();
      InputStream var2 = var1;
      if (var1 != null) {
         if (!(var1 instanceof BufferedInputStream)) {
            var2 = new BufferedInputStream(var1);
         }

         this.f10 = (BufferedInputStream)var2;
         this.m17();
         if (!this.m16()) {
            this.m2();
            if (this.f25 < 0) {
               this.f33 = 1;
            }
         }
      } else {
         this.f33 = 2;
      }

      try {
         var2.close();
      } catch (IOException var4) {
         return this.f33;
      }

      return this.f33;
   }

   public BufferedImage m13() {
      return this.m9(0);
   }

   public int m14(int var1) {
      this.f1 = -1;
      if (var1 >= 0 && var1 < this.f25) {
         this.f1 = ((GifDecoder$GifFrame)this.f18.get(var1)).f2;
      }

      return this.f1;
   }

   protected void m15() {
      this.f23 = this.f35;
      this.f28 = new Rectangle(this.f4, this.f15, this.f11, this.f2);
      this.f30 = this.f17;
      this.f14 = this.f5;
      boolean var2 = false;
      var2 = false;
      var2 = false;
      this.f19 = null;
   }

   protected boolean m16() {
      return this.f33 != 0;
   }

   protected void m17() {
      String var3 = "";

      int var4;
      for (int var10000 = var4 = 0; var10000 < 6; var10000 = var4) {
         StringBuilder var5 = new StringBuilder().insert(0, var3);
         char var10001 = (char)this.m7();
         var4++;
         var3 = var5.append(var10001).toString();
      }

      if (!var3.startsWith("GIF")) {
         this.f33 = 1;
      } else {
         this.m10();
         if (this.f29 && !this.m16()) {
            this.f32 = this.m11(this.f37);
            this.f5 = this.f32[this.f9];
         }
      }
   }

   public Dimension m18() {
      return new Dimension(this.f34, this.f12);
   }

   public int m19() {
      return this.f13;
   }

   protected void m20() {
      do {
         this.m24();
      } while (this.f26 > 0 && !this.m16());
   }

   public int m21() {
      return this.f25;
   }

   protected void m22() {
      this.m7();
      int var2;
      this.f35 = ((var2 = this.m7()) & 28) >> 2;
      if (this.f35 == 0) {
         this.f35 = 1;
      }

      this.f22 = (var2 & 1) != 0;
      this.f1 = this.m6() * 10;
      this.f3 = this.m7();
      this.m7();
   }

   protected void m23() {
      int[] var1 = ((DataBufferInt)this.f17.getRaster().getDataBuffer()).getData();
      if (this.f23 > 0) {
         GifDecoder var10000;
         label80: {
            if (this.f23 == 3) {
               int var2;
               if ((var2 = this.f25 - 2) > 0) {
                  var10000 = this;
                  this.f30 = this.m9(var2 - 1);
                  break label80;
               }

               this.f30 = null;
            }

            var10000 = this;
         }

         if (var10000.f30 != null) {
            int[] var10 = ((DataBufferInt)this.f30.getRaster().getDataBuffer()).getData();
            System.arraycopy(var10, 0, var1, 0, this.f34 * this.f12);
            if (this.f23 == 2) {
               Graphics2D var3 = this.f17.createGraphics();
               Object var4 = null;
               Graphics2D var19;
               if (this.f22) {
                  var4 = new Color(0, 0, 0, 0);
                  var19 = var3;
               } else {
                  var4 = new Color(this.f14);
                  var19 = var3;
               }

               var19.setColor((Color)var4);
               var3.setComposite(AlphaComposite.Src);
               var3.fill(this.f28);
               var3.dispose();
            }
         }
      }

      int var11 = 1;
      byte var12 = 8;
      byte var14 = 0;

      int var5;
      for (int var20 = var5 = 0; var20 < this.f2; var20 = ++var5) {
         int var6 = var5;
         if (this.f31) {
            if (var14 >= this.f2) {
               var11++;
               switch (var11) {
                  case 2:
                     var14 = 4;
                     break;
                  case 3:
                     var14 = 2;
                     var12 = 4;
                     break;
                  case 4:
                     var14 = 1;
                     var12 = 2;
               }
            }

            var6 = var14;
            var14 += var12;
         }

         if ((var6 = var6 + this.f15) < this.f12) {
            int var7;
            int var16;
            int var8 = (var7 = (var16 = var6 * this.f34) + this.f4) + this.f11;
            if (var16 + this.f34 < var8) {
               var8 = var16 + this.f34;
            }

            var6 = var5 * this.f11;

            for (int var22 = var7; var22 < var8; var22 = ++var7) {
               int var10001 = this.f36[var6] & 255;
               var6++;
               int var9 = var10001;
               if ((var9 = this.f6[var9]) != 0) {
                  var1[var7] = var9;
               }
            }
         }
      }
   }

   protected int m24() {
      this.f26 = this.m7();
      int var3 = 0;
      if (this.f26 > 0) {
         try {
            int var4 = 0;

            while (var3 < this.f26 && (var4 = this.f10.read(this.f16, var3, this.f26 - var3)) != -1) {
               var3 += var4;
            }
         } catch (IOException var6) {
         }

         if (var3 < this.f26) {
            this.f33 = 1;
         }
      }

      return var3;
   }

   public int m25(String var1) {
      this.f33 = 0;

      try {
         GifDecoder var7;
         if ((var1 = var1.trim().toLowerCase()).indexOf("file:") < 0 && var1.indexOf(":/") <= 0) {
            var7 = this;
            this.f10 = new BufferedInputStream(new FileInputStream(var1));
         } else {
            URL var3 = new URL(var1);
            var7 = this;
            this.f10 = new BufferedInputStream(var3.openStream());
         }

         var7.f33 = this.m1(this.f10);
      } catch (IOException var5) {
         this.f33 = 2;
         return this.f33;
      }

      return this.f33;
   }
}

