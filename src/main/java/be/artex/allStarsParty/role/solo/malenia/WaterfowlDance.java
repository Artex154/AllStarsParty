package be.artex.allStarsParty.role.solo.malenia;

import be.artex.allStarsParty.api.message.Message;
import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.item.Cooldown;
import be.artex.allStarsParty.api.item.CustomItem;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class WaterfowlDance extends CustomItem {
    private final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.GOLD + "" + ChatColor.BOLD + "Waterfowl Dance").lore().build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Cooldown cooldown = Cooldown.getCooldown("waterfowl_dance", 45*20, ChatColor.GOLD + "" + ChatColor.BOLD + "Waterfowl Dance");

        if (cooldown.isPlayerInCooldown(player)) {
            player.sendMessage(Message.cooldownTimeLeft(cooldown.getPlayerCooldownTimeLeft(player)));
            return;
        }

        Vector dir = player.getLocation().getDirection().normalize().multiply(2f);
        dir.setY(0.75);

        player.setVelocity(dir);

        cooldown.putPlayerInCooldown(player);

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            for (Entity en : player.getNearbyEntities(10, 10, 10)) {
                if (!(en instanceof Player))
                    return;

                Player pl = (Player) en;

                pl.damage(0);

                if (pl.getHealth() - 2 <= 0)
                    pl.setHealth(0);
                else
                    pl.setHealth(pl.getHealth() - 2);
            }
        }, 15);
    }
}
