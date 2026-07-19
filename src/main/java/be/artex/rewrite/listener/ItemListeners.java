package be.artex.rewrite.listener;

import be.artex.rewrite.api.item.CustomItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemListeners implements Listener {
    @EventHandler
    public void onItemInteract(PlayerInteractEvent event) {
        ItemStack stack = event.getItem();
        CustomItem cItem = CustomItem.manager.getItemFromStack(stack);

        if (cItem != null)
            cItem.onInteract(event);
    }
}
