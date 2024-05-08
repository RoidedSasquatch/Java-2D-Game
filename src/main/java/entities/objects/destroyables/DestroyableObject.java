package entities.objects.destroyables;

import entities.Entity;
import entities.objects.GameObject;
import entities.objects.equipables.WeaponObject;
import views.GameScreen;

import java.awt.image.BufferedImage;

public abstract class DestroyableObject extends GameObject {
    private final GameScreen gameScreen;
    protected BufferedImage destroyed;
    protected boolean destroyable;

    protected abstract boolean isCorrectTool(Entity entity);
    public abstract void checkDrop();

    public DestroyableObject(GameScreen gameScreen) {
        super(gameScreen);
        this.gameScreen = gameScreen;
        destroyable = false;
    }

    public void damageDestroyableObject(int index) {
        if(index != 999 && destroyable && isCorrectTool(gameScreen.getPlayer()) && !invulnerable) {
            durability -= ((WeaponObject)gameScreen.getPlayer().getEquippedWeapon()).getDamage();
            invulnerable = true;
            generateParticle(this, this);
            if(durability <= 0 ) {
                gameScreen.getDestroyableObjects().remove(index);
                checkDrop();
            }
        }
    }

    @Override
    public void update() {
        if(invulnerable) {
            invulnerabilityTimer ++;
            if(invulnerabilityTimer > 60) {
                invulnerable = false;
                invulnerabilityTimer = 0;
            }
        }
    }
}
