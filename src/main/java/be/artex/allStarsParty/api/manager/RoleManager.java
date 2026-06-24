package be.artex.allStarsParty.api.manager;

import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.Cooldown;
import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.stats.Resistance;
import be.artex.allStarsParty.api.stats.Speed;
import be.artex.allStarsParty.api.stats.Strength;
import be.artex.allStarsParty.role.AOT.reiner.TransformationReiner;
import be.artex.allStarsParty.role.HXH.kurapika.Serment;
import be.artex.allStarsParty.role.MHA.denki.Decharge;
import org.bukkit.entity.Player;

import java.util.*;

public class RoleManager {
    private final List<Role> registeredRoles = new ArrayList<>();
    private final Map<UUID, Role> playersRole = new HashMap<>();
    private final List<Role> aliveRoles = new ArrayList<>(registeredRoles);

    // Getters

    public List<Role> getRegisteredRoles() {
        return Collections.unmodifiableList(registeredRoles);
    }

    public List<Role> getRolesAlive() {
        return Collections.unmodifiableList(aliveRoles);
    }

    public Role getPlayerRole(Player player) {
        return playersRole.get(player.getUniqueId());
    }

    // Player Roles

    public void setPlayerRole(Player player, Role role) {
        playersRole.put(player.getUniqueId(), role);
    }

    public void clearPlayersRole() {
        playersRole.clear();
    }

    // Alive Roles

    public void removeAliveRole(Role role) {
        aliveRoles.remove(role);
    }

    public void resetAliveRoles() {
        aliveRoles.clear();
        aliveRoles.addAll(registeredRoles);
    }

    // Registration

    public Role registerRole(Role role) {
        registeredRoles.add(role);
        aliveRoles.add(role);

        return role;
    }

    // Assignment

    public void assignRolesRandomly(List<Player> players) {
        Collections.shuffle(registeredRoles);

        for (int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            Role r = registeredRoles.get(i);

            setPlayerRole(p, r);

            for (ASPItem item : r.getItems()) {
                p.getInventory().addItem(item.getStack());
            }

            Cooldown.clearAllCooldowns(p);

            Serment.playersInSerment.remove(p);
            TransformationReiner.transformedPlayer.remove(p);
            Decharge.electrocutedPlayers.remove(p);

            p.setPlayerListName(r.getSide().getColor() + p.getName());
            p.sendMessage(r.getDescription());
            p.setMaxHealth(r.getMaxHealth());
            p.setHealth(r.getMaxHealth());
            Strength.setPlayerStrength(p, r.getStrength());
            Speed.setPlayerSpeed(p, r.getSpeed());
            Resistance.setPlayerResistance(p, r.getResistance());
        }
    }
}
