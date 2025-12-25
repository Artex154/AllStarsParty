package be.artex.allStarsParty.logic.listener.player;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.PlayerUtil;
import be.artex.allStarsParty.logic.Role;
import be.artex.allStarsParty.logic.manager.RoleManager;
import be.artex.allStarsParty.logic.Side;
import be.artex.allStarsParty.role.MHA.denki.Decharge;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;

public class PlayerDeathListener implements Listener {
    private static final RoleManager roleManager = AllStarsParty.roleManager;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Player killer = player.getKiller();

        Role playerRole = roleManager.getPlayerRole(player);
        Role killerRole = roleManager.getPlayerRole(killer);

        event.getDrops().clear();
        event.setDeathMessage(
                ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "\n All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.DARK_AQUA + player.getName() + ChatColor.WHITE + " est mort, son rôle est " + playerRole.getSide().getColor() + playerRole.getName() + ChatColor.WHITE + ".");

        Decharge.electrocutedPlayers.remove(player);

        roleManager.removeAliveRole(playerRole);

        killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));

        Side firstSide = killerRole.getSide();

        PlayerUtil.updateAllPlayerScoreboards();

        killerRole.onKill(event);

        if (!roleManager.getRolesAlive().stream().allMatch(role -> role.getSide() == firstSide))
            return;

        PlayerUtil.sendMessageToAllPlayers(
                ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.GREEN + "Victoire " + ChatColor.WHITE + "du camp " + firstSide.getColor() + firstSide.getName() + ChatColor.WHITE + "."
        );

        Collection<? extends Player> players = Bukkit.getOnlinePlayers();

        for (Player p : players) {
            p.teleport(new Location(AllStarsParty.world, AllStarsParty.CENTER_X, AllStarsParty.CENTER_Y + 2, AllStarsParty.CENTER_Z));
            p.setGameMode(GameMode.ADVENTURE);
            p.getInventory().clear();
            p.setMaxHealth(20);
            p.setHealth(20);

            for (PotionEffect effect : p.getActivePotionEffects())
                p.removePotionEffect(effect.getType());
        }

        AllStarsParty.gameManager.setInGame(false);
    }

   @EventHandler
   public void onPlayerRespawn(PlayerRespawnEvent event) {
        event.setRespawnLocation(new Location(AllStarsParty.world, AllStarsParty.CENTER_X, AllStarsParty.CENTER_Y + 2, AllStarsParty.CENTER_Z));
        event.getPlayer().setGameMode(GameMode.SPECTATOR);
   }
}
