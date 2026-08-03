package be.artex.rewrite.role.divergents.sasuke;

import be.artex.rewrite.api.item.CustomItem;
import be.artex.rewrite.api.role.Aura;
import be.artex.rewrite.api.role.Role;
import be.artex.rewrite.api.role.Side;
import be.artex.rewrite.registry.ItemRegistry;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Sasuke extends Role {
    private final String DESCRIPTION =
            ChatColor.GRAY + " Vous êtes " + ChatColor.YELLOW + ChatColor.BOLD + "Sasuke\n" +
                    ChatColor.GRAY + " Objectif:" + ChatColor.WHITE + " Vous devez gagner avec les " + ChatColor.RED + "divergents" + ChatColor.WHITE + ".\n \n" +
                    ChatColor.GRAY + ChatColor.BOLD + "» Passifs: \n" +
                    ChatColor.WHITE + " Vous possédez " + ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "➤" + ChatColor.DARK_GRAY + "]" + ChatColor.YELLOW + " Vitesse 1" + ChatColor.WHITE + "ainsi que " + ChatColor.GOLD + "resistance au feu" + ChatColor.WHITE + ".\n \n" +
                    ChatColor.GRAY + ChatColor.BOLD + "» Compétences activables: \n" +
                    ChatColor.DARK_GRAY + " [" + ChatColor.GOLD + "✦" + ChatColor.DARK_GRAY + "]" + ChatColor.GOLD + ChatColor.BOLD + " Amaterasu" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "clic droit" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "1x/1mn30s\n" +
                    ChatColor.WHITE + "     Vous infligez un" + ChatColor.GOLD + " feu non-éteignable " + ChatColor.WHITE + "au joueur ciblé pendant 8 secondes.\n" +
                    ChatColor.DARK_GRAY + " [" + ChatColor.DARK_PURPLE + "✦" + ChatColor.DARK_GRAY + "]" + ChatColor.DARK_PURPLE + ChatColor.BOLD + " Rinnegan" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "clic droit" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "1x/1mn15s\n" +
                    ChatColor.WHITE + "     Vous échangez de place avec le joueur ciblé.";


    @Override
    public @NotNull String getName() {
        return "Sasuke";
    }

    @Override
    public @NotNull Side getSide() {
        return Side.DIVERGENTS;
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
    public int getBonusSpeed() {
        return 10;
    }

    @Override
    public void whenAssigned(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
    }

    @Override
    public List<CustomItem> getCustomItems() {
        List<CustomItem> customItems = new ArrayList<>();

        customItems.add(ItemRegistry.SASUKE_AMATERASU);
        customItems.add(ItemRegistry.SASUKE_RINNEGAN);

        return customItems;
    }
}
