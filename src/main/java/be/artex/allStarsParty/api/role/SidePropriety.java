package be.artex.allStarsParty.api.role;

import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

public enum SidePropriety {
    TEAM("avec les ", "des "),
    ALONE(ChatColor.GOLD + "seul", "de "),
    LGB(ChatColor.GOLD + "seul", "du ");

    private final String objective;
    private final String determiner;

    SidePropriety(@NotNull String objective, @NotNull String determiner) {
        this.objective = objective;
        this.determiner = determiner;
    }

    public @NotNull String getObjective() {
        return objective;
    }

    public @NotNull String getDeterminer() {
        return determiner;
    }
}
