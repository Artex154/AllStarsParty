package be.artex.allStarsParty.role.old.MHA.hawks;

import be.artex.allStarsParty.TextUtil;
import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.registry.ItemRegistry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Hawks extends Role {
    private static final String DESCRIPTION = TextUtil.BORDER +
            "\n" + ChatColor.WHITE + " Vous êtes " + ChatColor.BLUE + "Hawks" + ChatColor.WHITE + "." +
            "\n " +
            "\n Vous possédez" + ChatColor.YELLOW + " 110% de vitesse" + ChatColor.WHITE + "." +
            "\n Vous possédez vos " + ChatColor.GOLD + ChatColor.BOLD + "plumes d'acier" + ChatColor.WHITE + "." +
            "\n - " + ChatColor.GOLD + ChatColor.BOLD + "Plumes d'Acier" + ChatColor.WHITE + ": un arc " + ChatColor.DARK_AQUA + "power 3" + ChatColor.WHITE + "." +
            "\n A chaque tir réussit, vous avez" + ChatColor.DARK_AQUA + " 45%" + ChatColor.WHITE + " de chance de régénerer " + ChatColor.LIGHT_PURPLE + "1 demi-coeur" + ChatColor.WHITE + "." +
            "\n " +
            "\n Lorsque vous " + ChatColor.RED + "tappez " + ChatColor.WHITE + "un joueur, vous avez " + ChatColor.DARK_AQUA + "20%" + ChatColor.WHITE + " de chance de récupérer une flèche." +
            "\n" + TextUtil.BORDER;

    @Override
    public String getName() {
        return "Hawks";
    }

    @Override
    public Side getSide() {
        return Side.MHA;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public int getSpeed() {
        return 110;
    }

    @Override
    public List<ASPItem> getItems() {
        return Collections.singletonList(ItemRegistry.AILES_ACIER);
    }

    @Override
    public void onHit(Player player, Player damager, double damage) {
        Random random = new Random();
        int randomInt = random.nextInt(100);

        if (randomInt > 20)
            return;

        damager.getInventory().addItem(new ItemStack(Material.ARROW));
    }
}