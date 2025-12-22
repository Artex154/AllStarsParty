package be.artex.allStarsParty.gameLogic;

import be.artex.allStarsParty.AllStarsParty;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cooldown {
    private final long cooldownTime;
    private final ArrayList<Player> playerCooldowns;
    private final Map<Player, Long> cooldownStartTimes;

    private static final Map<String, Cooldown> cooldowns = new HashMap<>();

    private Cooldown(long cooldownTime) {
        this.cooldownTime = cooldownTime;
        this.playerCooldowns = new ArrayList<>();
        this.cooldownStartTimes = new HashMap<>();
    }

    public static Cooldown getCooldown(String cooldownID, long cooldownTime) {
        return cooldowns.computeIfAbsent(cooldownID, id -> new Cooldown(cooldownTime));
    }

    public boolean isPlayerInCooldown(Player player) {
        return playerCooldowns.contains(player);
    }

    public void putPlayerInCooldown(Player player) {
        playerCooldowns.add(player);
        cooldownStartTimes.put(player, System.currentTimeMillis());

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            playerCooldowns.remove(player);
            cooldownStartTimes.put(player, System.currentTimeMillis());
        }, cooldownTime);
    }

    public long getPlayerCooldownTimeLeft(Player player) {
        if (!isPlayerInCooldown(player))
            return 0;

        long startTime = cooldownStartTimes.getOrDefault(player, 0L);
        long elapsedMillis = System.currentTimeMillis() - startTime;

        long cooldownMillis = cooldownTime * 50L;

        long remainingMillis = cooldownMillis - elapsedMillis;

        return Math.max(0, remainingMillis / 1000);
    }

}
