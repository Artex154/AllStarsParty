package be.artex.allStarsParty.role.rewrited.MHA.shoto;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.Cooldown;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class FireSide extends ASPItem {
    private static final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.GOLD + "" + ChatColor.BOLD + "côté gauche").build();
    public static final List<Player> playersInFire = new ArrayList<>();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onHit(Player damager, Player player, double damage) {
        Cooldown cooldown = Cooldown.getCooldown("shoto_fire_side", 40*20, ChatColor.WHITE + "Votre" + ChatColor.GOLD + ChatColor.BOLD + " côté droit");

        if (cooldown.isPlayerInCooldown(damager)) {
            damager.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE +" Vous êtes encore en cooldown pour " + ChatColor.DARK_AQUA + cooldown.getPlayerCooldownTimeLeft(damager) + ChatColor.WHITE + ".");
            return;
        }

        playersInFire.add(player);

        new BukkitRunnable() {
            int runs = 0;

            @Override
            public void run() {
                if (!playersInFire.contains(player) || runs++ == 16) {
                    playersInFire.remove(player);
                    cancel();
                    return;
                }

                player.setFireTicks(20);
            }
        }.runTaskTimer(AllStarsParty.instance, 0L, 5L);

        cooldown.putPlayerInCooldown(damager);
    }
}
