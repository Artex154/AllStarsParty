package be.artex.allStarsParty.gameLogic.listener;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.gameLogic.ASPItem;
import be.artex.allStarsParty.gameLogic.manager.ItemManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractEventListener implements Listener {
    private static final ItemManager itemManager = AllStarsParty.itemManager;

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        ItemStack stack = event.getItem();

        ASPItem item = itemManager.getItemFromStack(stack);

        if (item != null)
            item.onInteraction(event);
    }
}
