package be.artex.allStarsParty.role.old.solo.hisoka;

import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.Cooldown;
import be.artex.allStarsParty.api.items.ASPItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JeuDeCartes extends ASPItem {
    private static final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Je" + ChatColor.RED + ChatColor.BOLD + "u de " + ChatColor.DARK_GRAY + ChatColor.BOLD + "Car" + ChatColor.RED + ChatColor.BOLD + "tes").build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteraction(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Cooldown cooldown = Cooldown.getCooldown("jeu_de_cartes", 120*20);

        if (cooldown.isPlayerInCooldown(player)) {
            player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE +" Vous êtes encore en cooldown pour " + ChatColor.DARK_AQUA + cooldown.getPlayerCooldownTimeLeft(player) + " secondes" + ChatColor.WHITE + ".");
            return;
        }

        HisokaPower[] powers = HisokaPower.values();

        List<HisokaPower> list = Arrays.asList(powers);
        Collections.shuffle(list);

        HisokaPower first = list.get(0);
        HisokaPower second = list.get(1);

        player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + "Vous avez tiré les cartes: " + first.getName() + ChatColor.WHITE + " et " + second.getName() + ChatColor.WHITE + ".");
        first.apply(player);
        second.apply(player);

        cooldown.putPlayerInCooldown(player);
        HisokaPower.powerAffectedPlayers.add(player);
    }
}
