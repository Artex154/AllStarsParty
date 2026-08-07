package be.artex.allStarsParty;

import be.artex.allStarsParty.api.GameManager;
import be.artex.allStarsParty.commands.ASCommand;
import be.artex.allStarsParty.commands.subCommands.*;
import be.artex.allStarsParty.listener.*;
import be.artex.allStarsParty.registry.ItemRegistry;
import be.artex.allStarsParty.registry.RoleRegistry;
import be.artex.allStarsParty.util.WorldUtil;
import org.bukkit.plugin.java.JavaPlugin;

public class AllStarsParty extends JavaPlugin {
    public static JavaPlugin instance;

    public static final GameManager gameManager = new GameManager();

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(new ConnectionsEventListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerListeners(), this);
        getServer().getPluginManager().registerEvents(new BlockListeners(), this);
        getServer().getPluginManager().registerEvents(new ItemListeners(), this);

        WorldUtil.setupSpawnArea();
        WorldUtil.setupWorldBorder();
        WorldUtil.defineGameRules();

        RoleRegistry.registerRoles();
        ItemRegistry.registerItems();

        new HelpSubCommand().register();
        new CompositionSubCommand().register();
        new RolesSubCommand().register();
        new EffectSubCommand().register();
        new SpecSubCommand().register();
        new StartSubCommand().register();
        new ForceStartSubCommand().register();
        new SelfRevealSubCommand().register();

        getCommand("as").setExecutor(new ASCommand());
    }
}
