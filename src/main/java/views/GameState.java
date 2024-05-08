package views;

public enum GameState {
    TITLESTATE (0),
    PLAYSTATE (1),
    PAUSESTATE (2),
    DIALOGUESTATE (3),
    CHARACTERSTATE (4);

    private int gameState;
    GameState(int gameState) {
        this.gameState = gameState;
    }
}
