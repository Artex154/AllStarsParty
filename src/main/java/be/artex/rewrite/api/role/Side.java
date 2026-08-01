package be.artex.rewrite.api.role;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public enum Side {
    PROTAGONISTES("Protagonistes", ChatColor.GREEN),
    ANTAGONISTES("Antagonistes", ChatColor.RED),
    MELENIA("Melenia", ChatColor.GOLD),
    LGB("Loup-Garou Blanc", ChatColor.GOLD);

    private final ChatColor color;
    private final String name;
    private final List<Player> players;

    Side(@NotNull String name, @NotNull ChatColor color) {
        this.name = name;
        this.color = color;
        this.players = new ArrayList<>();
    }

    public @NotNull ChatColor getColor() {
        return color;
    }

    public @NotNull String getName() {
        return name;
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
