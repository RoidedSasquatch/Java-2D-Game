package entities.objects.equipables;

import entities.objects.ObjectTypes;
import utils.UtilityMethods;
import views.GameScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static utils.Constants.GAME_TILE_SIZE;
import static utils.Constants.SCALE;

public class Wood_Armor extends WearableObject {

    public Wood_Armor(GameScreen gameScreen) {
        super(gameScreen);
        name = "Wooden Armor";
        blockProtection = 1;
        durability = 150;
        description = "[ " + name + " ]\nA set of wooden armor. That can't be comfortable.";
        type = ObjectTypes.ARMOR;

        width = 15 * SCALE;
        height = 15 * SCALE;
        collisionArea = new Rectangle(0, 0, width, height);

        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/wood_armor.png"));
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
