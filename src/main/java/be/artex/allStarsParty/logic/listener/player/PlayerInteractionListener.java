package be.artex.allStarsParty.logic.listener.player;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.logic.ASPItem;
import be.artex.allStarsParty.logic.manager.ItemManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractionListener implements Listener {
    private static final ItemManager itemManager = AllStarsParty.itemManager;

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        ItemStack stack = event.getItem();

        ASPItem item = itemManager.getItemFromStack(stack);

        if (item != null)
            item.onInteraction(event);
    }
}
