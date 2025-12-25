package be.artex.allStarsParty.role.MHA.denki;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.item_builder.ItemBuilder;
import be.artex.allStarsParty.logic.ASPItem;
import be.artex.allStarsParty.logic.Cooldown;
import be.artex.allStarsParty.logic.Side;
import be.artex.allStarsParty.logic.stats.Speed;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Decharge extends ASPItem {
    public static final List<Player> electrocutedPlayers = new ArrayList<>();
    private static final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.YELLOW + "" + ChatColor.BOLD + "Décharge").build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteraction(PlayerInteractEvent event) {
        Cooldown cooldown = Cooldown.getCooldown("denki_decharge", 5*20);
        Player player = event.getPlayer();

        if (cooldown.isPlayerInCooldown(player)) {
            player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE +" Vous êtes encore en cooldown pour " + ChatColor.DARK_AQUA + cooldown.getPlayerCooldownTimeLeft(player) + ChatColor.WHITE + ".");
            return;
        }

        int number = 0;

        for (Entity entity : player.getNearbyEntities(15, 15, 15)) {
            if (!(entity instanceof Player))
                return;

            Player p = (Player) entity;

            if (AllStarsParty.roleManager.getPlayerRole(p).getSide() == Side.MHA)
                return;

            electrocutedPlayers.add(p);

            Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
                if (!electrocutedPlayers.contains(p))
                    return;

                electrocutedPlayers.remove(p);

                Speed.setPlayerSpeed(p, Speed.getPlayerSpeed(p) + 60);
            }, 7*20);

            entity.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.BLUE + "Denki" + ChatColor.WHITE + " vous " + ChatColor.DARK_AQUA + "électrocute" + ChatColor.WHITE + ".");

            number++;

            Speed.setPlayerSpeed(p, Speed.getPlayerSpeed(p) - 60);
        }

        if (number == 0)
            return;

        player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE + " Vous avez électrocuté " + ChatColor.DARK_AQUA + number + " joueurs" + ChatColor.WHITE + ".");
        cooldown.putPlayerInCooldown(player);
    }
}
