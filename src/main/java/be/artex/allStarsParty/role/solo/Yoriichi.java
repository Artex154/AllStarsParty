package be.artex.allStarsParty.role.solo;

import be.artex.allStarsParty.TextUtil;
import be.artex.allStarsParty.logic.ASPItem;
import be.artex.allStarsParty.logic.Role;
import be.artex.allStarsParty.logic.Side;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Yoriichi extends Role {
    private static final String DESCRIPTION = TextUtil.BORDER +
            "\n" + ChatColor.WHITE + " Vous êtes " + ChatColor.YELLOW + "Yoriichi" + ChatColor.WHITE + "." +
            "\n Vous possédez 120% de " + ChatColor.RED + "force" + ChatColor.WHITE + " et de " + ChatColor.GRAY + "resistance" + ChatColor.WHITE + "." +
            "\n Vous possédez aussi " + ChatColor.YELLOW + "24 pommes dorées" + ChatColor.WHITE + "." +
            "\n" + TextUtil.BORDER;

    @Override
    public String getName() {
        return "Yoriichi";
    }

    @Override
    public Side getSide() {
        return Side.YORIICHI;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public List<ASPItem> getItems() {
        List<ASPItem> items = new ArrayList<>();

        items.add(new BonusGoldenApples());

        return items;
    }

    @Override
    public int getResistance() {
        return 120;
    }

    @Override
    public int getStrength() {
        return 120;
    }
}
