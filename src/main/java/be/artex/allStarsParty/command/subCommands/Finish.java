package be.artex.allStarsParty.command.subCommands;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.command.SubCommand;
import be.artex.allStarsParty.manager.GameManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class Finish extends SubCommand {
    private final GameManager gameManager = AllStarsParty.gameManager;

    @Override
    public String getArgument() {
        return "finish";
    }

    @Override
    public void whenCalled(Player sender) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + "Vous devez être " + ChatColor.RED + "opérateur" + ChatColor.WHITE + " pour exécuter cette " + ChatColor.RED + "commande" + ChatColor.WHITE + ".");
            return;
        }

        if (!gameManager.isInGame()) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + "Vous ne pouvez pas terminer une " + ChatColor.RED + "partie" + ChatColor.WHITE + " qui n'a" + ChatColor.RED + " pas " + ChatColor.WHITE + "cours.");
            return;
        }

        Bukkit.broadcastMessage(" ");
        Bukkit.broadcastMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + "Cette partie a été interrompue par un opérateur. Elle se termine donc.");
        Bukkit.broadcastMessage(" ");

        gameManager.setInGame(false);

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(new Location(AllStarsParty.world, AllStarsParty.CENTER_X, AllStarsParty.CENTER_Y + 2, AllStarsParty.CENTER_Z));
            player.getInventory().clear();
            player.setMaxHealth(20);
            player.setHealth(20);
            player.setGameMode(GameMode.ADVENTURE);
            gameManager.resetAllPlayerState(player);

            for (PotionEffect effect : player.getActivePotionEffects())
                player.removePotionEffect(effect.getType());
        }

    }
}
