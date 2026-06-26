package be.artex.allStarsParty.role.rewrited.sasuke;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.Cooldown;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.stats.Resistance;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Susano extends ASPItem {
    private static final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Susano").build();
    public static final List<Player> playersWithSusano = new ArrayList<>();

    @Override
    public ItemStack getStack() {
        return STACK;
    }

    @Override
    public void onInteraction(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Cooldown cooldown = Cooldown.getCooldown("susano", 150*20, ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Susano");

        if (cooldown.isPlayerInCooldown(player)) {
            player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE +" Vous êtes encore en cooldown pour " + ChatColor.DARK_AQUA + cooldown.getPlayerCooldownTimeLeft(player) + " secondes" + ChatColor.WHITE + ".");
            return;
        }

        Resistance.setPlayerResistance(player, 110);

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            if (!playersWithSusano.contains(player))
                return;

            playersWithSusano.remove(player);
            Resistance.setPlayerResistance(player, 100);
        }, 2000);

        playersWithSusano.add(player);
        player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE + " Vous avez activé" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + " Susano" + ChatColor.WHITE + ".");

        cooldown.putPlayerInCooldown(player);
    }
}
