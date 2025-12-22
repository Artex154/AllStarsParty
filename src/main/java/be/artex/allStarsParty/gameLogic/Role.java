package be.artex.allStarsParty.gameLogic;

import java.util.Collections;
import java.util.List;

public abstract class Role {
    public abstract String getName();
    public abstract Side getSide();
    public abstract String getDescription();

    public List<ASPItem> getItems() {
        return Collections.emptyList();
    }

    public int getMaxHealth() {
        return 20;
    }

    public int getStrength() {
        return 100;
    }

    public int getResistance() {
        return 100;
    }

    public int getSpeed() {
        return 100;
    }
}
