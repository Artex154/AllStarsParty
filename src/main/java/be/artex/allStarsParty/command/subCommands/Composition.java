package be.artex.allStarsParty.command.subCommands;

import be.artex.allStarsParty.TextUtil;
import be.artex.allStarsParty.command.SubCommand;
import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.registry.RoleRegistry;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Composition extends SubCommand {
    @Override
    public String getArgument() {
        return "composition";
    }

    @Override
    public void whenCalled(Player sender) {
        StringBuilder message =
                new StringBuilder(TextUtil.BORDER + "\n" +
                        ChatColor.DARK_AQUA + ChatColor.BOLD + "COMPOSITION:");

        for (Role role : RoleRegistry.roleManager.getRegisteredRoles())
            message.append("\n ").append(role.getSide().getColor()).append(role.getName());

        message.append("\n").append(TextUtil.BORDER);

        sender.sendMessage(String.valueOf(message));
    }
}
