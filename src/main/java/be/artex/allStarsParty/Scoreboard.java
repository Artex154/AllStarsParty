package be.artex.allStarsParty;

import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Scoreboard {
    private static final String BORDER = ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "                        ";

    public static void updateScoreboard(FastBoard board, Player player) {
        board.updateLines(
                BORDER,
                " ",
                ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Joueurs: " + ChatColor.WHITE  + Bukkit.getOnlinePlayers().size() + "/" + AllStarsParty.maxPlayers,
                ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Rôle: " + ChatColor.WHITE + "Aucun",
                " ",
                BORDER,
                ChatColor.GRAY + "" + ChatColor.ITALIC + "Par Artex54"
        );
    }
}
