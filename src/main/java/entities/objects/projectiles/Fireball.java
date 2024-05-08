package entities.objects.projectiles;

import entities.Entity;
import entities.characters.AICharacter;
import entities.characters.Character;
import inputs.Directions;
import utils.UtilityMethods;
import views.GameScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

import static utils.Constants.GAME_TILE_SIZE;
import static utils.Constants.SCALE;

public class Fireball extends ProjectileObject {
    GameScreen gameScreen;
    public Fireball(GameScreen gameScreen) {
        super(gameScreen);
        this.gameScreen = gameScreen;

        name = "Fireball";
        speed = 5;
        cooldown = 70;
        projectileDamage = 5;
        range = 80;
        currentRange = range;
        manaCost = 5;
        active = false;

        width = 15 * SCALE;
        height = 15 * SCALE;
        collisionArea = new Rectangle(0, 0, width, height);

        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/green_jelly.png"));
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
        return ((Character) user).getCurrentMana() >= manaCost;
    }

    @Override
    public void subtractResources(Entity user) {
        ((Character)user).setCurrentMana(((Character)user).getCurrentMana() - manaCost);
    }
}
