package be.artex.AllStarsParty.commands.subCommands;

import be.artex.AllStarsParty.api.message.Message;
import be.artex.AllStarsParty.AllStarsParty;
import be.artex.AllStarsParty.api.GameManager;
import be.artex.AllStarsParty.commands.SubCommand;
import be.artex.AllStarsParty.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class ForceStartSubCommand extends SubCommand {
    private final GameManager gameManager = AllStarsParty.gameManager;

    @Override
    public String[] getArgument() {
        return new String[]{"forcestart", "fstart", "fs"};
    }

    @Override
    public void whenCalled(Player sender) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + "Vous devez être " + ChatColor.RED + "opérateur" + ChatColor.WHITE + " pour exécuter cette " + ChatColor.RED + "commande" + ChatColor.WHITE + ".");
            return;
        }

        List<Player> playersToStartWith = new ArrayList<>(Bukkit.getOnlinePlayers());

        for (Player player : SpecSubCommand.playersInSpec) {
            playersToStartWith.remove(player);
        }

        int maxPlayers = gameManager.getMaxPlayerCount();

        if (playersToStartWith.size() > maxPlayers) {
            sender.sendMessage(Message.error("Le nombre de rôles disponibles doit être supérieur au nombre de joueurs."));
            return;
        }

        if (gameManager.isInGame()) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + "Une " + ChatColor.RED + "partie" + ChatColor.WHITE + " est déjà en cours.");
            return;
        }

        for (Player p : playersToStartWith)
            StartSubCommand.setupPlayer(p);

        for (Player p : SpecSubCommand.playersInSpec)
            p.setGameMode(GameMode.SPECTATOR);

        gameManager.start(playersToStartWith);

        ScoreboardManager.updateAllPlayerScoreboards();
    }

    @Override
    public String getDescription(Player sender) {
        return null;
    }

}
