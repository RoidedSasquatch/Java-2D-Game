package entities.objects.equipables;

import entities.objects.ObjectTypes;
import utils.UtilityMethods;
import views.GameScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static utils.Constants.GAME_TILE_SIZE;
import static utils.Constants.SCALE;

public class Wood_Axe extends WeaponObject {

    public Wood_Axe(GameScreen gameScreen) {
        super(gameScreen);
        name = "Wooden Axe";
        damage = 4;
        durability = 150;
        description = "[ " + name + " ]\nA wooden axe, use it to get more wood.";
        type = ObjectTypes.AXE;

        width = 15 * SCALE;
        height = 15 * SCALE;
        collisionArea = new Rectangle(0, 0, width, height);
        attackCollision.width = 28;
        attackCollision.height = 28;

        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/wood_axe.png"));
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
