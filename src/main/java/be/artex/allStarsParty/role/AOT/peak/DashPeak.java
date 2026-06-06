package be.artex.allStarsParty.role.AOT.peak;

import be.artex.allStarsParty.item_builder.ItemBuilder;
import be.artex.allStarsParty.logic.ASPItem;
import be.artex.allStarsParty.logic.Cooldown;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class DashPeak extends ASPItem {
    private static final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.GOLD + "" + ChatColor.BOLD + "Dash").build();

    @Override
    public ItemStack getStack() {
        return STACK;
    }

    @Override
    public void onInteraction(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!TransformationPeak.transformedPlayer.contains(player)) {
            player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE +" Vous devez être transformé pour utiliser ce dash.");
            return;
        }

        Cooldown cooldown = Cooldown.getCooldown("dash_peak", 10*20);

        if (cooldown.isPlayerInCooldown(player)) {
            player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE +" Vous êtes encore en cooldown pour " + ChatColor.DARK_AQUA + cooldown.getPlayerCooldownTimeLeft(player) + "secondes" + ChatColor.WHITE + ".");
            return;
        }

        Vector dir = player.getLocation().getDirection().normalize().multiply(1.74f);
        dir.setY(0.4f);

        player.setVelocity(dir);

        cooldown.putPlayerInCooldown(player);
    }
}
