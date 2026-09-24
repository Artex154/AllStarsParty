package be.artex.AllStarsParty.role.autre.minato;

import be.artex.AllStarsParty.api.item.CustomItem;
import be.artex.AllStarsParty.api.message.Message;
import be.artex.AllStarsParty.util.StatValues;
import be.artex.AllStarsParty.util.Stats;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class Ermite extends CustomItem {
    private final ItemStack STACK = new ItemStack(Material.BLAZE_ROD);
    public static HashMap<UUID, Boolean> PLAYERS_ACTIVATED = new HashMap<>();

    @Override
    public ItemStack getStack() {
        return STACK;
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        System.out.println("a");

        boolean isActivated = PLAYERS_ACTIVATED.getOrDefault(player.getUniqueId(), false);

        if (isActivated) {
            disable(player);
            player.sendMessage(Message.info("Vous avez " + ChatColor.RED + "désactivé " + ChatColor.WHITE + "le " + ChatColor.DARK_GREEN + "mode ermite" + ChatColor.WHITE + "."));
        } else {
            if (Minato.playersErmiteChakra.getOrDefault(player.getUniqueId(), 4000) < 20) {
                player.sendMessage(Message.error("Il ne vous reste plus assez de " + ChatColor.DARK_GREEN + "chakra d'ermite" + ChatColor.WHITE + "."));
                return;
            }

            enable(player);
            player.sendMessage(Message.info("Vous avez " + ChatColor.GREEN + "activé " + ChatColor.WHITE + "le " + ChatColor.DARK_GREEN + "mode ermite" + ChatColor.WHITE + "."));
        }

        PLAYERS_ACTIVATED.put(player.getUniqueId(), !isActivated);
    }

    public static void enable(Player player) {
        Stats stats = Stats.get(player.getUniqueId());

        stats.addBonus(StatValues.STRENGTH, 7.5f);
        stats.addBonus(StatValues.SPEED, 10f);

        PLAYERS_ACTIVATED.put(player.getUniqueId(), true);
    }

    public static void disable(Player player) {
        Stats stats = Stats.get(player.getUniqueId());

        stats.addBonus(StatValues.STRENGTH, -7.5f);
        stats.addBonus(StatValues.SPEED, -10f);

        PLAYERS_ACTIVATED.put(player.getUniqueId(), false);
    }
}
