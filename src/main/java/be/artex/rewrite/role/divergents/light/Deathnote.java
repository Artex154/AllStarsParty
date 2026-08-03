package be.artex.rewrite.role.divergents.light;

import be.artex.allStarsParty.api.message.Message;
import be.artex.rewrite.AllStarsParty;
import be.artex.rewrite.api.item.Cooldown;
import be.artex.rewrite.api.item.CustomItem;
import be.artex.rewrite.api.itemBuilder.ItemBuilder;
import be.artex.rewrite.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Deathnote extends CustomItem {
    private final ItemStack STACK = new ItemBuilder(Material.BOOK).name(ChatColor.GOLD + "" + ChatColor.BOLD + "Death Note").addEnchant(Enchantment.DURABILITY, 1).itemFlags(ItemFlag.HIDE_ENCHANTS).lore().build();
    public static final List<UUID> playersAffectedByDeathNote = new ArrayList<>();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Cooldown cooldown = Cooldown.getCooldown("light_deathnote", 120*20, "Le" + ChatColor.GOLD + ChatColor.BOLD + "Death Note");
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

        playersAffectedByDeathNote.add(target.getUniqueId());
        target.setMaxHealth(target.getMaxHealth() - 4);

        target.sendMessage(Message.info("Le " + ChatColor.GOLD + ChatColor.BOLD + "Death Note " + ChatColor.WHITE + "de " + ChatColor.YELLOW + "Kira " + ChatColor.WHITE + "fait effet sur vous."));
        player.sendMessage(Message.info("Le " + ChatColor.GOLD + ChatColor.BOLD + "Death Note " + ChatColor.WHITE + "s'active sur " + ChatColor.DARK_AQUA + target.getName() + ChatColor.WHITE + "."));

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            if (!playersAffectedByDeathNote.contains(target.getUniqueId()))
                return;

            target.setMaxHealth(target.getMaxHealth() + 4);
            playersAffectedByDeathNote.remove(target.getUniqueId());

            target.sendMessage(Message.info("Le " + ChatColor.GOLD + ChatColor.BOLD + "Death Note " + ChatColor.WHITE + "ne fait plus effet sur vous."));
            player.sendMessage(Message.info("Le " + ChatColor.GOLD + ChatColor.BOLD + "Death Note " + ChatColor.WHITE + "ne fais plus effet sur " + ChatColor.DARK_AQUA + target.getName() + ChatColor.WHITE + "."));
        }, 400);

        cooldown.putPlayerInCooldown(player);
    }
}
