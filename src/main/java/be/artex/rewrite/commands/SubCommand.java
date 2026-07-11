package be.artex.rewrite.commands;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class SubCommand {
    public static SubCommand.Manager manager = new Manager();

    public abstract String[] getArgument();
    public abstract void whenCalled(Player sender);
    public abstract String getDescription();

    public final void register() {
        manager.registeredSubCommands.add(this);
    }

    public static class Manager {
        private final List<SubCommand> registeredSubCommands = new ArrayList<>();

        public List<SubCommand> getRegisteredCommands() {
            return Collections.unmodifiableList(registeredSubCommands);
        }

        public @Nullable SubCommand getSubCommandFromArg(@NotNull String arg) {
            for (SubCommand command : registeredSubCommands)
                for (String args : command.getArgument())
                    if (args.equalsIgnoreCase(arg))
                        return command;

            return null;
        }
    }
}
