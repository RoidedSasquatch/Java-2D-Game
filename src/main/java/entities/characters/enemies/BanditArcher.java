package entities.characters.enemies;

import entities.Entity;
import entities.objects.crafting.BlueJelly;
import entities.objects.misc.Coin;
import entities.objects.projectiles.WoodenArrow;
import inputs.Directions;
import textures.animations.Animation;
import textures.animations.Sprite;
import views.GameScreen;

import java.awt.*;
import java.util.Random;

import static utils.Constants.GAME_TILE_SIZE;

public class BanditArcher extends Enemy {
    //Animations
    private Sprite bArcherSprite;
    private Animation idle, walk, attack, hurt, death;

    public BanditArcher(GameScreen gameScreen) {
        super(gameScreen);
        collisionArea = new Rectangle();
        projectile = new WoodenArrow(gameScreen);
        bArcherSprite = new Sprite();
        bArcherSprite.loadSheet("entities/enemy/slime_sprites.png", 64, 64);
        createAnimations();
        resetEntity();
    }

    public void resetEntity() {
        name = "Bandit Archer";
        speed = 1;
        maxHealth = 15;
        currentHealth = maxHealth;
        super.attack = 10f;
        defense = 0;
        exp = 10;
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
        idle = new Animation(bArcherSprite.getSprite(0, 6, 2), 10, true);
        walk = new Animation(bArcherSprite.getSprite(1, 6, 2), 10, true);
        attack = new Animation(bArcherSprite.getSprite(2, 15, 2), 5, false);
        hurt = new Animation(bArcherSprite.getSprite(3, 4, 2), 10, false);
        death = new Animation(bArcherSprite.getSprite(4, 11, 2), 10, false);
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
                        dropItems(new WoodenArrow(gameScreen), worldX, worldY);
                    } else if(dropped == 1) {
                        dropItems(new WoodenArrow(gameScreen), worldX + GAME_TILE_SIZE, worldY);
                    } else {
                        dropItems(new WoodenArrow(gameScreen), worldX - GAME_TILE_SIZE, worldY);
                    }
                }
                dropped++;
            }
        }
    }
}
