package be.artex.allStarsParty.gameLogic;

import org.bukkit.ChatColor;

import java.util.ArrayList;

public enum Side {
    ;

    private final ChatColor color;
    private final ArrayList<Role> roles;

    Side(ChatColor color, ArrayList<Role> roles) {
        this.color = color;
        this.roles = roles;
    }

    public ChatColor getColor() {
        return color;
    }

    public ArrayList<Role> getRoles() {
        return roles;
    }
}
