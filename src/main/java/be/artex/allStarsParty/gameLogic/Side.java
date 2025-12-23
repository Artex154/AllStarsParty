package be.artex.allStarsParty.gameLogic;

import org.bukkit.ChatColor;

import java.util.ArrayList;

public enum Side {
    MHA("MHA", ChatColor.BLUE, new ArrayList<>()),
    YORIICHI("Yoriichi", ChatColor.YELLOW, new ArrayList<>());

    private final String name;
    private final ChatColor color;
    private final ArrayList<Role> roles;

    Side(String name, ChatColor color, ArrayList<Role> roles) {
        this.name = name;
        this.color = color;
        this.roles = roles;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }
}
