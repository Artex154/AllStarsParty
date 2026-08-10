package be.artex.AllStarsParty.role.antagoniste.akaza;

import be.artex.AllStarsParty.api.message.Message;
import be.artex.AllStarsParty.AllStarsParty;
import be.artex.AllStarsParty.api.item.CustomItem;
import be.artex.AllStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.AllStarsParty.util.PlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class LCAB extends CustomItem {
    private final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.BLUE + "" + ChatColor.BOLD + "Lueur chaotique d'agent bleu").lore().build();
    public static final List<UUID> playersThatHaveActivedIt = new ArrayList<>();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (playersThatHaveActivedIt.contains(player.getUniqueId())) {
            player.sendMessage(Message.error("Vous avez déjà utilisé cette technique lors de cette partie."));
            return;
        }

        playersThatHaveActivedIt.add(player.getUniqueId());

        new BukkitRunnable() {
            int i = 0;

            @Override
            public void run() {
                if (i == 3) {
                    cancel();
                    return;
                }

                i++;

                player.getNearbyEntities(20, 20, 20).forEach(e -> {
                    if (!(e instanceof Player))
                        return;

                    Player p = (Player) e;

                    PlayerUtil.inflictTrueDamage(p, 2);

                    Vector direction = p.getLocation().toVector()
                            .subtract(player.getLocation().toVector())
                            .normalize();

                    Vector velocity = direction.multiply(1.0);
                    velocity.setY(0.4);

                    p.setVelocity(velocity);
                });
            }
        }.runTaskTimer(AllStarsParty.instance, 5, 20);
    }
}
