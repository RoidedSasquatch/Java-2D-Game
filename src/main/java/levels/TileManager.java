package levels;

import views.GameScreen;
import utils.UtilityMethods;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static utils.Constants.*;

public class TileManager {
    private final GameScreen gameScreen;
    private final Tile[] tile;
    int[][] mapTileNum;

    //World Settings
    private final int worldCols = 16;
    private final int worldRows = 12;
    private int worldWidth = GAME_TILE_SIZE * worldCols;
    private int worldHeight = GAME_TILE_SIZE * worldRows;

    public TileManager(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        tile = new Tile[20];
        mapTileNum = new int[worldCols][worldRows];

        getTileImage();
        loadMap("levels/test.txt");
    }

    public void draw(Graphics2D graphics2D) {
        int worldCol = 0;
        int worldRow = 0;

        //Loop until all tiles drawn
        while(worldCol < worldCols && worldRow < worldRows) {
            int tileNum = mapTileNum[worldCol][worldRow];
            int worldX = worldCol * GAME_TILE_SIZE;
            int worldY = worldRow * GAME_TILE_SIZE;
            int screenX = worldX - gameScreen.getPlayer().getWorldX() + gameScreen.getPlayer().getScreenX();
            int screenY = worldY - gameScreen.getPlayer().getWorldY() + gameScreen.getPlayer().getScreenY();

            //Camera - Only Render within a certain radius of player
            if(worldX + (GAME_TILE_SIZE * 3) > gameScreen.getPlayer().getWorldX() - gameScreen.getPlayer().getScreenX() &&
                    worldX - (GAME_TILE_SIZE * 3) < gameScreen.getPlayer().getWorldX() + gameScreen.getPlayer().getScreenX() &&
                    worldY + (GAME_TILE_SIZE * 3) > gameScreen.getPlayer().getWorldY() - gameScreen.getPlayer().getScreenY() &&
                    worldY - (GAME_TILE_SIZE * 3) < gameScreen.getPlayer().getWorldY() + gameScreen.getPlayer().getScreenY()) {
                graphics2D.drawImage(tile[tileNum].getImage(), screenX, screenY,null);
            }

            worldCol++;

            if(worldCol == worldCols) {
                worldCol = 0;
                worldRow++;
            }
        }
    }

    private void getTileImage() {
        setup(0, "019", false); // Grass
        setup(1, "081", true);
        setup(2, "083", true);
        setup(3, "093", true);
        setup(4, "096", true);
        setup(5, "082", true);
        setup(6, "094", true);
        setup(7, "089", true);
        setup(8, "090", true);
        setup(9, "005", false); //Forest Grass
        setup(10, "056", false); //water
        setup(11, "061", false); //mountain
    }

    public void setup(int index, String path, boolean collision) {
        try {
            tile[index] = new Tile();
            tile[index].setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("tiles/terrain/" + path + ".png")));
            tile[index].setImage(UtilityMethods.scaleImage(tile[index].getImage(), GAME_TILE_SIZE, GAME_TILE_SIZE));
            tile[index].setCollision(collision);
        } catch (IOException e){
            System.out.println("Could not load image.");
        }
    }

    public void loadMap(String filePath) {
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
            assert inputStream != null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            int col = 0;
            int row = 0;

            while(col < worldCols && row < worldRows) {
                String line = bufferedReader.readLine();
                while(col < worldCols) {
                    String[] numbers = line.split(" ");
                    int number = Integer.parseInt(numbers[col]);

                    mapTileNum[col][row] = number;
                    col++;
                }
                if(col == worldCols) {
                    col = 0;
                    row++;
                }
            }
            bufferedReader.close();
        } catch(Exception e) {
            System.out.println("Failed to load map.");
        }
    }

    public Tile[] getTile() {
        return tile;
    }

    public int[][] getMapTileNum() {
        return mapTileNum;
    }
}
