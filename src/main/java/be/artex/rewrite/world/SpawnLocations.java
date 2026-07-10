package be.artex.rewrite.world;

import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public enum SpawnLocations {
    A(137, 23, -1217),
    B(151, 21, -1252),
    C(170, 21, -1277),
    D(203, 22, -1274),
    E(209, 22, -1245),
    F(187, 22, -1232),
    G(163, 22, -1243),
    H(132, 23, -1231),
    I(144, 21, -1267),
    J(184, 23, -1278);

    private final double x;
    private final double y;
    private final double z;

    SpawnLocations(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public @NotNull Location getLocation() {
        return new Location(WorldUtil.world, x, y, z);
    }
}
