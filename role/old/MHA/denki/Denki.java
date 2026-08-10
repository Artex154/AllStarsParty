package be.artex.role.old.MHA.denki;

import be.artex.AllStarsParty.api.items.ASPItem;
import be.artex.AllStarsParty.api.Role;
import be.artex.AllStarsParty.api.Side;
import be.artex.AllStarsParty.registry.ItemRegistry;

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
