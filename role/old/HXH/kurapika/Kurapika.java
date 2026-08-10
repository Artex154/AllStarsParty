package be.artex.role.old.HXH.kurapika;

import be.artex.AllStarsParty.api.items.ASPItem;
import be.artex.AllStarsParty.api.Role;
import be.artex.AllStarsParty.api.Side;
import be.artex.AllStarsParty.registry.ItemRegistry;

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
