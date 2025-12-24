package be.artex.allStarsParty.logic;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public abstract class ASPItem {
    public abstract ItemStack getStack();

    public void onInteraction(PlayerInteractEvent event) {
    }

    public void onHit(Player damager, Player player, double damage) {
    }
}
