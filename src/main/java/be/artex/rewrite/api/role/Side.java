package be.artex.rewrite.api.role;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public enum Side {
    PROTAGONISTES("Protagonistes", ChatColor.GREEN),
    ANTAGONISTES("Antagonistes", ChatColor.RED);

    private final ChatColor color;
    private final String name;

    Side(@NotNull String name, @NotNull ChatColor color) {
        this.name = name;
        this.color = color;
    }

    public @NotNull ChatColor getColor() {
        return color;
    }

    public @NotNull String getName() {
        return name;
    }
}
