package entities.characters;

import entities.Entity;
import entities.objects.GameObject;
import entities.objects.destroyables.DestroyableObject;
import entities.objects.equipables.*;
import entities.objects.misc.MiscObject;
import entities.objects.projectiles.ProjectileObject;
import inventory.Inventory;
import textures.animations.Animation;
import textures.animations.Sprite;
import inputs.Directions;
import inputs.KeyHandler;
import views.GameScreen;
import views.GameState;
import java.awt.*;
import java.util.ArrayList;

import static entities.objects.ObjectTypes.PICKUP;
import static entities.objects.ObjectTypes.SPELLBOOK;

public class Player extends Character {
    private final KeyHandler keyHandler;
    private final Inventory inventory;

    //Animations
    private Sprite playerSprite;
    private Animation downIdle, upIdle, leftIdle, rightIdle;
    private Animation downWalk, upWalk, leftWalk, rightWalk;
    private Animation downMelee, upMelee, leftMelee, rightMelee;
    private Animation downHit, upHit, leftHit, rightHit;
    private boolean attackCancel;

    public Player(GameScreen gameScreen, KeyHandler keyHandler) {
        super(gameScreen);
        this.gameScreen = gameScreen;
        this.keyHandler = keyHandler;

        createPlayerEntity();

        //Player Status
        name = "";
        level = 1;
        maxHealth = 50;
        currentHealth = maxHealth;
        maxMana = 50;
        currentMana = maxMana;
        strength = 1;
        dex = 1;
        exp = 0;
        expToNextLevel = 50;
        money = 0;

        equippedWeapon = null;
        equippedShield = null;
        equippedHelmet = null;
        equippedArmor = null;
        equippedTrinket = null;

        attack = calculateAttack();
        defense = calculateDefense();

        inventory = new Inventory(gameScreen);
    }

    private void createPlayerEntity() {
        //Position Settings
        worldX = 100;
        worldY = 100;
        screenX = gameScreen.screenWidth / 2 - 64;
        screenY = gameScreen.screenHeight / 2 - 64;

        //Direction and Movement Settings
        moving = false;
        speed = 3;

        //Collision Settings
        collisionArea = new Rectangle(52, 66, 22, 22);
        solidAreaDefaultX = collisionArea.x;
        solidAreaDefaultY = collisionArea.y;

        //Melee Hitbox Settings
        attackCancel = false;
        attackCollision.width = 20;
        attackCollision.height = 20;

        //Animation Settings
        playerSprite = new Sprite();
        playerSprite.loadSheet("entities/player/player_sprites.png", 64, 64);
        createAnimations();
        currentAnimation = downIdle;

        spellCooldown = 0;
    }

    private void createAnimations() {
        //TODO: Add Animations for different items and define conditions for them
        downIdle = new Animation(playerSprite.getSprite(0, 12, 2), 10, true);
        upIdle = new Animation(playerSprite.getSprite(4, 12, 2), 10, true);
        leftIdle = new Animation(playerSprite.getSprite(8, 12, 2), 10, true);
        rightIdle = new Animation(playerSprite.getSprite(12, 12, 2), 10, true);
        downWalk = new Animation(playerSprite.getSprite(1, 6, 2), 10, true);
        upWalk = new Animation(playerSprite.getSprite(5, 6, 2), 10, true);
        leftWalk = new Animation(playerSprite.getSprite(9, 6, 2), 10, true);
        rightWalk = new Animation(playerSprite.getSprite(13, 6, 2), 10, true);
        downMelee = new Animation(playerSprite.getSprite(2, 7, 2), 10, false);
        upMelee = new Animation(playerSprite.getSprite(6, 7, 2), 10, false);
        leftMelee = new Animation(playerSprite.getSprite(10, 7, 2), 10, false);
        rightMelee = new Animation(playerSprite.getSprite(14, 7, 2), 10, false);
        downHit = new Animation(playerSprite.getSprite(3, 4, 2), 10, false);
        upHit = new Animation(playerSprite.getSprite(7, 4, 2), 10, false);
        leftHit = new Animation(playerSprite.getSprite(11, 4, 2), 10, false);
        rightHit = new Animation(playerSprite.getSprite(15, 4, 2), 10, false);
    }

    @Override
    public void update() {
        //Get Spell Cooldown
        if(equippedWeapon instanceof WeaponObject) {
            if(((GameObject)equippedWeapon).getType() == SPELLBOOK) {
                spellCooldown = ((ProjectileObject)((WeaponObject)equippedWeapon).getEquippedSpell()).getCooldown();
            }
        }

        //Displaying Current Attack Value
        equippedWeapon = inventory.getHotbar()[gameScreen.getGui().getHotbarCol()];
        attack = calculateAttack();

        //Attacking
        if(hit) {
            attacking = false;
        }
        if(attacking) {
            if(equippedWeapon instanceof WeaponObject) {
                if (((GameObject) equippedWeapon).getType() == SPELLBOOK) {
                    attackCollision.width = 0;
                    attackCollision.height = 0;
                    if(!((ProjectileObject) ((WeaponObject) equippedWeapon).getEquippedSpell()).isActive() && projectileTimer == spellCooldown && ((ProjectileObject) ((WeaponObject) equippedWeapon).getEquippedSpell()).haveResources(this)) {
                        ((ProjectileObject) ((WeaponObject) equippedWeapon).getEquippedSpell()).setProjectile(worldX + 40, worldY + 48, direction, true, this);
                        ((ProjectileObject) ((WeaponObject) equippedWeapon).getEquippedSpell()).subtractResources(this);
                        gameScreen.getProjectileList().add(((WeaponObject) equippedWeapon).getEquippedSpell());
                        projectileTimer = 0;
                    }
                } else {
                    attackCollision.width = equippedWeapon.getAttackCollision().width;
                    attackCollision.height = equippedWeapon.getAttackCollision().height;
                    attack();
                }
            } else {
                attackCollision.width = 20;
                attackCollision.height = 20;
                attack();
            }
        } else if((keyHandler.isUpPressed() || keyHandler.isDownPressed() || keyHandler.isLeftPressed() || keyHandler.isRightPressed() || keyHandler.isInteractPressed()) && !attacking && !hit) {
            if (keyHandler.isUpPressed()) {
                direction = Directions.UP;
            } else if (keyHandler.isDownPressed()) {
                direction = Directions.DOWN;
            } else if (keyHandler.isLeftPressed()) {
                direction = Directions.LEFT;
            } else if (keyHandler.isRightPressed()) {
                direction = Directions.RIGHT;
            }

            //Tile Collision
            collisionActive = false;
            gameScreen.getCollisionSystem().checkTile(this);

            //Object Collision
            int objIndex = gameScreen.getCollisionSystem().checkObject(this, true);
            pickupObject(objIndex);

            //Entity Collision
            int entityIndex = gameScreen.getCollisionSystem().checkEntity(this, (ArrayList<Entity>) gameScreen.getGameNPCs());
            interactEntity(entityIndex);

            //Monster Collision
            int monsterIndex = gameScreen.getCollisionSystem().checkEntity(this, (ArrayList<Entity>) gameScreen.getGameEnemies());
            interactMonster(monsterIndex);

            //Destroyable Collision
            gameScreen.getCollisionSystem().checkEntity(this, (ArrayList<Entity>) gameScreen.getDestroyableObjects());

            //Event Collision
            gameScreen.getEventSystem().checkEvent();

            //Move Player
            if (!collisionActive && !keyHandler.isInteractPressed()) {
                switch (direction) {
                    case DOWN:
                        worldY += speed;
                        moving = true;
                        break;
                    case UP:
                        worldY -= speed;
                        moving = true;
                        break;
                    case LEFT:
                        worldX -= speed;
                        moving = true;
                        break;
                    case RIGHT:
                        worldX += speed;
                        moving = true;
                        break;
                }
            } else
                moving = false;
        } else
            moving = false;

        if(keyHandler.isInteractPressed() && !attackCancel) {
            if(equippedWeapon instanceof WeaponObject) {
                if(((GameObject)equippedWeapon).getType() == SPELLBOOK) {
                    if(projectileTimer == ((ProjectileObject)((WeaponObject)equippedWeapon).getEquippedSpell()).getCooldown()) {
                        attacking = true;
                    }
                } else {
                    attacking = true;
                }
            } else if(equippedWeapon == null) {
                attacking = true;
            }
        }
        attackCancel = false;

        keyHandler.setInteractPressed(false);

        //Reset Iframes After Being Hit
        if(invulnerable) {
            invulnerabilityTimer++;
            if(invulnerabilityTimer > 60) {
                invulnerable = false;
                invulnerabilityTimer = 0;
            }
        }

        if(projectileTimer < spellCooldown) {
            projectileTimer ++;
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        currentAnimation.update();
        setStateAnimation();
        graphics2D.setColor(Color.white);
        graphics2D.drawImage(currentAnimation.getSprite(), screenX, screenY, null);
    }

    @Override
    public void setStateAnimation() {
        if(!attacking && !hit) {
            if (moving && direction == Directions.DOWN) {
                currentAnimation = downWalk;
            } else if (moving && direction == Directions.UP) {
                currentAnimation = upWalk;
            } else if (moving && direction == Directions.LEFT) {
                currentAnimation = leftWalk;
            } else if (moving && direction == Directions.RIGHT) {
                currentAnimation = rightWalk;
            } else if (!moving && direction == Directions.DOWN) {
                currentAnimation = downIdle;
            } else if (!moving && direction == Directions.UP) {
                currentAnimation = upIdle;
            } else if (!moving && direction == Directions.LEFT) {
                currentAnimation = leftIdle;
            } else if (!moving && direction == Directions.RIGHT) {
                currentAnimation = rightIdle;
            }
        } else if(attacking && !hit){
            if(direction == Directions.DOWN) {
                currentAnimation = downMelee;
                if(currentAnimation.isAnimationFinished()) {
                    attacking = false;
                    currentAnimation.reset();
                    currentAnimation = downIdle;
                }
            } else if(direction == Directions.UP) {
                currentAnimation = upMelee;
                if(currentAnimation.isAnimationFinished()) {
                    attacking = false;
                    currentAnimation.reset();
                    currentAnimation = upIdle;
                }
            } else if(direction == Directions.LEFT) {
                currentAnimation = leftMelee;
                if(currentAnimation.isAnimationFinished()) {
                    attacking = false;
                    currentAnimation.reset();
                    currentAnimation = leftIdle;
                }
            } else if(direction == Directions.RIGHT) {
                currentAnimation = rightMelee;
                if(currentAnimation.isAnimationFinished()) {
                    attacking = false;
                    currentAnimation.reset();
                    currentAnimation = rightIdle;
                }
            }
        } else {
            if(direction == Directions.DOWN) {
                currentAnimation = downHit;
                if(currentAnimation.isAnimationFinished()) {
                    hit = false;
                    currentAnimation.reset();
                    currentAnimation = downIdle;
                }
            } else if(direction == Directions.UP) {
                currentAnimation = upHit;
                if(currentAnimation.isAnimationFinished()) {
                    hit = false;
                    currentAnimation.reset();
                    currentAnimation = upIdle;
                }
            } else if(direction == Directions.LEFT) {
                currentAnimation = leftHit;
                if(currentAnimation.isAnimationFinished()) {
                    hit = false;
                    currentAnimation.reset();
                    currentAnimation = leftIdle;
                }
            } else if(direction == Directions.RIGHT) {
                currentAnimation = rightHit;
                if(currentAnimation.isAnimationFinished()) {
                    hit = false;
                    currentAnimation.reset();
                    currentAnimation = rightIdle;
                }
            }
        }
        currentAnimation.start();
    }

    public void pickupObject(int collidedKey) {
         if(collidedKey != 999) {
             if(((GameObject)gameScreen.getGameObjects().get(collidedKey)).getType() == PICKUP) {
                 ((MiscObject)gameScreen.getGameObjects().get(collidedKey)).collect(gameScreen.getGameObjects().get(collidedKey));
                 gameScreen.getGameObjects().remove(collidedKey);
             } else {
                 if (!(gameScreen.getGameObjects().get(collidedKey) instanceof DestroyableObject)) {
                     inventory.addItemToInventory(collidedKey);
                 }
             }
         }
    }

    public void interactEntity(int entity) {
        if(keyHandler.isInteractPressed()) {
            if (entity != 999) {
                attackCancel = true;
                gameScreen.setGameState(GameState.DIALOGUESTATE);
                ((AICharacter) gameScreen.getGameNPCs().get(entity)).speak();
            }
        }
    }

    public void interactMonster(int entity) {
        if(entity != 999) {
            if(!invulnerable && !((Character) gameScreen.getGameEnemies().get(entity)).dying) {
                float damage = ((Character) gameScreen.getGameEnemies().get(entity)).attack - defense;
                if(damage < 0) {
                    damage = 0;
                }
                hit = true;
                currentHealth -= damage;
                invulnerable = true;
                generateParticle(gameScreen.getGameEnemies().get(entity), this);
            }
        }
    }

    private void attack() {
        if(currentAnimation.getCurrentFrame() < 5) {
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = collisionArea.width;
            int solidAreaHeight = collisionArea.height;

            switch (direction) {
                case DOWN:
                    worldY += attackCollision.height;
                    break;
                case UP:
                    worldY -= attackCollision.height;
                    break;
                case LEFT:
                    worldX -= attackCollision.width;
                    break;
                case RIGHT:
                    worldX += attackCollision.width;
                    break;
            }

            collisionArea.width = attackCollision.width;
            collisionArea.height = attackCollision.height;

            int monsterIndex = gameScreen.getCollisionSystem().checkEntity(this, (ArrayList<Entity>) gameScreen.getGameEnemies());
            if (monsterIndex != 999) {
                if(!((AICharacter)gameScreen.getGameEnemies().get(monsterIndex)).isDying()) {
                    ((AICharacter) gameScreen.getGameEnemies().get(monsterIndex)).damageEntity(monsterIndex);
                }
            }

            int objectIndex = gameScreen.getCollisionSystem().checkEntity(this, (ArrayList<Entity>) gameScreen.getDestroyableObjects());
            if(objectIndex != 999) {
                ((DestroyableObject) gameScreen.getDestroyableObjects().get(objectIndex)).damageDestroyableObject(objectIndex);
            }

            worldX = currentWorldX;
            worldY = currentWorldY;
            collisionArea.width = solidAreaWidth;
            collisionArea.height = solidAreaHeight;
        }
    }

    public void checkLevelUp() {
        if(exp >= expToNextLevel) {
            level ++;
            expToNextLevel *= 2;
            maxHealth += 2;
            strength ++;
            dex ++;
            attack = calculateAttack();
            defense = calculateDefense();

            gameScreen.setGameState(GameState.DIALOGUESTATE);
            gameScreen.getGui().setCurrentDialogue("Reached Level " + level + "!\nYou feel stronger.");
        }
    }

    public float calculateAttack() {
        float weaponAtk = 2;
        if(equippedWeapon != null) {
            if(((GameObject)equippedWeapon).getType() != SPELLBOOK) {
                weaponAtk = strength * ((WeaponObject) equippedWeapon).getDamage();
            } else {
                weaponAtk = strength * ((ProjectileObject)((WeaponObject) equippedWeapon).getEquippedSpell()).getProjectileDamage();
            }
        }
        return weaponAtk;
    }

    public float calculateDefense() {
        float shieldDef = 0, helmetDef = 0, armorDef = 0;
        if(equippedShield != null) {
            shieldDef = (dex * ((WearableObject) equippedShield).getBlockProtection());
        }
        if(equippedHelmet != null) {
            helmetDef = (dex * ((WearableObject)equippedHelmet).getBlockProtection());
        }
        if(equippedArmor != null) {
            armorDef = (dex * ((WearableObject)equippedArmor).getBlockProtection());
        }
        return shieldDef + helmetDef + armorDef;
    }

    @Override
    public Color getParticleColor() {
        return new Color(152, 0 , 2);
    }

    public Sprite getPlayerSprite() {
        return playerSprite;
    }

    public void setAttackCancel(boolean attackCancel) {
        this.attackCancel = attackCancel;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
