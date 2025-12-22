package be.artex.allStarsParty;

import be.artex.allStarsParty.command.Composition;
import be.artex.allStarsParty.command.Start;
import be.artex.allStarsParty.gameLogic.listener.EntityDamageByEntityEventListener;
import be.artex.allStarsParty.gameLogic.listener.PlayerInteractEventListener;
import be.artex.allStarsParty.gameLogic.manager.GameManager;
import be.artex.allStarsParty.gameLogic.manager.ItemManager;
import be.artex.allStarsParty.gameLogic.manager.RoleManager;
import be.artex.allStarsParty.gameLogic.listener.ConnectionsEventListener;
import be.artex.allStarsParty.gameLogic.listener.PlayerDeathEventListener;
import be.artex.allStarsParty.role.Izuku;
import be.artex.allStarsParty.role.Tomura;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class AllStarsParty extends JavaPlugin {
    public static JavaPlugin instance;

    public static RoleManager roleManager = new RoleManager();
    public static GameManager gameManager = new GameManager();
    public static ItemManager itemManager = new ItemManager();

    public static final Map<UUID, FastBoard> boards = new HashMap<>();

    public static World world;
    public static final int CENTER_X = 0;
    public static final int CENTER_Y = 120;
    public static final int CENTER_Z = 0;

    @Override
    public void onEnable() {
        instance = this;
        world = Bukkit.getWorlds().get(0);

        gameManager.setMaxPlayerCount(2);

        setupSpawnArea();
        setupWorldBorder(world.getWorldBorder());

        defineGameRules();

        roleManager.registerRole(new Tomura());
        roleManager.registerRole(new Izuku());

        getCommand("composition").setExecutor(new Composition());
        getCommand("start").setExecutor(new Start());

        getServer().getPluginManager().registerEvents(new ConnectionsEventListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathEventListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractEventListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByEntityEventListener(), this);
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
