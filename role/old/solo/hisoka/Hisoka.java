package be.artex.role.old.solo.hisoka;

import be.artex.AllStarsParty.api.Role;
import be.artex.AllStarsParty.api.Side;
import be.artex.AllStarsParty.api.items.ASPItem;
import be.artex.AllStarsParty.registry.ItemRegistry;

import java.util.ArrayList;
import java.util.List;

public class Hisoka extends Role {
    @Override
    public String getName() {
        return "Hisoka";
    }

    @Override
    public Side getSide() {
        return Side.HISOKA;
    }

    @Override
    public List<ASPItem> getItems() {
        ArrayList<ASPItem> list = new ArrayList<>();

        list.add(ItemRegistry.JEU_DE_CARTES);
        list.add(ItemRegistry.BONUS_GOLDEN_APPLES);

        return list;
    }
}