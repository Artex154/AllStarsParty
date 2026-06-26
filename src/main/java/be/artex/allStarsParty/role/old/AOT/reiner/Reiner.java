package be.artex.allStarsParty.role.old.AOT.reiner;

import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.registry.ItemRegistry;

import java.util.Collections;
import java.util.List;

public class Reiner extends Role {
    @Override
    public String getName() {
        return "Reiner";
    }

    @Override
    public Side getSide() {
        return Side.AOT;
    }

    @Override
    public List<ASPItem> getItems() {
        return Collections.singletonList(ItemRegistry.TRANSFORMATION_REINER);
    }
}
