package be.artex.allStarsParty.gameLogic.listener;

import be.artex.allStarsParty.role.MHA.shoto.IceSide;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PlayerConsumeEventListener implements Listener {
    @EventHandler
    public static void onPlayerEat(PlayerItemConsumeEvent event) {
        if (!event.getItem().getType().equals(Material.GOLDEN_APPLE))
            return;

        if (IceSide.playersInIce.contains(event.getPlayer()))
            event.setCancelled(true);
    }
}
