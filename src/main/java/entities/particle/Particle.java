package entities.particle;

import entities.Entity;
import entities.characters.AICharacter;
import entities.characters.Player;
import entities.characters.enemies.BlueSlime;
import entities.objects.destroyables.DestroyableObject;
import entities.objects.destroyables.Tree;
import views.GameScreen;

import java.awt.*;

import static utils.Constants.GAME_TILE_SIZE;

public class Particle extends Entity {
    private final Entity generator;
    private Color color;
    private int max;
    private int current;
    private int size;
    private int xD;
    private int yD;


    public Particle(GameScreen gameScreen, Entity generator, Color color, int size, int speed, int max, int xD, int yD) {
        super(gameScreen);
        this.generator = generator;
        this.color = color;
        this.size = size;
        this.speed = speed;
        this.max = max;
        this.xD = xD;
        this.yD = yD;
        active = true;

        current = max;

        if(generator instanceof DestroyableObject) {
            worldX = generator.getWorldX() + 70;
            worldY = generator.getWorldY() + 160;
        }
        else if(generator instanceof AICharacter){
            worldX = generator.getWorldX() + GAME_TILE_SIZE + 15;
            worldY = generator.getWorldY() + GAME_TILE_SIZE + 10;
        } else if(generator instanceof Player) {
            worldX = generator.getWorldX() + GAME_TILE_SIZE + 10;
            worldY = generator.getWorldY() + GAME_TILE_SIZE + 24;
        }
    }

    @Override
    public void update() {
        current --;

        if(current < max / 3) {
            yD ++;
        }

        worldX += xD * speed;
        worldY += yD * speed;

        if(current == 0) {
            active = false;
        }

    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int screenX = worldX - gameScreen.getPlayer().getWorldX() + gameScreen.getPlayer().getScreenX();
        int screenY = worldY - gameScreen.getPlayer().getWorldY() + gameScreen.getPlayer().getScreenY();

        graphics2D.setColor(color);
        graphics2D.fillRect(screenX, screenY, size, size);
    }

    @Override
    protected void setStateAnimation() {

    }
}
