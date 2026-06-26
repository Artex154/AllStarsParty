package be.artex.allStarsParty.role.rewrited.tobirama;

import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.items.ASPItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class RNK extends ASPItem {
    private static final ItemStack STACK = new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 3).name(ChatColor.GOLD + "" + ChatColor.BOLD + "Raijin no Ken").build();
    public static final HashMap<Player, Integer> playerHitNumber = new HashMap<>();

    @Override
    public ItemStack getStack() {
        return STACK;
    }

    @Override
    public void onHit(Player damager, Player player, double damage) {
        int hitNumber = playerHitNumber.getOrDefault(damager, 0);
        hitNumber++;


        if (hitNumber != 10) {
            playerHitNumber.put(damager, hitNumber);
            return;
        }

        playerHitNumber.put(damager, 0);

        player.getWorld().strikeLightning(player.getLocation());

        if ((player.getHealth() - 4) < 0)
            player.setHealth(1);
        else
            player.setHealth(player.getHealth() - 4);
    }
}
