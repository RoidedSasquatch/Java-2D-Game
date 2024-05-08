package entities.objects.equipables;

import entities.Entity;
import entities.objects.GameObject;
import views.GameScreen;

public abstract class WeaponObject extends GameObject {
    protected float damage;
    protected Entity equippedSpell;
    public WeaponObject(GameScreen gameScreen) {
        super(gameScreen);
        equippedSpell = null;
    }

    public float getDamage() {
        return damage;
    }

    public Entity getEquippedSpell() {
        return equippedSpell;
    }

    public void setEquippedSpell(Entity equippedSpell) {
        this.equippedSpell = equippedSpell;
    }
}
