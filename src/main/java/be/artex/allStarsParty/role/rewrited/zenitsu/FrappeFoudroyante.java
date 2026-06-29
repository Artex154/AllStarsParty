package be.artex.allStarsParty.role.rewrited.zenitsu;

import be.artex.allStarsParty.api.Cooldown;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.items.ASPItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class FrappeFoudroyante extends ASPItem {
    private static final ItemStack STACK = new ItemBuilder(Material.GLOWSTONE_DUST).name(ChatColor.YELLOW + "" + ChatColor.BOLD + "Frappe Foudroyante").build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteraction(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Cooldown cooldown = Cooldown.getCooldown("frappe_foudroyante", 15*20, ChatColor.WHITE + "Votre" + ChatColor.YELLOW + ChatColor.BOLD + " Frappe Foudroyante");

        if (cooldown.isPlayerInCooldown(player)) {
            player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE +" Vous êtes encore en cooldown pour " + ChatColor.DARK_AQUA + cooldown.getPlayerCooldownTimeLeft(player) + " secondes" + ChatColor.WHITE + ".");
            return;
        }

        Vector dir = player.getLocation().getDirection().normalize().multiply(2f);
        dir.setY(0.75);

        player.setVelocity(dir);

        cooldown.putPlayerInCooldown(player);
    }
}
