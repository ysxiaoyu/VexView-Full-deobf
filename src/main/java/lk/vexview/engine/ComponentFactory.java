/*
 * Decompiled with CFR 0.152.
 */
package lk.vexview.engine;

import lk.vexview.engine.collection.HoverTextElementCollection;
import lk.vexview.engine.collection.ShowElementCollection;
import lk.vexview.engine.collection.TagElementCollection;
import lk.vexview.gui.core.VexBase64Image;
import lk.vexview.gui.core.VexButton;
import lk.vexview.gui.core.VexCheckBox;
import lk.vexview.gui.core.VexComponent;
import lk.vexview.gui.core.VexEntityDraw;
import lk.vexview.gui.core.VexGifImage;
import lk.vexview.gui.core.VexImage;
import lk.vexview.gui.core.VexMcImage;
import lk.vexview.gui.core.VexPlayerDraw;
import lk.vexview.gui.core.VexSlot;
import lk.vexview.gui.core.VexText;
import lk.vexview.gui.core.VexTextArea;
import lk.vexview.gui.core.VexTextBox;
import lk.vexview.hud.core.VexBase64ImageShow;
import lk.vexview.hud.core.VexButtonShow;
import lk.vexview.hud.core.VexEntityDrawShow;
import lk.vexview.hud.core.VexImageShow;
import lk.vexview.hud.core.VexMcImageShow;
import lk.vexview.hud.core.VexPlayerDrawShow;
import lk.vexview.hud.core.VexShow;
import lk.vexview.hud.core.VexSplitImageShow;
import lk.vexview.hud.core.VexTextShow;
import lk.vexview.tag.core.ImageGifTag;
import lk.vexview.tag.core.ImageTag;
import lk.vexview.tag.core.McImageTag;
import lk.vexview.tag.core.SpiltImageTag;
import lk.vexview.tag.core.TextTag;
import lk.vexview.tag.core.VexEntityDrawTag;
import lk.vexview.tag.core.VexTag;

public class ComponentFactory {
    public static VexComponent m1(HoverTextElementCollection hoverTextElementCollection, String string) {
        HoverTextElementCollection hoverTextElementCollection2 = hoverTextElementCollection;
        hoverTextElementCollection2.m2(string);
        return ComponentFactory.m4(hoverTextElementCollection2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static VexShow m2(ShowElementCollection showElementCollection) {
        int n;
        block20: {
            String string = showElementCollection.m10();
            int n2 = -1;
            switch (string.hashCode()) {
                case 0x220252: {
                    if (!string.equals("HUDI")) break;
                    n = n2 = 0;
                    break block20;
                }
                case 2228829: {
                    if (!string.equals("HUDT")) break;
                    n = n2 = 1;
                    break block20;
                }
                case 78881061: {
                    if (!string.equals("SHUDI")) break;
                    n = n2 = 2;
                    break block20;
                }
                case 73339935: {
                    if (!string.equals("MHUDI")) break;
                    n = n2 = 3;
                    break block20;
                }
                case 69093302: {
                    if (!string.equals("HUDED")) break;
                    n = n2 = 4;
                    break block20;
                }
                case 69093643: {
                    if (!string.equals("HUDPD")) break;
                    n = n2 = 5;
                    break block20;
                }
                case 69093214: {
                    if (!string.equals("HUDBI")) break;
                    n = n2 = 6;
                    break block20;
                }
                case 2228811: {
                    if (!string.equals("HUDB")) break;
                    n2 = 7;
                }
            }
            n = n2;
        }
        switch (n) {
            case 0: {
                return new VexImageShow(showElementCollection.m8(0), showElementCollection.m1(), showElementCollection.m3(1), showElementCollection.m3(2), showElementCollection.m3(3), showElementCollection.m3(4), showElementCollection.m3(7), showElementCollection.m1(8), showElementCollection.m3(9), showElementCollection.m3(10)).m2(showElementCollection.m2()).m1(showElementCollection.m4());
            }
            case 1: {
                return new VexTextShow(showElementCollection.m1(), showElementCollection.m3(0), showElementCollection.m3(1), showElementCollection.m4(2), showElementCollection.m3(4), showElementCollection.m6(), showElementCollection.m3(3)).m2(showElementCollection.m2()).m1(showElementCollection.m4());
            }
            case 2: {
                return new VexSplitImageShow(showElementCollection.m8(0), showElementCollection.m1(), showElementCollection.m3(1), showElementCollection.m3(2), showElementCollection.m3(3), showElementCollection.m3(4), showElementCollection.m3(5), showElementCollection.m3(6), showElementCollection.m3(7), showElementCollection.m3(8), showElementCollection.m3(9), showElementCollection.m3(10), showElementCollection.m3(11), showElementCollection.m3(12)).m2(showElementCollection.m2()).m1(showElementCollection.m4());
            }
            case 3: {
                return new VexMcImageShow(showElementCollection.m8(0), showElementCollection.m1(), showElementCollection.m3(1), showElementCollection.m3(2), showElementCollection.m3(3), showElementCollection.m3(4), showElementCollection.m3(5), showElementCollection.m3(6), showElementCollection.m3(7), showElementCollection.m3(8), showElementCollection.m3(9), showElementCollection.m3(10), showElementCollection.m3(11), showElementCollection.m3(12)).m2(showElementCollection.m2()).m1(showElementCollection.m4());
            }
            case 4: {
                return new VexEntityDrawShow(showElementCollection.m8(0), showElementCollection.m3(1), showElementCollection.m3(2), showElementCollection.m1(3), showElementCollection.m3(4), showElementCollection.m3(5), showElementCollection.m3(6), showElementCollection.m1(7), showElementCollection.m8(8), showElementCollection.m8(9)).m2(showElementCollection.m2()).m1(showElementCollection.m4());
            }
            case 5: {
                return new VexPlayerDrawShow(showElementCollection.m8(0), showElementCollection.m3(1), showElementCollection.m3(2), showElementCollection.m1(3), showElementCollection.m3(4), showElementCollection.m3(5), showElementCollection.m3(6), showElementCollection.m1(7), showElementCollection.m8(8), showElementCollection.m8(9), showElementCollection.m8(10)).m2(showElementCollection.m2()).m1(showElementCollection.m4());
            }
            case 6: {
                return new VexBase64ImageShow(showElementCollection.m1(), showElementCollection.m3(0), showElementCollection.m3(1), showElementCollection.m3(2), showElementCollection.m3(3), showElementCollection.m3(4), showElementCollection.m3(5)).m2(showElementCollection.m2()).m1(showElementCollection.m4());
            }
            case 7: {
                return new VexButtonShow(showElementCollection.m8(0), showElementCollection.m3(1), showElementCollection.m3(2), showElementCollection.m3(3), showElementCollection.m3(4), showElementCollection.m8(5), showElementCollection.m8(6), showElementCollection.m8(7), showElementCollection.m8(8), showElementCollection.m3(9), showElementCollection.m3(10)).m1(showElementCollection.m3()).m2(showElementCollection.m2()).m1(showElementCollection.m4());
            }
        }
        System.out.println(new StringBuilder().insert(0, "Error HUD type:").append(showElementCollection.m10()).toString());
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static VexTag m3(TagElementCollection tagElementCollection) {
        int n;
        block16: {
            String string = tagElementCollection.m10();
            int n2 = -1;
            switch (string.hashCode()) {
                case 100313435: {
                    if (!string.equals("image")) break;
                    n = n2 = 0;
                    break block16;
                }
                case 3556653: {
                    if (!string.equals("text")) break;
                    n = n2 = 1;
                    break block16;
                }
                case 839930903: {
                    if (!string.equals("gifimage")) break;
                    n = n2 = 2;
                    break block16;
                }
                case 1398249895: {
                    if (!string.equals("spiltimage")) break;
                    n = n2 = 3;
                    break block16;
                }
                case 888252805: {
                    if (!string.equals("mcimage")) break;
                    n = n2 = 4;
                    break block16;
                }
                case -1482528857: {
                    if (!string.equals("entitydraw")) break;
                    n2 = 5;
                }
            }
            n = n2;
        }
        switch (n) {
            case 0: {
                return new ImageTag(tagElementCollection.m8(2), tagElementCollection.m4(3), tagElementCollection.m4(4), tagElementCollection.m4(5), tagElementCollection.m3(6), tagElementCollection.m3(7), tagElementCollection.m5(8), tagElementCollection.m5(9), tagElementCollection.m5(10), tagElementCollection.m5(11), tagElementCollection.m5(12), tagElementCollection.m1(13), tagElementCollection.m1(14));
            }
            case 1: {
                return new TextTag(tagElementCollection.m4(2), tagElementCollection.m4(3), tagElementCollection.m4(4), tagElementCollection.m5(7), tagElementCollection.m5(8), tagElementCollection.m5(9), tagElementCollection.m1(10), tagElementCollection.m1(11), tagElementCollection.m1(6), tagElementCollection.m8(5));
            }
            case 2: {
                return new ImageGifTag(tagElementCollection.m8(2), tagElementCollection.m4(3), tagElementCollection.m4(4), tagElementCollection.m4(5), tagElementCollection.m3(6), tagElementCollection.m3(9), tagElementCollection.m5(7), tagElementCollection.m5(8), tagElementCollection.m5(10), tagElementCollection.m5(11), tagElementCollection.m5(12), tagElementCollection.m1(13), tagElementCollection.m1(14));
            }
            case 3: {
                return new SpiltImageTag(tagElementCollection.m8(2), tagElementCollection.m4(3), tagElementCollection.m4(4), tagElementCollection.m4(5), tagElementCollection.m3(6), tagElementCollection.m3(7), tagElementCollection.m5(12), tagElementCollection.m5(13), tagElementCollection.m3(8), tagElementCollection.m3(9), tagElementCollection.m3(10), tagElementCollection.m3(11), tagElementCollection.m5(14), tagElementCollection.m5(15), tagElementCollection.m5(16), tagElementCollection.m1(17), tagElementCollection.m1(18));
            }
            case 4: {
                return new McImageTag(tagElementCollection.m8(2), tagElementCollection.m4(3), tagElementCollection.m4(4), tagElementCollection.m4(5), tagElementCollection.m3(6), tagElementCollection.m3(7), tagElementCollection.m5(12), tagElementCollection.m5(13), tagElementCollection.m3(8), tagElementCollection.m3(9), tagElementCollection.m3(10), tagElementCollection.m3(11), tagElementCollection.m5(14), tagElementCollection.m5(15), tagElementCollection.m5(16), tagElementCollection.m1(17), tagElementCollection.m1(18));
            }
            case 5: {
                return new VexEntityDrawTag(tagElementCollection.m4(2), tagElementCollection.m4(3), tagElementCollection.m4(4), tagElementCollection.m5(5), tagElementCollection.m5(6), tagElementCollection.m5(7), tagElementCollection.m1(8), tagElementCollection.m1(9), tagElementCollection.m3(12), tagElementCollection.m1(13), tagElementCollection.m8(14), tagElementCollection.m8(15));
            }
        }
        System.out.println(new StringBuilder().insert(0, "Error Tag type:").append(tagElementCollection.m10()).toString());
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    public static VexComponent m4(HoverTextElementCollection hoverTextElementCollection) {
        int n;
        block37: {
            String string = hoverTextElementCollection.m10();
            int n2 = -1;
            switch (string.hashCode()) {
                case 104387: {
                    if (!string.equals("img")) break;
                    n = n2 = 0;
                    break block37;
                }
                case 3172860: {
                    if (!string.equals("gimg")) break;
                    n = n2 = 1;
                    break block37;
                }
                case 3023905: {
                    if (!string.equals("bimg")) break;
                    n = n2 = 2;
                    break block37;
                }
                case 3236112: {
                    if (!string.equals("imgs")) break;
                    n = n2 = 3;
                    break block37;
                }
                case 3236117: {
                    if (!string.equals("imgx")) break;
                    n = n2 = 4;
                    break block37;
                }
                case 97921: {
                    if (!string.equals("but")) break;
                    n = n2 = 5;
                    break block37;
                }
                case 115312: {
                    if (!string.equals("txt")) break;
                    n = n2 = 6;
                    break block37;
                }
                case 3698: {
                    if (!string.equals("tf")) break;
                    n = n2 = 7;
                    break block37;
                }
                case 110846: {
                    if (!string.equals("pdr")) break;
                    n = n2 = 8;
                    break block37;
                }
                case 100275: {
                    if (!string.equals("edr")) break;
                    n = n2 = 9;
                    break block37;
                }
                case 3047230: {
                    if (!string.equals("cbut")) break;
                    n = n2 = 10;
                    break block37;
                }
                case 98837: {
                    if (!string.equals("ctf")) break;
                    n = n2 = 11;
                    break block37;
                }
                case 3693: {
                    if (!string.equals("ta")) break;
                    n = n2 = 12;
                    break block37;
                }
                case 3167: {
                    if (!string.equals("cb")) break;
                    n = n2 = 13;
                    break block37;
                }
                case 113974: {
                    if (!string.equals("slo")) break;
                    n2 = 14;
                }
            }
            n = n2;
        }
        switch (n) {
            case 0: {
                return new VexImage(hoverTextElementCollection.m3(1), hoverTextElementCollection.m3(2), hoverTextElementCollection.m3(3), hoverTextElementCollection.m3(4), hoverTextElementCollection.m3(5), hoverTextElementCollection.m3(6), hoverTextElementCollection.m8(0)).m1(hoverTextElementCollection.m1());
            }
            case 1: {
                return new VexGifImage(hoverTextElementCollection.m3(1), hoverTextElementCollection.m3(2), hoverTextElementCollection.m3(3), hoverTextElementCollection.m3(4), hoverTextElementCollection.m3(5), hoverTextElementCollection.m3(6), hoverTextElementCollection.m8(0), hoverTextElementCollection.m3(7)).m1(hoverTextElementCollection.m1());
            }
            case 2: {
                return new VexBase64Image(hoverTextElementCollection.m3(0), hoverTextElementCollection.m3(1), hoverTextElementCollection.m3(2), hoverTextElementCollection.m3(3), hoverTextElementCollection.m8(4)).m1(hoverTextElementCollection.m1());
            }
            case 3: {
                return new VexImage(hoverTextElementCollection.m3(1), hoverTextElementCollection.m3(2), hoverTextElementCollection.m3(3), hoverTextElementCollection.m3(4), hoverTextElementCollection.m3(5), hoverTextElementCollection.m3(6), hoverTextElementCollection.m8(0), hoverTextElementCollection.m3(7), hoverTextElementCollection.m3(8), hoverTextElementCollection.m3(9), hoverTextElementCollection.m3(10)).m1(hoverTextElementCollection.m1());
            }
            case 4: {
                return new VexMcImage(hoverTextElementCollection.m3(1), hoverTextElementCollection.m3(2), hoverTextElementCollection.m3(3), hoverTextElementCollection.m3(4), hoverTextElementCollection.m3(5), hoverTextElementCollection.m3(6), hoverTextElementCollection.m8(0), hoverTextElementCollection.m3(7), hoverTextElementCollection.m3(8), hoverTextElementCollection.m3(9), hoverTextElementCollection.m3(10)).m1(hoverTextElementCollection.m1());
            }
            case 5: {
                return new VexButton(hoverTextElementCollection.m3(6), hoverTextElementCollection.m3(1), hoverTextElementCollection.m3(2), hoverTextElementCollection.m3(3), hoverTextElementCollection.m3(4), hoverTextElementCollection.m8(5), hoverTextElementCollection.m8(7), hoverTextElementCollection.m8(0), hoverTextElementCollection.m8(8)).m1(hoverTextElementCollection.m1());
            }
            case 6: {
                return new VexText(hoverTextElementCollection.m3(0), hoverTextElementCollection.m3(1), hoverTextElementCollection.m4(2), hoverTextElementCollection.m9(3)).m1(hoverTextElementCollection.m1());
            }
            case 7: {
                String string;
                int n3 = hoverTextElementCollection.m3(0);
                int n4 = hoverTextElementCollection.m3(1);
                int n5 = hoverTextElementCollection.m3(2);
                int n6 = hoverTextElementCollection.m3(3);
                int n7 = hoverTextElementCollection.m3(4);
                int n8 = hoverTextElementCollection.m3(5);
                if (hoverTextElementCollection.m2() >= 6) {
                    string = hoverTextElementCollection.m8(6);
                    return new VexTextBox(n3, n4, n5, n6, n7, n8, string).m1(hoverTextElementCollection.m1());
                }
                string = "";
                return new VexTextBox(n3, n4, n5, n6, n7, n8, string).m1(hoverTextElementCollection.m1());
            }
            case 8: {
                return new VexPlayerDraw(hoverTextElementCollection.m3(0), hoverTextElementCollection.m3(1), hoverTextElementCollection.m3(2), hoverTextElementCollection.m1(3), hoverTextElementCollection.m8(4), hoverTextElementCollection.m8(5), hoverTextElementCollection.m8(6)).m1(hoverTextElementCollection.m1());
            }
            case 9: {
                return new VexEntityDraw(hoverTextElementCollection.m3(0), hoverTextElementCollection.m3(1), hoverTextElementCollection.m3(2), hoverTextElementCollection.m1(3), hoverTextElementCollection.m8(4), hoverTextElementCollection.m8(5)).m1(hoverTextElementCollection.m1());
            }
            case 10: {
                return new VexButton(hoverTextElementCollection.m3(6), hoverTextElementCollection.m3(1), hoverTextElementCollection.m3(2), hoverTextElementCollection.m3(3), hoverTextElementCollection.m3(4), hoverTextElementCollection.m8(5), hoverTextElementCollection.m8(7), hoverTextElementCollection.m8(8), hoverTextElementCollection.m8(0), hoverTextElementCollection.m8(9), hoverTextElementCollection.m1(10)).m1(hoverTextElementCollection.m1());
            }
            case 11: {
                String string;
                int n9 = hoverTextElementCollection.m3(0);
                int n10 = hoverTextElementCollection.m3(1);
                int n11 = hoverTextElementCollection.m3(2);
                int n12 = hoverTextElementCollection.m3(3);
                int n13 = hoverTextElementCollection.m3(4);
                int n14 = hoverTextElementCollection.m3(5);
                int n15 = hoverTextElementCollection.m3(6);
                int n16 = hoverTextElementCollection.m3(7);
                if (hoverTextElementCollection.m2() >= 8) {
                    string = hoverTextElementCollection.m8(8);
                    return new VexTextBox(n9, n10, n11, n12, n13, n14, n15, n16, string).m1(hoverTextElementCollection.m1());
                }
                string = "";
                return new VexTextBox(n9, n10, n11, n12, n13, n14, n15, n16, string).m1(hoverTextElementCollection.m1());
            }
            case 12: {
                String string;
                int n17 = hoverTextElementCollection.m3(0);
                int n18 = hoverTextElementCollection.m3(1);
                int n19 = hoverTextElementCollection.m3(2);
                int n20 = hoverTextElementCollection.m3(3);
                int n21 = hoverTextElementCollection.m3(4);
                int n22 = hoverTextElementCollection.m3(5);
                int n23 = hoverTextElementCollection.m3(6);
                int n24 = hoverTextElementCollection.m3(7);
                if (hoverTextElementCollection.m2() >= 8) {
                    string = hoverTextElementCollection.m8(8);
                    return new VexTextArea(n17, n18, n19, n20, n21, n22, n23, n24, string).m1(hoverTextElementCollection.m1());
                }
                string = "";
                return new VexTextArea(n17, n18, n19, n20, n21, n22, n23, n24, string).m1(hoverTextElementCollection.m1());
            }
            case 13: {
                return new VexCheckBox(hoverTextElementCollection.m3(5), hoverTextElementCollection.m8(6), hoverTextElementCollection.m8(7), hoverTextElementCollection.m3(1), hoverTextElementCollection.m3(2), hoverTextElementCollection.m3(3), hoverTextElementCollection.m3(4), hoverTextElementCollection.m1(0)).m1(hoverTextElementCollection.m1());
            }
            case 14: {
                return new VexSlot(hoverTextElementCollection.m3(0), hoverTextElementCollection.m3(1), hoverTextElementCollection.m3(2)).m3(hoverTextElementCollection.m8(3));
            }
        }
        System.out.println(new StringBuilder().insert(0, "Error Component type:").append(hoverTextElementCollection.m10()).toString());
        return null;
    }
}

