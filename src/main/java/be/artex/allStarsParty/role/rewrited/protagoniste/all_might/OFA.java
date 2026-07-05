package be.artex.allStarsParty.role.rewrited.protagoniste.all_might;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.message.Message;
import be.artex.allStarsParty.api.stats.Resistance;
import be.artex.allStarsParty.api.stats.Speed;
import be.artex.allStarsParty.api.stats.Strength;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class OFA extends ASPItem {
    private static final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.GOLD + "" + ChatColor.BOLD + "One For All").build();
    public static final Map<Player, Boolean> playersActivated = new HashMap<>();
    public static final Map<Player, Long> playersWhenActivated = new HashMap<>();
    public static final Map<Player, Long> playersTimeLeft = new HashMap<>();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteraction(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        boolean isActivated = playersActivated.getOrDefault(player, false);
        long currentTimeLeftTicks = playersTimeLeft.getOrDefault(player, 45*20L);
        long currentSystemMillis = System.currentTimeMillis();

        if (isActivated) {
            playersActivated.put(player, false);

            player.sendMessage(Message.info("Vous avez" + ChatColor.RED + " désactivé" + ChatColor.WHITE + " le " + ChatColor.GOLD + ChatColor.BOLD + "One For All" + ChatColor.WHITE + "."));

            Strength.removeStrengthFromPlayer(player, 10);
            Speed.removeSpeedFromPlayer(player, 30);
            Resistance.removeResistanceFromPlayer(player, 10);

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

            player.sendMessage(Message.info("Vous avez" + ChatColor.GREEN + " activé" + ChatColor.WHITE + " le " + ChatColor.GOLD + ChatColor.BOLD + "One For All" + ChatColor.WHITE + "."));

            playersActivated.put(player, true);
            playersWhenActivated.put(player, currentSystemMillis);

            Strength.addStrengthToPlayer(player, 10);
            Speed.addSpeedToPlayer(player, 30);
            Resistance.addResistanceToPlayer(player, 10);

            Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
                if (playersWhenActivated.get(player) == currentSystemMillis && playersActivated.get(player)) {
                    Strength.removeStrengthFromPlayer(player, 10);
                    Speed.removeSpeedFromPlayer(player, 30);
                    Resistance.removeResistanceFromPlayer(player, 10);

                    player.sendMessage(Message.warn("Le " + ChatColor.GOLD + ChatColor.BOLD + "One For All" + ChatColor.WHITE + " cesse."));
                    playersTimeLeft.put(player, 0L);
                    playersActivated.put(player, false);
                }
            }, currentTimeLeftTicks);
        }

    }
}
