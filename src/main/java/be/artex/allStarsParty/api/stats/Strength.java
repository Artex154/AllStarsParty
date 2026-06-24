package be.artex.allStarsParty.api.stats;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Strength {
    private static final HashMap<Player, Integer> PLAYER_STRENGTH = new HashMap<>();

    public static void setPlayerStrength(Player player, int strength) {
        PLAYER_STRENGTH.put(player, strength);
    }

    public static int getPlayerStrength(Player player) {
        return PLAYER_STRENGTH.getOrDefault(player, 100);
    }

    public static void addStrengthToPlayer(Player player, int amount) {
        PLAYER_STRENGTH.put(player, getPlayerStrength(player) + amount);
    }

    public static void removeStrengthFromPlayer(Player player, int amount) {
        PLAYER_STRENGTH.put(player, getPlayerStrength(player) - amount);
    }
}
