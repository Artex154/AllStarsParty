package be.artex.rewrite.commands.subCommands;

import be.artex.rewrite.commands.SubCommand;
import be.artex.rewrite.util.StatValues;
import be.artex.rewrite.util.Stats;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class EffectSubCommand extends SubCommand {
    @Override
    public String[] getArgument() {
        return new String[]{"effects", "effect", "effets", "effet"};
    }

    @Override
    public void whenCalled(Player sender) {
        Stats playerStats = Stats.get(sender.getUniqueId());

        String message = ChatColor.GOLD + " \n" + ChatColor.BOLD + "» Vos pourentages d'effets \n " +
                ChatColor.WHITE + "- " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + ChatColor.BOLD + "⚔" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " Force" + ChatColor.WHITE + " : " + (100 + playerStats.getBonus(StatValues.STRENGTH)) + "%\n " +
                ChatColor.WHITE + "- " + ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "✦" + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY + " Resistance" + ChatColor.WHITE + " : " + (100 + playerStats.getBonus(StatValues.RESISTANCE)) + "%\n " +
                ChatColor.WHITE + "- " + ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "➤" + ChatColor.DARK_GRAY + "]" + ChatColor.YELLOW + " Vitesse" + ChatColor.WHITE + " : " + (100 + playerStats.getBonus(StatValues.SPEED)) + "%\n ";

        sender.sendMessage(message);
    }

    @Override
    public String getDescription() {
        return "Donne vos pourcentages d'effets.";
    }
}
