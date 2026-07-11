package be.artex.rewrite.listener;

import be.artex.rewrite.util.Stats;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) || !(event.getDamager() instanceof Player))
            return;

        Player player = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        double playerResistanceBonus = Stats.get(player.getUniqueId()).getResistanceBonus();
        double damagerStrengthBonus = Stats.get(damager.getUniqueId()).getStrengthBonus();

        double multiplier = 1 + (damagerStrengthBonus - playerResistanceBonus) / 100;

        event.setDamage(event.getFinalDamage() * multiplier);
    }
}
