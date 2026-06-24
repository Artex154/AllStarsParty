package be.artex.allStarsParty.role.NS;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.registry.ItemRegistry;

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
        return Collections.singletonList(ItemRegistry.HNJ);
    }

    @Override
    public boolean hasNoFall() {
        return true;
    }
}
