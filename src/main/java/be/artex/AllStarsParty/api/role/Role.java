package be.artex.AllStarsParty.api.role;

import be.artex.AllStarsParty.api.item.CustomItem;
import be.artex.AllStarsParty.util.PlayerUtil;
import be.artex.AllStarsParty.util.StatValues;
import be.artex.AllStarsParty.util.Stats;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;
import java.util.stream.Collectors;

public abstract class Role {
    private static final List<Role> REGISTERED_ROLES = new ArrayList<>();
    private static final Map<Player, Role> PLAYERS_ROLE = new HashMap<>();

    private TextComponent desc = new TextComponent("<N/A>");

    /**
     * @return the role's name.
     */
    public abstract @NotNull String getName();

    /**
     * Initializes the role's description.
     * @return the role's initialized description.
     */
    protected abstract @NotNull TextComponent descriptionInitialization();

    /**
     * @return the role's side.
     */
    public abstract @NotNull Side getSide();

    /**
     * @return the strongness of the role's aura.
     */
    public abstract @NotNull Aura getAura();

    /**
     * This is called every tick.
     * @param player the plauer having the role.
     */
    public abstract void tick(Player player);

    /**
     * @return the role's base strength buff.
     */
    public int getBonusStrength() {
        return 0;
    }

    /**
     * @return the role's base resistance buff.
     */
    public int getBonusResistance() {
        return 0;
    }

    /**
     * @return the role's base speed buff.
     */
    public int getBonusSpeed() {
        return 0;
    }

    /**
     * @return the role's base health buff.
     */
    public int getBonusMaxHealth() {
        return 0;
    }

    /**
     * @return the color displayed in the TAB.
     */
    public ChatColor getDisplayColor() {
        return getSide().getColor();
    }

    /**
     * @return the role's name for the scoreboard. (Bypass for 32c limit in scoreboards)
     */
    public String getScoreboardName() {
        return getName();
    }

    /**
     * @return the list of all CustomItems the role has.
     */
    public List<CustomItem> getCustomItems() {
        return Collections.emptyList();
    }

    /**
     * Called when the role gets assigned.
     * @param player the player that gets the role.
     */
    public void whenAssigned(Player player) {
    }

    /**
     * Called when the player having the role makes a kill.
     * @param event the event.
     */
    public void onKill(PlayerDeathEvent event) {
    }

    /**
     * Called when the player having the role dies.
     * @param event the event.
     */
    public void onDeath(PlayerDeathEvent event) {
    }

    /**
     * Called when the player having the role hits another player.
     * @param player the hit player.
     * @param damager the player with the role.
     * @param damage the damage.
     * @param event the event.
     */
    public void onHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
    }

    /**
     * Called when the player having the role is hit.
     * @param player the player having the role.
     * @param damager the player who hit.
     * @param damage the damage.
     * @param event the event
     */
    public void whenHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
    }

    /**
     * @param player the player.
     * @param user the user, the player with the role.
     * @return the bonus damage based on the player and user.
     */
    public double bonusStrength(Player player, Player user) {
        return 0;
    }

    /**
     * @return the role's description.
     */
    public final @NotNull TextComponent getDescription() {
        return desc;
    }

    /**
     * Registers the role.
     */
    public final void register() {
        REGISTERED_ROLES.add(this);
        desc = descriptionInitialization();
    }

    /**
     * @return a set of all players having the role.
     */
    public final Set<Player> getPlayersWithRole() {
        return PLAYERS_ROLE.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), this))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    /**
     * @return the list of registered roles.
     */
    public static @NotNull @Unmodifiable List<Role> getRegisteredRoles() {
        return Collections.unmodifiableList(REGISTERED_ROLES);
    }

    /**
     * @param player the player.
     * @return the player's role.
     */
    public static @Nullable Role getPlayerRole(@NotNull Player player) {
        return PLAYERS_ROLE.get(player);
    }

    /**
     * Puts the player's role.
     * @param player the player.
     * @param role the role.
     */
    public static void setPlayerRole(@NotNull Player player, @NotNull Role role) {
        PLAYERS_ROLE.put(player, role);
    }

    /**
     * Removes the player's role.
     * @param player the player.
     */
    public static void removePlayerRole(@NotNull Player player) {
        PLAYERS_ROLE.remove(player);
    }

    /**
     * Assigns the role to the player. Applies the display color, sends the description to the player, gives all the custom, gives the bonus: max health, strength, resistance, speed.
     * @param player the player.
     */
    public void assignRoleToPlayer(@NotNull Player player) {
        setPlayerRole(player, this);
        PlayerUtil.setGlobalNameColor(player, this.getDisplayColor());
        player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "                                                                                \n");
        player.spigot().sendMessage(this.getDescription());
        player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.STRIKETHROUGH + "                                                                                \n");

        for (CustomItem i : this.getCustomItems())
            player.getInventory().addItem(i.getStack());

        player.setMaxHealth(20 + this.getBonusMaxHealth());
        player.setHealth(player.getMaxHealth());
        this.getSide().addPlayer(player);

        Stats playerStats = Stats.get(player.getUniqueId());
        playerStats.setBonus(StatValues.RESISTANCE, this.getBonusResistance());
        playerStats.setBonus(StatValues.STRENGTH, this.getBonusStrength());
        playerStats.setBonus(StatValues.SPEED, this.getBonusSpeed());

        this.whenAssigned(player);

        PlayerUtil.sendTitle(player, ChatColor.DARK_GRAY + "» " + this.getSide().getColor() + this.getName() + ChatColor.DARK_GRAY + " «", "", 20, 60, 20);
    }
}
