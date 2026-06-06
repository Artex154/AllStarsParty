package be.artex.allStarsParty.role.solo;

import be.artex.allStarsParty.item_builder.ItemBuilder;
import be.artex.allStarsParty.logic.items.ASPItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BonusGoldenApples extends ASPItem {
    private static ItemBuilder STACK = new ItemBuilder(Material.GOLDEN_APPLE).amount(12);

    public BonusGoldenApples(int bonusAmount) {
        STACK = STACK.amount(bonusAmount);
    }

    @Override
    public ItemStack getStack() {
        return STACK.build().clone();
    }
}
