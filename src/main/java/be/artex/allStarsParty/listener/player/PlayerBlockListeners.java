package be.artex.allStarsParty.listener.player;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.message.Message;
import be.artex.allStarsParty.role.rewrited.protagoniste.shoto.IceSide;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

public class PlayerBlockListeners implements Listener {
    @EventHandler
    public void onPlayerBlockPlace(BlockPlaceEvent event) {
        switch (event.getBlock().getType()) {
            case DIAMOND_BLOCK:
                event.setCancelled(true);
            case BARRIER:
                event.setCancelled(true);
                event.getPlayer().sendMessage(Message.error(ChatColor.RED + "Tomura " + ChatColor.WHITE + "a désactivé cet item."));
        }

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

    @EventHandler
    public void onBlockPhysics(BlockPhysicsEvent event) {
        switch (event.getBlock().getType()) {
            case ICE:
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event) {
        if (event.getCause() == BlockIgniteEvent.IgniteCause.LIGHTNING)
            event.setCancelled(true);
    }
}
