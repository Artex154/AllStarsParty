package be.artex.rewrite.commands.subCommands;

import be.artex.allStarsParty.api.message.Message;
import be.artex.rewrite.AllStarsParty;
import be.artex.rewrite.api.GameManager;
import be.artex.rewrite.commands.SubCommand;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SpecSubCommand extends SubCommand {
    public static final List<Player> playersInSpec = new ArrayList<>();

    @Override
    public String[] getArgument() {
        return new String[]{"spectate", "spec"};
    }

    @Override
    public void whenCalled(Player sender) {
        if (AllStarsParty.gameManager.isInGame()) {
            sender.sendMessage(Message.error("Vous ne pouvez pas éxecuter cette commande alors qu'une partie est en cours."));
            return;
        }

        if (playersInSpec.contains(sender)) {
            playersInSpec.remove(sender);
            sender.sendMessage(Message.info("Vous ne serez plus spectateur pour les prochaines parties."));
        } else {
            playersInSpec.add(sender);
            sender.sendMessage(Message.info("Vous serez spectateur pour les prochaines parties."));
        }
    }

    @Override
    public String getDescription(Player sender) {
        return "Permet d'activer ou non le fait d'être spectateur pour les prochaines parties.";
    }
}
