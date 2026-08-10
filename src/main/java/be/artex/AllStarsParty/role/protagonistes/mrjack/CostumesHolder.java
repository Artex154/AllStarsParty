package be.artex.AllStarsParty.role.protagonistes.mrjack;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CostumesHolder {
    private static final Map<UUID, CostumesHolder> playerCostumesHolder = new HashMap<>();

    private CostumesType type;
    private long noelTicksLeft;
    private long citrouilleTicksLeft;
    private boolean isActive;

    private CostumesHolder() {
        type = CostumesType.NOEL;
        noelTicksLeft = 60*20L;
        citrouilleTicksLeft = 60*20L;
        isActive = false;
    }

    public static @NotNull CostumesHolder get(@NotNull UUID uuid) {
        return playerCostumesHolder.computeIfAbsent(uuid, x -> new CostumesHolder());
    }

    public static void remove(@NotNull UUID uuid) {
        playerCostumesHolder.remove(uuid);
    }

    public @NotNull CostumesType getType() {
        return type;
    }

    public long getTypeTicksLeft(@NotNull CostumesType type) {
        if (type == CostumesType.NOEL)
            return noelTicksLeft;
        else return citrouilleTicksLeft;
    }

    public void setTicksLeft(@NotNull CostumesType type, long ticks) {
        if (type == CostumesType.NOEL)
            noelTicksLeft = ticks;
        else citrouilleTicksLeft = ticks;
    }

    public boolean isActive() {
        return isActive;
    }

    public void removeTicks(@NotNull CostumesType type, long amount) {
        if (type == CostumesType.NOEL)
            noelTicksLeft -= amount;
        else citrouilleTicksLeft -= amount;
    }

    public void addTicks(@NotNull CostumesType type, long amount) {
        if (type == CostumesType.NOEL)
            noelTicksLeft += amount;
        else citrouilleTicksLeft += amount;
    }

    public void switchTypes() {
        if (type == CostumesType.NOEL)
            type = CostumesType.CITROUILLE;
        else type = CostumesType.NOEL;
    }

    public void deactivate(Player player) {
        type.onDeactivation(player);
        isActive = false;
    }

    public void activate(Player player) {
        type.onActivation(player);
        isActive = true;
    }
}
