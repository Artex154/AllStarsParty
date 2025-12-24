package be.artex.allStarsParty.role.DS;

import be.artex.allStarsParty.TextUtil;
import be.artex.allStarsParty.logic.Role;
import be.artex.allStarsParty.logic.Side;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Kokushibo extends Role {
    private static final String DESCRIPTION = TextUtil.BORDER +
            "\n" + ChatColor.WHITE + " Vous êtes " + ChatColor.RED + "Kokushibo" + ChatColor.WHITE + "." +
            "\n Vous possédez 105% de " + ChatColor.RED + "force" + ChatColor.WHITE + "." +
            "\n Quand vous " + ChatColor.RED + "tuez " + ChatColor.WHITE + "un joueur, vous gagnez " + ChatColor.LIGHT_PURPLE + "1 coeur " + ChatColor.WHITE + "permanent." +
            "\n" + TextUtil.BORDER;

    @Override
    public String getName() {
        return "Kokushibo";
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
    public void onKill(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();

        killer.setMaxHealth(killer.getMaxHealth() + 2);
        killer.setHealth(killer.getHealth() + 2);
    }
}
