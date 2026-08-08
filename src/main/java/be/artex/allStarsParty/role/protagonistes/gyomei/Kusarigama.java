package be.artex.allStarsParty.role.protagonistes.gyomei;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.message.Message;
import be.artex.allStarsParty.api.item.Cooldown;
import be.artex.allStarsParty.api.item.CustomItem;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.util.PlayerUtil;
import be.artex.allStarsParty.util.StatValues;
import be.artex.allStarsParty.util.Stats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Kusarigama extends CustomItem {
    private final ItemStack STACK = new ItemBuilder(Material.DIAMOND_AXE).name(ChatColor.RED + "" + ChatColor.BOLD + "Kusarigama").addEnchant(Enchantment.DAMAGE_ALL, 4).build();

    public static final List<UUID> playersWithSpeedDebuff = new ArrayList<>();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!player.isSneaking())
            return;

        if (event.getAction() == Action.PHYSICAL)
            return;

        Player target = PlayerUtil.getPlayerTargetEntity(player, 50);

        if (target == null) {
            player.sendMessage(Message.error("Vous ne visez aucun joueur."));
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Cooldown cooldown = Cooldown.getCooldown("gyomei_kusarigama_right", 45*20, ChatColor.RED + "" + ChatColor.BOLD + "Kusarigama" + ChatColor.GRAY + " (clic-droit)");

            if (cooldown.isPlayerInCooldown(player)) {
                player.sendMessage(Message.cooldownTimeLeft(cooldown.getPlayerCooldownTimeLeft(player)));
                return;
            }

            target.teleport(player.getLocation());

            cooldown.putPlayerInCooldown(player);
        } else {
            Cooldown cooldown = Cooldown.getCooldown("gyomei_kusarigama_left", 90*20, ChatColor.RED + "" + ChatColor.BOLD + "Kusarigama" + ChatColor.GRAY + " (clic-gauche)");

            if (cooldown.isPlayerInCooldown(player)) {
                player.sendMessage(Message.cooldownTimeLeft(cooldown.getPlayerCooldownTimeLeft(player)));
                return;
            }

            PlayerUtil.inflictTrueDamage(target, 2);
            Stats stats = Stats.get(target.getUniqueId());
            stats.addBonus(StatValues.SPEED, -40);
            playersWithSpeedDebuff.add(target.getUniqueId());

            Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
                if (!playersWithSpeedDebuff.contains(target.getUniqueId()))
                    return;

                stats.addBonus(StatValues.SPEED, 40);
                playersWithSpeedDebuff.remove(target.getUniqueId());
            }, 200);

            cooldown.putPlayerInCooldown(player);
        }

    }

}
