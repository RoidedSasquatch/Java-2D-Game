package utils;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.GAME_TILE_SIZE;

public class UtilityMethods {

    public static BufferedImage scaleImage(BufferedImage image, int width, int height) {
        BufferedImage scaled = new BufferedImage(width, height, 2);
        Graphics2D graphics2D = scaled.createGraphics();
        graphics2D.drawImage(image, 0, 0, width, height, null);
        graphics2D.dispose();
        return scaled;
    }
}
