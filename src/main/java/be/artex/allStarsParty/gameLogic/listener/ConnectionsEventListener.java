package be.artex.allStarsParty.gameLogic.listener;


import be.artex.allStarsParty.AllStarsParty;

import be.artex.allStarsParty.PlayerUtil;
import fr.mrmicky.fastboard.FastBoard;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;

public class ConnectionsEventListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        event.setJoinMessage("");

        PlayerUtil.sendMessageToAllPlayers(
                ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + player.getName() + " a rejoint la partie." + ChatColor.GRAY + " (" + Bukkit.getOnlinePlayers().size() + "/" + AllStarsParty.maxPlayers + ")"
        );

        FastBoard board = new FastBoard(player);
        board.updateTitle(ChatColor.DARK_AQUA + "" + ChatColor.ITALIC + ChatColor.BOLD + "All Stars Party");

        AllStarsParty.boards.put(player.getUniqueId(), board);

        PlayerUtil.updateAllPlayerScoreboards();

        player.teleport(new Location(AllStarsParty.world, AllStarsParty.CENTER_X, AllStarsParty.CENTER_Y + 2, AllStarsParty.CENTER_Z));

        player.getInventory().clear();
        player.setMaxHealth(20);
        player.setHealth(20);

        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        if (AllStarsParty.inGame) {
            PlayerUtil.sendMessageToAllPlayers(
                    ChatColor.WHITE + "  La partie ayant déjà été lancée, il devient " + ChatColor.DARK_AQUA + "spectateur" + ChatColor.WHITE + "."
            );

            player.setGameMode(GameMode.SPECTATOR);

            return;
        }

        player.setGameMode(GameMode.ADVENTURE);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        event.setQuitMessage(
                ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + player.getName() + " a quitté la partie." +  ChatColor.GRAY + " (" +  Bukkit.getOnlinePlayers().size() + "/" + AllStarsParty.maxPlayers + ")"
        );

        AllStarsParty.boards.remove(event.getPlayer().getUniqueId());
    }
}
