package be.artex.allStarsParty.role.rewrited.protagoniste.all_might;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.registry.ItemRegistry;

import java.util.ArrayList;
import java.util.List;

public class AllMight extends Role {
    @Override
    public String getName() {
        return "All Might";
    }

    @Override
    public Side getSide() {
        return Side.PROTAGONISTE;
    }

    @Override
    public int getStrength() {
        return 110;
    }

    @Override
    public List<ASPItem> getItems() {
        ArrayList<ASPItem> list = new ArrayList<>();

        list.add(ItemRegistry.USOS);
        list.add(ItemRegistry.PLUS_ULTRA);

        return list;
    }
}
