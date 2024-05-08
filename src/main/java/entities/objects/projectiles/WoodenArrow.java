package entities.objects.projectiles;

import entities.Entity;
import entities.characters.Character;
import inputs.Directions;
import utils.UtilityMethods;
import views.GameScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static utils.Constants.GAME_TILE_SIZE;
import static utils.Constants.SCALE;

public class WoodenArrow extends ProjectileObject {
    private final GameScreen gameScreen;

    public WoodenArrow(GameScreen gameScreen) {
        super(gameScreen);
        this.gameScreen = gameScreen;

        name = "Wooden Arrow";
        speed = 8;
        cooldown = 70;
        projectileDamage = 7;
        range = 80;
        currentRange = range;
        ammoCost = 1;
        active = false;

        width = 15 * SCALE;
        height = 15 * SCALE;
        collisionArea = new Rectangle(0, 0, width, height);

        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/wood_sword.png"));
            image = UtilityMethods.scaleImage(image, width, height);

        } catch(IOException e) {
            System.out.println("Could not load image.");
        }
    }

    @Override
    protected void setStateAnimation() {

    }

    @Override
    public boolean haveResources(Entity user) {
        return ((Character) user).getCurrentAmmo() >= ammoCost;
    }

    @Override
    public void subtractResources(Entity user) {
        ((Character)user).setCurrentMana(((Character)user).getCurrentAmmo() - ammoCost);
    }

    @Override
    public Color getParticleColor() {
        return new Color(152, 0 , 2);
    }
}
