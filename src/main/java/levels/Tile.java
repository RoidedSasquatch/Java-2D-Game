package levels;

import java.awt.image.BufferedImage;

public class Tile {
    private BufferedImage image;
    private boolean collision;

    public Tile() {
        collision = false;
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean isCollision() {
        return collision;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public void setCollision(boolean collision) {
        this.collision = collision;
    }
}
