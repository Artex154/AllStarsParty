package be.artex.allStarsParty;

import be.artex.allStarsParty.listener.ConnectionsEventListener;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.plugin.java.JavaPlugin;

public final class AllStarsParty extends JavaPlugin {
    public static JavaPlugin instance;
    public static boolean inGame;
    public static int maxPlayers;

    public static World world;
    public static final int CENTER_X = 0;
    public static final int CENTER_Y = 120;
    public static final int CENTER_Z = 0;

    @Override
    public void onEnable() {
        instance = this;
        inGame = false;
        maxPlayers = 4;
        world = Bukkit.getWorlds().get(0);

        setupSpawnArea();
        setupWorldBorder(world.getWorldBorder());

        defineGameRules();

        getServer().getPluginManager().registerEvents(new ConnectionsEventListener(), this);
    }

    private void setupWorldBorder(WorldBorder border) {
        final int BORDER_SIZE = 50;

        border.setCenter(0, 0);
        border.setSize(BORDER_SIZE);
    }

    private void setupSpawnArea() {
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

    private void defineGameRules() {
        world.setGameRuleValue("naturalRegeneration", "false");
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("doMobSpawning", "false");
    }
}
