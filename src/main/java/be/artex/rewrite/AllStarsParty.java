package be.artex.rewrite;

import be.artex.rewrite.listener.ConnectionsEventListener;
import be.artex.rewrite.listener.EntityDamageByEntity;
import be.artex.rewrite.world.WorldUtil;
import org.bukkit.plugin.java.JavaPlugin;

public class AllStarsParty extends JavaPlugin {
    public static JavaPlugin instance;

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(new ConnectionsEventListener(), this);
        getServer().getPluginManager().registerEvents(new EntityDamageByEntity(), this);

        WorldUtil.setupWorldBorder();
        WorldUtil.setupSpawnArea();
    }
}
