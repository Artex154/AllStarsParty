package be.artex.allStarsParty;

import be.artex.allStarsParty.listener.ConnectionsEventListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class AllStarsParty extends JavaPlugin {
    public static JavaPlugin instance;
    public static boolean inGame;
    public static int maxPlayers;

    @Override
    public void onEnable() {
        instance = this;
        inGame = false;
        maxPlayers = 4;

        getServer().getPluginManager().registerEvents(new ConnectionsEventListener(), this);
    }
}
