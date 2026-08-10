package be.artex.AllStarsParty.role.divergents.sasuke;

import be.artex.AllStarsParty.api.message.Message;
import be.artex.AllStarsParty.api.item.Cooldown;
import be.artex.AllStarsParty.api.item.CustomItem;
import be.artex.AllStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.AllStarsParty.util.PlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Rinnegan extends CustomItem {
    private final ItemStack STACK = new ItemBuilder(Material.FERMENTED_SPIDER_EYE).name(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Rinnegan").build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Integer ch = Sasuke.playersChakra.get(player.getUniqueId());

        if (ch - 45 <= -1) {
            player.sendMessage(Message.error("Vous n'avez pas assez de chakra."));
            return;
        }

        Cooldown cooldown = Cooldown.getCooldown("sasuke_rinnegan", 5*20, ChatColor.WHITE + "Votre " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Rinnegan");

        if (cooldown.isPlayerInCooldown(player)) {
            player.sendMessage(Message.cooldownTimeLeft(cooldown.getPlayerCooldownTimeLeft(player)));
            return;
        }

        Player target = PlayerUtil.getPlayerTargetEntity(player, 20);

        if (target == null) {
            player.sendMessage(Message.error("Vous ne visez aucun joueur."));
            return;
        }

        Sasuke.playersChakra.put(player.getUniqueId(), ch - 45);

        target.sendMessage(Message.info(ChatColor.YELLOW + "Sasuke" + ChatColor.WHITE + " échange de place avec vous."));
        player.sendMessage(Message.info("Vous échangez de place avec " + ChatColor.DARK_AQUA + target.getName() + ChatColor.WHITE + "."));

        Location plLoc = player.getLocation();
        Location targetLoc = target.getLocation();

        player.teleport(targetLoc);
        target.teleport(plLoc);

        cooldown.putPlayerInCooldown(player);
    }
}
