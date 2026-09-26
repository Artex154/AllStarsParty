package be.artex.AllStarsParty.listener;

import be.artex.AllStarsParty.api.GameManager;
import be.artex.AllStarsParty.api.message.Message;
import be.artex.AllStarsParty.scoreboard.ScoreboardManager;
import be.artex.AllStarsParty.api.role.Role;
import be.artex.AllStarsParty.api.role.Side;
import be.artex.AllStarsParty.util.PlayerUtil;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.HashMap;
import java.util.Map;

public class PlayerListeners implements Listener {
    public static final Map<Player, Integer> PLAYERS_KILL_AMOUNT = new HashMap<>();

    public static boolean hasLGBBonus = false;

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        Role playerRole = Role.getPlayerRole(player);

        event.setDeathMessage("");

        GameManager.getAlivePlayers().remove(player);
        PlayerUtil.resetPlayerStates(player);

        if (playerRole != null) {
            Bukkit.broadcastMessage(Message.info(
                    ChatColor.DARK_AQUA + player.getName() + ChatColor.WHITE + " est mort, son rôle était " + playerRole.getSide().getColor() + playerRole.getName() + ChatColor.WHITE + ".",
            ChatColor.GOLD));

            playerRole.onDeath(event);

            playerRole.getSide().removePlayer(player);
        }

        if (player.getKiller() != null) {
            Player killer = player.getKiller();

            int amountOfKills = PLAYERS_KILL_AMOUNT.getOrDefault(killer, 0);
            amountOfKills++;
            PLAYERS_KILL_AMOUNT.put(killer, amountOfKills);

            player.sendMessage(Message.info(ChatColor.YELLOW + killer.getName() + ChatColor.WHITE + " possèdait " + ChatColor.LIGHT_PURPLE + (Math.round(killer.getHealth()) / 2) + " coeurs" + ChatColor.WHITE + "."));

            Role killerRole = Role.getPlayerRole(killer);

            if (killerRole != null)
                killerRole.onKill(event);
        }

        if (GameManager.getAlivePlayers().isEmpty()) {
            Bukkit.broadcastMessage(getKillLeaderBoard());

            GameManager.end();

            Bukkit.broadcastMessage(Message.info("La partie s'est finit en nulle.", ChatColor.GOLD) + "\n ");

            event.getDrops().clear();
            return;
        }

        Side firstSide = Role.getPlayerRole(GameManager.getAlivePlayers().get(0)).getSide();

        if (isWonBy(firstSide)) {
            Bukkit.broadcastMessage(getKillLeaderBoard());

            GameManager.end();

            Bukkit.broadcastMessage(Message.info("Victoire " + firstSide.getPropriety().getDeterminer() + firstSide.getColor() + firstSide.getName() + ChatColor.WHITE + ".", ChatColor.GOLD) + "\n ");

            event.getDrops().clear();
        } else {
            event.getDrops().removeIf(stack -> {
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
                        return true;
                    default:
                        return false;
                }
            });
        }

        ScoreboardManager.updateAllPlayerScoreboards();
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

    public static String getKillLeaderBoard() {
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
                    Role playerRoleForKills = Role.getPlayerRole(p);
                    String roleName = playerRoleForKills != null ? playerRoleForKills.getName() : "Inconnu";
                    String roleColor = playerRoleForKills != null ?
                            playerRoleForKills.getSide().getColor().toString() :
                            ChatColor.GRAY.toString();

                    killLeaderboard.append(ChatColor.GOLD + "" + ChatColor.BOLD + "- " + ChatColor.WHITE + p.getName() + " (" + roleColor + roleName + ChatColor.WHITE + "): " + ChatColor.GOLD + ChatColor.BOLD + kills + " kill(s)\n ");
                });

        return String.valueOf(killLeaderboard);
    }

    public static boolean isWonBy(Side side) {
        return GameManager.getAlivePlayers().stream()
                .map(Role::getPlayerRole)
                .allMatch(role -> role.getSide() == side);
    }
}
