package be.artex.allStarsParty.role.HXH;

import be.artex.allStarsParty.TextUtil;
import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Random;

public class Neferupito extends Role {
    private static final String DESCRIPTION = TextUtil.BORDER +
            "\n" + ChatColor.WHITE + " Vous êtes " + ChatColor.GREEN + "Neferupito" + ChatColor.WHITE + "." +
            "\n " +
            "\n Lorsque vous " + ChatColor.RED + "tappez " + ChatColor.WHITE + "un joueur, vous possédez " + ChatColor.DARK_AQUA + "15% " + ChatColor.WHITE + "de chance" +
            "\n de lui infliger " + ChatColor.DARK_GRAY + "blindless " + ChatColor.WHITE + "pendant" + ChatColor.YELLOW + " 3 secondes" + ChatColor.WHITE + "." +
            "\n " +
            "\n Lorsque vous " + ChatColor.RED + "tuez " + ChatColor.WHITE + "un joueur, vous gagnez" + ChatColor.GOLD + " 4 coeurs d'absorption" + ChatColor.WHITE + "." +
            "\n" + TextUtil.BORDER;

    @Override
    public String getName() {
        return "Neferupito";
    }

    @Override
    public Side getSide() {
        return Side.HXH;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
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
