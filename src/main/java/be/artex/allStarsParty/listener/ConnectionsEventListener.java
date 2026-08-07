package be.artex.allStarsParty.listener;

import be.artex.allStarsParty.api.message.Message;
import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.scoreboard.ScoreboardManager;
import be.artex.allStarsParty.api.role.Role;
import be.artex.allStarsParty.api.role.Side;
import be.artex.allStarsParty.util.PlayerUtil;
import be.artex.allStarsParty.util.WorldUtil;
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

        event.setJoinMessage(ChatColor.GOLD + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.GOLD + player.getName() + ChatColor.WHITE + " a rejoint la partie." + ChatColor.GOLD + " (" + Bukkit.getOnlinePlayers().size() + "/" + Role.manager.getRegisteredRoles().size() + ")");

        FastBoard board = new FastBoard(player);
        board.updateTitle(ChatColor.GOLD + "" + ChatColor.BOLD + ChatColor.BOLD + " All Stars Party ");

        ScoreboardManager.boards.put(player.getUniqueId(), board);
        ScoreboardManager.updateAllPlayerScoreboards();

        player.setGameMode(GameMode.ADVENTURE);

        if (AllStarsParty.gameManager.isInGame())
            player.setGameMode(GameMode.SPECTATOR);

        player.teleport(new Location(WorldUtil.world, WorldUtil.CENTER_X, WorldUtil.CENTER_Y + 2, WorldUtil.CENTER_Z));
        PlayerUtil.resetPlayerStates(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(
                ChatColor.GOLD + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.GOLD + player.getName() + ChatColor.WHITE + " a quitté la partie." +  ChatColor.GOLD + " (" +  (Bukkit.getOnlinePlayers().size() - 1) + "/" + Role.manager.getRegisteredRoles().size() + ")"
        );

        ScoreboardManager.boards.remove(event.getPlayer().getUniqueId());
        ScoreboardManager.updateAllPlayerScoreboardsExcept(player);

        Role playerRole = Role.manager.getPlayerRole(player.getUniqueId());

        if (playerRole == null)
            return;

        Role.manager.removePlayerRole(player.getUniqueId());

        if (AllStarsParty.gameManager.isInGame()) {
            Role.manager.removeAliveRole(playerRole);

            if (Role.manager.getRolesAlive().isEmpty())  {
                AllStarsParty.gameManager.end();
                return;
            }

            Side firstSide = Role.manager.getRolesAlive().get(0).getSide();

            if (Role.manager.isWonBy(firstSide, Role.manager.getRolesAlive())) {
                AllStarsParty.gameManager.end();

                Bukkit.broadcastMessage(Message.info("Victoire " + firstSide.getName() + ChatColor.WHITE + ".") + "\n ");

                Bukkit.broadcastMessage(PlayerListeners.getKillLeaderBoard());
            }

            ScoreboardManager.updateAllPlayerScoreboardsExcept(player);
        }
    }
}
