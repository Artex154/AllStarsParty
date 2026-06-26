package be.artex.allStarsParty.role.old.DS.muichiro;

import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.registry.ItemRegistry;

import java.util.Collections;
import java.util.List;

public class Muichiro extends Role {
    @Override
    public String getName() {
        return "Muichiro";
    }

    @Override
    public Side getSide() {
        return Side.DS;
    }

    @Override
    public List<ASPItem> getItems() {
        return Collections.singletonList(ItemRegistry.SOUFFLE_BRUME);
    }
}
