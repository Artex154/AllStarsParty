package be.artex.AllStarsParty.api;

import be.artex.AllStarsParty.api.role.Role;
import be.artex.AllStarsParty.listener.BlockListeners;
import be.artex.AllStarsParty.listener.PlayerListeners;
import be.artex.AllStarsParty.util.PlayerUtil;
import be.artex.AllStarsParty.util.WorldUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GameManager {
    private static boolean inGame = false;
    private static final List<Player> alivePlayers = new ArrayList<>();

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
        alivePlayers.addAll(players);
    }

    public void end() {
        if (!inGame)
            return;

        inGame = false;

        Role.manager.finishGame();
        alivePlayers.clear();

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.teleport(new Location(WorldUtil.world, WorldUtil.CENTER_X, WorldUtil.CENTER_Y + 2, WorldUtil.CENTER_Z));
            p.setGameMode(GameMode.ADVENTURE);
        }

        BlockListeners.PLACED_BLOCKS.forEach(b -> b.setType(Material.AIR));
        BlockListeners.PLACED_BLOCKS.clear();
        PlayerListeners.PLAYERS_KILL_AMOUNT.clear();
        PlayerUtil.playersName.clear();
        PlayerUtil.playersColor.clear();
    }

    public void removePlayer(@NotNull Player player) {
        alivePlayers.remove(player);
    }

    public @NotNull List<Player> getAlivePlayers() {
        return alivePlayers;
    }
}
