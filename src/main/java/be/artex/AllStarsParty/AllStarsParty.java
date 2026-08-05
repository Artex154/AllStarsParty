package be.artex.AllStarsParty;

import be.artex.AllStarsParty.api.GameManager;
import be.artex.AllStarsParty.commands.ASCommand;
import be.artex.AllStarsParty.commands.subCommands.*;
import be.artex.AllStarsParty.listener.*;
import be.artex.AllStarsParty.registry.ItemRegistry;
import be.artex.AllStarsParty.registry.RoleRegistry;
import be.artex.AllStarsParty.world.WorldUtil;
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
