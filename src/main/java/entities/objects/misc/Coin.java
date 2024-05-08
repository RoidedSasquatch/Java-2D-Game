package entities.objects.misc;

import entities.Entity;
import entities.objects.GameObject;
import entities.objects.ObjectTypes;
import utils.UtilityMethods;
import views.GameScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static utils.Constants.SCALE;

public class Coin extends MiscObject {
    public Coin(GameScreen gameScreen) {
        super(gameScreen);
        name = "Coin";
        type = ObjectTypes.PICKUP;
        value = 1;

        width = 15 * SCALE;
        height = 15 * SCALE;
        collisionArea = new Rectangle(0, 0, width, height);

        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/coin.png"));
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
