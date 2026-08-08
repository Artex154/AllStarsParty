package be.artex.allStarsParty.role.antagoniste.akaza;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.item.Cooldown;
import be.artex.allStarsParty.api.item.CustomItem;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.message.Message;
import be.artex.allStarsParty.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class AirType extends CustomItem {
    private final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.BLUE + "" + ChatColor.BOLD + "Air Type").build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL)
            return;

        Player player = event.getPlayer();

        Cooldown cooldown = Cooldown.getCooldown("akaza_airtype", 2);

        if (cooldown.isPlayerInCooldown(event.getPlayer()))
            return;

        int i = Akaza.playersWithAkaza.get(player.getUniqueId());

        if (i == 0) {
            player.sendMessage(Message.error("Il ne vous reste plus aucune balle."));
            return;
        }

        Player target = PlayerUtil.getPlayerTargetEntity(player, 50);

        if (target == null) {
            player.sendMessage(Message.error("Vous ne visez aucun joueur."));
            return;
        }

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Vector direction = target.getLocation().toVector()
                    .subtract(player.getLocation().toVector())
                    .normalize();

            Vector velocity = direction.multiply(1.0);
            velocity.setY(0.4);

            target.setVelocity(velocity);
        } else {
            PlayerUtil.inflictTrueDamage(player, 1);
        }

        i--;

        if (i == 0) {
            Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
                if (!Akaza.playersWithAkaza.containsKey(player.getUniqueId()))
                    return;

                Akaza.playersWithAkaza.put(player.getUniqueId(), 3);
                player.sendMessage(Message.info("Vous récupérez " + ChatColor.BLUE + "3 balles" + ChatColor.WHITE + "."));
            }, 1200);
        }

        Akaza.playersWithAkaza.put(player.getUniqueId(), i);
        target.sendMessage(Message.info("Une " + ChatColor.BLUE + "balle" + ChatColor.WHITE + " du" + ChatColor.BLUE + ChatColor.BOLD + " Air Type" + ChatColor.WHITE + " d'" + ChatColor.RED + "Akaza" + ChatColor.WHITE + "vous touche."));
        cooldown.putPlayerInCooldownWithoutEndMessage(player);
    }
}
