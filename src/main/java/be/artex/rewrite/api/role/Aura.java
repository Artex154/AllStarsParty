package be.artex.rewrite.api.role;

import org.bukkit.ChatColor;

public enum Aura {
    AUCUNE(ChatColor.WHITE + "" + ChatColor.BOLD + "A"),
    FAIBLE(ChatColor.GREEN + "" + ChatColor.BOLD + "Fa"),
    MOYENNE(ChatColor.YELLOW + "" + ChatColor.BOLD + "M"),
    FORTE(ChatColor.RED + "" + ChatColor.BOLD + "Fo");

    private final String name;

    Aura(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
