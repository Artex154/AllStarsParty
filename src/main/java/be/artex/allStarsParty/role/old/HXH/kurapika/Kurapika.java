package be.artex.allStarsParty.role.old.HXH.kurapika;

import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.registry.ItemRegistry;

import java.util.Collections;
import java.util.List;

public class Kurapika extends Role {
    @Override
    public String getName() {
        return "Kurapika";
    }

    @Override
    public Side getSide() {
        return Side.HXH;
    }

    @Override
    public List<ASPItem> getItems() {
        return Collections.singletonList(ItemRegistry.SERMENT);
    }
}
