package be.artex.role.old.DS.muichiro;

import be.artex.AllStarsParty.api.items.ASPItem;
import be.artex.AllStarsParty.api.Role;
import be.artex.AllStarsParty.api.Side;
import be.artex.AllStarsParty.registry.ItemRegistry;

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
