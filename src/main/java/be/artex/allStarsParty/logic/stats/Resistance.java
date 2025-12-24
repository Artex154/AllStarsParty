package be.artex.allStarsParty.logic.stats;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Resistance {
    private static final HashMap<Player, Integer> PLAYER_RESISTANCE = new HashMap<>();

    public static void setPlayerResistance(Player player, int resistance) {
        PLAYER_RESISTANCE.put(player, resistance);
    }

    public static int getPlayerResistance(Player player) {
        return PLAYER_RESISTANCE.getOrDefault(player, 100);
    }
}
