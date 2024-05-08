package entities.characters.enemies;

import entities.Entity;
import entities.characters.AICharacter;
import views.GameScreen;

public abstract class Enemy extends AICharacter {
    public abstract void resetEntity();
    public Enemy(GameScreen gameScreen) {
        super(gameScreen);
    }
}
