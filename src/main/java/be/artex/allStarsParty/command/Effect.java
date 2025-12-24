package be.artex.allStarsParty.command;

import be.artex.allStarsParty.TextUtil;
import be.artex.allStarsParty.logic.stats.Resistance;
import be.artex.allStarsParty.logic.stats.Speed;
import be.artex.allStarsParty.logic.stats.Strength;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Effect implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player))
            return true;

        Player player = (Player) commandSender;

        String message = TextUtil.BORDER +
                "\n" + ChatColor.DARK_AQUA + ChatColor.BOLD + "EFFETS:" +
                "\n" + ChatColor.RED + " Force:          " + ChatColor.WHITE + Strength.getPlayerStrength(player) + "%" +
                "\n" + ChatColor.GRAY + " Resistance:  " + ChatColor.WHITE + Resistance.getPlayerResistance(player) + "%" +
                "\n" + ChatColor.YELLOW + " Vitesse:        " + ChatColor.WHITE + Speed.getPlayerSpeed(player) + "%" +
                "\n" + TextUtil.BORDER;

        player.sendMessage(message);

        return true;
    }
}
