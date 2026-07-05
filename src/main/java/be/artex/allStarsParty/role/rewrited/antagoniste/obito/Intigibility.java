package be.artex.allStarsParty.role.rewrited.antagoniste.obito;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Intigibility extends ASPItem {
    private static final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Intigibilité").build();
    public static final List<Player> playersActivated = new ArrayList<>();
    public static final Map<Player, Long> playersWhenActivated = new HashMap<>();
    public static final Map<Player, Long> playersTimeLeft = new HashMap<>();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteraction(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        boolean isActivated = playersActivated.contains(player);
        long currentTimeLeftTicks = playersTimeLeft.getOrDefault(player, 20*20L);
        long currentSystemMillis = System.currentTimeMillis();

        if (isActivated) {
            playersActivated.remove(player);

            player.sendMessage(Message.info("Vous avez" + ChatColor.RED + " désactivé" + ChatColor.WHITE + " l'" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "intigibilité" + ChatColor.WHITE + "."));

            long elapsedMillis = currentSystemMillis - playersWhenActivated.get(player);
            long elapsedTicks = elapsedMillis / 50;

            long remainingTicks = currentTimeLeftTicks - elapsedTicks;
            playersTimeLeft.put(player, Math.max(0L, remainingTicks));

            player.sendMessage(Message.info("Il vous reste " + ChatColor.DARK_AQUA + ((float) remainingTicks / 20) + " secondes" + ChatColor.WHITE + "."));
        } else {
            if (currentTimeLeftTicks == 0L) {
                player.sendMessage(Message.error("Il ne vous reste " + ChatColor.RED + "aucun temps" + ChatColor.WHITE + "."));
                return;
            }

            player.sendMessage(Message.info("Vous avez" + ChatColor.GREEN + " activé" + ChatColor.WHITE + " l'" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "intigibilité" + ChatColor.WHITE + "."));

            playersActivated.add(player);
            playersWhenActivated.put(player, currentSystemMillis);

            Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
                if (playersWhenActivated.get(player) == currentSystemMillis && playersActivated.contains(player)) {
                    player.sendMessage(Message.warn("L'" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "intigibilité" + ChatColor.WHITE + " cesse."));
                    playersTimeLeft.put(player, 0L);
                    playersActivated.remove(player);
                }
            }, currentTimeLeftTicks);
        }

    }
}
