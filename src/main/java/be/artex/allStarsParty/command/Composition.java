package be.artex.allStarsParty.command;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.TextUtil;
import be.artex.allStarsParty.logic.Role;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Composition implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player))
            return true;

        Player player = (Player) commandSender;

        StringBuilder message =
                new StringBuilder(TextUtil.BORDER + "\n" +
                        ChatColor.DARK_AQUA + ChatColor.BOLD + "COMPOSITION:");

        for (Role role : AllStarsParty.roleManager.getRegisteredRoles())
            message.append("\n ").append(role.getSide().getColor()).append(role.getName());

        message.append("\n").append(TextUtil.BORDER);

        player.sendMessage(String.valueOf(message));

        return true;
    }
}
