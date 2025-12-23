package be.artex.allStarsParty.role.MHA.shoto;

import be.artex.allStarsParty.gameLogic.ASPItem;
import be.artex.allStarsParty.gameLogic.Cooldown;
import be.artex.allStarsParty.itemBuilder.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FireSide extends ASPItem {
    private static final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.GOLD + "" + ChatColor.BOLD + "côté gauche").build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onHit(Player damager, Player player, double damage) {
        Cooldown cooldown = Cooldown.getCooldown("shoto_fire_side", 10*20);

        if (cooldown.isPlayerInCooldown(damager)) {
            damager.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE +" Vous êtes encore en cooldown pour " + ChatColor.DARK_AQUA + cooldown.getPlayerCooldownTimeLeft(damager) + ChatColor.WHITE + ".");
            return;
        }

        player.setFireTicks(8*20);

        cooldown.putPlayerInCooldown(damager);
    }
}
