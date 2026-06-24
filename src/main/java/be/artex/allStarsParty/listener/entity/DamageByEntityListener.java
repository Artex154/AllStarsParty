package be.artex.allStarsParty.listener.entity;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.registry.ItemRegistry;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.stats.Resistance;
import be.artex.allStarsParty.api.stats.Strength;
import be.artex.allStarsParty.registry.RoleRegistry;
import org.bukkit.GameMode;
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

        if (damager.getGameMode().equals(GameMode.ADVENTURE) || player.getGameMode().equals(GameMode.ADVENTURE)) {
            event.setCancelled(true);
            return;
        }

        double damage = event.getDamage();

        damage = damage / 100 * (100 + (Strength.getPlayerStrength(damager) - Resistance.getPlayerResistance(player)));

        event.setDamage(damage);

        ItemStack stack = damager.getItemInHand();

        Role damagerRole = RoleRegistry.roleManager.getPlayerRole(damager);

        if (damagerRole != null)
            damagerRole.onHit(player, damager, damage);

        ASPItem item = ItemRegistry.itemManager.getItemFromStack(stack);

        if (item != null)
            item.onHit(damager, player, damage);
    }
}
