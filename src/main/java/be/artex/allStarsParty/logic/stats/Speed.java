package be.artex.allStarsParty.logic.stats;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Speed {
    private static final HashMap<Player, Integer> PLAYER_SPEED = new HashMap<>();

    private static final float PLAYER_BASE_WALK_SPEED = 0.2f;

    public static void setPlayerSpeed(Player player, float speed) {
        speed = PLAYER_BASE_WALK_SPEED * (speed / 100f);
        int percentage = Math.round((speed / PLAYER_BASE_WALK_SPEED) * 100);

        PLAYER_SPEED.put(player, percentage);

        player.setWalkSpeed(speed);
    }

    public static int getPlayerSpeed(Player player) {
        return PLAYER_SPEED.getOrDefault(player, 100);
    }

    public static void resetPlayerSpeed(Player player) {
        player.setWalkSpeed(PLAYER_BASE_WALK_SPEED);

        PLAYER_SPEED.put(player, 100);
    }
}
