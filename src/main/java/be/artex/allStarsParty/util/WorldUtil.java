package be.artex.allStarsParty.util;

import org.bukkit.*;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class WorldUtil {
    public static World world = Bukkit.getWorlds().get(0);
    public static final int CENTER_X = 0;
    public static final int CENTER_Y = 120;
    public static final int CENTER_Z = 0;

    public static void setupSpawnArea() {
        final int HALF_SIZE = 6;
        final int WALL_HEIGHT = 4;

        for (int x = -HALF_SIZE; x <= HALF_SIZE; x++)
            for (int z = -HALF_SIZE; z <= HALF_SIZE; z++)
                world.getBlockAt(CENTER_X + x, CENTER_Y, CENTER_Z + z).setType(Material.GLASS);

        int outerLimit = HALF_SIZE + 1;

        for (int x = -outerLimit; x <= outerLimit; x++) {
            for (int z = -outerLimit; z <= outerLimit; z++) {
                if (!(x == -outerLimit || x == outerLimit || z == -outerLimit || z == outerLimit))
                    continue;

                for (int y = 0; y < WALL_HEIGHT; y++)
                    world.getBlockAt(CENTER_X + x, CENTER_Y + y, CENTER_Z + z).setType(Material.GLASS);
            }
        }
    }

    public static @NotNull Location getRandomSpawnLocation() {
        Random random = new Random();

        int x = random.nextInt(25);
        int z = random.nextInt(25);

        if (Math.random() < 0.5)
            x = -x;
        if (Math.random() < 0.5)
            z = -z;

        int y = world.getHighestBlockYAt(x, z);

        return new Location(world, x, y, z);
    }

    public static void setupWorldBorder() {
        final int BORDER_SIZE = 100;
        WorldBorder border = world.getWorldBorder();

        border.setCenter(0, 0);
        border.setSize(BORDER_SIZE);
    }

    public static void defineGameRules() {
        world.setGameRuleValue("naturalRegeneration", "false");
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("doFireTick", "false");
    }

}
