package be.artex.allStarsParty.role.MHA.shoto;

import be.artex.allStarsParty.TextUtil;
import be.artex.allStarsParty.logic.ASPItem;
import be.artex.allStarsParty.logic.Role;
import be.artex.allStarsParty.logic.Side;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Shoto extends Role {
    private static final String DESCRIPTION = TextUtil.BORDER +
            "\n" + ChatColor.WHITE + " Vous êtes " + ChatColor.BLUE + "Shoto" + ChatColor.WHITE + "." +
            "\n Vous possédez deux items, votre " + ChatColor.GOLD + ChatColor.BOLD + "côté gauche" + ChatColor.WHITE + " et votre " + ChatColor.AQUA + ChatColor.BOLD + "côté droit" + ChatColor.WHITE + "." +
            "\n - " + ChatColor.GOLD + ChatColor.BOLD + "Côté gauche" + ChatColor.WHITE + ": Quand vous " + ChatColor.RED + "tappez" + ChatColor.WHITE + " un joueur, vous l'" + ChatColor.GOLD  + "enflammez" + ChatColor.WHITE + " pendant 8 secondes. " + ChatColor.GRAY + "(cooldown de 10s)" +
            "\n" + ChatColor.WHITE + " - " + ChatColor.AQUA + ChatColor.BOLD + "Côté droit" + ChatColor.WHITE + ": Quand vous " + ChatColor.RED + "tappez" + ChatColor.WHITE + " un joueur, vous l'enfermez dans un " + ChatColor.AQUA + "cocon de glace" + ChatColor.WHITE + ". " + ChatColor.GRAY + "(cooldown de 60s)" +
            "\n" + TextUtil.BORDER;

    @Override
    public String getName() {
        return "Shoto";
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
        List<ASPItem> items = new ArrayList<>();

        items.add(new FireSide());
        items.add(new IceSide());

        return items;
    }
}
