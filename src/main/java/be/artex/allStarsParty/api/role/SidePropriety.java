package be.artex.allStarsParty.api.role;

import org.bukkit.ChatColor;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;
import be.artex.allStarsParty.api.descriptionBuilder.DescriptionBuilder;
import be.artex.allStarsParty.listener.PlayerListeners;

/**
 * Used for messages.
 */
public enum SidePropriety {
    TEAM("avec les ", "des "),
    ALONE(ChatColor.GOLD + "seul", "de "),
    LGB(ChatColor.GOLD + "seul", "du ");

    private final String objective;
    private final String determiner;

    /**
     * @param objective used by {@link DescriptionBuilder}
     * @param determiner used by {@link PlayerListeners#onPlayerDeath(PlayerDeathEvent)}
     */
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
