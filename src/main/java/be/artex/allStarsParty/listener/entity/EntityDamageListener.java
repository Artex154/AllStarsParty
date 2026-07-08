package be.artex.allStarsParty.listener.entity;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.manager.RoleManager;
import be.artex.allStarsParty.registry.RoleRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
    private static final RoleManager roleManager = RoleRegistry.roleManager;

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();
        Role role = roleManager.getPlayerRole(player);

        EntityDamageEvent.DamageCause cause = event.getCause();

        switch (cause) {
            case LIGHTNING:
                event.setDamage(0);
            case FALL:
                if (role == null)
                    return;

                if (role.hasNoFall())
                    event.setCancelled(true);
        }
    }
}
