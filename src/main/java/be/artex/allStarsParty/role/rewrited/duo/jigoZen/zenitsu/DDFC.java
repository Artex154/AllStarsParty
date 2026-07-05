package be.artex.allStarsParty.role.rewrited.duo.jigoZen.zenitsu;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.message.Message;
import be.artex.allStarsParty.api.stats.Speed;
import be.artex.allStarsParty.api.stats.Strength;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class DDFC extends ASPItem {
    public static final Map<UUID, Integer> playersActivation = new HashMap<>();
    public static final List<UUID> playersWith7thMove = new ArrayList<>();
    private static final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.GOLD + "" + ChatColor.BOLD + "Dieu du Feu Céleste").build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteraction(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        int activationCount = playersActivation.getOrDefault(player.getUniqueId(), 0);
        SleepState playerSleepState = Zenitsu.getPlayerSleepState(player.getUniqueId());

        if (playerSleepState == SleepState.AWAKE) {
            player.sendMessage(Message.error("Vous devez être endormi pour utiliser la " + ChatColor.GOLD + ChatColor.BOLD + "Dieu du Feu Céleste" + ChatColor.WHITE + "."));
            return;
        }

        if (activationCount == 1 && playerSleepState != SleepState.ASLEEP_PERMANENT) {
            player.sendMessage(Message.error("Vous avez déjà utilisé le " + ChatColor.GOLD + ChatColor.BOLD + "Dieu du Feu Céleste" + ChatColor.WHITE + ". Vous possédez une deuxième activation seulement si Jigoro vient à mourir."));
            return;
        }

        if (activationCount == 2) {
            player.sendMessage(Message.error("Vous avez déjà utilisé vos deux utilisations du " + ChatColor.GOLD + ChatColor.BOLD + "Dieu du Feu Céleste" + ChatColor.WHITE + "."));
            return;
        }

        if (Zenitsu.isAwake(player.getUniqueId()))
            return;

        activationCount++;

        playersWith7thMove.add(player.getUniqueId());
        playersActivation.put(player.getUniqueId(), activationCount);

        player.sendMessage(Message.info("Vous avez activé le" + ChatColor.GOLD + ChatColor.BOLD + " Dieu du Feu Céleste" + ChatColor.WHITE + "."));

        Strength.addStrengthToPlayer(player, 20);
        Speed.addSpeedToPlayer(player, 50);

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            if (!playersWith7thMove.contains(player.getUniqueId()))
                return;

            playersWith7thMove.remove(player.getUniqueId());
            Strength.removeStrengthFromPlayer(player, 20);
            Speed.removeSpeedFromPlayer(player, 50);
            player.sendMessage(Message.info("Les effets du " + ChatColor.GOLD + ChatColor.BOLD + " Dieu du Feu Céleste" + ChatColor.WHITE + " cesse."));
        }, 400);
    }
}
