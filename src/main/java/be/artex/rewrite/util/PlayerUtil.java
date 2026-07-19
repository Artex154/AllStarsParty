package be.artex.rewrite.util;

import be.artex.rewrite.api.item.Cooldown;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

public class PlayerUtil {
    public static void setGlobalNameColor(Player player, ChatColor color) {
        String teamName = "color_" + color.getChar();

        for (Player p : Bukkit.getOnlinePlayers()) {
            Scoreboard board = p.getScoreboard();

            if (board == null) {
                board = Bukkit.getScoreboardManager().getNewScoreboard();
                p.setScoreboard(board);
            }

            Team team = board.getTeam(teamName);

            if (team == null) {
                team = board.registerNewTeam(teamName);
                team.setPrefix(color.toString());
            }

            for (ChatColor c : ChatColor.values()) {
                if (c != color && c.isColor()) {
                    Team oldTeam = board.getTeam("color_" + c.getChar());

                    if (oldTeam != null && oldTeam.hasEntry(player.getName()))
                        oldTeam.removeEntry(player.getName());
                }
            }

            team.addEntry(player.getName());
        }
    }

    public static @Nullable Player getPlayerTargetEntity(Player player, double maxDistance) {
        Location eye = player.getEyeLocation();
        Vector direction = eye.getDirection().normalize();
        World world = player.getWorld();

        double step = 0.2;

        for (double d = 0; d <= maxDistance; d += step) {
            Location point = eye.clone().add(direction.clone().multiply(d));

            if (point.getBlock().getType().isSolid())
                return null;

            for (Entity entity : world.getNearbyEntities(point, 0.5, 0.5, 0.5)) {
                if (entity.equals(player))
                    continue;

                if (entity instanceof Player)
                    return (Player) entity;
            }
        }

        return null;
    }

    public static void resetPlayerStates(Player player) {
        player.getInventory().clear();
        player.setMaxHealth(20);
        player.setHealth(20);
        PlayerUtil.setGlobalNameColor(player, ChatColor.WHITE);
        Cooldown.clearAllCooldowns(player);

        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());

        Stats.remove(player.getUniqueId());
    }
}
