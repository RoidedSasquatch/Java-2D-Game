import views.GameScreen;

import javax.swing.*;

public class GameLauncher {
    public static void main(String[] args) {
        //Game Windows
        JFrame gameWindow = new JFrame();
        GameScreen gameScreen = new GameScreen();

        //Game Window Settings
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);
        gameWindow.setTitle("Top-Down Survival");

        gameWindow.add(gameScreen);
        gameWindow.pack();

        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);

        gameScreen.setupGame();
        gameScreen.startGameThread();
    }
}