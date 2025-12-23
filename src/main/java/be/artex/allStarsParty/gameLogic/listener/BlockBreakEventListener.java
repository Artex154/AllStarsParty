package be.artex.allStarsParty.gameLogic.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakEventListener implements Listener {
    @EventHandler
    public static void onBlockBreak(BlockBreakEvent event) {
        switch (event.getBlock().getType()) {
            case STONE:
            case ICE:
                event.setCancelled(true);
        }
    }
}
