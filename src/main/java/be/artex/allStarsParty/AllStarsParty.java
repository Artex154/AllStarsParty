package be.artex.allStarsParty;

import be.artex.allStarsParty.command.ASCommand;
import be.artex.allStarsParty.command.CommandManager;
import be.artex.allStarsParty.command.subCommands.*;
import be.artex.allStarsParty.listener.entity.DamageByEntityListener;
import be.artex.allStarsParty.listener.entity.EntityDamageListener;
import be.artex.allStarsParty.listener.inventory.InventoryClickListener;
import be.artex.allStarsParty.listener.player.*;
import be.artex.allStarsParty.listener.projectile.ProjectileListeners;
import be.artex.allStarsParty.manager.GUIManager;
import be.artex.allStarsParty.manager.GameManager;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public final class AllStarsParty extends JavaPlugin {
    public static JavaPlugin instance;

    public static GameManager gameManager = new GameManager();
    public static GUIManager GUIManager = new GUIManager();
    public static CommandManager commandManager = new CommandManager();

    public static final Map<UUID, FastBoard> boards = new HashMap<>();

    public static World world;
    public static final int CENTER_X = 0;
    public static final int CENTER_Y = 120;
    public static final int CENTER_Z = 0;

    @Override
    public void onEnable() {
        instance = this;
        world = Bukkit.getWorlds().get(0);

        setupSpawnArea();
        setupWorldBorder(world.getWorldBorder());

        defineGameRules();

        getCommand("as").setExecutor(new ASCommand());

        commandManager.registerSubCommand(new Composition());
        commandManager.registerSubCommand(new Start());
        commandManager.registerSubCommand(new Effect());
        commandManager.registerSubCommand(new Finish());
        commandManager.registerSubCommand(new Docs());

        getServer().getPluginManager().registerEvents(new ConnectionsEventListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractionListener(), this);
        getServer().getPluginManager().registerEvents(new DamageByEntityListener(), this);
        getServer().getPluginManager().registerEvents(new ItemDamageListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractionListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerBlockListeners(), this);
        getServer().getPluginManager().registerEvents(new ItemConsumeListener(), this);
        getServer().getPluginManager().registerEvents(new ProjectileListeners(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryClickListener(), this);
    }

    private void setupWorldBorder(WorldBorder border) {
        final int BORDER_SIZE = 100;

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
