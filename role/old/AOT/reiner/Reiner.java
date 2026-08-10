package be.artex.role.old.AOT.reiner;

import be.artex.AllStarsParty.api.items.ASPItem;
import be.artex.AllStarsParty.api.Role;
import be.artex.AllStarsParty.api.Side;
import be.artex.AllStarsParty.registry.ItemRegistry;

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
