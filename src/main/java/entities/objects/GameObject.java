package entities.objects;

import entities.Entity;
import entities.objects.misc.Coin;
import views.GameScreen;

import java.awt.image.BufferedImage;

public abstract class GameObject extends Entity {
    //Objects
    protected int width, height;
    protected String description;
    protected ObjectTypes type;
    protected float durability;
    protected int value;

    public GameObject(GameScreen gameScreen) {
        super(gameScreen);
        description = "";
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getDescription() {
        return description;
    }

    public ObjectTypes getType() {
        return type;
    }

    public int getValue() {
        return value;
    }
}
