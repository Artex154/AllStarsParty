package be.artex.rewrite.role.divergents.sasuke;

import be.artex.allStarsParty.api.message.Message;
import be.artex.rewrite.AllStarsParty;
import be.artex.rewrite.api.item.Cooldown;
import be.artex.rewrite.api.item.CustomItem;
import be.artex.rewrite.api.itemBuilder.ItemBuilder;
import be.artex.rewrite.util.PlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Amaterasu extends CustomItem {
    private final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.GOLD + "" + ChatColor.BOLD + "Amaterasu").build();
    public static final List<UUID> playersAffectedByAmaterasu = new ArrayList<>();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Cooldown cooldown = Cooldown.getCooldown("sasuke_amaterasu", 90*20, ChatColor.GOLD + "" + ChatColor.BOLD + "Amaterasu");
        Player player = event.getPlayer();

        if (cooldown.isPlayerInCooldown(player)) {
            player.sendMessage(Message.cooldownTimeLeft(cooldown.getPlayerCooldownTimeLeft(player)));
            return;
        }

        Player target = PlayerUtil.getPlayerTargetEntity(player, 20);

        if (target == null) {
            player.sendMessage(Message.error("Vous ne visez aucun joueur."));
            return;
        }

        playersAffectedByAmaterasu.add(target.getUniqueId());

        target.sendMessage(Message.info(ChatColor.YELLOW + "Sasuke " + ChatColor.WHITE + " vous inflige l'" + ChatColor.GOLD + ChatColor.BOLD + "Amaterasu" + ChatColor.WHITE + "."));
        player.sendMessage(Message.info("Vous infligez l' " + ChatColor.GOLD + ChatColor.BOLD + "Amaterasu " + ChatColor.WHITE + "à " + ChatColor.DARK_AQUA + target.getName() + ChatColor.WHITE + "."));

        PlayerUtil.setFireColor(target.getUniqueId(), Color.BLACK);

        new BukkitRunnable() {
            int runs = 0;

            @Override
            public void run() {
                if (!playersAffectedByAmaterasu.contains(target.getUniqueId()) || runs++ == 32) {
                    playersAffectedByAmaterasu.remove(target.getUniqueId());
                    PlayerUtil.resetFireColor(target.getUniqueId());
                    cancel();
                    return;
                }

                if (!(target.getFireTicks() > 0))
                    target.setFireTicks(20);
            }
        }.runTaskTimer(AllStarsParty.instance, 0L, 5L);

        cooldown.putPlayerInCooldown(player);
    }
}
