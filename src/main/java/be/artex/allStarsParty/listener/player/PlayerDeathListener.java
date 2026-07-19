package be.artex.allStarsParty.listener.player;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.PlayerUtil;
import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.message.Message;
import be.artex.allStarsParty.manager.GameManager;
import be.artex.allStarsParty.manager.RoleManager;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.registry.RoleRegistry;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeathListener implements Listener {
    private static final RoleManager roleManager = RoleRegistry.roleManager;
    private static final GameManager gameManager = AllStarsParty.gameManager;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Role playerRole = roleManager.getPlayerRole(player);

        playerRole.whenKilled(event);

        event.getDrops().clear();
        event.setDeathMessage(
                ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "\n All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.DARK_AQUA + player.getName() + ChatColor.WHITE + " est mort, son rôle est " + playerRole.getSide().getColor() + playerRole.getName() + ChatColor.WHITE + ".");

        roleManager.removeAliveRole(playerRole);
        roleManager.setPlayerRole(player, null);

        PlayerUtil.updateAllPlayerScoreboards();

        if (player.getKiller() != null) {
            Player killer = player.getKiller();
            Role killerRole = roleManager.getPlayerRole(killer);

            killerRole.onKill(event);
            killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 5));

            player.sendMessage(Message.info(ChatColor.DARK_AQUA + killer.getName() + " possèdait " + ChatColor.LIGHT_PURPLE + (Math.round(player.getHealth() * 2) / 4 + "❤" + ChatColor.WHITE + ".")));
        }

        Side firstSide = roleManager.getRolesAlive().get(0).getSide();

        if (roleManager.isWonBy(firstSide, roleManager.getRolesAlive()))
            gameManager.finishGame(firstSide);
    }

   @EventHandler
   public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.setRespawnLocation(new Location(AllStarsParty.world, AllStarsParty.CENTER_X, AllStarsParty.CENTER_Y + 2, AllStarsParty.CENTER_Z));

        if (gameManager.isInGame())
            event.getPlayer().setGameMode(GameMode.SPECTATOR);
        else
            event.getPlayer().setGameMode(GameMode.ADVENTURE);
   }
}
