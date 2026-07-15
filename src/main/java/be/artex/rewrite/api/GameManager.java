package be.artex.rewrite.api;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.rewrite.api.role.Role;
import be.artex.rewrite.listener.BlockListeners;
import be.artex.rewrite.listener.PlayerDeathListener;
import be.artex.rewrite.util.PlayerUtil;
import be.artex.rewrite.world.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class GameManager {
    private static boolean inGame = false;

    public int getMaxPlayerCount() {
        return Role.manager.getRegisteredRoles().size();
    }

    public boolean isInGame() {
        return inGame;
    }

    public void start() {
        if (inGame)
            return;

        inGame = true;

        Role.manager.startGame(new ArrayList<>(Bukkit.getOnlinePlayers()));
    }

    public void end() {
        if (!inGame)
            return;

        inGame = false;

        Role.manager.finishGame();

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.teleport(new Location(WorldUtil.world, WorldUtil.CENTER_X, WorldUtil.CENTER_Y + 2, WorldUtil.CENTER_Z));
            p.setGameMode(GameMode.ADVENTURE);

            PlayerUtil.resetPlayerStates(p);
        }

        BlockListeners.PLACED_BLOCKS.forEach(b -> b.setType(Material.AIR));
        BlockListeners.PLACED_BLOCKS.clear();
        PlayerDeathListener.PLAYERS_KILL_AMOUNT.clear();

    }

}
