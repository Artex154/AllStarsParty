package be.artex.rewrite.role.protagonistes.mrjack;

import be.artex.allStarsParty.api.message.Message;
import be.artex.rewrite.AllStarsParty;
import be.artex.rewrite.api.item.CustomItem;
import be.artex.rewrite.api.itemBuilder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Costumes extends CustomItem {
    private final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.GOLD + "" + ChatColor.BOLD + "Costumes").lore().build();
    public static final Map<UUID, Long> playersWhenActivated = new HashMap<>();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        CostumesHolder holder = CostumesHolder.get(player.getUniqueId());
        CostumesType type = holder.getType();
        boolean isActive = holder.isActive();
        long time = System.currentTimeMillis();

        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (isActive) {
                holder.deactivate(player);
                long elapsedMillis = time - playersWhenActivated.get(player.getUniqueId());
                long elapsedTicks = elapsedMillis / 50;

                holder.removeTicks(type, elapsedTicks);

                player.sendMessage(Message.warn("Il ne vous reste " + ChatColor.GOLD + ((double) holder.getTypeTicksLeft(type) / 20) + " secondes" + ChatColor.WHITE + " sur votre " + type.getName() + ChatColor.WHITE + "."));
            }

            holder.switchTypes();

            player.sendMessage(Message.info("Vous changez en " + holder.getType().getName() + ChatColor.WHITE + "."));
        } else if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            long tickLefts = holder.getTypeTicksLeft(type);

            if (!holder.isActive()) {
                if (tickLefts == 0) {
                    player.sendMessage(Message.error("Il ne vous reste aucun temps."));
                    return;
                }

                player.sendMessage(Message.info("Vous " + ChatColor.GREEN + "activez" + ChatColor. WHITE + " le " + holder.getType().getName() + ChatColor.WHITE + "."));

                holder.activate(player);
                playersWhenActivated.put(player.getUniqueId(), time);

                Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
                    if (playersWhenActivated.get(player.getUniqueId()) == null)
                        return;

                    if (holder.isActive() && time == playersWhenActivated.get(player.getUniqueId())) {
                        player.sendMessage(Message.warn("Il ne vous reste plus de temps"));
                        holder.deactivate(player);
                        holder.setTicksLeft(type, 0);
                    }
                }, tickLefts);
            } else {
                player.sendMessage(Message.info("Vous " + ChatColor.RED + "désactivez" + ChatColor. WHITE + " le " + holder.getType().getName() + ChatColor.WHITE + "."));

                long elapsedMillis = time - playersWhenActivated.get(player.getUniqueId());
                long elapsedTicks = elapsedMillis / 50;

                holder.removeTicks(type, elapsedTicks);
                holder.deactivate(player);

                player.sendMessage(Message.warn("Il ne vous reste " + ChatColor.GOLD + ((double) holder.getTypeTicksLeft(type) / 20) + " secondes" + ChatColor.WHITE + " sur votre " + type.getName() + ChatColor.WHITE + "."));
            }
        }
    }
}
