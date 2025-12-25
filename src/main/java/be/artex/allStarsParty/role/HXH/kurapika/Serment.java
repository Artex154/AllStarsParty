package be.artex.allStarsParty.role.HXH.kurapika;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.logic.ASPItem;
import be.artex.allStarsParty.logic.Cooldown;
import be.artex.allStarsParty.logic.stats.Resistance;
import be.artex.allStarsParty.logic.stats.Strength;
import be.artex.allStarsParty.item_builder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Serment extends ASPItem {
    private static final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.GOLD + "" + ChatColor.BOLD + "serment").build();
    public static final List<Player> playersInSerment = new ArrayList<>();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteraction(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Cooldown cooldown = Cooldown.getCooldown("kurapika_serment", 150*20);

        if (cooldown.isPlayerInCooldown(player)) {
            player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE +" Vous êtes encore en cooldown pour " + ChatColor.DARK_AQUA + cooldown.getPlayerCooldownTimeLeft(player) + ChatColor.WHITE + ".");
            return;
        }

        Strength.setPlayerStrength(player, 130);
        Resistance.setPlayerResistance(player, 85);

        playersInSerment.add(player);

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            if (!playersInSerment.contains(player))
                return;

            Strength.setPlayerStrength(player, 100);
            Resistance.setPlayerResistance(player, 100);
        }, 25*20L);

        cooldown.putPlayerInCooldown(player);
    }
}
