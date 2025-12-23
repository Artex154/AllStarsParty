package be.artex.allStarsParty.gameLogic.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageEventListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();

        if (event.getCause().equals(EntityDamageEvent.DamageCause.LIGHTNING)) {
            event.setCancelled(true);
            return;
        }
    }
}
