package entities.objects.projectiles;

import entities.Entity;
import entities.characters.AICharacter;
import entities.objects.GameObject;
import inputs.Directions;
import views.GameScreen;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class ProjectileObject extends GameObject {
    protected float projectileDamage;
    protected float manaCost;
    protected float ammoCost;
    protected int range;
    protected int currentRange;
    protected Entity caster;
    protected int cooldown;

    public abstract boolean haveResources(Entity user);
    public abstract void subtractResources(Entity user);

    public ProjectileObject(GameScreen gameScreen) {
        super(gameScreen);
        projectileDamage = 0;
        manaCost = 0;
        range = 0;
        active = false;
        cooldown = 0;
        ammoCost = 0;
    }

    public void setProjectile(int worldX, int worldY, Directions direction, boolean active, Entity caster) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.active = active;
        this.caster = caster;
        this.currentRange = this.range;
    }

    @Override
    public void update() {
        if(caster == gameScreen.getPlayer()) {
            int index = gameScreen.getCollisionSystem().checkEntity(this, (ArrayList<Entity>) gameScreen.getGameEnemies());
            if(index != 999) {
                if(!((AICharacter)gameScreen.getGameEnemies().get(index)).isDying()) {
                    ((AICharacter) gameScreen.getGameEnemies().get(index)).damageEntity(index);
                    active = false;
                }
            }

        }
        if(caster != gameScreen.getPlayer()) {
            boolean contactPlayer = gameScreen.getCollisionSystem().checkPlayer(this);
            if(!gameScreen.getPlayer().isInvulnerable() && contactPlayer) {
                ((AICharacter)caster).damagePlayer(projectileDamage);
                generateParticle(this, gameScreen.getPlayer());
                active = false;
            }
        }

        switch(direction) {
            case DOWN:
                worldY += speed;
                break;
            case UP:
                worldY -= speed;
                break;
            case LEFT:
                worldX -= speed;
                break;
            case RIGHT:
                worldX += speed;
                break;
        }
        currentRange --;
        if(currentRange <= 0) {
            active = false;
        }
    }

    public float getProjectileDamage() {
        return projectileDamage;
    }

    public float getManaCost() {
        return manaCost;
    }

    public int getRange() {
        return range;
    }

    public int getCurrentRange() {
        return currentRange;
    }

    public Entity getCaster() {
        return caster;
    }

    public int getCooldown() {
        return cooldown;
    }
}
