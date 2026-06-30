package be.artex.allStarsParty.api.message;

import org.bukkit.ChatColor;

public class Message {
    public static String info(String string) {
        return ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + string;
    }

    public static String warn(String string) {
        return ChatColor.GOLD + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + string;
    }

    public static String error(String string) {
        return ChatColor.RED + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + string;
    }

    public static String cooldownTimeLeft(long timeLeft) {
        return info("Vous êtes encore en cooldown pour " + ChatColor.DARK_AQUA + timeLeft + " secondes" + ChatColor.WHITE + ".");
    }
}
