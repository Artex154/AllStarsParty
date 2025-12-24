package be.artex.allStarsParty.logic.listener.player;

import be.artex.allStarsParty.role.MHA.shoto.IceSide;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class ItemConsumeListener implements Listener {
    @EventHandler
    public static void onPlayerEat(PlayerItemConsumeEvent event) {
        if (!event.getItem().getType().equals(Material.GOLDEN_APPLE))
            return;

        if (IceSide.playersInIce.contains(event.getPlayer()))
            event.setCancelled(true);
    }
}
