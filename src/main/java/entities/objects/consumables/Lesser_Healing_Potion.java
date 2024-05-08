package entities.objects.consumables;

import entities.Entity;
import entities.characters.Character;
import utils.UtilityMethods;
import views.GameScreen;
import views.GameState;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

import static utils.Constants.GAME_TILE_SIZE;
import static utils.Constants.SCALE;

public class Lesser_Healing_Potion extends ConsumableObject {
    private GameScreen gameScreen;

    public Lesser_Healing_Potion(GameScreen gameScreen) {
        super(gameScreen);
        this.gameScreen = gameScreen;
        name = "Lesser Health Potion";
        description = "[ " + name + " ]\nA basic health potion, won't heal more than a scratch.";
        healingAmount = 5;

        width = 15 * SCALE;
        height = 15 * SCALE;
        collisionArea = new Rectangle(0, 0, width, height);

        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/healing_potion.png"));
            image = UtilityMethods.scaleImage(image, width, height);

        } catch(IOException e) {
            System.out.println("Could not load image.");
        }
    }

    @Override
    public void Consume(Entity entity) {
        gameScreen.setGameState(GameState.DIALOGUESTATE);
        gameScreen.getGui().setCurrentDialogue("Used " + name + ". Recovered " + healingAmount + " health.");
        ((Character)entity).setCurrentHealth(((Character)entity).getCurrentHealth() + healingAmount);
        if(gameScreen.getPlayer().getCurrentHealth() > gameScreen.getPlayer().getMaxHealth()) {
            gameScreen.getPlayer().setCurrentHealth(gameScreen.getPlayer().getMaxHealth());
        }
    }

    @Override
    public void update() {

    }

    @Override
    protected void setStateAnimation() {

    }
}
