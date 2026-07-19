package be.artex.rewrite.role.antagoniste.katarina;

import be.artex.allStarsParty.api.message.Message;
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
import org.bukkit.util.Vector;

public class Shunpo extends CustomItem {
    private final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.GOLD + "" + ChatColor.BOLD + "Shunpo").lore(
                    ChatColor.GRAY + " Shunpo" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "clic" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "1x/1mn20",
                    ChatColor.WHITE + "   Vous vous téléportez dans le dos du joueur ciblé. ",
                    ChatColor.WHITE + "  S'il s'agit d'un joueur ennemi, " + ChatColor.LIGHT_PURPLE + "1❤ " + ChatColor.WHITE + "lui est infligé et vous",
                    ChatColor.WHITE + "  deveniendrez aussi invisible à ses yeux pendant une seconde.    ")
            .build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Cooldown cooldown = Cooldown.getCooldown("katarina_shunpo", 120, ChatColor.GOLD + "" + ChatColor.BOLD + "Shunpo");
        Player player = event.getPlayer();

        if (cooldown.isPlayerInCooldown(player)) {
            player.sendMessage(Message.cooldownTimeLeft(cooldown.getPlayerCooldownTimeLeft(player)));
            return;
        }

        Player target = PlayerUtil.getPlayerTargetEntity(player, 50);

        if (target == null) {
            player.sendMessage(Message.error("Vous ne visez aucun joueur."));
            return;
        }

        Location targetLoc = target.getLocation();
        Vector direction = targetLoc.getDirection();
        Vector behindVector = direction.clone().multiply(-1);
        Location behindLoc = targetLoc.clone().add(behindVector);

        player.teleport(behindLoc);

        target.sendMessage(Message.info(ChatColor.RED + "Katarina" + ChatColor.WHITE + " se téléporte derrière vous, elle vous inflige " + ChatColor.LIGHT_PURPLE + "1❤" + ChatColor.WHITE + "."));

        target.damage(0);

        if (target.getHealth() - 2 <= 0) {
            target.setHealth(0);
        } else target.setHealth(target.getHealth() - 2);

        cooldown.putPlayerInCooldown(player);
    }
}
