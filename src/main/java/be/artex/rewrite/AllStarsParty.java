package be.artex.rewrite;

import org.bukkit.plugin.java.JavaPlugin;

public class AllStarsParty extends JavaPlugin {
    public static JavaPlugin instance;

    @Override
    public void onEnable() {
        instance = this;
    }
}
