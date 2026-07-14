package be.artex.rewrite;

import be.artex.rewrite.api.role.Role;
import be.artex.rewrite.listener.PlayerDeathListener;
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
                ChatColor.GOLD + "" + ChatColor.BOLD + " - " + ChatColor.WHITE + "Joueurs: " + ChatColor.GOLD + ChatColor.BOLD + getPlayerCount(),
                ChatColor.GOLD + "" + ChatColor.BOLD + " - " + ChatColor.WHITE + "Rôle: " + ChatColor.GOLD + ChatColor.BOLD + getRoleNameFromPlayer(player),
                ChatColor.GOLD + "" + ChatColor.BOLD + " - " + ChatColor.WHITE + "Kills: " + ChatColor.GOLD + ChatColor.BOLD + getAmountOfKills(player),
                " ",
                ChatColor.GOLD + "play.azuriteuhc.fr"
        );
    }

    private static @NotNull String getRoleNameFromPlayer(@NotNull Player player) {
        Role role = Role.manager.getPlayerRole(player.getUniqueId());

        if (role == null)
            return "Aucun";

        return role.getName();
    }

    private static @NotNull String getPlayerCount() {
        if (AllStarsParty.gameManager.isInGame())
            return String.valueOf(Role.manager.getRolesAlive().size());

        return Bukkit.getOnlinePlayers().size() + "/" + AllStarsParty.gameManager.getMaxPlayerCount();
    }

    private static int getAmountOfKills(@NotNull Player player) {
        return PlayerDeathListener.playersKillAmount.getOrDefault(player.getUniqueId(), 0);
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
