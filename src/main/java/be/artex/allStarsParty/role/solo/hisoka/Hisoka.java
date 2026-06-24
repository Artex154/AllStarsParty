package be.artex.allStarsParty.role.solo.hisoka;

import be.artex.allStarsParty.TextUtil;
import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.registry.ItemRegistry;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class Hisoka extends Role {
    private static final String DESCRIPTION = TextUtil.BORDER +
            "\n" + ChatColor.WHITE + " Vous êtes " + ChatColor.YELLOW + "Hisoka" + ChatColor.WHITE + "." +
            "\n " +
            "\n Vous possédez votre " + ChatColor.DARK_GRAY + ChatColor.BOLD + "je" + ChatColor.RED + ChatColor.BOLD + "u de " + ChatColor.DARK_GRAY + ChatColor.BOLD + "car" + ChatColor.RED + ChatColor.BOLD + "tes" + ChatColor.WHITE + "." +
            "\n  - " + ChatColor.DARK_GRAY + ChatColor.BOLD + "Je" + ChatColor.RED + ChatColor.BOLD + "u de " + ChatColor.DARK_GRAY + ChatColor.BOLD + "Car" + ChatColor.RED + ChatColor.BOLD + "tes" + ChatColor.WHITE + " : vous recevez deux types de cartes aléatoires pendant" + ChatColor.YELLOW + " 90s" + ChatColor.WHITE + " parmmis ceux ci-dessous. " + ChatColor.GRAY + "(Cooldown: 2m)" +
            "\n    " + ChatColor.DARK_GRAY + "Pique ♠" + ChatColor.WHITE + " : +20% de" + ChatColor.RED + " force" + ChatColor.WHITE + "." +
            "\n    " + ChatColor.DARK_GRAY + "Trèfle ♣" + ChatColor.WHITE + " : + 20% de" + ChatColor.YELLOW + " vitesse" + ChatColor.WHITE + "." +
            "\n    " + ChatColor.RED + "Coeur ♥" + ChatColor.WHITE + " : +5" + ChatColor.LIGHT_PURPLE + " coeurs " + ChatColor.WHITE + "permanents." +
            "\n    " + ChatColor.RED + "Carreau ♥" + ChatColor.WHITE + " : +20% de" + ChatColor.GRAY + " resistance" + ChatColor.WHITE + "." +
            "\n " +
            "\n Vous possédez aussi " + ChatColor.YELLOW + "24 pommes dorées" + ChatColor.WHITE + "." +
            "\n" + TextUtil.BORDER;

    @Override
    public String getName() {
        return "Hisoka";
    }

    @Override
    public Side getSide() {
        return Side.HISOKA;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public List<ASPItem> getItems() {
        ArrayList<ASPItem> list = new ArrayList<>();

        list.add(ItemRegistry.JEU_DE_CARTES);
        list.add(ItemRegistry.BONUS_GOLDEN_APPLES);

        return list;
    }
}