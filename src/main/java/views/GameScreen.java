package views;

import entities.Entity;
import entities.characters.AICharacter;
import entities.characters.Player;
import entities.characters.enemies.Enemy;
import entities.characters.enemies.EnemyFactory;
import entities.objects.projectiles.ProjectileObject;
import gui.GUI;
import inputs.KeyHandler;
import levels.AssetLoader;
import levels.TileManager;
import systems.CollisionSystem;
import systems.EventSystem;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import static utils.Constants.*;

public class GameScreen extends JPanel implements Runnable {
    private Thread gameThread;
    private final Player player;
    private final TileManager tileManager;
    private final CollisionSystem collisionSystem;
    private final EnemyFactory enemyFactory;

    //Entity Lists
    private List<Entity> destroyableObjects;
    private List<Entity> gameObjects;
    private List<Entity> gameNPCs;
    private List<Entity> gameEnemies;
    private List<Entity> projectileList;
    private List<Entity> particleList;
    private List<Entity> entityList;


    private final AssetLoader assetLoader;
    private final GUI gui;
    private GameState gameState;
    private final EventSystem eventSystem;
    private final KeyHandler keyHandler;

    //Screen Settings
    public int screenCols = 16;
    public int screenRows = 12;
    public int screenWidth = GAME_TILE_SIZE * screenCols;
    public int screenHeight = GAME_TILE_SIZE * screenRows;

    public GameScreen() {
        keyHandler = new KeyHandler(this);
        player = new Player(this, keyHandler);
        tileManager = new TileManager(this);
        collisionSystem = new CollisionSystem(this);
        enemyFactory = new EnemyFactory(this, 50);
        destroyableObjects = new ArrayList<>();
        gameObjects = new ArrayList<>();
        gameNPCs = new ArrayList<>();
        gameEnemies = new ArrayList<>();
        entityList = new ArrayList<>();
        particleList = new ArrayList<>();
        projectileList = new ArrayList<>();
        assetLoader = new AssetLoader(this);
        gui = new GUI(this);
        eventSystem = new EventSystem(this);

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusTraversalKeysEnabled(false);
        this.setFocusable(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    public void setupGame() {
        gameState = GameState.TITLESTATE;
        assetLoader.setObject();
        assetLoader.setNPC();
        assetLoader.setMonster();
        assetLoader.setDestroyableObjects();
    }

    @Override
    public void run() {
        float drawInterval = 1000000000f / FPS;
        float delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime-lastTime) / drawInterval;
            lastTime = currentTime;
            if(delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        if(gameState == GameState.PLAYSTATE || gameState == GameState.CHARACTERSTATE) {
            player.update();

            for (Entity entity : gameNPCs) {
                if (entity != null) {
                    entity.update();
                }
            }

            Iterator<Entity> iterator = gameEnemies.iterator();
            while(iterator.hasNext()) {
                Entity entity = iterator.next();
                if(entity != null) {
                    if(((AICharacter) entity).isAlive()) {
                        entity.update();
                    } else {
                        ((AICharacter)entity).checkDrop();
                        iterator.remove();
                        ((Enemy)entity).resetEntity();
                        enemyFactory.release((Enemy) entity);
                    }
                }
            }

            iterator = projectileList.iterator();
            while (iterator.hasNext()) {
                Entity projectile = iterator.next();
                if(projectile != null) {
                    if((projectile).isActive()) {
                        projectile.update();
                    }
                    if(!(projectile).isActive()) {
                        iterator.remove();
                    }
                }
            }

            for (Entity destroyableObject : destroyableObjects) {
                if (destroyableObject != null) {
                    destroyableObject.update();
                }
            }

            iterator = particleList.iterator();
            while (iterator.hasNext()) {
                Entity particle = iterator.next();
                if(particle != null) {
                    if(particle.isActive()) {
                        particle.update();
                    }
                    if(!particle.isActive()) {
                        iterator.remove();
                    }
                }
            }
        }
        if(gameState == GameState.PAUSESTATE) {

        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics2D = (Graphics2D) g;

        //Title Screen
        if(gameState == GameState.TITLESTATE) {
            gui.draw(graphics2D);
        } else {
            //Draw Tiles
            tileManager.draw(graphics2D);

            //Populate Rendering ArrayList
            entityList.add(player);

            for(Entity entity: destroyableObjects) {
                if(entity != null) {
                    entityList.add(entity);
                }
            }
            for(Entity entity: gameObjects) {
                if(entity != null) {
                    entityList.add(entity);
                }
            }
            for(Entity entity: gameNPCs) {
                if(entity != null) {
                    entityList.add(entity);
                }
            }
            for(Entity entity: gameEnemies) {
                if(entity != null) {
                    entityList.add(entity);
                }
            }
            for(Entity entity : projectileList) {
                if(entity != null) {
                    entityList.add(entity);
                }
            }
            for(Entity entity : particleList) {
                if(entity != null) {
                    entityList.add(entity);
                }
            }

            //Sort by Y Value
            entityList.sort(Comparator.comparingInt(o -> o.getWorldY() + o.getCollisionArea().y));

            //Draw Entities
            for (Entity entity : entityList) {
                entity.draw(graphics2D);
            }

            entityList.clear();

            gui.draw(graphics2D);
        }
        if(keyHandler.debugPressed) {
            graphics2D.setFont(new Font("Arial", Font.PLAIN, 20));
            int x = 10;
            int y = 400;
            int lineHeight = 20;

            graphics2D.drawString("WorldX " + player.getWorldX(), x, y);
            y += lineHeight;
            graphics2D.drawString("WorldY " + player.getWorldY(), x, y);
            y += lineHeight;
            graphics2D.drawString("Col " + (player.getWorldX() + player.getCollisionArea().x) / GAME_TILE_SIZE, x, y);
            y += lineHeight;
            graphics2D.drawString("Row " + (player.getWorldY() + player.getCollisionArea().y) / GAME_TILE_SIZE, x, y);
        }
        graphics2D.dispose();
    }

    public Player getPlayer() {
        return player;
    }

    public TileManager getTileManager() {
        return tileManager;
    }

    public CollisionSystem getCollisionSystem() {
        return collisionSystem;
    }

    public List<Entity> getGameObjects() {
        return gameObjects;
    }

    public List<Entity> getGameNPCs() { return gameNPCs; }

    public List<Entity> getGameEnemies() { return gameEnemies; }

    public GUI getGui() {
        return gui;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public EventSystem getEventSystem() {
        return eventSystem;
    }

    public KeyHandler getKeyHandler() {
        return keyHandler;
    }

    public AssetLoader getAssetLoader() {
        return assetLoader;
    }

    public List<Entity> getProjectileList() {
        return projectileList;
    }

    public EnemyFactory getEnemyFactory() {
        return enemyFactory;
    }

    public List<Entity> getDestroyableObjects() {
        return destroyableObjects;
    }

    public List<Entity> getParticleList() {
        return particleList;
    }
}
