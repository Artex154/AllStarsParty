package be.artex.allStarsParty.logic;

import org.bukkit.ChatColor;

public enum Side {
    MHA("MHA", ChatColor.BLUE),
    DS("Demon Slayer", ChatColor.RED),
    HXH("Hunter x Hunter", ChatColor.GREEN),
    AOT("Attaque des Titans", ChatColor.DARK_AQUA),
    YORIICHI("Yoriichi", ChatColor.YELLOW);

    private final String name;
    private final ChatColor color;

    Side(String name, ChatColor color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public ChatColor getColor() {
        return color;
    }
}
