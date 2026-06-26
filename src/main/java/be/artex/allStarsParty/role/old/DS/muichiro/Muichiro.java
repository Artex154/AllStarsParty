package be.artex.allStarsParty.role.old.DS.muichiro;

import be.artex.allStarsParty.TextUtil;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.registry.ItemRegistry;
import org.bukkit.ChatColor;

import java.util.Collections;
import java.util.List;

public class Muichiro extends Role {
    private static final String DESCRIPTION = TextUtil.BORDER +
            "\n" + ChatColor.WHITE + " Vous êtes " + ChatColor.RED + "Muichiro" + ChatColor.WHITE + "." +
            "\n Vous possédez un item, votre " + ChatColor.DARK_AQUA + ChatColor.BOLD + "souffle de la brume" + ChatColor.WHITE + "." +
            "\n - " + ChatColor.DARK_AQUA + ChatColor.BOLD + "Souffle de la brume" + ChatColor.WHITE + ": Quand vous " + ChatColor.DARK_AQUA + "cliquez" + ChatColor.WHITE + ", vous " + ChatColor.DARK_AQUA  + "aveuglez" + ChatColor.WHITE + " tout les joueurs dans un rayon de 15 blocs pendant 10 secondes. " + ChatColor.GRAY + "(cooldown de 60s)" +
            "\n" + TextUtil.BORDER;

    @Override
    public String getName() {
        return "Muichiro";
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
    public List<ASPItem> getItems() {
        return Collections.singletonList(ItemRegistry.SOUFFLE_BRUME);
    }
}
