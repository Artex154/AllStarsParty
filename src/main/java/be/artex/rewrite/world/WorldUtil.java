package be.artex.rewrite.world;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WorldUtil {
    public static World world = Bukkit.getWorlds().get(0);
    public static final int CENTER_X = 172;
    public static final int CENTER_Y = 120;
    public static final int CENTER_Z = -1256;

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

    public static @NotNull SpawnLocations getRandomSpawnLocation() {
        List<SpawnLocations> spawnLocations = Arrays.asList(SpawnLocations.values());

        Collections.shuffle(spawnLocations);

        return spawnLocations.get(0);
    }

    public static void defineGameRules() {
        world.setGameRuleValue("naturalRegeneration", "false");
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("doFireTick", "false");
    }

}
