package be.artex.AllStarsParty.listener;

import be.artex.AllStarsParty.AllStarsParty;
import be.artex.AllStarsParty.util.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerRespawnEvent implements Listener {
    @EventHandler
    public void onPlayerRespawn(org.bukkit.event.player.PlayerRespawnEvent event) {
        System.out.println("--------called-------------");
        System.out.println("--------called-------------");
        System.out.println("--------called-------------");
        System.out.println("--------called-------------");

        event.setRespawnLocation(new Location(
                WorldUtil.world,
                WorldUtil.CENTER_X,
                WorldUtil.CENTER_Y + 2,
                WorldUtil.CENTER_Z
        ));

        Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            if (AllStarsParty.gameManager.isInGame())
                player.setGameMode(GameMode.SPECTATOR);
            else
                player.setGameMode(GameMode.ADVENTURE);
        }, 1);
    }
}
