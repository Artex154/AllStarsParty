package be.artex.allStarsParty.role.AOT.peak;

import be.artex.allStarsParty.TextUtil;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.registry.ItemRegistry;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Peak extends Role {
    private static final String DESCRIPTION = TextUtil.BORDER +
            "\n" + ChatColor.WHITE + " Vous êtes " + ChatColor.DARK_AQUA + "Peak" + ChatColor.WHITE + "." +
            "\n " +
            "\n Vous possédez votre " + ChatColor.GOLD + ChatColor.BOLD + "transformation" + ChatColor.WHITE + "." +
            "\n - " + ChatColor.GOLD + ChatColor.BOLD + "Transformation" + ChatColor.WHITE + ": lorsque vous faites un " + ChatColor.DARK_AQUA + "clic droit" + ChatColor.WHITE + ", vous vous " + ChatColor.DARK_AQUA + "transformerez " + ChatColor.WHITE + "pendant " + ChatColor.YELLOW + "25 secondes" + ChatColor.WHITE + "." +
            "\n   Vous gagnez alors +25% de " + ChatColor.YELLOW + "vitesse" + ChatColor.WHITE + "." + ChatColor.GRAY + " (Cooldown: 3m)" +
            "\n" + ChatColor.WHITE + "   De plus, en étant transformé, vous pourrez utiliser votre" + ChatColor.GOLD + ChatColor.BOLD + "dash" + ChatColor.WHITE +
            "\n à l'aide d'un " + ChatColor.DARK_AQUA + "clic droit" + ChatColor.WHITE + "." + ChatColor.GRAY + " (Cooldown: 10s)" +
            "\n" + TextUtil.BORDER;

    @Override
    public String getName() {
        return "Peak";
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
    public List<ASPItem> getItems() {
        ArrayList<ASPItem> list = new ArrayList<>();

        list.add(ItemRegistry.TRANSFORMATION_PEAK);
        list.add(ItemRegistry.DASH_PEAK);

        return list;
    }
}
