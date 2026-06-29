package be.artex.allStarsParty.role.rewrited.all_might;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.Cooldown;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.items.ASPItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class USOS extends ASPItem {
    private static final ItemStack STACK = new ItemBuilder(Material.DIAMOND_BLOCK).name(ChatColor.GOLD + "" + ChatColor.BOLD + "United States of Smash").build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onHit(Player damager, Player player, double damage) {
        Cooldown cooldown = Cooldown.getCooldown("USOS", 120*20, ChatColor.WHITE + "Votre" + ChatColor.GOLD + ChatColor.BOLD + " United States of Smash");

        if (cooldown.isPlayerInCooldown(damager)) {
            damager.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE +" Vous êtes encore en cooldown pour " + ChatColor.DARK_AQUA + cooldown.getPlayerCooldownTimeLeft(player) + " secondes" + ChatColor.WHITE + ".");
            return;
        }

        if ((player.getHealth() - 4) <= 0)
            player.setHealth(0);
        else player.setHealth(player.getHealth() - 4);

        player.setVelocity(new Vector(0, 1.75, 0));

        Vector direction = player.getLocation().toVector()
                .subtract(damager.getLocation().toVector())
                .normalize().multiply(10);

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> player.setVelocity(direction), 10);

        cooldown.putPlayerInCooldown(damager);
    }
}
