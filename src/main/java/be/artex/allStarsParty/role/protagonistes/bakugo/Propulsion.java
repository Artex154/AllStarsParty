package be.artex.allStarsParty.role.protagonistes.bakugo;

import be.artex.allStarsParty.api.message.Message;
import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.item.CustomItem;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Propulsion extends CustomItem {
    private final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.GOLD + "" + ChatColor.BOLD + "Propulsion").build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        int nitro = Bakugo.playersNitro.get(player.getUniqueId());

        if (nitro - 1 <= -1) {
            player.sendMessage(Message.error("Vous n'avez pas assez de nitroglycérine."));
            return;
        }

        player.setVelocity(new Vector(0, 3f, 0));

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () ->  {
            Vector velocity = player.getLocation().getDirection()
                    .normalize()
                    .setY(0.25)
                    .multiply(1.75);

            player.setVelocity(velocity);
        }, 1);

        Bakugo.playersNitro.put(player.getUniqueId(), nitro - 1);
    }
}
