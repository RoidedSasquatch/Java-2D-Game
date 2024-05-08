package entities.objects.equipables;

import entities.objects.ObjectTypes;
import entities.objects.projectiles.Fireball;
import utils.UtilityMethods;
import views.GameScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static utils.Constants.GAME_TILE_SIZE;
import static utils.Constants.SCALE;

public class ApprenticeSpellbook extends WeaponObject {
    public ApprenticeSpellbook(GameScreen gameScreen) {
        super(gameScreen);
        name = "Apprentice Spellbook";
        durability = 150;
        description = "[ " + name + " ]\nA magical book containing basic incantations.";
        type = ObjectTypes.SPELLBOOK;
        equippedSpell = new Fireball(gameScreen);

        width = 15 * SCALE;
        height = 15 * SCALE;
        collisionArea = new Rectangle(0, 0, width, height);

        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/apprentice_spellbook.png"));
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
