package be.artex.allStarsParty.role.NS;

import be.artex.allStarsParty.logic.Role;
import be.artex.allStarsParty.logic.Side;
import be.artex.allStarsParty.logic.items.ASPItem;

import java.util.Collections;
import java.util.List;

public class Minato extends Role {
    @Override
    public String getName() {
        return "Minato";
    }

    @Override
    public Side getSide() {
        return Side.NS;
    }

    @Override
    public String getDescription() {
        return "a";
    }

    @Override
    public List<ASPItem> getItems() {
        return Collections.singletonList(new HNJ());
    }

    @Override
    public boolean hasNoFall() {
        return true;
    }
}
