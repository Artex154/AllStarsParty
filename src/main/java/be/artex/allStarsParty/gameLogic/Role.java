package be.artex.allStarsParty.gameLogic;

import be.artex.allStarsParty.AllStarsParty;
import org.bukkit.entity.Player;

import java.util.*;

public abstract class Role {
    public abstract String getName();
    public abstract Side getSide();

    public void register() {
        this.getSide().getRoles().add(this);
        AllStarsParty.registeredRoles.add(this);
    }

    public static final Map<Player, Role> playersRole = new HashMap<>();

    public static Role getPlayerRole(Player player) {
        return playersRole.get(player);
    }

    public static void setPlayerRole(Player player, Role role) {
        playersRole.put(player, role);
    }

    public static void assignRolesRandomly(List<Role> roles, List<Player> players) {
        Collections.shuffle(roles);

        for (int i = 0; i < players.size(); i++) {
            setPlayerRole(players.get(i), roles.get(i));
        }
    }
}
