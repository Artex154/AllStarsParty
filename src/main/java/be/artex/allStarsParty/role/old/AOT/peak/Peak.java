package be.artex.allStarsParty.role.old.AOT.peak;

import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.registry.ItemRegistry;

import java.util.ArrayList;
import java.util.List;

public class Peak extends Role {
    @Override
    public String getName() {
        return "Peak";
    }

    @Override
    public Side getSide() {
        return Side.AOT;
    }

    @Override
    public List<ASPItem> getItems() {
        ArrayList<ASPItem> list = new ArrayList<>();

        list.add(ItemRegistry.TRANSFORMATION_PEAK);
        list.add(ItemRegistry.DASH_PEAK);

        return list;
    }
}
