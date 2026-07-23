package be.artex.allstars;

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
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class AllStarsParty extends JavaPlugin {
    private static AllStarsParty INSTANCE;

    public static final GameManager gameManager = new GameManager();

    @Override
    public void onEnable() {
        if (INSTANCE != null)
            throw new IllegalStateException("Multiple instances of AllStartParty exist!");
        INSTANCE = this;

        this.registerListener(
                new ConnectionsEventListener(),
                new EntityDamageByEntityListener(),
                new PlayerDeathListener(),
                new BlockListeners(),
                new ItemListeners()
        );

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

    private void registerListener(Listener... listeners) {
        PluginManager manager = this.getServer().getPluginManager();

        for (Listener listener : listeners)
            manager.registerEvents(listener, this);
    }

    public static AllStarsParty getInstance() {
        return INSTANCE;
    }
}
