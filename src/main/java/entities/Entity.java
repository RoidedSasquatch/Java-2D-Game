package entities;

import entities.particle.Particle;
import inputs.Directions;
import textures.animations.Animation;
import views.GameScreen;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.GAME_TILE_SIZE;

public abstract class Entity {
    protected  GameScreen gameScreen;

    //Entity Position In World
    protected int worldX;
    protected int worldY;

    //Entity Movement
    protected int speed;
    protected  boolean moving;
    protected Directions direction;

    //Will change when texture atlas implemented
    protected BufferedImage image;
    protected Animation currentAnimation;

    //Entity Collision
    protected Rectangle collisionArea;
    protected int solidAreaDefaultX, solidAreaDefaultY;
    protected boolean collisionActive = false;
    protected Rectangle attackCollision;

    //Entity Name
    protected String name;
    protected boolean active;

    //Invulnerability After Attacked
    protected boolean invulnerable;
    protected int invulnerabilityTimer;

    public abstract void update();
    protected abstract void setStateAnimation();

    public Entity(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        direction = Directions.DOWN;
        collisionArea = new Rectangle(0, 0, 0, 0);
        attackCollision = new Rectangle(0, 0, 0, 0);
    }

    public void draw(Graphics2D graphics2D) {
        int screenX = worldX - gameScreen.getPlayer().getWorldX() + gameScreen.getPlayer().getScreenX();
        int screenY = worldY - gameScreen.getPlayer().getWorldY() + gameScreen.getPlayer().getScreenY();

        //Camera - Only Render within a certain radius of player
        if(worldX + (GAME_TILE_SIZE * 3) > gameScreen.getPlayer().getWorldX() - gameScreen.getPlayer().getScreenX() &&
                worldX - (GAME_TILE_SIZE * 3) < gameScreen.getPlayer().getWorldX() + gameScreen.getPlayer().getScreenX() &&
                worldY + (GAME_TILE_SIZE * 3) > gameScreen.getPlayer().getWorldY() - gameScreen.getPlayer().getScreenY() &&
                worldY - (GAME_TILE_SIZE * 3) < gameScreen.getPlayer().getWorldY() + gameScreen.getPlayer().getScreenY()) {
            graphics2D.drawImage(image, screenX, screenY, null);
        }
    }

    protected void dropItems(Entity droppedItem, int worldX, int worldY) {
        for(Entity entity : gameScreen.getGameObjects()) {
            if(entity != null) {
                entity = droppedItem;
                gameScreen.getGameObjects().add(entity);
                entity.setWorldX(worldX + 40);
                entity.setWorldY(worldY + 48);
                break;
            }
        }
    }

    public void generateParticle(Entity generator, Entity target) {
        Color color = generator.getParticleColor();
        int size = generator.getParticleSize();
        int speed = generator.getParticleSpeed();
        int max = generator.getParticleMax();

        Particle p1 = new Particle(gameScreen, target, color, size, speed, max, -2, -1);
        Particle p2 = new Particle(gameScreen, target, color, size, speed, max, 2, -1);
        Particle p3 = new Particle(gameScreen, target, color, size, speed, max, -2, 1);
        Particle p4 = new Particle(gameScreen, target, color, size, speed, max, 2, 1);
        gameScreen.getParticleList().add(p1);
        gameScreen.getParticleList().add(p2);
        gameScreen.getParticleList().add(p3);
        gameScreen.getParticleList().add(p4);
    }

    public Color getParticleColor() { return null; }

    public int getParticleSize() {
        return 6;
    }

    public int getParticleSpeed() {
        return 1;
    }

    public int getParticleMax() {
        return 20;
    }

    public int getWorldX() {
        return worldX;
    }

    public int getWorldY() {
        return worldY;
    }

    public int getSpeed() {
        return speed;
    }

    public Directions getDirection() {
        return direction;
    }

    public Rectangle getCollisionArea() {
        return collisionArea;
    }

    public boolean isCollisionActive() {
        return collisionActive;
    }

    public void setCollisionActive(boolean collisionActive) {
        this.collisionActive = collisionActive;
    }

    public int getSolidAreaDefaultX() {
        return solidAreaDefaultX;
    }

    public int getSolidAreaDefaultY() {
        return solidAreaDefaultY;
    }

    public void setWorldX(int worldX) {
        this.worldX = worldX;
    }

    public void setWorldY(int worldY) {
        this.worldY = worldY;
    }

    public String getName() {
        return name;
    }

    public Rectangle getAttackCollision() {
        return attackCollision;
    }

    public BufferedImage getImage() {
        return image;
    }

    public boolean isInvulnerable() { return  invulnerable; }

    public void setInvulnerable(boolean invulnerable) { this.invulnerable = invulnerable; }

    public boolean isActive() {
        return active;
    }
}
