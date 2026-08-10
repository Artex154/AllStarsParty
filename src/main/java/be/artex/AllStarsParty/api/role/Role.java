package be.artex.AllStarsParty.api.role;

import be.artex.AllStarsParty.AllStarsParty;
import be.artex.AllStarsParty.api.item.CustomItem;
import be.artex.AllStarsParty.util.PlayerUtil;
import be.artex.AllStarsParty.util.StatValues;
import be.artex.AllStarsParty.util.Stats;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Role {
    public static final Role.Manager manager = Manager.get(AllStarsParty.instance);
    private TextComponent desc = new TextComponent("<N/A>");

    public abstract @NotNull String getName();
    public abstract @NotNull TextComponent descriptionInitialization();
    public abstract @NotNull Side getSide();
    public abstract @NotNull Aura getAura();

    public int getBonusStrength() {
        return 0;
    }

    public int getBonusResistance() {
        return 0;
    }

    public int getBonusSpeed() {
        return 0;
    }

    public int getBonusMaxHealth() {
        return 0;
    }

    public ChatColor getDisplayColor() {
        return getSide().getColor();
    }

    public String getScoreboardName() {
        return getName();
    }

    public List<CustomItem> getCustomItems() {
        return Collections.emptyList();
    }

    public void whenAssigned(Player player) {
    }

    public void onKill(PlayerDeathEvent event) {
    }

    public void onDeath(PlayerDeathEvent event) {
    }

    public void onHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
    }

    /**
     * @param player the damaged player.
     * @param damager the damager (the player with the role).
     * @return bonus damage as a percentage.
     */
    public double bonusStrength(Player player, Player damager) {
        return 0;
    }

    public void whenHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
    }

    public final void register() {
        manager.registeredRoles.add(this);
        desc = descriptionInitialization();
    }

    public final @NotNull TextComponent getDescription() {
        return desc;
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

        public Set<UUID> getPlayersWithRole(Role role) {
            return playersRole.entrySet()
                    .stream()
                    .filter(entry -> Objects.equals(entry.getValue(), role))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toSet());
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

            if (players.size() != registeredRoles.size()) {
                Collections.shuffle(aliveRoles);

                int i = aliveRoles.size() - players.size();

                aliveRoles.subList(0, i).clear();
            }

            assignRolesRandomly(players, aliveRoles);
        }

        public void finishGame() {
            aliveRoles.clear();

            for (Side side : Side.values())
                side.clearPlayers();
        }

        public void assignRolesRandomly(@NotNull List<Player> players, @NotNull List<Role> roles) {
            List<Role> registeredRolesCopy = new ArrayList<>(roles);

            Collections.shuffle(registeredRolesCopy);

            for (int i = 0; i < players.size(); i++) {
                Player p = players.get(i);
                Role r = registeredRolesCopy.get(i);

                assignRoleToPlayer(p, r);
            }
        }

        public void assignRoleToPlayer(@NotNull Player player, @NotNull Role role) {
            setPlayerRole(player.getUniqueId(), role);
            PlayerUtil.setGlobalNameColor(player, role.getDisplayColor());
            player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "                                                                                \n");
            player.spigot().sendMessage(role.getDescription());
            player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "                                                                                \n");

            for (CustomItem i : role.getCustomItems()) {
                player.getInventory().addItem(i.getStack());
            }

            player.setMaxHealth(20 + role.getBonusMaxHealth());
            player.setHealth(player.getMaxHealth());
            role.getSide().addPlayer(player);

            Stats playerStats = Stats.get(player.getUniqueId());
            playerStats.setBonus(StatValues.RESISTANCE, role.getBonusResistance());
            playerStats.setBonus(StatValues.STRENGTH, role.getBonusStrength());
            playerStats.setBonus(StatValues.SPEED, role.getBonusSpeed());

            role.whenAssigned(player);

            PlayerUtil.sendTitle(player, ChatColor.DARK_GRAY + "» " + role.getSide().getColor() + role.getName() + ChatColor.DARK_GRAY + " «", "", 20, 60, 20);
        }
    }
}
