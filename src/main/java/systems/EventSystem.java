package systems;

import inputs.Directions;
import views.GameScreen;
import views.GameState;

import java.awt.*;

import static utils.Constants.*;

public class EventSystem {
    private GameScreen gameScreen;
    private EventRect[][] eventRect;
    private int previousEventX, previousEventY;
    private boolean canTriggerEvent;

    public EventSystem(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        canTriggerEvent = true;

        eventRect = new EventRect[gameScreen.screenCols][gameScreen.screenRows];
        int col = 0;
        int row = 0;
        while(col < gameScreen.screenCols && row < gameScreen.screenRows) {
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].setEventRectDefaultX(eventRect[col][row].x);
            eventRect[col][row].setEventRectDefaultY(eventRect[col][row].y);

            col++;
            if(col == gameScreen.screenCols) {
                col = 0;
                row ++;
            }
        }
    }

    public void checkEvent() {
        int xDistance = Math.abs(gameScreen.getPlayer().getWorldX() - previousEventX);
        int yDistance = Math.abs(gameScreen.getPlayer().getWorldY() - previousEventY);
        int distance = Math.max(xDistance, yDistance);

        if(distance > GAME_TILE_SIZE) {
            canTriggerEvent = true;
        }
        if(canTriggerEvent) {
            if(trigger(14, 10, Directions.ALL)) {
                triggerDamageTile(GameState.DIALOGUESTATE, 10);
            }
        }

        if(trigger(12, 10, Directions.ALL)) {
            triggerHealingTile(12, 10, GameState.DIALOGUESTATE, 10);
        }

        if(trigger(10, 10, Directions.ALL)) {
            triggerTeleportTile(GameState.DIALOGUESTATE, 13, 0);
        }

    }

    public boolean trigger(int eventCol, int eventRow, Directions direction) {
        boolean trigger = false;
        gameScreen.getPlayer().getCollisionArea().x += gameScreen.getPlayer().getWorldX();
        gameScreen.getPlayer().getCollisionArea().y += gameScreen.getPlayer().getWorldY();
        eventRect[eventCol][eventRow].x += eventCol * GAME_TILE_SIZE;
        eventRect[eventCol][eventRow].y += eventRow * GAME_TILE_SIZE;

        if(gameScreen.getPlayer().getCollisionArea().intersects(eventRect[eventCol][eventRow]) && !eventRect[eventCol][eventRow].isEventDone()) {
            if(gameScreen.getPlayer().getDirection().equals(direction) || direction.equals(Directions.ALL)) {
                trigger = true;

                previousEventX = gameScreen.getPlayer().getWorldX();
                previousEventY = gameScreen.getPlayer().getWorldY();
            }
        }
        gameScreen.getPlayer().getCollisionArea().x = gameScreen.getPlayer().getSolidAreaDefaultX();
        gameScreen.getPlayer().getCollisionArea().y = gameScreen.getPlayer().getSolidAreaDefaultY();
        eventRect[eventCol][eventRow].x = eventRect[eventCol][eventRow].getEventRectDefaultX();
        eventRect[eventCol][eventRow].y = eventRect[eventCol][eventRow].getEventRectDefaultY();

        return trigger;
    }

    //One Time Trigger
    public void triggerDamageTile(int col, int row, GameState gameState, float damage) {
        damage = gameScreen.getPlayer().getCurrentHealth() - damage;
        gameScreen.setGameState(gameState);
        gameScreen.getGui().setCurrentDialogue("You walked into fire.");
        gameScreen.getPlayer().setCurrentHealth(damage);
        eventRect[col][row].setEventDone(true);
    }

    //Can Reactivate After Moving Away 1 Tile
    public void triggerDamageTile(GameState gameState, float damage) {
        damage = gameScreen.getPlayer().getCurrentHealth() - damage;
        gameScreen.setGameState(gameState);
        gameScreen.getGui().setCurrentDialogue("You walked into fire.");
        gameScreen.getPlayer().setHit(true);
        gameScreen.getPlayer().setInvulnerable(true);
        gameScreen.getPlayer().setCurrentHealth(damage);
        canTriggerEvent = false;
    }

    public void triggerHealingTile(int col, int row, GameState gameState, float healing) {
        healing = gameScreen.getPlayer().getCurrentHealth() + healing;
        if(gameScreen.getKeyHandler().isInteractPressed()) {
            gameScreen.setGameState(gameState);
            gameScreen.getPlayer().setAttackCancel(true);
            gameScreen.getGui().setCurrentDialogue("Life restored.");
            gameScreen.getPlayer().setCurrentHealth(healing);
        }
    }

    public void triggerTeleportTile(GameState gameState, int col, int row) {
        gameScreen.setGameState(gameState);
        gameScreen.getGui().setCurrentDialogue("Teleported.");
        gameScreen.getPlayer().setWorldX(col * GAME_TILE_SIZE);
        gameScreen.getPlayer().setWorldY(row * GAME_TILE_SIZE);
    }
}
