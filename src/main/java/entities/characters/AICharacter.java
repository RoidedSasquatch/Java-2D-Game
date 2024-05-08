package entities.characters;

import entities.Entity;
import entities.characters.enemies.Enemy;
import entities.objects.projectiles.ProjectileObject;
import inputs.Directions;
import views.GameScreen;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

import static utils.Constants.GAME_TILE_SIZE;

public abstract class AICharacter extends Character {
    //NPC Dialogue
    protected String[] dialogue;
    protected int dialogueIndex;

    protected int actionLockCounter = 0;

    protected boolean barVisible;
    protected int barCounter;

    protected Entity projectile;

    public abstract void checkDrop();

    public AICharacter(GameScreen gameScreen) {
        super(gameScreen);
        dialogue = new String[20];
        dialogueIndex = 0;
        barVisible = false;
        barCounter = 0;
        projectile = null;
    }

    @Override
    public void update() {
        setAction();
        collisionActive = false;
        gameScreen.getCollisionSystem().checkTile(this);
        gameScreen.getCollisionSystem().checkObject(this, false);
        gameScreen.getCollisionSystem().checkEntity(this, (ArrayList<Entity>) gameScreen.getGameNPCs());
        gameScreen.getCollisionSystem().checkEntity(this, (ArrayList<Entity>) gameScreen.getGameEnemies());
        gameScreen.getCollisionSystem().checkEntity(this, (ArrayList<Entity>) gameScreen.getDestroyableObjects());
        boolean contact = gameScreen.getCollisionSystem().checkPlayer(this);

        if(this instanceof Enemy && contact) {
            damagePlayer(attack);
        }

        if (!collisionActive && !dying) {
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

        if(invulnerable) {
            invulnerabilityTimer++;
            if(invulnerabilityTimer > 60) {
                invulnerable = false;
                invulnerabilityTimer = 0;
            }
        }

        if(projectile != null) {
            if (projectileTimer < ((ProjectileObject)projectile).getCooldown()) {
                projectileTimer++;
            }
        }
    }

    @Override
    public void draw(Graphics2D graphics2D) {
        int screenX = worldX - gameScreen.getPlayer().getWorldX() + gameScreen.getPlayer().getScreenX();
        int screenY = worldY - gameScreen.getPlayer().getWorldY() + gameScreen.getPlayer().getScreenY();

        //Camera - Only Render within a certain radius of player
        if(worldX + (GAME_TILE_SIZE * 3) > gameScreen.getPlayer().getWorldX() - gameScreen.getPlayer().getScreenX() &&
                worldX - (GAME_TILE_SIZE * 3) < gameScreen.getPlayer().getWorldX() + gameScreen.getPlayer().getScreenX() &&
                worldY + (GAME_TILE_SIZE * 3) > gameScreen.getPlayer().getWorldY() - gameScreen.getPlayer().getScreenY() &&
                worldY - (GAME_TILE_SIZE * 3) < gameScreen.getPlayer().getWorldY() + gameScreen.getPlayer().getScreenY()) {
            currentAnimation.update();
            setStateAnimation();
            graphics2D.setColor(Color.WHITE);
            graphics2D.drawImage(currentAnimation.getSprite(), screenX, screenY, null);
        }

        //Health Bar
        if(this instanceof Enemy && barVisible) {
            float barScale = GAME_TILE_SIZE / maxHealth;
            float barValue = barScale * currentHealth;

            graphics2D.setColor(new Color(35, 35, 35));
            graphics2D.fillRect(screenX + 39, screenY + 19, GAME_TILE_SIZE + 2, 12);
            graphics2D.setColor(new Color(205, 0, 30));
            graphics2D.fillRect(screenX + 40, screenY + 20, (int) barValue, 10);

            barCounter++;

            if(barCounter > 600) {
                barCounter = 0;
                barVisible = false;
            }
        }

        if(invulnerable) {
            barCounter = 0;
            barVisible = true;
        }
    }

    public void setAction() {
        actionLockCounter++;

        if (actionLockCounter > 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;

            if (i <= 25) {
                direction = Directions.DOWN;
            }
            if (i > 25 && i <= 50) {
                direction = Directions.UP;
            }
            if (i > 50 && i <= 75) {
                direction = Directions.LEFT;
            }
            if (i > 75) {
                direction = Directions.RIGHT;
            }
            actionLockCounter = 0;
        }

        if(projectile != null) {
            int i = new Random().nextInt(100) + 1;
            if (i > 99 && !((ProjectileObject) projectile).isActive() && projectileTimer == ((ProjectileObject)projectile).getCooldown() && !dying) {
                ((ProjectileObject) projectile).setProjectile(worldX + 40, worldY + 48, direction, true, this);
                gameScreen.getProjectileList().add(projectile);
                projectileTimer = 0;
            }
        }
    }

    protected void speak() {
        if (dialogue[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gameScreen.getGui().setCurrentDialogue(dialogue[dialogueIndex]);
        dialogueIndex++;

        switch(gameScreen.getPlayer().getDirection()) {
            case DOWN:
                direction = Directions.UP;
                break;
            case UP:
                direction = Directions.DOWN;
                break;
            case LEFT:
                direction = Directions.RIGHT;
                break;
            case RIGHT:
                direction = Directions.LEFT;
                break;
        }
    }

    public void damagePlayer(float attack) {
        if(!gameScreen.getPlayer().isInvulnerable()) {
            float damage = attack - gameScreen.getPlayer().defense;
            if(damage < 0) {
                damage = 0;
            }
            attacking = true;
            gameScreen.getPlayer().hit = true;
            gameScreen.getPlayer().currentHealth -= damage;
            gameScreen.getPlayer().setInvulnerable(true);
            generateParticle(this, gameScreen.getPlayer());
        }
    }

    public void damageEntity(int index) {
        if(!invulnerable) {
            float damage = gameScreen.getPlayer().getAttack() - defense;
            if(damage < 0) {
                damage = 0;
            }
            currentHealth -= damage;
            gameScreen.getGui().addMessage("You hit " + gameScreen.getGameEnemies().get(index).getName() + " for " + damage + " damage");
            hit = true;
            invulnerable = true;
            generateParticle(gameScreen.getPlayer(), this);
            damageReact();
            if(currentHealth <= 0) {
                ((AICharacter)gameScreen.getGameEnemies().get(index)).dying = true;
                gameScreen.getGui().addMessage("You killed the " + gameScreen.getGameEnemies().get(index).getName());
                gameScreen.getPlayer().setExp(gameScreen.getPlayer().getExp() + exp);
                gameScreen.getGui().addMessage("Gained " + exp + " experience");
                gameScreen.getPlayer().checkLevelUp();
            }
        }
    }

    protected void damageReact() {
        actionLockCounter = 0;
        direction = gameScreen.getPlayer().getDirection();
    }

    @Override
    public Color getParticleColor() {
        return new Color(152, 0 , 2);
    }
}
