package be.artex.allStarsParty.listener;


import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class ConnectionsEventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage(
                ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + player.getName() + " a rejoint la partie."
        );
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(
                ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + player.getName() + " a quitté la partie."
        );
    }
}
