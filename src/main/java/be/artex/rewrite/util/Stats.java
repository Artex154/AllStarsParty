package be.artex.rewrite.util;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Stats {
    private static final Map<UUID, Stats> PLAYERS_STATS = new HashMap<>();

    private final UUID player;
    private double strengthBonus;
    private double resistanceBonus;
    private double speedBonus;

    private Stats(UUID player) {
        this.player = player;
        this.strengthBonus = 0;
        this.resistanceBonus = 0;
        this.speedBonus = 0;
    }

    public static @NotNull Stats get(@NotNull UUID uuid) {
        return PLAYERS_STATS.computeIfAbsent(uuid, Stats::new);
    }

    public double getBonus(@NotNull StatValues stat) {
        switch (stat) {
            case SPEED:
                return speedBonus;
            case RESISTANCE:
                return resistanceBonus;
            case STRENGTH:
                return strengthBonus;
            default:
                return 0;
        }
    }

    public void setBonus(StatValues stat, double value) {
        switch (stat) {
            case SPEED:
                this.speedBonus = value;
                applySpeed(Bukkit.getPlayer(this.player));
                break;
            case RESISTANCE:
                this.resistanceBonus = value;
                break;
            case STRENGTH:
                this.strengthBonus = value;
                break;
        }
    }

    public void addBonus(StatValues stat, double amount) {
        switch (stat) {
            case SPEED:
                this.speedBonus += amount;
                applySpeed(Bukkit.getPlayer(this.player));
                break;
            case RESISTANCE:
                this.resistanceBonus += amount;
                break;
            case STRENGTH:
                this.strengthBonus += amount;
                break;
        }
    }

    public static void remove(@NotNull UUID uuid) {
        PLAYERS_STATS.remove(uuid);
    }

    private void applySpeed(Player player) {
        float totalSpeed = (float) (0.2 * (1 + this.speedBonus / 100.0));

        player.setWalkSpeed(totalSpeed);
    }
}
