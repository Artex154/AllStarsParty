package be.artex.rewrite.api.role;

import be.artex.rewrite.AllStarsParty;
import be.artex.rewrite.util.PlayerUtil;
import be.artex.rewrite.util.StatValues;
import be.artex.rewrite.util.Stats;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

public abstract class Role {
    public static final Role.Manager manager = Manager.get(AllStarsParty.instance);

    public abstract @NotNull String getName();
    public abstract @NotNull Side getSide();
    public abstract @NotNull TextComponent getDescription();

    public int getBonusStrength() {
        return 0;
    }

    public int getBonusResistance() {
        return 0;
    }

    public int getBonusSpeed() {
        return 0;
    }

    public final void register() {
        manager.registeredRoles.add(this);
    }

    public static class Manager {
        private static final Map<Plugin, Manager> managers = new HashMap<>();

        private final List<Role> registeredRoles = new ArrayList<>();
        private final Map<UUID, Role> playersRole = new HashMap<>();
        private final List<Role> aliveRoles = new ArrayList<>();

        private Manager() {
        }

        public static @NotNull Manager get(@NotNull Plugin plugin) {
            return managers.computeIfAbsent(plugin, ignored -> new Manager());
        }

        public void setPlayerRole(@NotNull UUID uuid, @NotNull Role role) {
            playersRole.put(uuid, role);
        }

        public @Nullable Role getPlayerRole(@NotNull UUID uuid) {
            return playersRole.get(uuid);
        }

        public @NotNull @Unmodifiable List<Role> getRegisteredRoles() {
            return Collections.unmodifiableList(registeredRoles);
        }

        public @NotNull @Unmodifiable List<Role> getRolesAlive() {
            return Collections.unmodifiableList(aliveRoles);
        }

        public boolean isRoleAlive(@NotNull Role role) {
            return aliveRoles.contains(role);
        }

        public boolean isWonBy(Side side, List<Role> aliveRoles) {
            return aliveRoles.stream().allMatch(role -> role.getSide() == side);
        }

        public void removeAliveRole(@NotNull Role role) {
            aliveRoles.remove(role);
        }

        public void removePlayerRole(@NotNull UUID player) {
            playersRole.remove(player);
        }

        public void startGame(List<Player> players) {
            aliveRoles.addAll(registeredRoles);
            assignRolesRandomly(players);
        }

        public void finishGame() {
            aliveRoles.clear();
        }

        public void assignRolesRandomly(@NotNull List<Player> players) {
            List<Role> registeredRolesCopy = new ArrayList<>(registeredRoles);

            Collections.shuffle(registeredRolesCopy);

            for (int i = 0; i < players.size(); i++) {
                Player p = players.get(i);
                Role r = registeredRolesCopy.get(i);

                assignRoleToPlayer(p, r);
            }
        }

        public void assignRoleToPlayer(@NotNull Player player, @NotNull Role role) {
            setPlayerRole(player.getUniqueId(), role);
            PlayerUtil.setGlobalNameColor(player, role.getSide().getColor());
            player.spigot().sendMessage(role.getDescription());

            Stats playerStats = Stats.get(player.getUniqueId());
            playerStats.setBonus(StatValues.RESISTANCE, role.getBonusResistance());
            playerStats.setBonus(StatValues.STRENGTH, role.getBonusStrength());
            playerStats.setBonus(StatValues.SPEED, role.getBonusSpeed());
        }
    }
}
