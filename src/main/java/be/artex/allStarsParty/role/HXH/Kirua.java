package be.artex.allStarsParty.role.HXH;

import be.artex.allStarsParty.TextUtil;
import be.artex.allStarsParty.logic.Role;
import be.artex.allStarsParty.logic.Side;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Kirua extends Role {
    public static final HashMap<Player, Integer> playerHitNumber = new HashMap<>();

    private static final String DESCRIPTION = TextUtil.BORDER +
            "\n" + ChatColor.WHITE + " Vous êtes " + ChatColor.GREEN + "Kirua" + ChatColor.WHITE + "." +
            "\n Vous possédez 120% de " + ChatColor.YELLOW + "speed" + ChatColor.WHITE + "." +
            "\n Tout les " + ChatColor.DARK_AQUA + "10 coups" + ChatColor.WHITE + ", vous infligez " + ChatColor.LIGHT_PURPLE + "3 demi-coeurs " + ChatColor.WHITE + "de dégats." +
            "\n" + TextUtil.BORDER;

    @Override
    public String getName() {
        return "Kirua";
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
        int hitNumber;

        if (playerHitNumber.get(damager) == null) {
            playerHitNumber.put(damager, 1);
            return;
        } else {
            hitNumber = playerHitNumber.get(damager);
            hitNumber++;

            playerHitNumber.put(damager, hitNumber);
        }

        if (hitNumber != 10)
            return;

        playerHitNumber.put(damager, 0);

        player.getWorld().strikeLightning(player.getLocation());

        if ((player.getHealth() - 3) < 0)
            player.setHealth(1);
        else
            player.setHealth(player.getHealth() - 3);
    }
}
