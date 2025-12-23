package be.artex.allStarsParty.gameLogic.listener;

import be.artex.allStarsParty.gameLogic.stats.Resistance;
import be.artex.allStarsParty.gameLogic.stats.Strength;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityEventListener implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player || event.getDamager() instanceof Player))
            return;

        Player player = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        double damage = event.getDamage();

        damage = damage / 100 * (100 + (Strength.getPlayerStrength(damager) - Resistance.getPlayerResistance(player)));

        event.setDamage(damage);
    }
}
