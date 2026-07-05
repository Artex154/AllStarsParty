package be.artex.allStarsParty.listener.entity;

import be.artex.allStarsParty.manager.RoleManager;
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
    private static final RoleManager ROLE_MANAGER = RoleRegistry.roleManager;

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

        int resistance = Resistance.getPlayerResistance(player);
        int strength = Strength.getPlayerStrength(damager);
        double bDamage = event.getDamage();
        double fDamage = (bDamage / 100) * (100 + resistance - strength);

        event.setDamage(fDamage);

        ItemStack stack = damager.getItemInHand();

        Role damagerRole = ROLE_MANAGER.getPlayerRole(damager);

        if (damagerRole != null) {
            damagerRole.onHit(player, damager, fDamage);
            damagerRole.onHit(player, damager, fDamage, event);
        }

        Role playerRole = ROLE_MANAGER.getPlayerRole(player);

        if (playerRole != null)
            playerRole.whenHit(player, damager, fDamage, event);

        ASPItem item = ItemRegistry.itemManager.getItemFromStack(stack);

        if (item != null)
            item.onHit(damager, player, fDamage);
    }
}
