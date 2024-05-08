package systems;

import entities.Entity;
import entities.objects.GameObject;
import views.GameScreen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static utils.Constants.*;

public class CollisionSystem {
    GameScreen gameScreen;

    public CollisionSystem(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void checkTile(Entity entity) {
        float entityLeftWorldX = entity.getWorldX() + entity.getCollisionArea().x;
        float entityRightWorldX = entity.getWorldX() + entity.getCollisionArea().x + entity.getCollisionArea().width;
        float entityTopWorldY = entity.getWorldY() + entity.getCollisionArea().y;
        float entityBottomWorldY = entity.getWorldY() + entity.getCollisionArea().y + entity.getCollisionArea().height;

        int entityLeftColumn = (int) (entityLeftWorldX / GAME_TILE_SIZE);
        int entityRightColumn = (int) (entityRightWorldX / GAME_TILE_SIZE);
        int entityTopRow = (int) (entityTopWorldY / GAME_TILE_SIZE);
        int entityBottomRow = (int) (entityBottomWorldY / GAME_TILE_SIZE);

        int tile1, tile2;

        switch(entity.getDirection()) {
            case DOWN:
                entityBottomRow = (int) ((entityBottomWorldY + entity.getSpeed()) / GAME_TILE_SIZE);
                tile1 = gameScreen.getTileManager().getMapTileNum()[entityLeftColumn][entityBottomRow];
                tile2 = gameScreen.getTileManager().getMapTileNum()[entityRightColumn][entityBottomRow];
                if(gameScreen.getTileManager().getTile()[tile1].isCollision() || gameScreen.getTileManager().getTile()[tile2].isCollision()) {
                    entity.setCollisionActive(true);
                }
                break;
            case UP:
                entityTopRow = (int) ((entityTopWorldY - entity.getSpeed()) / GAME_TILE_SIZE);
                tile1 = gameScreen.getTileManager().getMapTileNum()[entityLeftColumn][entityTopRow];
                tile2 = gameScreen.getTileManager().getMapTileNum()[entityRightColumn][entityTopRow];
                if(gameScreen.getTileManager().getTile()[tile1].isCollision() || gameScreen.getTileManager().getTile()[tile2].isCollision()) {
                    entity.setCollisionActive(true);
                }
                break;
            case LEFT:
                entityLeftColumn = (int) ((entityLeftWorldX - entity.getSpeed()) / GAME_TILE_SIZE);
                tile1 = gameScreen.getTileManager().getMapTileNum()[entityLeftColumn][entityTopRow];
                tile2 = gameScreen.getTileManager().getMapTileNum()[entityLeftColumn][entityBottomRow];
                if(gameScreen.getTileManager().getTile()[tile1].isCollision() || gameScreen.getTileManager().getTile()[tile2].isCollision()) {
                    entity.setCollisionActive(true);
                }
                break;
            case RIGHT:
                entityRightColumn = (int) ((entityRightWorldX + entity.getSpeed()) / GAME_TILE_SIZE);
                tile1 = gameScreen.getTileManager().getMapTileNum()[entityRightColumn][entityTopRow];
                tile2 = gameScreen.getTileManager().getMapTileNum()[entityRightColumn][entityBottomRow];
                if(gameScreen.getTileManager().getTile()[tile1].isCollision() || gameScreen.getTileManager().getTile()[tile2].isCollision()) {
                    entity.setCollisionActive(true);
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
       int collided = 999;
       int i = 0;

        for(Entity object : gameScreen.getGameObjects()) {
            if(object != null) {
                //Entity Collision Area
                entity.getCollisionArea().x += entity.getWorldX();
                entity.getCollisionArea().y += entity.getWorldY();
                //Object Collision Area
                object.getCollisionArea().x += object.getWorldX();
                object.getCollisionArea().y += object.getWorldY();

                switch(entity.getDirection()) {
                    case DOWN:
                        entity.getCollisionArea().y += entity.getSpeed();
                        break;
                    case UP:
                        entity.getCollisionArea().y -= entity.getSpeed();
                        break;
                    case LEFT:
                        entity.getCollisionArea().x -= entity.getSpeed();
                        break;
                    case RIGHT:
                        entity.getCollisionArea().x += entity.getSpeed();
                        break;
                }

                if(entity.getCollisionArea().intersects(object.getCollisionArea())) {
                    if(object.isCollisionActive()) {
                        entity.setCollisionActive(true);
                    }
                    if(player) {
                        collided = i;
                    }
                }

                entity.getCollisionArea().x = entity.getSolidAreaDefaultX();
                entity.getCollisionArea().y = entity.getSolidAreaDefaultY();
                object.getCollisionArea().x = object.getSolidAreaDefaultX();
                object.getCollisionArea().y = object.getSolidAreaDefaultY();
            }
            i++;
        }
        return collided;
    }

    public int checkEntity(Entity entity, ArrayList<Entity> target) {
        int collided = 999;
        int i = 0;

        for(Entity entityCol : target) {
            if(entityCol != null) {
                //Entity Collision Area
                entity.getCollisionArea().x += entity.getWorldX();
                entity.getCollisionArea().y += entity.getWorldY();
                //Object Collision Area
                entityCol.getCollisionArea().x += entityCol.getWorldX();
                entityCol.getCollisionArea().y += entityCol.getWorldY();

                switch(entity.getDirection()) {
                    case DOWN:
                        entity.getCollisionArea().y += entity.getSpeed();
                        break;
                    case UP:
                        entity.getCollisionArea().y -= entity.getSpeed();
                        break;
                    case LEFT:
                        entity.getCollisionArea().x -= entity.getSpeed();
                        break;
                    case RIGHT:
                        entity.getCollisionArea().x += entity.getSpeed();
                        break;
                }

                if(entity.getCollisionArea().intersects(entityCol.getCollisionArea())) {
                    if(entityCol != entity) {
                        entity.setCollisionActive(true);
                        collided = i;
                    }
                }

                entity.getCollisionArea().x = entity.getSolidAreaDefaultX();
                entity.getCollisionArea().y = entity.getSolidAreaDefaultY();
                entityCol.getCollisionArea().x = entityCol.getSolidAreaDefaultX();
                entityCol.getCollisionArea().y = entityCol.getSolidAreaDefaultY();
            }
            i++;
        }
        return collided;
    }

    public boolean checkPlayer(Entity entity) {
        boolean contactingPlayer = false;

        //Entity Collision Area
        entity.getCollisionArea().x += entity.getWorldX();
        entity.getCollisionArea().y += entity.getWorldY();
        //Object Collision Area
        gameScreen.getPlayer().getCollisionArea().x += gameScreen.getPlayer().getWorldX();
        gameScreen.getPlayer().getCollisionArea().y += gameScreen.getPlayer().getWorldY();

        switch(entity.getDirection()) {
            case DOWN:
                entity.getCollisionArea().y += entity.getSpeed();
                break;
            case UP:
                entity.getCollisionArea().y -= entity.getSpeed();
                break;
            case LEFT:
                entity.getCollisionArea().x -= entity.getSpeed();
                break;
            case RIGHT:
                entity.getCollisionArea().x += entity.getSpeed();
                break;
        }

        if(entity.getCollisionArea().intersects(gameScreen.getPlayer().getCollisionArea())) {
            entity.setCollisionActive(true);
            contactingPlayer = true;
        }

        entity.getCollisionArea().x = entity.getSolidAreaDefaultX();
        entity.getCollisionArea().y = entity.getSolidAreaDefaultY();
        gameScreen.getPlayer().getCollisionArea().x = gameScreen.getPlayer().getSolidAreaDefaultX();
        gameScreen.getPlayer().getCollisionArea().y = gameScreen.getPlayer().getSolidAreaDefaultY();

        return contactingPlayer;
    }
}
