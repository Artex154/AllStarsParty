package be.artex.allStarsParty.role.rewrited.NS.tobirama;

import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.Cooldown;
import be.artex.allStarsParty.api.items.ASPBowItem;
import be.artex.allStarsParty.api.message.Message;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class HNJ extends ASPBowItem {
    private static final ItemStack STACK = new ItemBuilder(Material.BOW).name(ChatColor.GOLD + "" + ChatColor.BOLD + "Hiraishin no Jutsu").addEnchant(Enchantment.ARROW_INFINITE, 1).itemFlags(ItemFlag.HIDE_ENCHANTS).build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onShoot(EntityShootBowEvent event) {
        Player player = (Player) event.getEntity();
        Cooldown cooldown = Cooldown.getCooldown("hnj", 180, "Votre " + ChatColor.GOLD + ChatColor.BOLD + "Hiraishin no Jutsu");

        if (cooldown.isPlayerInCooldown(player)) {
            player.sendMessage(Message.cooldownTimeLeft(cooldown.getPlayerCooldownTimeLeft(player)));
            return;
        }

        event.setProjectile(null);
        player.launchProjectile(EnderPearl.class);

        cooldown.putPlayerInCooldown(player);
    }
}
