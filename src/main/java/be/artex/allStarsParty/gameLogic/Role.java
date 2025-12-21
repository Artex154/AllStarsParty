package be.artex.allStarsParty.gameLogic;

import be.artex.allStarsParty.AllStarsParty;

public abstract class Role {
    public abstract String getName();
    public abstract Side getSide();

    public void register() {
        this.getSide().getRoles().add(this);
        AllStarsParty.registeredRoles.add(this);
    }
}
