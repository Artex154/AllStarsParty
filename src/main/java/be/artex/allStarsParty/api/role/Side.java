package be.artex.allStarsParty.api.role;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Every role has a team, a side to win with. This enum has all the sides.
 */
public enum Side {
    PROTAGONISTES("protagonistes", ChatColor.GREEN, SidePropriety.TEAM),
    ANTAGONISTES("antagonistes", ChatColor.RED, SidePropriety.TEAM),
    DIVERGENTS("divergents", ChatColor.YELLOW, SidePropriety.TEAM),
    MELENIA("Melenia", ChatColor.GOLD, SidePropriety.ALONE),
    LGB("Loup-Garou Blanc", ChatColor.GOLD, SidePropriety.LGB);

    private final ChatColor color;
    private final String name;
    private final SidePropriety propriety;
    private final List<Player> players;

    Side(@NotNull String name, @NotNull ChatColor color, @NotNull SidePropriety propriety) {
        this.name = name;
        this.color = color;
        this.propriety = propriety;
        this.players = new ArrayList<>();
    }

    public @NotNull ChatColor getColor() {
        return color;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull SidePropriety getPropriety() {
        return propriety;
    }

    public @NotNull List<Player> getPlayers() {
        return Collections.unmodifiableList(players);
    }

    public void addPlayer(@NotNull Player player) {
       players.add(player);
    }

    public void removePlayer(@NotNull Player player) {
        players.remove(player);
    }

    public void clearPlayers() {
        players.clear();
    }
}
