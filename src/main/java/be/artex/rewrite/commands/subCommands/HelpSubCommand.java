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
        sender.sendMessage(ChatColor.GOLD + " \n" + ChatColor.BOLD + "» Liste des commandes \n" +
                ChatColor.WHITE + " - " + ChatColor.GOLD + ChatColor.BOLD + "/as help: " + ChatColor.WHITE + "donne la liste de toutes les commandes.\n ");

    }
}
