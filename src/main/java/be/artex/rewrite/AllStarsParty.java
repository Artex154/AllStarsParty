package be.artex.rewrite;

import be.artex.rewrite.api.GameManager;
import be.artex.rewrite.commands.ASCommand;
import be.artex.rewrite.commands.subCommands.CompositionSubCommand;
import be.artex.rewrite.commands.subCommands.EffectSubCommand;
import be.artex.rewrite.commands.subCommands.HelpSubCommand;
import be.artex.rewrite.commands.subCommands.StartSubCommand;
import be.artex.rewrite.listener.BlockListeners;
import be.artex.rewrite.listener.ConnectionsEventListener;
import be.artex.rewrite.listener.EntityDamageByEntityListener;
import be.artex.rewrite.listener.PlayerDeathListener;
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

        WorldUtil.setupSpawnArea();
        WorldUtil.defineGameRules();

        RoleRegistry.registerRoles();

        new HelpSubCommand().register();
        new CompositionSubCommand().register();
        new StartSubCommand().register();
        new EffectSubCommand().register();

        getCommand("as").setExecutor(new ASCommand());
    }
}
