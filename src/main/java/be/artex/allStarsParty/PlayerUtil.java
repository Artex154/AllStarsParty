package be.artex.allStarsParty;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerUtil {
    public static void sendMessageToAllPlayers(String message) {
        for (Player player : Bukkit.getOnlinePlayers())
            player.sendMessage(message);
    }

    public static void updateAllPlayerScoreboards() {
        for (Player player : Bukkit.getOnlinePlayers())
            Scoreboard.updateScoreboard(AllStarsParty.boards.get(player.getUniqueId()), player);
    }
}
