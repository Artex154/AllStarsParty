package be.artex.rewrite.listener;

import be.artex.rewrite.ScoreboardManager;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
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

        event.setJoinMessage(ChatColor.GOLD + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.GOLD + player.getName() + ChatColor.WHITE + " a rejoint la partie." + ChatColor.GOLD + " (" + Bukkit.getOnlinePlayers().size() + "/" + 4 + ")");

        FastBoard board = new FastBoard(player);
        board.updateTitle(ChatColor.GOLD + "" + ChatColor.BOLD + ChatColor.BOLD + " All Stars Party ");

        ScoreboardManager.boards.put(player.getUniqueId(), board);
        ScoreboardManager.updateAllPlayerScoreboards();
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(
                ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.GOLD + player.getName() + ChatColor.WHITE + " a quitté la partie." +  ChatColor.GOLD + " (" +  (Bukkit.getOnlinePlayers().size() - 1) + "/" + 4 + ")"
        );

        ScoreboardManager.boards.remove(event.getPlayer().getUniqueId());
        ScoreboardManager.updateAllPlayerScoreboardsExcept(player);
    }
}
