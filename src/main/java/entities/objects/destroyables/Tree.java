package entities.objects.destroyables;

import entities.Entity;
import entities.characters.Character;
import entities.objects.GameObject;
import entities.objects.ObjectTypes;
import entities.objects.crafting.BlueJelly;
import entities.objects.crafting.Wood;
import entities.objects.misc.Coin;
import utils.UtilityMethods;
import views.GameScreen;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import static utils.Constants.GAME_TILE_SIZE;
import static utils.Constants.SCALE;

public class Tree extends DestroyableObject {
    private final GameScreen gameScreen;

    public Tree(GameScreen gameScreen) {
        super(gameScreen);
        this.gameScreen = gameScreen;

        name = "Tree";
        durability = 35;
        destroyable = true;

        width = 48 * SCALE;
        height = 64 * SCALE;
        collisionArea = new Rectangle(52, 148, 48, 28);
        solidAreaDefaultX = 52;
        solidAreaDefaultY = 148;
        collisionActive = true;

        try {
            image = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/tree1.png"));
            image = UtilityMethods.scaleImage(image, width, height);
            destroyed = ImageIO.read(getClass().getClassLoader().getResourceAsStream("objects/tree_trunk.png"));
            destroyed = UtilityMethods.scaleImage(destroyed, width, height);

        } catch(IOException e) {
            System.out.println("Could not load image.");
        }
    }

    @Override
    protected void setStateAnimation() {

    }

    @Override
    public void update() {
        super.update();
        if(durability <= 10) {
            image = destroyed;
        }
    }

    @Override
    protected boolean isCorrectTool(Entity entity) {
        if(((Character)entity).getEquippedWeapon() != null) {
            return ((GameObject) ((Character) entity).getEquippedWeapon()).getType() == ObjectTypes.AXE;
        } else if (((Character)entity).getEquippedWeapon() == null) {
            return false;
        }
        return false;
    }

    @Override
    public void checkDrop() {
        //Todo add stackable wood drop once implemented stacking
        dropItems(new Wood(gameScreen), worldX + 17, worldY + 64);
    }

    @Override
    public Color getParticleColor() {
        return new Color(65, 50, 30);
    }
}
