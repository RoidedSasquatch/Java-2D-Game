package entities.objects.crafting;

import utils.UtilityMethods;
import views.GameScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static utils.Constants.SCALE;

public class Wood extends CraftingObject {
    public Wood(GameScreen gameScreen) {
        super(gameScreen);
        name = "Wood";
        value = 5;
        description = "[ " + name + " ]\nWood obtained from cutting down a tree.";

        width = 15 * SCALE;
        height = 15 * SCALE;
        collisionArea = new Rectangle(0, 0, width, height);
        attackCollision.width = 34;
        attackCollision.height = 34;

        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/green_jelly.png"));
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
