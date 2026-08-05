package be.artex.AllStarsParty.role.protagonistes.gyomei;

import be.artex.AllStarsParty.api.message.Message;
import be.artex.AllStarsParty.api.item.Cooldown;
import be.artex.AllStarsParty.api.item.CustomItem;
import be.artex.AllStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.AllStarsParty.util.PlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Kusarigama extends CustomItem {
    private final ItemStack STACK = new ItemBuilder(Material.DIAMOND_AXE).name(ChatColor.RED + "" + ChatColor.BOLD + "Kusarigama").addEnchant(Enchantment.DAMAGE_ALL, 4).build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (!player.isSneaking())
            return;

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
            return;

        Cooldown cooldown = Cooldown.getCooldown("gyomei_kusarigama", 45*20, ChatColor.RED + "" + ChatColor.BOLD + "Kusarigama");

        if (cooldown.isPlayerInCooldown(player)) {
            player.sendMessage(Message.cooldownTimeLeft(cooldown.getPlayerCooldownTimeLeft(player)));
            return;
        }

        Player target = PlayerUtil.getPlayerTargetEntity(player, 50);

        if (target == null) {
            player.sendMessage(Message.error("Vous ne visez aucun joueur."));
            return;
        }

        target.teleport(player.getLocation());

        cooldown.putPlayerInCooldown(player);
    }
}
