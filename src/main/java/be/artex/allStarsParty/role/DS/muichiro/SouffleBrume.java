package be.artex.allStarsParty.role.DS.muichiro;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.logic.ASPItem;
import be.artex.allStarsParty.logic.Cooldown;
import be.artex.allStarsParty.item_builder.ItemBuilder;
import be.artex.allStarsParty.logic.Role;
import be.artex.allStarsParty.logic.Side;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class SouffleBrume extends ASPItem {
    private static final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + "Souffle de la brume").build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteraction(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Cooldown cooldown = Cooldown.getCooldown("souffleBrume", 5*20);

        if (cooldown.isPlayerInCooldown(player)) {
            player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE +" Vous êtes encore en cooldown pour " + ChatColor.DARK_AQUA + cooldown.getPlayerCooldownTimeLeft(player) + ChatColor.WHITE + ".");
            return;
        }

        int number = 0;

        for (Entity entity : player.getNearbyEntities(15, 15, 15)) {
            if (!(entity instanceof Player))
                return;

            Player p = (Player) entity;

            if (AllStarsParty.roleManager.getPlayerRole(p).getSide() == Side.DS)
                return;

            entity.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.RED + "Muichiro" + ChatColor.WHITE + " vous " + ChatColor.DARK_AQUA + "aveugle" + ChatColor.WHITE + ".");

            number++;

            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 10*20, 0));
        }

        if (number == 0)
            return;

        player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE + " Vous avez aveuglé " + ChatColor.DARK_AQUA + number + " joueurs" + ChatColor.WHITE + ".");
        cooldown.putPlayerInCooldown(player);
    }
}
