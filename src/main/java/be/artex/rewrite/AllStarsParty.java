package be.artex.rewrite;

import be.artex.rewrite.api.GameManager;
import be.artex.rewrite.commands.ASCommand;
import be.artex.rewrite.commands.subCommands.CompositionSubCommand;
import be.artex.rewrite.commands.subCommands.EffectSubCommand;
import be.artex.rewrite.commands.subCommands.HelpSubCommand;
import be.artex.rewrite.commands.subCommands.StartSubCommand;
import be.artex.rewrite.listener.*;
import be.artex.rewrite.registry.ItemRegistry;
import be.artex.rewrite.registry.RoleRegistry;
import be.artex.rewrite.world.WorldUtil;
import org.bukkit.plugin.java.JavaPlugin;

public class AllStarsParty extends JavaPlugin {
    public static JavaPlugin instance;

    public static final GameManager gameManager = new GameManager();

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(new ConnectionsEventListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByEntityListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new BlockListeners(), this);
        getServer().getPluginManager().registerEvents(new ItemListeners(), this);

        WorldUtil.setupSpawnArea();
        WorldUtil.defineGameRules();

        RoleRegistry.registerRoles();
        ItemRegistry.registerItems();

        new HelpSubCommand().register();
        new CompositionSubCommand().register();
        new EffectSubCommand().register();
        new StartSubCommand().register();

        getCommand("as").setExecutor(new ASCommand());
    }
}
