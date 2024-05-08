package entities.characters.npcs;

import entities.Entity;
import entities.characters.AICharacter;
import textures.animations.Animation;
import textures.animations.Sprite;
import inputs.Directions;
import views.GameScreen;

import java.awt.*;

public class NPCGuide extends AICharacter {
    //Animations
    private Sprite guideSprite;
    private Animation downIdle, upIdle, leftIdle, rightIdle;
    private Animation downWalk, upWalk, leftWalk, rightWalk;

    public NPCGuide(GameScreen gameScreen) {
        super(gameScreen);

        name = "Wise Old Guide";
        speed = 1;
        maxHealth = 20;
        currentHealth = maxHealth;

        createEntity();
        setDialogue();
    }

    private void createEntity() {
        collisionArea = new Rectangle(52, 66, 22, 22);
        solidAreaDefaultX = collisionArea.x;
        solidAreaDefaultY = collisionArea.y;

        guideSprite = new Sprite();
        guideSprite.loadSheet("entities/npc/guide_sprites.png", 64, 64);
        createAnimations();
        currentAnimation = downIdle;
        currentAnimation.start();
    }

    private void createAnimations() {
        downIdle = new Animation(guideSprite.getSprite(0, 12, 2), 10, true);
        upIdle = new Animation(guideSprite.getSprite(4, 12, 2), 10, true);
        leftIdle = new Animation(guideSprite.getSprite(8, 12, 2), 10, true);
        rightIdle = new Animation(guideSprite.getSprite(12, 12, 2), 10, true);
        downWalk = new Animation(guideSprite.getSprite(1, 6, 2), 10, true);
        upWalk = new Animation(guideSprite.getSprite(5, 6, 2), 10, true);
        leftWalk = new Animation(guideSprite.getSprite(9, 6, 2), 10, true);
        rightWalk = new Animation(guideSprite.getSprite(13, 6, 2), 10, true);
    }

    @Override
    public void setStateAnimation() {
        if(moving && direction == Directions.DOWN) {
            currentAnimation = downWalk;
            currentAnimation.start();
        } else if(moving && direction == Directions.UP) {
            currentAnimation = upWalk;
            currentAnimation.start();
        } else if(moving && direction == Directions.LEFT) {
            currentAnimation = leftWalk;
            currentAnimation.start();
        } else if(moving && direction == Directions.RIGHT) {
            currentAnimation = rightWalk;
            currentAnimation.start();
        } else if(!moving && direction == Directions.DOWN) {
            currentAnimation = downIdle;
            currentAnimation.start();
        } else if(!moving && direction == Directions.UP) {
            currentAnimation = upIdle;
            currentAnimation.start();
        } else if(!moving && direction == Directions.LEFT) {
            currentAnimation = leftIdle;
            currentAnimation.start();
        } else if(!moving && direction == Directions.RIGHT) {
            currentAnimation = rightIdle;
            currentAnimation.start();
        }
    }

    public void setDialogue() {
        dialogue[0] = "Well met traveller. Finally woken up have we?";
        dialogue[1] = "I suppose you're wondering what happened. \nTo be honest, I have no idea...";
        dialogue[2] = "I found you lying where you woke a few \nhours ago.";
        dialogue[3] = "Do you remember anything at all about how \nyou got here?";
    }

    @Override
    public void checkDrop() {

    }
}
