package be.artex.role.old.solo;

import be.artex.AllStarsParty.api.items.ASPItem;
import be.artex.AllStarsParty.api.Role;
import be.artex.AllStarsParty.api.Side;
import be.artex.AllStarsParty.registry.ItemRegistry;

import java.util.Collections;
import java.util.List;

public class Yoriichi extends Role {
    @Override
    public String getName() {
        return "Yoriichi";
    }

    @Override
    public Side getSide() {
        return Side.YORIICHI;
    }

    @Override
    public List<ASPItem> getItems() {
        return Collections.singletonList(ItemRegistry.BONUS_GOLDEN_APPLES);
    }

    @Override
    public int getResistance() {
        return 120;
    }

    @Override
    public int getStrength() {
        return 120;
    }
}
