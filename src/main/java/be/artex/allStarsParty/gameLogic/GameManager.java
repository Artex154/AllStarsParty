package be.artex.allStarsParty.gameLogic;

public class GameManager {
    private boolean inGame = false;
    private int maxPlayerCount = 1;

    // Max Player Count

    public int getMaxPlayerCount() {
        return maxPlayerCount;
    }

    public void setMaxPlayerCount(int maxPlayerCount) {
        this.maxPlayerCount = maxPlayerCount;
    }

    // In Game

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean isInGame) {
        inGame = isInGame;
    }
}
