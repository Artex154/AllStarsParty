package be.artex.allStarsParty.role.solo;

import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.items.ASPItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BonusGoldenApples extends ASPItem {
    private static final ItemBuilder STACK = new ItemBuilder(Material.GOLDEN_APPLE).amount(16);

    @Override
    public ItemStack getStack() {
        return STACK.build().clone();
    }
}
