package be.artex.allStarsParty.gameLogic;

import org.bukkit.ChatColor;

import java.util.ArrayList;

public abstract class Side {
    public abstract String getName();
    public abstract ChatColor getColor();
    public abstract ArrayList<Role> getRoles();
}
