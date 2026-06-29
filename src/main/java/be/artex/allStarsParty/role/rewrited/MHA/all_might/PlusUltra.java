package be.artex.allStarsParty.role.rewrited.MHA.all_might;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.Cooldown;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.message.Message;
import be.artex.allStarsParty.api.stats.Strength;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlusUltra extends ASPItem {
    private static final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.GOLD + "" + ChatColor.BOLD + "Plus Ultra").build();
    public static final List<Player> playersWithPlusUltra = new ArrayList<>();

    @Override
    public ItemStack getStack() {
        return STACK;
    }

    @Override
    public void onInteraction(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Cooldown cooldown = Cooldown.getCooldown("plus_ultra", 150*20, ChatColor.WHITE + "Le " + ChatColor.GOLD + ChatColor.BOLD + "Plus Ultra");

        if (cooldown.isPlayerInCooldown(player)) {
            player.sendMessage(Message.cooldownTimeLeft(cooldown.getPlayerCooldownTimeLeft(player)));
            return;
        }

        Strength.addStrengthToPlayer(player, 10);

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            if (!playersWithPlusUltra.contains(player))
                return;

            playersWithPlusUltra.remove(player);
            Strength.removeStrengthFromPlayer(player, 10);
            player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE + " Le" + ChatColor.GOLD + ChatColor.BOLD + " Plus Ultra" + ChatColor.WHITE + " cesse.");
        }, 400);

        playersWithPlusUltra.add(player);
        player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE + " Vous avez activé le" + ChatColor.GOLD + ChatColor.BOLD + " Plus Ultra" + ChatColor.WHITE + ".");

        cooldown.putPlayerInCooldown(player);
    }
}
