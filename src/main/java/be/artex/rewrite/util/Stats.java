package be.artex.rewrite.util;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Stats {
    private static final Map<UUID, Stats> PLAYERS_STATS = new HashMap<>();

    private double strengthBonus;
    private double resistanceBonus;

    private Stats() {
        this.strengthBonus = 0;
        this.resistanceBonus = 0;
    }

    public static @NotNull Stats get(@NotNull UUID uuid) {
        return PLAYERS_STATS.computeIfAbsent(uuid, ignored -> new Stats());
    }

    public double getStrengthBonus() {
        return this.strengthBonus;
    }

    public double getResistanceBonus() {
        return this.resistanceBonus;
    }

    public void setStrengthBonus(double strengthBonus) {
        this.strengthBonus = strengthBonus;
    }

    public void setResistanceBonus(double resistanceBonus) {
        this.resistanceBonus = resistanceBonus;
    }

    public void addStrength(double amount) {
        this.strengthBonus += amount;
    }

    public void addResistance(double amount) {
        this.resistanceBonus += amount;
    }

    public static void remove(@NotNull UUID uuid) {
        PLAYERS_STATS.remove(uuid);
    }
}
