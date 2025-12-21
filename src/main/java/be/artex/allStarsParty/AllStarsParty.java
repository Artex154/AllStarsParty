package be.artex.allStarsParty;

import be.artex.allStarsParty.listener.ConnectionsEventListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class AllStarsParty extends JavaPlugin {
    public static boolean inGame;
    public static JavaPlugin instance;

    @Override
    public void onEnable() {
        inGame = false;
        instance = this;

        getServer().getPluginManager().registerEvents(new ConnectionsEventListener(), this);
    }
}
