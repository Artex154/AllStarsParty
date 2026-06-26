package be.artex.allStarsParty.role.old.AOT;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;

public class Livai extends Role {
    @Override
    public String getName() {
        return "Livaï";
    }

    @Override
    public Side getSide() {
        return Side.AOT;
    }

    @Override
    public int getSpeed() {
        return 115;
    }

    @Override
    public int getStrength() {
        return 105;
    }
}
