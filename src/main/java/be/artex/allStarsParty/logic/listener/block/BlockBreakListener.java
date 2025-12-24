package be.artex.allStarsParty.logic.listener.block;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    @EventHandler
    public static void onBlockBreak(BlockBreakEvent event) {
        switch (event.getBlock().getType()) {
            case STONE:
            case ICE:
                event.setCancelled(true);
        }
    }
}
