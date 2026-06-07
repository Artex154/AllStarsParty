package be.artex.allStarsParty.command;

import org.bukkit.entity.Player;

public abstract class SubCommand {
    public abstract String getArgument();
    public abstract void whenCalled(Player sender);
}
