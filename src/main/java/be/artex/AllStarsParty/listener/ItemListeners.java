package be.artex.AllStarsParty.listener;

import be.artex.AllStarsParty.api.item.CustomItem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

public class ItemListeners implements Listener {
    @EventHandler
    public void onItemInteract(PlayerInteractEvent event) {
        ItemStack stack = event.getItem();
        CustomItem cItem = CustomItem.manager.getItemFromStack(stack);

        if (cItem != null)
            cItem.onInteract(event);
    }

    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent event) {
        event.setDamage(0);
    }
}
