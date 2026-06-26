package be.artex.allStarsParty.role.old.MHA.denki;

import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.registry.ItemRegistry;

import java.util.Collections;
import java.util.List;

public class Denki extends Role {
    @Override
    public String getName() {
        return "Denki";
    }

    @Override
    public Side getSide() {
        return Side.MHA;
    }

    @Override
    public List<ASPItem> getItems() {
        return Collections.singletonList(ItemRegistry.DECHARGE);
    }
}
