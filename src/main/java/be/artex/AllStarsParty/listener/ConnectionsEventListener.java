package be.artex.AllStarsParty.listener;

import be.artex.AllStarsParty.api.GameManager;
import be.artex.AllStarsParty.api.message.Message;
import be.artex.AllStarsParty.scoreboard.ScoreboardManager;
import be.artex.AllStarsParty.api.role.Role;
import be.artex.AllStarsParty.api.role.Side;
import be.artex.AllStarsParty.util.PlayerUtil;
import be.artex.AllStarsParty.util.WorldUtil;
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

        event.setJoinMessage(ChatColor.GOLD + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.GOLD + player.getName() + ChatColor.WHITE + " a rejoint la partie." + ChatColor.GOLD + " (" + Bukkit.getOnlinePlayers().size() + "/" + Role.getRegisteredRoles().size() + ")");

        FastBoard board = new FastBoard(player);
        board.updateTitle(ChatColor.GOLD + "" + ChatColor.BOLD + ChatColor.BOLD + " All Stars Party ");

        ScoreboardManager.boards.put(player.getUniqueId(), board);
        ScoreboardManager.updateAllPlayerScoreboards();

        player.setGameMode(GameMode.ADVENTURE);

        if (GameManager.isInGame())
            player.setGameMode(GameMode.SPECTATOR);

        player.teleport(new Location(WorldUtil.world, WorldUtil.CENTER_X, WorldUtil.CENTER_Y + 2, WorldUtil.CENTER_Z));
        PlayerUtil.resetPlayerStates(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(
                ChatColor.GOLD + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.GOLD + player.getName() + ChatColor.WHITE + " a quitté la partie." +  ChatColor.GOLD + " (" +  (Bukkit.getOnlinePlayers().size() - 1) + "/" + Role.getRegisteredRoles().size() + ")"
        );

        ScoreboardManager.boards.remove(event.getPlayer().getUniqueId());
        ScoreboardManager.updateAllPlayerScoreboardsExcept(player);

        Role playerRole = Role.getPlayerRole(player);

        if (playerRole == null)
            return;

        Role.removePlayerRole(player);

        if (GameManager.isInGame()) {
            if (GameManager.getAlivePlayers().isEmpty())  {
                GameManager.end();
                return;
            }

            Side firstSide = Role.getPlayerRole(GameManager.getAlivePlayers().get(0)).getSide();

            if (PlayerListeners.isWonBy(firstSide)) {
                GameManager.end();

                Bukkit.broadcastMessage(Message.info("Victoire " + firstSide.getName() + ChatColor.WHITE + ".") + "\n ");

                Bukkit.broadcastMessage(PlayerListeners.getKillLeaderBoard());
            }

            ScoreboardManager.updateAllPlayerScoreboardsExcept(player);
        }
    }
}
