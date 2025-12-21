package be.artex.allStarsParty;

import be.artex.allStarsParty.listener.ConnectionsEventListener;
import org.bukkit.Bukkit;
import org.bukkit.World;
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

        World world = Bukkit.getWorlds().get(0);

        world.setGameRuleValue("naturalRegeneration", "false");
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("doMobSpawning", "false");

        getServer().getPluginManager().registerEvents(new ConnectionsEventListener(), this);
    }
}
