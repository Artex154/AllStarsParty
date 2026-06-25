package be.artex.allStarsParty.listener.player;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.role.MHA.shoto.IceSide;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import static sun.audio.AudioPlayer.player;

public class PlayerBlockListeners implements Listener {
    @EventHandler
    public void onPlayerBlockPlace(BlockPlaceEvent event) {
        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () ->
                event.getBlock().setType(Material.AIR), 10*20L);
    }

    @EventHandler
    public void onPlayerEmptyBucket(PlayerBucketEmptyEvent event) {
        if (IceSide.playersInIce.contains(event.getPlayer()))
            event.setCancelled(true);
    }

    @EventHandler
    public static void onBlockBreak(BlockBreakEvent event) {
        switch (event.getBlock().getType()) {
            case STONE:
            case ICE:
                event.setCancelled(true);
        }
    }
}
