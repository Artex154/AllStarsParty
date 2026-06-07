package be.artex.allStarsParty.role.MHA.hawks;

import be.artex.allStarsParty.item_builder.ItemBuilder;
import be.artex.allStarsParty.logic.items.ASPBowItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class AilesDAcier extends ASPBowItem {
    private static final ItemStack STACK = new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 3).name(ChatColor.YELLOW + "" + ChatColor.BOLD + "Ailes d'Acier").build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onProjectileHit(ProjectileHitEvent event) {
        Player player = (Player) event.getEntity().getShooter();
        Random random = new Random();
        int randomInt = random.nextInt(100);

        if (randomInt > 45)
            return;

        player.setHealth(player.getHealth() + 1);
    }
}
