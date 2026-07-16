package be.artex.rewrite.listener;

import be.artex.rewrite.util.StatValues;
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

        double playerResistanceBonus = Stats.get(player.getUniqueId()).getBonus(StatValues.RESISTANCE);
        double damagerStrengthBonus = Stats.get(damager.getUniqueId()).getBonus(StatValues.STRENGTH);

        double multiplier = 1 + (damagerStrengthBonus - playerResistanceBonus) / 100;

        event.setDamage(event.getDamage() * multiplier);
    }
}
