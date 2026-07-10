package be.artex.rewrite;

import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ScoreboardManager {
    public static final Map<UUID, FastBoard> boards = new HashMap<>();

    public static void updateScoreboard(FastBoard board, Player player) {
        board.updateLines(
                ChatColor.GRAY + "  " + ChatColor.ITALIC + getDate(),
                " ",
                ChatColor.GOLD + "" + ChatColor.BOLD + "» Informations",
                ChatColor.GOLD + "" + ChatColor.BOLD + " - " + ChatColor.WHITE + "Joueurs: " + ChatColor.GOLD + ChatColor.BOLD + getOnlinePlayers(),
                ChatColor.GOLD + "" + ChatColor.BOLD + " - " + ChatColor.WHITE + "Rôle: " + ChatColor.GOLD + ChatColor.BOLD + getRoleNameFromPlayer(player),
                ChatColor.GOLD + "" + ChatColor.BOLD + " - " + ChatColor.WHITE + "Kills: " + ChatColor.GOLD + ChatColor.BOLD + getAmountOfKills(player),
                " ",
                ChatColor.GOLD + "play.azuriteuhc.fr"
        );
    }

    private static @NotNull String getRoleNameFromPlayer(@NotNull Player player) {
        return "Sasuke";
    }

    private static @NotNull String getOnlinePlayers() {
        return Bukkit.getOnlinePlayers().size() + "/" + 4;
    }

    private static int getAmountOfKills(@NotNull Player player) {
        return 1;
    }

    private static @NotNull String getDate() {
        LocalDate date = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return date.format(formatter);
    }

    public static void updateAllPlayerScoreboards() {
        for (Player player : Bukkit.getOnlinePlayers())
            updateScoreboard(boards.get(player.getUniqueId()), player);
    }

    public static void updateAllPlayerScoreboardsExcept(@NotNull Player p) {
        List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());

        onlinePlayers.remove(p);

        for (Player player : onlinePlayers)
            updateScoreboard(boards.get(player.getUniqueId()), player);
    }
}
