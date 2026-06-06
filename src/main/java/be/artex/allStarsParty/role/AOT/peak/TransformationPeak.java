package be.artex.allStarsParty.role.AOT.peak;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.item_builder.ItemBuilder;
import be.artex.allStarsParty.logic.ASPItem;
import be.artex.allStarsParty.logic.Cooldown;
import be.artex.allStarsParty.logic.stats.Speed;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class TransformationPeak extends ASPItem {
    private static final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.GOLD + "" + ChatColor.BOLD + "Transformation : Titan Charrette").build();

    public static final List<Player> transformedPlayer = new ArrayList<>();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteraction(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Cooldown cooldown = Cooldown.getCooldown("transformation_peak", 180*20);

        if (cooldown.isPlayerInCooldown(player)) {
            player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE +" Vous êtes encore en cooldown pour " + ChatColor.DARK_AQUA + cooldown.getPlayerCooldownTimeLeft(player) + ChatColor.WHITE + ".");
            return;
        }

        Speed.setPlayerSpeed(player, 125);

        player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE +" Vous vous êtes " + ChatColor.DARK_AQUA + "transformez" + ChatColor.WHITE + ".");

        transformedPlayer.remove(player);
        transformedPlayer.add(player);

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            if (transformedPlayer.contains(player))
                return;

            player.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE + " Votre " + ChatColor.DARK_AQUA + "transformation" + ChatColor.WHITE + " cesse.");

            Speed.setPlayerSpeed(player, 100);
        }, 25*20L);

        cooldown.putPlayerInCooldown(player);
    }
}
