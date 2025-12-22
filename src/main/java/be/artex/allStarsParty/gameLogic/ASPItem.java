package be.artex.allStarsParty.gameLogic;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class ASPItem {
    public abstract ItemStack getStack();

    public void onInteraction(PlayerInteractEvent event) {
    }
}
