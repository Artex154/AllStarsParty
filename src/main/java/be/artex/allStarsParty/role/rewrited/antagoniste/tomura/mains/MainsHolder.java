package be.artex.allStarsParty.role.rewrited.antagoniste.tomura.mains;

import org.bukkit.inventory.ItemStack;

public class MainsHolder {
    private final ItemStack stack;
    private final Integer placement;

    public MainsHolder(ItemStack stack, Integer placement) {
        this.stack = stack;
        this.placement = placement;
    }

    public ItemStack getStack() {
        return stack;
    }

    public Integer getPlacement() {
        return placement;
    }

}
