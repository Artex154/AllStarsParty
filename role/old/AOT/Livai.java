package be.artex.role.old.AOT;

import be.artex.AllStarsParty.api.Role;
import be.artex.AllStarsParty.api.Side;

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
