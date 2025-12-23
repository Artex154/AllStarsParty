package be.artex.allStarsParty.role.solo;

import be.artex.allStarsParty.gameLogic.ASPItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class BonusGoldenApples extends ASPItem {
    private static final ItemStack STACK = new ItemStack(Material.GOLDEN_APPLE, 12);

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }
}
