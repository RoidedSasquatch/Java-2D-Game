package levels;

import entities.characters.enemies.BanditArcher;
import entities.characters.enemies.BlueSlime;
import entities.characters.npcs.NPCGuide;
import entities.objects.consumables.Lesser_Healing_Potion;
import entities.objects.equipables.*;
import entities.objects.destroyables.Tree;
import entities.objects.misc.Coin;
import views.GameScreen;

import static utils.Constants.GAME_TILE_SIZE;

public class AssetLoader {
    GameScreen gameScreen;

    public AssetLoader(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }

    public void setObject() {
        gameScreen.getGameObjects().add(new Wood_Axe(gameScreen));
        gameScreen.getGameObjects().get(0).setWorldX(8 * GAME_TILE_SIZE);
        gameScreen.getGameObjects().get(0).setWorldY(6 * GAME_TILE_SIZE);

        gameScreen.getGameObjects().add(new Wood_Sword(gameScreen));
        gameScreen.getGameObjects().get(1).setWorldX(9 * GAME_TILE_SIZE);
        gameScreen.getGameObjects().get(1).setWorldY(6 * GAME_TILE_SIZE);

        gameScreen.getGameObjects().add(new Wood_Armor(gameScreen));
        gameScreen.getGameObjects().get(2).setWorldX(8 * GAME_TILE_SIZE);
        gameScreen.getGameObjects().get(2).setWorldY(7 * GAME_TILE_SIZE);

        gameScreen.getGameObjects().add(new Wood_Shield(gameScreen));
        gameScreen.getGameObjects().get(3).setWorldX(9 * GAME_TILE_SIZE);
        gameScreen.getGameObjects().get(3).setWorldY(7 * GAME_TILE_SIZE);

        gameScreen.getGameObjects().add(new Lesser_Healing_Potion(gameScreen));
        gameScreen.getGameObjects().get(4).setWorldX(4 * GAME_TILE_SIZE);
        gameScreen.getGameObjects().get(4).setWorldY(9 * GAME_TILE_SIZE);

        gameScreen.getGameObjects().add(new ApprenticeSpellbook(gameScreen));
        gameScreen.getGameObjects().get(5).setWorldX(8 * GAME_TILE_SIZE);
        gameScreen.getGameObjects().get(5).setWorldY(8 * GAME_TILE_SIZE);
    }

    public void setNPC() {
        gameScreen.getGameNPCs().add(new NPCGuide(gameScreen));
        gameScreen.getGameNPCs().get(0).setWorldX(13 * GAME_TILE_SIZE);
        gameScreen.getGameNPCs().get(0).setWorldY(3 * GAME_TILE_SIZE);
    }

    public void setMonster() {
        gameScreen.getGameEnemies().add(gameScreen.getEnemyFactory().request(0, 13 * GAME_TILE_SIZE, 7 * GAME_TILE_SIZE));

        gameScreen.getGameEnemies().add(gameScreen.getEnemyFactory().request(1, 3 * GAME_TILE_SIZE, 7 * GAME_TILE_SIZE));
    }

    public void setDestroyableObjects() {
        gameScreen.getDestroyableObjects().add(new Tree(gameScreen));
        gameScreen.getDestroyableObjects().get(0).setWorldX(4 * GAME_TILE_SIZE);
        gameScreen.getDestroyableObjects().get(0).setWorldY(4 * GAME_TILE_SIZE);

        gameScreen.getDestroyableObjects().add(new Tree(gameScreen));
        gameScreen.getDestroyableObjects().get(1).setWorldX(2 * GAME_TILE_SIZE);
        gameScreen.getDestroyableObjects().get(1).setWorldY(3 * GAME_TILE_SIZE);
    }
}
