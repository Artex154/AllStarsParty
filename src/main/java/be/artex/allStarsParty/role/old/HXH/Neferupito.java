package be.artex.allStarsParty.role.old.HXH;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Neferupito extends Role {
    @Override
    public String getName() {
        return "Neferupito";
    }

    @Override
    public Side getSide() {
        return Side.HXH;
    }

    @Override
    public void onHit(Player player, Player damager, double damage) {
        Random random = new Random();
        int randomInt = random.nextInt(100);

        if (randomInt > 15)
            return;

        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 0), true);
        player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.DARK_AQUA + "Neferupito" + ChatColor.WHITE + " vous inflige " + ChatColor.DARK_GRAY + "blindless" + ChatColor.GRAY + ".");

        damager.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE + "vous avez infligé " + ChatColor.DARK_GRAY + "blindless" + ChatColor.WHITE + ".");
    }

    @Override
    public void onKill(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();

        killer.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 600, 1, false, false), true);
    }
}
