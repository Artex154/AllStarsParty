package be.artex.allStarsParty.role.AOT;

import be.artex.allStarsParty.TextUtil;
import be.artex.allStarsParty.gameLogic.Role;
import be.artex.allStarsParty.gameLogic.Side;
import org.bukkit.ChatColor;

public class Livai extends Role {
    private static final String DESCRIPTION = TextUtil.BORDER +
            "\n" + ChatColor.WHITE + " Vous êtes " + ChatColor.DARK_AQUA + "Livaï" + ChatColor.WHITE + "." +
            "\n Vous possédez 125% de " + ChatColor.YELLOW + "speed" + ChatColor.WHITE + " ainsi que 105% de" + ChatColor.RED + " force" + ChatColor.WHITE + "." +
            "\n" + TextUtil.BORDER;

    @Override
    public String getName() {
        return "Livaï";
    }

    @Override
    public Side getSide() {
        return Side.AOT;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public int getSpeed() {
        return 125;
    }

    @Override
    public int getStrength() {
        return 105;
    }
}
