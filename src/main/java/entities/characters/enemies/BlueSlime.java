package entities.characters.enemies;

import entities.Entity;
import entities.objects.crafting.BlueJelly;
import entities.objects.misc.Coin;
import inputs.Directions;
import textures.animations.Animation;
import textures.animations.Sprite;
import views.GameScreen;

import java.awt.*;
import java.util.Random;

import static utils.Constants.GAME_TILE_SIZE;

public class BlueSlime extends Enemy {
    //Animations
    private Sprite slimeSprite;
    private Animation idle, walk, attack, hurt, death;

    public BlueSlime(GameScreen gameScreen) {
        super(gameScreen);
        slimeSprite = new Sprite();
        slimeSprite.loadSheet("entities/enemy/slime_sprites.png", 64, 64);
        collisionArea = new Rectangle();
        createAnimations();
        resetEntity();
    }

    public void resetEntity() {
        name = "Blue Slime";
        speed = 1;
        maxHealth = 20;
        currentHealth = maxHealth;
        super.attack = 10f;
        defense = 0;
        exp = 5;
        alive = true;
        dying = false;
        invulnerable = false;

        collisionArea.x = 49;
        collisionArea.y = 52;
        collisionArea.width = 32;
        collisionArea.height = 30;
        solidAreaDefaultX = collisionArea.x;
        solidAreaDefaultY = collisionArea.y;

        currentAnimation = idle;
    }

    private void createAnimations() {
        idle = new Animation(slimeSprite.getSprite(0, 6, 2), 10, true);
        walk = new Animation(slimeSprite.getSprite(1, 6, 2), 10, true);
        attack = new Animation(slimeSprite.getSprite(2, 15, 2), 5, false);
        hurt = new Animation(slimeSprite.getSprite(3, 4, 2), 10, false);
        death = new Animation(slimeSprite.getSprite(4, 11, 2), 10, false);
    }

    @Override
    protected void setStateAnimation() {
        if(!attacking && !hit && !dying) {
            if (moving && direction == Directions.DOWN) {
                currentAnimation = walk;
            } else if (moving && direction == Directions.UP) {
                currentAnimation = walk;
            } else if (moving && direction == Directions.LEFT) {
                currentAnimation = walk;
            } else if (moving && direction == Directions.RIGHT) {
                currentAnimation = walk;
            } else if (!moving && direction == Directions.DOWN) {
                currentAnimation = idle;
            } else if (!moving && direction == Directions.UP) {
                currentAnimation = idle;
            } else if (!moving && direction == Directions.LEFT) {
                currentAnimation = idle;
            } else if (!moving && direction == Directions.RIGHT) {
                currentAnimation = idle;
            }
        } else if(attacking && !hit &&!dying) {
            currentAnimation = attack;
            if(attack.isAnimationFinished()) {
                attacking = false;
                currentAnimation.reset();
            }
        } else if(hit){
            currentAnimation = hurt;
            if(currentAnimation.isAnimationFinished()) {
                hit = false;
                currentAnimation.reset();
            }
        } else {
            currentAnimation = death;
            if(currentAnimation.isAnimationFinished()) {
                alive = false;
                currentAnimation.reset();
            }
        }
        currentAnimation.start();
    }

    @Override
    public void checkDrop() {
        int dropped = 0;
        for(int i = 0 ; i < 3 ; i ++) {
            int rng = new Random().nextInt(100) + 1;
            if (rng < 50) {
                rng = new Random().nextInt(100) + 1;
                if(rng < 50) {
                    if(dropped == 0) {
                        dropItems(new Coin(gameScreen), worldX, worldY);
                    } else if(dropped == 1) {
                        dropItems(new Coin(gameScreen), worldX + GAME_TILE_SIZE, worldY);
                    } else {
                        dropItems(new Coin(gameScreen), worldX - GAME_TILE_SIZE, worldY);
                    }
                } else {
                    if(dropped == 0) {
                        dropItems(new BlueJelly(gameScreen), worldX, worldY);
                    } else if(dropped == 1) {
                        dropItems(new BlueJelly(gameScreen), worldX + GAME_TILE_SIZE, worldY);
                    } else {
                        dropItems(new BlueJelly(gameScreen), worldX - GAME_TILE_SIZE, worldY);
                    }
                }
                dropped++;
            }
        }
    }
}
