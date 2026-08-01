package be.artex.rewrite.api;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HitCountHolder {
    public static final Map<UUID, HitCountHolder> playerHolders = new HashMap<>();
    private int hitCount;

    private HitCountHolder() {
        hitCount = 0;
    }

    public static HitCountHolder get(@NotNull UUID uuid) {
        return playerHolders.computeIfAbsent(uuid, x -> new HitCountHolder());
    }

    public static void removePlayer(@NotNull UUID uuid) {
        playerHolders.remove(uuid);
    }

    public int getHitCount() {
        return hitCount;
    }

    public void addHit() {
        hitCount++;
    }

    public void resetCount() {
        hitCount = 0;
    }
}
