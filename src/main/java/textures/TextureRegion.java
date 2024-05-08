package textures;

import java.awt.image.BufferedImage;

import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.core.*;

public class TextureRegion {

    private String filename;


    private int x;
    private int y;
    private int w;
    private int h;
    private BufferedImage texture;

    public TextureRegion(int x, int y, int w, int h) {

    }

    public String getFileName() {
        return filename;
    }

    public void setFileName(String filename) {
        this.filename = filename;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }
}
