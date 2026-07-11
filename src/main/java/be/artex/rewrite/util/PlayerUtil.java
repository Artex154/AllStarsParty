package be.artex.rewrite.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

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

    public static void resetPlayerStates(Player player) {
        player.getInventory().clear();
        player.setMaxHealth(20);
        player.setHealth(20);

        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());

        Stats stats = Stats.get(player.getUniqueId());

        stats.setStrengthBonus(0);
        stats.setResistanceBonus(0);
    }
}
