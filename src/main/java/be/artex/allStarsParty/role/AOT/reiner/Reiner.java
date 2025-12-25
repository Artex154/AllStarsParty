package be.artex.allStarsParty.role.AOT.reiner;

import be.artex.allStarsParty.TextUtil;
import be.artex.allStarsParty.logic.ASPItem;
import be.artex.allStarsParty.logic.Role;
import be.artex.allStarsParty.logic.Side;
import org.bukkit.ChatColor;

import java.util.Collections;
import java.util.List;

public class Reiner extends Role {
    private static final String DESCRIPTION = TextUtil.BORDER +
            "\n" + ChatColor.WHITE + " Vous êtes " + ChatColor.DARK_AQUA + "Reiner" + ChatColor.WHITE + "." +
            "\n Vous possédez un item, votre " + ChatColor.GOLD + ChatColor.BOLD + "transformation" + ChatColor.WHITE + "." +
            "\n - " + ChatColor.GOLD + ChatColor.BOLD + "Transformation" + ChatColor.WHITE + ": Quand vous " + ChatColor.DARK_AQUA + "cliquez" + ChatColor.WHITE + ", vous vous " + ChatColor.DARK_AQUA + "transformez" + ChatColor.WHITE + ". Vous gagnez alors 110% de " + ChatColor.GRAY + "resistance" + ChatColor.WHITE + ". et de" + ChatColor.YELLOW + " vitesse" + ChatColor.WHITE + "." + ChatColor.GRAY + " (Cooldown: 3m)" +
            "\n" + TextUtil.BORDER;

    @Override
    public String getName() {
        return "Reiner";
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
        return Collections.singletonList(new TransformationReiner());
    }
}
