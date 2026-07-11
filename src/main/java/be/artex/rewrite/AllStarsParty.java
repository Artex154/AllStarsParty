package be.artex.rewrite;

import be.artex.rewrite.api.GameManager;
import be.artex.rewrite.listener.ConnectionsEventListener;
import be.artex.rewrite.listener.EntityDamageByEntityListener;
import be.artex.rewrite.listener.PlayerDeathListener;
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

        WorldUtil.setupSpawnArea();
    }
}
