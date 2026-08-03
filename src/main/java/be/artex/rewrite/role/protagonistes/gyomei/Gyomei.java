package be.artex.rewrite.role.protagonistes.gyomei;

import be.artex.rewrite.api.role.Aura;
import be.artex.rewrite.api.role.Role;
import be.artex.rewrite.api.role.Side;
import be.artex.rewrite.registry.ItemRegistry;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Gyomei extends Role {
    private final String DESCRIPTION =
            ChatColor.GRAY + " Vous êtes " + ChatColor.GREEN + ChatColor.BOLD + "Gyomei\n" +
                    ChatColor.GRAY + " Objectif:" + ChatColor.WHITE + " Vous devez gagner avec les " + ChatColor.GREEN + "protagonistes" + ChatColor.WHITE + ".\n \n" +
                    ChatColor.GRAY + ChatColor.BOLD + "» Passifs: \n" +
                    ChatColor.WHITE + " Vous possédez " + ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "✦" + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY + " Resistance 0,5" + ChatColor.WHITE + " ainsi que" + ChatColor.LIGHT_PURPLE + " 12❤ permanents" + ChatColor.WHITE + ".\n" +
                    ChatColor.WHITE + " Vous disposez d'une " + ChatColor.AQUA + "hache en diamant" + ChatColor.WHITE + ", " + ChatColor.RED + ChatColor.BOLD + "Kusarigama" + ChatColor.WHITE + ". Elle est enchantée avec" + ChatColor.AQUA + " tranchant 4" + ChatColor.WHITE + ".\n \n" +
                    ChatColor.GRAY + ChatColor.BOLD + "» Compétences activables: \n" +
                    ChatColor.DARK_GRAY + " [" + ChatColor.RED + ChatColor.BOLD + "⚔" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + ChatColor.BOLD + " Kusarigama" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "clic droit en sneakant" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "1x/45s\n" +
                    ChatColor.WHITE + "   Vous téléportez le joueur ciblé sur vous.";

    @Override
    public @NotNull String getName() {
        return "Gyomei";
    }

    @Override
    public @NotNull Side getSide() {
        return Side.PROTAGONISTES;
    }

    @Override
    public @NotNull String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public @NotNull Aura getAura() {
        return Aura.FORTE;
    }

    @Override
    public int getBonusResistance() {
        return 5;
    }

    @Override
    public int getBonusMaxHealth() {
        return 4;
    }

    @Override
    public void whenAssigned(Player player) {
        player.getInventory().setItem(0, ItemRegistry.GYOMEI_KUSARIGAMA.getStack());
    }
}
