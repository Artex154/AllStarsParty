package be.artex.allStarsParty.commands.subCommands;

import be.artex.allStarsParty.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpSubCommand extends SubCommand {
    @Override
    public String[] getArgument() {
        return new String[]{"help", "h", "commands", "command"};
    }

    @Override
    public void whenCalled(Player sender) {
        if (sender == null)
            return;

        StringBuilder str = new StringBuilder();

        str.append(ChatColor.GOLD + " \n" + ChatColor.BOLD + "» Liste des commandes \n ");

        SubCommand.manager.getRegisteredCommands().forEach(c -> {
            if (c.getDescription(sender.getPlayer()) != null) {
                str.append(ChatColor.WHITE + "- " + ChatColor.GOLD + ChatColor.BOLD + "/as " + c.getArgument()[0] + ChatColor.WHITE + ": " + c.getDescription(sender.getPlayer()) + "\n ");
            }
        });

        sender.sendMessage(String.valueOf(str));
    }

    @Override
    public String getDescription(Player player) {
        return "Donne la liste de toutes les commandes.";
    }
}
