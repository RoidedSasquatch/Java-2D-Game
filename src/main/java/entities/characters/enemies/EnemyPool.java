package entities.characters.enemies;

import entities.Entity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class EnemyPool<T> {
    private final Set<T> availableEnemies = new HashSet<>();
    private final Set<T> inUseEnemies = new HashSet<>();
    private int currentEnemyAmount;

    protected abstract Optional<T> createEnemy(int prefab);

    public synchronized T request(int prefab, int worldX, int worldY) {
        if(availableEnemies.isEmpty()) {
            Optional<T> enemy = createEnemy(prefab);
            if(enemy.isPresent()) {
                availableEnemies.add(enemy.get());
                currentEnemyAmount += 1;
            }
        }

        var instance = availableEnemies.iterator().next();
        availableEnemies.remove(instance);
        inUseEnemies.add(instance);
        ((Entity) instance).setWorldX(worldX);
        ((Entity) instance).setWorldY(worldY);
        return instance;
    }

    public synchronized void release(T instance) {
        inUseEnemies.remove(instance);
        availableEnemies.add(instance);
    }

    public int getCurrentEnemyAmount() {
        return currentEnemyAmount;
    }
}
