package be.artex.allStarsParty.role.rewrited.antagoniste.tomura.mains;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.Cooldown;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.message.Message;
import be.artex.allStarsParty.registry.ItemRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Mains extends ASPItem {
    public static final Map<Player, MainsHolder> playerLosedItems = new HashMap<>();
    private static final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.GRAY + "" + ChatColor.BOLD + "Mains").build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onHit(Player damager, Player player, double damage) {
        Cooldown cooldown = Cooldown.getCooldown("mains", 90*20, ChatColor.WHITE + "Vos " +ChatColor.GRAY + ChatColor.BOLD + "Mains");

        if (cooldown.isPlayerInCooldown(damager)) {
            damager.sendMessage(Message.cooldownTimeLeft(cooldown.getPlayerCooldownTimeLeft(damager)));
            return;
        }

        playerLosedItems.put(player, new MainsHolder(player.getItemInHand(), player.getInventory().getHeldItemSlot()));

        player.setItemInHand(ItemRegistry.UNUSABLE.getStack());

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            if (playerLosedItems.get(player) != null) {
                MainsHolder holder = playerLosedItems.get(player);
                player.getInventory().setItem(holder.getPlacement(), holder.getStack());
            }
        }, 7*20L);

        cooldown.putPlayerInCooldown(damager);
    }

}
