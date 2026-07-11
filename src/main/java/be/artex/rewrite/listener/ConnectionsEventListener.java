package be.artex.rewrite.listener;

import be.artex.rewrite.ScoreboardManager;
import be.artex.rewrite.util.PlayerUtil;
import be.artex.rewrite.world.WorldUtil;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
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

        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(new Location(WorldUtil.world, WorldUtil.CENTER_X, WorldUtil.CENTER_Y + 2, WorldUtil.CENTER_Z));
        PlayerUtil.resetPlayerStates(player);
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
