package be.artex.rewrite.role.protagonistes.bakugo;

import be.artex.allStarsParty.api.message.Message;
import be.artex.rewrite.AllStarsParty;
import be.artex.rewrite.api.item.Cooldown;
import be.artex.rewrite.api.item.CustomItem;
import be.artex.rewrite.api.itemBuilder.ItemBuilder;
import be.artex.rewrite.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Cluster extends CustomItem {
    public static final List<UUID> playersInFire = new ArrayList<>();

    private final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.GOLD + "" + ChatColor.BOLD + "Cluster").build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        int nitro = Bakugo.playersNitro.get(player.getUniqueId());

        if (nitro - 7 <= -1) {
            player.sendMessage(Message.error("Vous n'avez pas assez de nitroglycérine."));
            return;
        }

        Cooldown cooldown = Cooldown.getCooldown("bakugo_cluster", 60*20, ChatColor.GOLD + "" + ChatColor.BOLD + "Cluster");

        if (cooldown.isPlayerInCooldown(player)) {
            player.sendMessage(Message.cooldownTimeLeft(cooldown.getPlayerCooldownTimeLeft(player)));
            return;
        }

        Player target = PlayerUtil.getPlayerTargetEntity(player, 20);

        if (target == null) {
            player.sendMessage(Message.error("Vous ne visez aucun joueur."));
            return;
        }

        player.setVelocity(new Vector(0, 3f, 0).normalize());

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () ->  {
            Vector velocity = player.getLocation().getDirection()
                    .normalize()
                    .multiply(-1.1);

            player.setVelocity(velocity);
        }, 1);

        target.setVelocity(new Vector(0, 3f, 0).normalize());

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () ->  {
            Vector direction = target.getLocation().toVector()
                    .subtract(player.getLocation().toVector())
                    .multiply(10).setY(0.5).normalize();

            target.setVelocity(direction);
        }, 1);

        playersInFire.add(target.getUniqueId());

        new BukkitRunnable() {
            int runs = 0;

            @Override
            public void run() {
                if (!playersInFire.contains(target.getUniqueId()) || runs++ == 16) {
                    playersInFire.remove(target.getUniqueId());
                    cancel();
                    return;
                }

                if (!(target.getFireTicks() > 0))
                    target.setFireTicks(20);
            }
        }.runTaskTimer(AllStarsParty.instance, 0L, 5L);

        if (target.getHealth() - 4 <= 0)
            target.setHealth(0);
        else
            target.setHealth(player.getHealth() - 4);

        player.sendMessage(Message.info("Vous subissez le" + ChatColor.GOLD + ChatColor.BOLD + " Cluster" + ChatColor.WHITE + " de " + ChatColor.GREEN + "Bakugo" + ChatColor.WHITE + "."));

        Bakugo.playersNitro.put(player.getUniqueId(), nitro - 7);
    }
}
