package be.artex.rewrite.role.divergents.sasuke;

import be.artex.allStarsParty.api.message.Message;
import be.artex.rewrite.AllStarsParty;
import be.artex.rewrite.api.item.Cooldown;
import be.artex.rewrite.api.item.CustomItem;
import be.artex.rewrite.api.itemBuilder.ItemBuilder;
import be.artex.rewrite.util.PlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;

public class Rinnegan extends CustomItem {
    private final ItemStack STACK = new ItemBuilder(Material.FERMENTED_SPIDER_EYE).name(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Rinnegan").build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Cooldown cooldown = Cooldown.getCooldown("sasuke_rinnegan", 75*20, ChatColor.WHITE + "votre " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Rinnegan");
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

        target.sendMessage(Message.info(ChatColor.YELLOW + "Sasuke" + ChatColor.WHITE + " échange de place avec vous."));
        player.sendMessage(Message.info("Vous échangez de place avec " + ChatColor.DARK_AQUA + target.getName() + ChatColor.WHITE + "."));

        Location plLoc = player.getLocation();
        Location targetLoc = target.getLocation();

        player.teleport(targetLoc);
        target.teleport(plLoc);

        cooldown.putPlayerInCooldown(player);
    }
}
