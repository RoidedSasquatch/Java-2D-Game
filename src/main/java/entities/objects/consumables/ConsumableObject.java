package entities.objects.consumables;

import entities.Entity;
import entities.objects.GameObject;
import entities.objects.ObjectTypes;
import views.GameScreen;

public abstract class ConsumableObject extends GameObject {
    float healingAmount;
    public ConsumableObject(GameScreen gameScreen) {
        super(gameScreen);
        type = ObjectTypes.CONSUMABLE;
        healingAmount = 0;
    }

    public abstract void Consume(Entity entity);
}
