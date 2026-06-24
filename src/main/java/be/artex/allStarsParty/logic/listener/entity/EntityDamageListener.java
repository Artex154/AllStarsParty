package be.artex.allStarsParty.logic.listener.entity;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.logic.Role;
import be.artex.allStarsParty.logic.manager.RoleManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {
    private static final RoleManager roleManager = AllStarsParty.roleManager;

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();
        Role role = roleManager.getPlayerRole(player);

        EntityDamageEvent.DamageCause cause = event.getCause();

        switch (cause) {
            case LIGHTNING:
                event.setCancelled(true);
            case FALL:
                if (role == null)
                    return;

                if (role.hasNoFall())
                    event.setCancelled(true);
        }
    }
}
