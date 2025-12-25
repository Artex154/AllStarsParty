package be.artex.allStarsParty.role.MHA.denki;

import be.artex.allStarsParty.TextUtil;
import be.artex.allStarsParty.logic.ASPItem;
import be.artex.allStarsParty.logic.Role;
import be.artex.allStarsParty.logic.Side;
import org.bukkit.ChatColor;

import java.util.Collections;
import java.util.List;

public class Denki extends Role {
    private static final String DESCRIPTION = TextUtil.BORDER +
            "\n" + ChatColor.WHITE + " Vous êtes " + ChatColor.BLUE + "Denki" + ChatColor.WHITE + "." +
            "\n Vous possédez un item, votre " + ChatColor.YELLOW + ChatColor.BOLD + "décharge" + ChatColor.WHITE + "." +
            "\n - " + ChatColor.YELLOW + ChatColor.BOLD + "Décharge" + ChatColor.WHITE + ": Quand vous " + ChatColor.DARK_AQUA + "cliquez" + ChatColor.WHITE + ", vous " + ChatColor.YELLOW + "électrifiez" + ChatColor.WHITE + " tout les joueurs dans un rayon de" + ChatColor.DARK_AQUA + " 10 blocs" + ChatColor.WHITE + ". Ils reçoivent -60% de" + ChatColor.YELLOW + " vitesse" + ChatColor.WHITE + "." + ChatColor.GRAY + " (Cooldown: 60s)" +
            "\n" + TextUtil.BORDER;

    @Override
    public String getName() {
        return "Denki";
    }

    @Override
    public Side getSide() {
        return Side.MHA;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public List<ASPItem> getItems() {
        return Collections.singletonList(new Decharge());
    }
}
