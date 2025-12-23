package be.artex.allStarsParty.gameLogic.listener;

import be.artex.allStarsParty.AllStarsParty;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerBlockPlaceEventListener implements Listener {
    @EventHandler
    public static void onPlayerBlockPlace(BlockPlaceEvent event) {
        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () ->
                event.getBlock().setType(Material.AIR), 10*20L);
    }
}
