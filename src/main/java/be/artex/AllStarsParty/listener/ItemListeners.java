package be.artex.AllStarsParty.listener;

import be.artex.AllStarsParty.api.item.CustomItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
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
    public void onEntityShootBow(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();
        ItemStack mainItem = player.getItemInHand();

        CustomItem cItem = CustomItem.manager.getItemFromStack(mainItem);

        if (cItem != null)
            cItem.onShoot(event);
    }

    @EventHandler
    public void onPlayerItemDamage(PlayerItemDamageEvent event) {
        event.setDamage(0);
    }
}
