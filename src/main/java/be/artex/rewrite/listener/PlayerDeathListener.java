package be.artex.rewrite.listener;

import be.artex.allStarsParty.api.message.Message;
import be.artex.rewrite.AllStarsParty;
import be.artex.rewrite.api.role.Role;
import be.artex.rewrite.api.role.Side;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeathListener implements Listener {
    private static final Role.Manager roleManager = Role.manager;

    @EventHandler
    public static void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Role playerRole = roleManager.getPlayerRole(player.getUniqueId());

        event.getDrops().clear();

        if (playerRole != null)
            event.setDeathMessage(
                ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "\n All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.DARK_AQUA + player.getName() + ChatColor.WHITE + " est mort, son rôle est " + playerRole.getSide().getColor() + playerRole.getName() + ChatColor.WHITE + ".");

        if (player.getKiller() != null) {
            Player killer = player.getKiller();

            player.sendMessage(Message.info(ChatColor.DARK_AQUA + killer.getName() + ChatColor.WHITE + " possèdait " + ChatColor.LIGHT_PURPLE + (Math.round(player.getHealth()) / 2) + " coeurs" + ChatColor.WHITE + "."));
        }

        Side firstSide = roleManager.getRolesAlive().get(0).getSide();

        if (roleManager.isWonBy(firstSide, roleManager.getRolesAlive())) {
            AllStarsParty.gameManager.end();

            Bukkit.broadcastMessage(Message.info("Victoire des " + firstSide.getColor() + firstSide.getName() + ChatColor.WHITE + "."));
        }

    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.setRespawnLocation(new Location(be.artex.allStarsParty.AllStarsParty.world, be.artex.allStarsParty.AllStarsParty.CENTER_X, be.artex.allStarsParty.AllStarsParty.CENTER_Y + 2, be.artex.allStarsParty.AllStarsParty.CENTER_Z));

        if (AllStarsParty.gameManager.isInGame())
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
        else
            event.getPlayer().setGameMode(GameMode.ADVENTURE);
    }
}
