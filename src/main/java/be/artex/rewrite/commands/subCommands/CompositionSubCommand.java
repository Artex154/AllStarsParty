package be.artex.rewrite.commands.subCommands;

import be.artex.rewrite.api.role.Role;
import be.artex.rewrite.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class CompositionSubCommand extends SubCommand {
    @Override
    public String[] getArgument() {
        return new String[]{"composition", "compo", "c", "roles", "r", "list"};
    }

    @Override
    public void whenCalled(Player sender) {
        StringBuilder str = new StringBuilder();

        str.append(ChatColor.GOLD + " \n" + ChatColor.BOLD + "» Liste des rôles de la partie \n ");

        Role.manager.getRegisteredRoles().forEach(r -> {
            str.append(ChatColor.WHITE + "- " + r.getSide().getColor() + r.getName() + "\n ");
        });

        sender.sendMessage(String.valueOf(str));
    }

    @Override
    public String getDescription() {
        return "Donne la liste de tous les rôles de la partie.";
    }
}
