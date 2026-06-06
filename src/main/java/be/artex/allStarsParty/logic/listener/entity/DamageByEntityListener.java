package be.artex.allStarsParty.logic.listener.entity;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.logic.items.ASPItem;
import be.artex.allStarsParty.logic.Role;
import be.artex.allStarsParty.logic.stats.Resistance;
import be.artex.allStarsParty.logic.stats.Strength;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class DamageByEntityListener implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() == null || event.getEntity() == null)
            return;

        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player))
            return;

        Player damager = (Player) event.getDamager();
        Player player = (Player) event.getEntity();

        double damage = event.getDamage();

        damage = damage / 100 * (100 + (Strength.getPlayerStrength(damager) - Resistance.getPlayerResistance(player)));

        event.setDamage(damage);

        ItemStack stack = damager.getItemInHand();

        Role damagerRole = AllStarsParty.roleManager.getPlayerRole(damager);

        damagerRole.onHit(player, damager, damage);

        ASPItem item = AllStarsParty.itemManager.getItemFromStack(stack);

        if (item != null)
            item.onHit(damager, player, damage);
    }
}
