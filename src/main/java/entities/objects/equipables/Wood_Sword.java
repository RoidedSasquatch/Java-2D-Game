package entities.objects.equipables;

import entities.objects.ObjectTypes;
import utils.UtilityMethods;
import views.GameScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static utils.Constants.GAME_TILE_SIZE;
import static utils.Constants.SCALE;

public class Wood_Sword extends WeaponObject {
    public Wood_Sword(GameScreen gameScreen) {
        super(gameScreen);
        name = "Wooden Sword";
        damage = 5;
        durability = 150;
        description = "[ " + name + " ]\nA shoddily crafted sword, made of foraged wood.";
        type = ObjectTypes.SWORD;

        width = 15 * SCALE;
        height = 15 * SCALE;
        collisionArea = new Rectangle(0, 0, width, height);
        attackCollision.width = 34;
        attackCollision.height = 34;

        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/wood_sword.png"));
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
