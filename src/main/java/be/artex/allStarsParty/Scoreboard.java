package be.artex.allStarsParty;

import be.artex.allStarsParty.api.manager.GameManager;
import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.manager.RoleManager;
import be.artex.allStarsParty.registry.RoleRegistry;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Scoreboard {
    private static final String BORDER = ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "                        ";
    private static final GameManager gameManager = AllStarsParty.gameManager;
    private static final RoleManager roleManager = RoleRegistry.roleManager;

    public static void updateScoreboard(FastBoard board, Player player) {
        board.updateLines(
                BORDER,
                " ",
                ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Joueurs: " + ChatColor.WHITE + getOnlinePlayers(),
                ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Rôle: " + ChatColor.WHITE + getRoleNameFromPlayer(player),
                " ",
                BORDER,
                ChatColor.GRAY + "" + ChatColor.ITALIC + "Par Artex54"
        );
    }

    private static String getRoleNameFromPlayer(Player player) {
        Role role = roleManager.getPlayerRole(player);

        if (role == null)
            return "Aucun";

        return role.getName();
    }

    private static String getOnlinePlayers() {
        StringBuilder string = new StringBuilder();

        if (gameManager.isInGame())
            string.append(roleManager.getRolesAlive().size());
        else
            string.append(Bukkit.getOnlinePlayers().size());

        string.append("/").append(gameManager.getMaxPlayerCount());

        return String.valueOf(string);
    }
}
