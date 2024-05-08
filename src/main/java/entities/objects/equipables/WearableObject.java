package entities.objects.equipables;

import entities.objects.GameObject;
import views.GameScreen;

public abstract class WearableObject extends GameObject {
    protected  float blockProtection;
    public WearableObject(GameScreen gameScreen) {
        super(gameScreen);
        blockProtection = 0;
    }

    public float getBlockProtection() {
        return blockProtection;
    }
}
