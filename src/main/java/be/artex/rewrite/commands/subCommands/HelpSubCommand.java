package be.artex.rewrite.commands.subCommands;

import be.artex.rewrite.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpSubCommand extends SubCommand {
    @Override
    public String[] getArgument() {
        return new String[]{"help", "h", "commands", "command"};
    }

    @Override
    public void whenCalled(Player sender) {
        StringBuilder str = new StringBuilder();

        str.append(ChatColor.GOLD + " \n" + ChatColor.BOLD + "» Liste des commandes \n");

        SubCommand.manager.getRegisteredCommands().forEach(c ->
                str.append(ChatColor.WHITE + " - " + ChatColor.GOLD + ChatColor.BOLD + "/as " + c.getArgument()[0] + ChatColor.WHITE + ": " + c.getDescription()
        ));

        str.append("\n ");

        sender.sendMessage(String.valueOf(str));
    }

    @Override
    public String getDescription() {
        return "Donne la liste de toutes les commandes.";
    }
}
