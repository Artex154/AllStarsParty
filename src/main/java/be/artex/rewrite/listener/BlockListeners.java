package be.artex.rewrite.listener;

import be.artex.allStarsParty.api.message.Message;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import java.util.ArrayList;

public class BlockListeners implements Listener {
    public static final ArrayList<Block> PLACED_BLOCKS = new ArrayList<>();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (PLACED_BLOCKS.contains(event.getBlock()))
            return;

        event.getPlayer().sendMessage(Message.error("Vous ne pouvez que casser des blocs posés par un autre joueur."));
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        PLACED_BLOCKS.add(event.getBlock());
    }

    @EventHandler
    public void onBucketEmpty(PlayerBucketEmptyEvent event) {
        Block placedBlock = event.getBlockClicked().getRelative(event.getBlockFace());

        PLACED_BLOCKS.add(placedBlock);
    }

    @EventHandler
    public void onLiquidFlow(BlockFromToEvent event) {
        event.setCancelled(true);
    }
}
