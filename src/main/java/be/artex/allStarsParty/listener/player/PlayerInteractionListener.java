package be.artex.allStarsParty.listener.player;

import be.artex.allStarsParty.registry.ItemRegistry;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.manager.ItemManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerInteractionListener implements Listener {
    private static final ItemManager itemManager = ItemRegistry.itemManager;

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        ItemStack stack = event.getItem();

        ASPItem item = itemManager.getItemFromStack(stack);

        System.out.println("called");

        if (item == null)
            return;

        item.onInteraction(event);
        System.out.println("interaction succesful");
    }
}
