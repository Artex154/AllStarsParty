package be.artex.allStarsParty.listener.player;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.role.MHA.shoto.IceSide;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockPlaceListener implements Listener {
    @EventHandler
    public static void onPlayerBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (IceSide.playersInIce.contains(player)) {
            event.setCancelled(true);
            return;
        }

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () ->
                event.getBlock().setType(Material.AIR), 10*20L);
    }
}
