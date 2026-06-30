package be.artex.allStarsParty.role.rewrited.antagoniste.muzan;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.Cooldown;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.message.Message;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Sang extends ASPItem {
    private static final ItemStack STACK = new ItemBuilder(Material.GHAST_TEAR).name(ChatColor.RED + "" + ChatColor.BOLD + "Sang").build();
    public static final List<Player> playersBleeding = new ArrayList<>();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onHit(Player damager, Player player, double damage) {
        Cooldown cooldown = Cooldown.getCooldown("sang", 50*20, ChatColor.WHITE + "Votre" + ChatColor.RED + ChatColor.BOLD + " sang");

        if (cooldown.isPlayerInCooldown(damager)) {
            damager.sendMessage(Message.cooldownTimeLeft(cooldown.getPlayerCooldownTimeLeft(damager)));
            return;
        }

        playersBleeding.add(player);

        new BukkitRunnable() {
            int runs = 0;

            @Override
            public void run() {
                if (!playersBleeding.contains(player) || runs++ == 7) {
                    playersBleeding.remove(player);
                    cancel();
                    return;
                }

                player.damage(0.1);
                if ((player.getHealth() - 1) > 0)
                    player.setHealth(player.getHealth() - 1);
            }
        }.runTaskTimer(AllStarsParty.instance, 0L, 20L);

        damager.sendMessage(Message.info("Vous faites" + ChatColor.RED + " saigné " + ChatColor.DARK_AQUA + player.getName() + ChatColor.WHITE + "."));
        player.sendMessage(Message.info(ChatColor.RED + "Muzan" + ChatColor.WHITE + " vous fait " + ChatColor.RED + "saigné " + ChatColor.WHITE + "."));

        cooldown.putPlayerInCooldown(damager);
    }
}
