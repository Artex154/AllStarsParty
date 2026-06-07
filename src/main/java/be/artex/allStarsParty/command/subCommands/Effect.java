package be.artex.allStarsParty.command.subCommands;

import be.artex.allStarsParty.TextUtil;
import be.artex.allStarsParty.command.SubCommand;
import be.artex.allStarsParty.logic.stats.Resistance;
import be.artex.allStarsParty.logic.stats.Speed;
import be.artex.allStarsParty.logic.stats.Strength;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Effect extends SubCommand {
    @Override
    public String getArgument() {
        return "effects";
    }

    @Override
    public void whenCalled(Player sender) {
        String message = TextUtil.BORDER +
                "\n" + ChatColor.DARK_AQUA + ChatColor.BOLD + "EFFETS:" +
                "\n" + ChatColor.RED + " Force:          " + ChatColor.WHITE + Strength.getPlayerStrength(sender) + "%" +
                "\n" + ChatColor.GRAY + " Resistance:    " + ChatColor.WHITE + Resistance.getPlayerResistance(sender) + "%" +
                "\n" + ChatColor.YELLOW + " Vitesse:        " + ChatColor.WHITE + Speed.getPlayerSpeed(sender) + "%" +
                "\n" + TextUtil.BORDER;

        sender.sendMessage(message);
    }
}
