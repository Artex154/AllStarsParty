package be.artex.allStarsParty.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandManager {
    private static final List<SubCommand> registeredSubCommands = new ArrayList<>();

    public List<SubCommand> getRegisteredRoles() {
        return Collections.unmodifiableList(registeredSubCommands);
    }

    public void registerSubCommand(SubCommand command) {
        registeredSubCommands.add(command);
    }

    public SubCommand getSubCommandFromArg(String arg) {
        for (SubCommand command : registeredSubCommands) {
            if (command.getArgument().equalsIgnoreCase(arg))
                return command;
        }

        return null;
    }
}
