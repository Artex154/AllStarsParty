package be.artex.allStarsParty.logic.manager;

import be.artex.allStarsParty.AllStarsParty;

public class GameManager {
    private final RoleManager roleManager = AllStarsParty.roleManager;
    private boolean inGame = false;

    public int getMaxPlayerCount() {
        return roleManager.getRegisteredRoles().size();
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean isInGame) {
        inGame = isInGame;
    }
}
