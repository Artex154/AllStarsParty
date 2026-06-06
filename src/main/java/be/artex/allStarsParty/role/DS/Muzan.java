package be.artex.allStarsParty.role.DS;

import be.artex.allStarsParty.TextUtil;
import be.artex.allStarsParty.logic.Role;
import be.artex.allStarsParty.logic.Side;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Muzan extends Role {
    private static final String DESCRIPTION = TextUtil.BORDER +
            "\n" + ChatColor.WHITE + " Vous êtes " + ChatColor.RED + "Muzan" + ChatColor.WHITE + "." +
            "\n Vous possédez 105% de " + ChatColor.RED + "force" + ChatColor.WHITE + "." +
            "\n Quand vous " + ChatColor.RED + "tappez " + ChatColor.WHITE + "un joueur, vous régénérez " + ChatColor.LIGHT_PURPLE + "10% des dégats infligés " + ChatColor.WHITE + "." +
            "\n" + TextUtil.BORDER;

    @Override
    public String getName() {
        return "Muzan";
    }

    @Override
    public Side getSide() {
        return Side.DS;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public int getStrength() {
        return 105;
    }

    @Override
    public void onHit(Player player, Player damager, double damage) {
        damager.setHealth(damager.getHealth() + (damage / 10));
    }
}
