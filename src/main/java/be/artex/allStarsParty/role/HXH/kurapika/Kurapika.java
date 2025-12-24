package be.artex.allStarsParty.role.HXH.kurapika;

import be.artex.allStarsParty.TextUtil;
import be.artex.allStarsParty.gameLogic.ASPItem;
import be.artex.allStarsParty.gameLogic.Role;
import be.artex.allStarsParty.gameLogic.Side;
import org.bukkit.ChatColor;

import java.util.Collections;
import java.util.List;

public class Kurapika extends Role {
    private static final String DESCRIPTION = TextUtil.BORDER +
            "\n" + ChatColor.WHITE + " Vous êtes " + ChatColor.GREEN + "Kurapika" + ChatColor.WHITE + "." +
            "\n Vous possédez un item, votre " + ChatColor.GOLD + ChatColor.BOLD + "serment" + ChatColor.WHITE + "." +
            "\n - " + ChatColor.GOLD + ChatColor.BOLD + "Serment" + ChatColor.WHITE + ": Quand vous " + ChatColor.DARK_AQUA + "cliquez" + ChatColor.WHITE + ", vous recevez +30% de " + ChatColor.RED + "force" + ChatColor.WHITE + " mais vous perdez -15% de " + ChatColor.GRAY + "resistance" + ChatColor.WHITE + " pendant une 25 secondes." + ChatColor.GRAY + " (Cooldown: 2m30)" +
            "\n" + TextUtil.BORDER;

    @Override
    public String getName() {
        return "Kurapika";
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
    public List<ASPItem> getItems() {
        return Collections.singletonList(new Serment());
    }
}
