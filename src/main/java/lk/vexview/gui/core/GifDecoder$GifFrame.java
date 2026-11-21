package lk.vexview.gui.core;

import java.awt.image.BufferedImage;

/**
 * One decoded GIF frame: the image plus its delay in milliseconds.
 *
 * In the original binary this class was named {@code GifDecoder$GifFrame}; the
 * "$" is kept so the binary name (and therefore every reference from
 * {@link GifDecoder}) stays identical to the shipped mod.
 */
public class GifDecoder$GifFrame {
    public BufferedImage f1;
    public int f2;

    public GifDecoder$GifFrame(BufferedImage image, int delay) {
        this.f1 = image;
        this.f2 = delay;
    }
}
