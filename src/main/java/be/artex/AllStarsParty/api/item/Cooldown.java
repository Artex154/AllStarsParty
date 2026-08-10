package be.artex.AllStarsParty.api.item;

import be.artex.AllStarsParty.AllStarsParty;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Cooldown {
    private final long cooldownTime;
    private final ArrayList<Player> playerCooldowns;
    private final Map<Player, Long> cooldownStartTimes;
    private final String displayName;

    private static final Map<String, Cooldown> cooldowns = new HashMap<>();

    private Cooldown(long cooldownTime, String displayName) {
        this.cooldownTime = cooldownTime;
        this.playerCooldowns = new ArrayList<>();
        this.cooldownStartTimes = new HashMap<>();
        this.displayName = displayName;
    }

    public static @NotNull Cooldown getCooldown(String cooldownID, long cooldownTime) {
        return cooldowns.computeIfAbsent(cooldownID, id -> new Cooldown(cooldownTime, "<N/A>"));
    }

    public static @NotNull Cooldown getCooldown(String cooldownID, long cooldownTime, String name) {
        return cooldowns.computeIfAbsent(cooldownID, id -> new Cooldown(cooldownTime, name));
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

    public boolean isPlayerInCooldown(Player player) {
        return playerCooldowns.contains(player);
    }

    public static void clearAllCooldowns(Player player) {
        for (Cooldown cooldown : cooldowns.values()) {
            cooldown.playerCooldowns.remove(player);
            cooldown.cooldownStartTimes.remove(player);
        }
    }

    public void putPlayerInCooldown(Player player) {
        if (isPlayerInCooldown(player))
            return;

        long currentStartTime = System.currentTimeMillis();

        playerCooldowns.add(player);
        cooldownStartTimes.put(player, currentStartTime);

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            if (currentStartTime != cooldownStartTimes.get(player))
                return;

            player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + this.displayName + ChatColor.WHITE + " n'est plus en cooldown.");

            playerCooldowns.remove(player);
            cooldownStartTimes.remove(player);
        }, cooldownTime);
    }

    public void putPlayerInCooldownWithoutEndMessage(Player player) {
        if (isPlayerInCooldown(player))
            return;

        long currentStartTime = System.currentTimeMillis();

        playerCooldowns.add(player);
        cooldownStartTimes.put(player, currentStartTime);

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            if (currentStartTime != cooldownStartTimes.get(player))
                return;

            playerCooldowns.remove(player);
            cooldownStartTimes.remove(player);
        }, cooldownTime);
    }

    public void removePlayerCooldown(Player player) {
        playerCooldowns.remove(player);
        cooldownStartTimes.remove(player);
    }
}
