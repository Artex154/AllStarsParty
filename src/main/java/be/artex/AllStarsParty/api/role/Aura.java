package be.artex.AllStarsParty.api.role;

import org.bukkit.ChatColor;

public enum Aura {
    AUCUNE(ChatColor.GRAY + "Aucune"),
    FAIBLE(ChatColor.GREEN + "Faible"),
    MOYENNE(ChatColor.YELLOW + "Moyenne"),
    FORTE(ChatColor.RED + "Forte");

    private final String name;

    Aura(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
