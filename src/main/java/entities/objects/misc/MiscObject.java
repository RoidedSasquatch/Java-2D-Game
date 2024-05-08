package entities.objects.misc;

import entities.Entity;
import entities.objects.GameObject;
import views.GameScreen;

public abstract class MiscObject extends GameObject {
    public MiscObject(GameScreen gameScreen) {
        super(gameScreen);
    }

    public void collect(Entity entity) {
        if(entity instanceof Coin) {
            gameScreen.getGui().addMessage("Picked up " + name);
            gameScreen.getPlayer().setMoney(gameScreen.getPlayer().getMoney() + value);
        }
    }
}
