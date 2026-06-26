package be.artex.allStarsParty.role.old.solo;

import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.registry.ItemRegistry;

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
