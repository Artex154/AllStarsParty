package be.artex.rewrite.listener;

import be.artex.allStarsParty.api.message.Message;
import be.artex.rewrite.AllStarsParty;
import be.artex.rewrite.ScoreboardManager;
import be.artex.rewrite.api.role.Role;
import be.artex.rewrite.api.role.Side;
import be.artex.rewrite.world.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerDeathListener implements Listener {
    private static final Role.Manager roleManager = Role.manager;
    public static final Map<UUID, Integer> PLAYERS_KILL_AMOUNT = new HashMap<>();

    @EventHandler
    public static void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Role playerRole = roleManager.getPlayerRole(player.getUniqueId());

        event.getDrops().clear();

        if (playerRole != null) {
            event.setDeathMessage(
                    ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "\n All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.DARK_AQUA + player.getName() + ChatColor.WHITE + " est mort, son rôle est " + playerRole.getSide().getColor() + playerRole.getName() + ChatColor.WHITE + ".");

            Role.manager.removeAliveRole(playerRole);
        }

        if (player.getKiller() != null) {
            Player killer = player.getKiller();

            int amountOfKills = PLAYERS_KILL_AMOUNT.getOrDefault(killer.getUniqueId(), 0);
            amountOfKills++;
            PLAYERS_KILL_AMOUNT.put(killer.getUniqueId(), amountOfKills);

            player.sendMessage(Message.info(ChatColor.DARK_AQUA + killer.getName() + ChatColor.WHITE + " possèdait " + ChatColor.LIGHT_PURPLE + (Math.round(killer.getHealth()) / 2) + " coeurs" + ChatColor.WHITE + "."));
        }

        Side firstSide = roleManager.getRolesAlive().get(0).getSide();

        if (roleManager.isWonBy(firstSide, roleManager.getRolesAlive())) {
            AllStarsParty.gameManager.end();

            Bukkit.broadcastMessage(Message.info("Victoire des " + firstSide.getColor() + firstSide.getName() + ChatColor.WHITE + "."));
        }

        ScoreboardManager.updateAllPlayerScoreboards();
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.setRespawnLocation(new Location(WorldUtil.world, WorldUtil.CENTER_X, WorldUtil.CENTER_Y + 2, WorldUtil.CENTER_Z));

        if (AllStarsParty.gameManager.isInGame())
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
        else
            event.getPlayer().setGameMode(GameMode.ADVENTURE);
    }
}
