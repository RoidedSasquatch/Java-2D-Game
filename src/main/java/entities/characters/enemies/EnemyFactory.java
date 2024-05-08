package entities.characters.enemies;

import entities.Entity;
import views.GameScreen;

import java.util.Optional;

public class EnemyFactory extends EnemyPool<Enemy> {
    private final GameScreen gameScreen;
    private final int maxEnemies;

    public EnemyFactory(GameScreen gameScreen, int maxEnemies) {
        this.gameScreen = gameScreen;
        this.maxEnemies = maxEnemies;
    }

    @Override
    protected Optional<Enemy> createEnemy(int prefab) {
        if(this.getCurrentEnemyAmount() < maxEnemies) {
            Enemy enemy;
            if(prefab == 0) {
                enemy = new BlueSlime(gameScreen);
            } else {
                enemy = new BanditArcher(gameScreen);
            }
            return Optional.of(enemy);
        }
        return Optional.empty();
    }
}
