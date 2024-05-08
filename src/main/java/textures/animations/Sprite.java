package textures.animations;

import utils.UtilityMethods;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Sprite {
    private BufferedImage spriteSheet;
    private int spriteWidth;
    private int spriteHeight;

    public BufferedImage loadSheet(String filePath, int spriteWidth, int spriteHeight) {
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        spriteSheet = null;

        try {
            spriteSheet = ImageIO.read(getClass().getClassLoader().getResourceAsStream(filePath));
        } catch(IOException e) {
            System.err.println("Could not load sprite sheet. Check file path. Error:" + e);
        }
        return spriteSheet;
    }

    public BufferedImage getSprite(int xGrid, int yGrid, int width, int height, int scale) {
        BufferedImage animation;
        if (spriteSheet == null) {
            spriteSheet = loadSheet("player_sprites", 64, 64);
        }

        animation = spriteSheet.getSubimage(xGrid * spriteWidth, yGrid * spriteHeight, spriteWidth, spriteHeight);
        animation = UtilityMethods.scaleImage(animation, width * scale, height * scale);
        return  animation;
    }

    //TEMP DELETE ONCE JSON PARSER WRITTEN
    public BufferedImage[] getSprite(int sheetRow, int animationLength, int animationScale) {
        BufferedImage[] animation = new BufferedImage[animationLength];
        if(spriteSheet == null) {
            spriteSheet = loadSheet("entities/player/player_sprites.png", 64, 64);
        }
        for(int i = 0 ; i < animationLength ; i++) {
            animation[i] = spriteSheet.getSubimage(i * spriteWidth, sheetRow * spriteHeight, spriteWidth, spriteHeight);
            animation[i] = UtilityMethods.scaleImage(animation[i], spriteWidth * animationScale, spriteHeight * animationScale);
        }
        return animation;
    }
}
