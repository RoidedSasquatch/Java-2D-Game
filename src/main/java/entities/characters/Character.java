package entities.characters;

import entities.Entity;
import inputs.Directions;
import views.GameScreen;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

import static utils.Constants.GAME_TILE_SIZE;

public abstract class Character extends Entity {
    //Player Only - Position On Screen for Camera
    protected int screenX;
    protected int screenY;

    //Combat
    protected boolean attacking;
    protected boolean hit;
    protected boolean alive;
    protected boolean dying;

    protected int projectileTimer;

    //Character Status
    protected float maxHealth;
    protected float currentHealth;
    protected float maxMana;
    protected  float currentMana;
    protected float maxAmmo;
    protected float currentAmmo;
    protected int strength;
    protected int dex;
    protected float attack;
    protected float defense;
    protected int level;
    protected float exp;
    protected float expToNextLevel;
    protected int money;
    protected Entity equippedWeapon;
    protected Entity equippedShield;
    protected Entity equippedHelmet;
    protected Entity equippedArmor;
    protected Entity equippedTrinket;
    protected int spellCooldown;


    public Character(GameScreen gameScreen) {
        super(gameScreen);
        attacking = false;
        hit = false;
        alive = true;
        dying = false;
        projectileTimer = 0;
        spellCooldown = 0;
    }

    public int getScreenX() {
        return screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public float getCurrentHealth() {
        return currentHealth;
    }

    public float getMaxMana() {
        return maxMana;
    }

    public float getCurrentMana() {
        return currentMana;
    }

    public void setMaxHealth(float maxHealth) {
        this.maxHealth = maxHealth;
    }

    public void setCurrentHealth(float currentHealth) {
        this.currentHealth = currentHealth;
    }

    public boolean isHit() { return hit; }

    public void setHit(boolean hit) {
        this.hit = hit;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isDying() {
        return dying;
    }

    public float getAttack() {
        return attack;
    }

    public float getDefense() {
        return defense;
    }

    public boolean isAttacking() {
        return attacking;
    }

    public int getStrength() {
        return strength;
    }

    public int getDex() {
        return dex;
    }

    public int getLevel() {
        return level;
    }

    public float getExp() {
        return exp;
    }

    public float getExpToNextLevel() {
        return expToNextLevel;
    }

    public int getMoney() {
        return money;
    }

    public Entity getEquippedWeapon() {
        return equippedWeapon;
    }

    public Entity getEquippedShield() {
        return equippedShield;
    }

    public Entity getEquippedHelmet() {
        return equippedHelmet;
    }

    public Entity getEquippedArmor() {
        return equippedArmor;
    }

    public Entity getEquippedTrinket() {
        return equippedTrinket;
    }

    public void setEquippedWeapon(Entity equippedWeapon) {
        this.equippedWeapon = equippedWeapon;
    }

    public void setEquippedShield(Entity equippedShield) {
        this.equippedShield = equippedShield;
    }

    public void setEquippedHelmet(Entity equippedHelmet) {
        this.equippedHelmet = equippedHelmet;
    }

    public void setEquippedArmor(Entity equippedArmor) {
        this.equippedArmor = equippedArmor;
    }

    public void setEquippedTrinket(Entity equippedTrinket) {
        this.equippedTrinket = equippedTrinket;
    }

    public void setAttack(float attack) {
        this.attack = attack;
    }

    public void setDefense(float defense) {
        this.defense = defense;
    }

    public void setExp(float exp) {
        this.exp = exp;
    }

    public void setExpToNextLevel(float expToNextLevel) {
        this.expToNextLevel = expToNextLevel;
    }

    public void setMaxMana(float maxMana) {
        this.maxMana = maxMana;
    }

    public void setCurrentMana(float currentMana) {
        this.currentMana = currentMana;
    }

    public float getMaxAmmo() {
        return maxAmmo;
    }

    public void setMaxAmmo(float maxAmmo) {
        this.maxAmmo = maxAmmo;
    }

    public float getCurrentAmmo() {
        return currentAmmo;
    }

    public void setCurrentAmmo(float currentAmmo) {
        this.currentAmmo = currentAmmo;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
