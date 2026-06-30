package be.artex.allStarsParty.role.rewrited.protagoniste.shoto;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.Cooldown;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.message.Message;
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
            damager.sendMessage(Message.cooldownTimeLeft(cooldown.getPlayerCooldownTimeLeft(damager)));
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

        damager.sendMessage(Message.info("Vous avez " + ChatColor.GOLD + "enflammé " + ChatColor.DARK_AQUA + player.getName() + ChatColor.WHITE + "."));
        player.sendMessage(Message.info(ChatColor.BLUE + "Shoto" + ChatColor.WHITE + " vous" + ChatColor.GOLD + " enflamme" + ChatColor.WHITE + "."));

        cooldown.putPlayerInCooldown(damager);
    }
}
