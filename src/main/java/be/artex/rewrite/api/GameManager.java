package be.artex.rewrite.api;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.rewrite.api.role.Role;
import be.artex.rewrite.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
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

    public static void start() {
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
            p.teleport(new Location(AllStarsParty.world, AllStarsParty.CENTER_X, AllStarsParty.CENTER_Y + 2, AllStarsParty.CENTER_Z));
            p.setGameMode(GameMode.ADVENTURE);

            PlayerUtil.resetPlayerStates(p);
        }

    }

}
