package be.artex.rewrite.listener;

import be.artex.allStarsParty.api.message.Message;
import be.artex.rewrite.AllStarsParty;
import be.artex.rewrite.registry.RoleRegistry;
import be.artex.rewrite.scoreboard.ScoreboardManager;
import be.artex.rewrite.api.role.Role;
import be.artex.rewrite.api.role.Side;
import be.artex.rewrite.util.StatValues;
import be.artex.rewrite.util.Stats;
import be.artex.rewrite.world.WorldUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class PlayerListeners implements Listener {
    private static final Role.Manager roleManager = Role.manager;
    public static final Map<Player, Integer> PLAYERS_KILL_AMOUNT = new HashMap<>();

    public static boolean hasLGBBonus = false;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Role playerRole = roleManager.getPlayerRole(player.getUniqueId());

        event.setDeathMessage("");

        if (playerRole != null) {
            Bukkit.broadcastMessage(
                    ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "\n All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.DARK_AQUA + player.getName() + ChatColor.WHITE + " est mort, son rôle était " + playerRole.getSide().getColor() + playerRole.getName() + ChatColor.WHITE + ".");

            playerRole.onDeath(event);

            Role.manager.removeAliveRole(playerRole);
            playerRole.getSide().removePlayer(player);
        }

        if (player.getKiller() != null) {
            Player killer = player.getKiller();

            int amountOfKills = PLAYERS_KILL_AMOUNT.getOrDefault(killer, 0);
            amountOfKills++;
            PLAYERS_KILL_AMOUNT.put(killer, amountOfKills);

            player.sendMessage(Message.info(ChatColor.DARK_AQUA + killer.getName() + ChatColor.WHITE + " possèdait " + ChatColor.LIGHT_PURPLE + (Math.round(killer.getHealth()) / 2) + " coeurs" + ChatColor.WHITE + "."));

            Role killerRole = Role.manager.getPlayerRole(killer.getUniqueId());

            if (killerRole != null)
                killerRole.onKill(event);
        }

        if (Side.ANTAGONISTES.getPlayers().isEmpty() && Role.manager.isRoleAlive(RoleRegistry.LGB) && !hasLGBBonus) {
            Role.manager.getPlayersWithRole(RoleRegistry.LGB).forEach(uuid -> {
                Player pl = Bukkit.getPlayer(uuid);
                Stats plStats = Stats.get(uuid);

                plStats.addBonus(StatValues.SPEED, 10);
                pl.sendMessage(Message.info("Plus aucun " + ChatColor.RED + "antagoniste " + ChatColor.WHITE + "est vivant. Vous écoper alors de " + ChatColor.YELLOW + "10%" + ChatColor.WHITE + " de " + ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "➤" + ChatColor.DARK_GRAY + "]" + ChatColor.YELLOW + " Vitesse" + ChatColor.WHITE + " supplémentaire."));
            });

            hasLGBBonus = true;
        }

        Side firstSide = roleManager.getRolesAlive().get(0).getSide();

        if (roleManager.isWonBy(firstSide, roleManager.getRolesAlive())) {
            Bukkit.broadcastMessage(getKillLeaderBoard());

            AllStarsParty.gameManager.end();

            Bukkit.broadcastMessage(Message.info("Victoire des " + firstSide.getColor() + firstSide.getName() + ChatColor.WHITE + ".") + "\n ");

            event.getDrops().clear();
        } else {
            for (ItemStack stack : event.getDrops()) {
                switch (stack.getType()) {
                    case DIAMOND_SWORD:
                    case IRON_SWORD:
                    case DIAMOND_HELMET:
                    case DIAMOND_CHESTPLATE:
                    case IRON_LEGGINGS:
                    case DIAMOND_BOOTS:
                    case DIAMOND_PICKAXE:
                    case CHEST:
                    case NETHER_STAR:
                    case BOW:
                        event.getDrops().remove(stack);

                    default:
                }
            }
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

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent event) {
        switch (event.getItemDrop().getItemStack().getType()) {
            case DIAMOND_SWORD:
            case IRON_SWORD:
            case DIAMOND_HELMET:
            case DIAMOND_CHESTPLATE:
            case IRON_LEGGINGS:
            case DIAMOND_BOOTS:
            case DIAMOND_PICKAXE:
            case NETHER_STAR:
            case BOW:
                event.setCancelled(true);

            case CHEST:
                event.getItemDrop().remove();

            default:
        }
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    private String getKillLeaderBoard() {
        Map<Player, Integer> allPlayersWithKills = new HashMap<>();

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            allPlayersWithKills.put(onlinePlayer, PLAYERS_KILL_AMOUNT.getOrDefault(onlinePlayer, 0));
        }

        StringBuilder killLeaderboard = new StringBuilder(ChatColor.GOLD + " \n" + ChatColor.BOLD + "» Classement du nombre de kills \n ");

        allPlayersWithKills.entrySet().stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue()))
                .forEach(entry -> {
                    Player p = entry.getKey();
                    int kills = entry.getValue();
                    Role playerRoleForKills = roleManager.getPlayerRole(p.getUniqueId());
                    String roleName = playerRoleForKills != null ? playerRoleForKills.getName() : "Inconnu";
                    String roleColor = playerRoleForKills != null ?
                            playerRoleForKills.getSide().getColor().toString() :
                            ChatColor.GRAY.toString();

                    killLeaderboard.append(ChatColor.GOLD + "" + ChatColor.BOLD + "- " + ChatColor.WHITE + p.getName() + " (" + roleColor + roleName + ChatColor.WHITE + "): " + ChatColor.GOLD + ChatColor.BOLD + kills + " kill(s)\n ");
                });

        return String.valueOf(killLeaderboard);
    }
}
