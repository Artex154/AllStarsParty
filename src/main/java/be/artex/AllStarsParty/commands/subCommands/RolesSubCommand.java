package be.artex.AllStarsParty.commands.subCommands;

import be.artex.AllStarsParty.api.message.Message;
import be.artex.AllStarsParty.AllStarsParty;
import be.artex.AllStarsParty.api.role.Role;
import be.artex.AllStarsParty.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RolesSubCommand extends SubCommand {
    @Override
    public String[] getArgument() {
        return new String[]{"roles", "r"};
    }

    @Override
    public void whenCalled(Player sender) {
        if (!AllStarsParty.gameManager.isInGame()) {
            sender.sendMessage(Message.error("Aucune partie est en cours."));
            return;
        }

        StringBuilder str = new StringBuilder();

        str.append(ChatColor.GOLD + " \n" + ChatColor.BOLD + "» Liste des rôles en vie dans la partie\n ");

        Role.manager.getRolesAlive().forEach(r -> {
            str.append(ChatColor.WHITE + "- " + r.getSide().getColor() + r.getName() + "\n ");
        });

        sender.sendMessage(String.valueOf(str));
    }

    @Override
    public String getDescription(Player sender) {
        return "Donne la liste des rôles vivants dans la partie.";
    }
}
