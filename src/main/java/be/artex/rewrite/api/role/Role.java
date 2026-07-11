package be.artex.rewrite.api.role;

import be.artex.rewrite.util.PlayerUtil;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public abstract class Role {
    public static final Role.Manager manager = new Manager();

    public abstract @NotNull String getName();
    public abstract @NotNull Side getSide();
    public abstract @NotNull TextComponent getDescription();

    public final void register() {
        manager.registeredRoles.add(this);
    }

    public static class Manager {
        private final List<Role> registeredRoles = new ArrayList<>();
        private final Map<UUID, Role> playersRole = new HashMap<>();
        private final List<Role> aliveRoles = new ArrayList<>(registeredRoles);

        public void setPlayerRole(@NotNull UUID uuid, @NotNull Role role) {
            playersRole.put(uuid, role);
        }

        public @Nullable Role getPlayerRole(@NotNull UUID uuid) {
            return playersRole.get(uuid);
        }

        public boolean isRoleAlive(@NotNull Role role) {
            return aliveRoles.contains(role);
        }

        public void removeAliveRole(@NotNull Role role) {
            aliveRoles.remove(role);
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
        }
    }
}
