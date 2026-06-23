package be.artex.allStarsParty.role.NS;

import be.artex.allStarsParty.item_builder.ItemBuilder;
import be.artex.allStarsParty.logic.items.ASPBowItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class HNJ extends ASPBowItem {
    private static final ItemStack STACK = new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 3).name(ChatColor.GOLD + "" + ChatColor.BOLD + "Hiraishin no Jutsu").addEnchant(Enchantment.ARROW_INFINITE, 1).itemFlags(ItemFlag.HIDE_ENCHANTS).build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onShoot(EntityShootBowEvent event) {
        Player player = (Player) event.getEntity();

        player.sendMessage("a");

        event.setProjectile(null);
        player.launchProjectile(EnderPearl.class);
    }
}
