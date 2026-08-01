package be.artex.rewrite.api;

import be.artex.rewrite.api.role.Role;
import be.artex.rewrite.listener.BlockListeners;
import be.artex.rewrite.listener.PlayerListeners;
import be.artex.rewrite.role.solo.malenia.Malenia;
import be.artex.rewrite.util.PlayerUtil;
import be.artex.rewrite.world.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private static boolean inGame = false;

    public int getMaxPlayerCount() {
        return Role.manager.getRegisteredRoles().size();
    }

    public boolean isInGame() {
        return inGame;
    }

    public void start(List<Player> players) {
        if (inGame)
            return;

        inGame = true;

        Role.manager.startGame(players);
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
        PlayerListeners.PLAYERS_KILL_AMOUNT.clear();
    }

}
