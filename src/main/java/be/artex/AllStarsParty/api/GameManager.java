package be.artex.AllStarsParty.api;

import be.artex.AllStarsParty.AllStarsParty;
import be.artex.AllStarsParty.api.role.Role;
import be.artex.AllStarsParty.api.role.Side;
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
import java.util.Collections;
import java.util.List;

public class GameManager {
    private static boolean inGame = false;

    private static final List<Player> alivePlayers = new ArrayList<>();
    private static final List<Role> aliveRoles = new ArrayList<>();

    public static int getMaxPlayerCount() {
        return Role.getRegisteredRoles().size();
    }

    public static boolean isInGame() {
        return inGame;
    }

    public static void start(List<Player> players) {
        if (inGame)
            return;

        inGame = true;

        List<Role> registeredRoles = Role.getRegisteredRoles();

        aliveRoles.addAll(registeredRoles);

        if (players.size() != registeredRoles.size()) {
            Collections.shuffle(aliveRoles);

            int i = aliveRoles.size() - players.size();

            aliveRoles.subList(0, i).clear();
        }

        List<Role> registeredRolesCopy = new ArrayList<>(registeredRoles);

        Collections.shuffle(registeredRolesCopy);

        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            Role r = registeredRolesCopy.get(i);

            r.assignRoleToPlayer(p);
        }

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            Bukkit.getScheduler().runTaskTimer(AllStarsParty.instance, () -> {

            }, 0L, 1L);
        }, 1);

        alivePlayers.addAll(players);
    }

    public static void end() {
        if (!inGame)
            return;

        inGame = false;

        alivePlayers.clear();
        aliveRoles.clear();

        for (Side side : Side.values())
            side.clearPlayers();

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

    public static void removePlayer(@NotNull Player player) {
        alivePlayers.remove(player);
    }

    public static @NotNull List<Player> getAlivePlayers() {
        return alivePlayers;
    }

}
