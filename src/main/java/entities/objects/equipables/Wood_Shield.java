package entities.objects.equipables;

import entities.objects.ObjectTypes;
import utils.UtilityMethods;
import views.GameScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static utils.Constants.GAME_TILE_SIZE;
import static utils.Constants.SCALE;

public class Wood_Shield extends WearableObject {
    public Wood_Shield(GameScreen gameScreen) {
        super(gameScreen);
        name = "Wooden Shield";
        blockProtection = 1;
        durability = 150;
        description = "[ " + name + " ]\nA rickety wooden shield, may give splinters.";
        type = ObjectTypes.SHIELD;

        width = 15 * SCALE;
        height = 15 * SCALE;
        collisionArea = new Rectangle(0, 0, width, height);

        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/wood_shield.png"));
            image = UtilityMethods.scaleImage(image, width, height);

        } catch(IOException e) {
            System.out.println("Could not load image.");
        }
    }

    @Override
    public void update() {

    }

    @Override
    protected void setStateAnimation() {

    }
}
