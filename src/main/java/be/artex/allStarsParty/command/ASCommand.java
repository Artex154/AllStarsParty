package be.artex.allStarsParty.command;

import be.artex.allStarsParty.AllStarsParty;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ASCommand implements CommandExecutor {
    private final CommandManager commandManager = AllStarsParty.commandManager;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player))
            return true;

        Player player = (Player) commandSender;

        if (strings.length == 0) {
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + "Vous devez donner un " + ChatColor.RED + "argument" + ChatColor.WHITE + ".");
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.RED + "/as help" + ChatColor.WHITE + " pour la liste de commandes.");
            return true;
        }

        String arg = strings[0];

        SubCommand subCommand = commandManager.getSubCommandFromArg(arg);

        if (subCommand == null) {
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + "Cette " + ChatColor.RED + "commande " + ChatColor.WHITE + "n'existe" + ChatColor.RED + " pas" + ChatColor.WHITE + ".");
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.RED + "/as help" + ChatColor.WHITE + " pour la liste de commandes.");
            return true;
        }

        subCommand.whenCalled(player);

        return true;
    }
}
