package be.artex.allStarsParty.role.rewrited.sasuke;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.Cooldown;
import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.stats.Speed;
import be.artex.allStarsParty.registry.RoleRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FDMO extends ASPItem {
    private static final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.AQUA + "" + ChatColor.BOLD + "Flux des Milles Oiseaux").build();
    public static final List<Player> playersLightenend = new ArrayList<>();

    @Override
    public ItemStack getStack() {
        return STACK;
    }

    @Override
    public void onInteraction(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Cooldown cooldown = Cooldown.getCooldown("FDMO", 120*20, ChatColor.WHITE + "Votre" + ChatColor.AQUA + ChatColor.BOLD + " Flux de Milles Oiseaux");

        if (cooldown.isPlayerInCooldown(player)) {
            player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE +" Vous êtes encore en cooldown pour " + ChatColor.DARK_AQUA + cooldown.getPlayerCooldownTimeLeft(player) + " secondes" + ChatColor.WHITE + ".");
            return;
        }

        for (Entity e : player.getNearbyEntities(30, 30, 30)) {
            if (!(e instanceof Player))
                continue;

            Player p = (Player) e;
            Role pR = RoleRegistry.roleManager.getPlayerRole(p);

            if (pR == null || pR.getSide() == Side.NS)
                continue;

            playersLightenend.add(p);
            p.damage(0.1);

            if ((p.getHealth() - 6) > 0)
                p.setHealth(p.getHealth() - 6);
            else p.setHealth(0);

            p.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.DARK_PURPLE + " Sasuke" + ChatColor.WHITE + " vous foudroie.");
            Speed.removeSpeedFromPlayer(p, 10);
        }

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            for (Player p : playersLightenend)
                Speed.addSpeedToPlayer(p, 10);
        }, 200);

        player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE + " Vous avez utilisé votre" + ChatColor.AQUA + ChatColor.BOLD + " Flux de Milles Oiseaux" + ChatColor.WHITE + ".");

        cooldown.putPlayerInCooldown(player);
    }
}
