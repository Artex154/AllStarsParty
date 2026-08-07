package be.artex.allStarsParty.listener;

import be.artex.allStarsParty.api.role.Role;
import be.artex.allStarsParty.util.StatValues;
import be.artex.allStarsParty.util.Stats;
import org.bukkit.GameMode;
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

        if (player.getGameMode() == GameMode.ADVENTURE || damager.getGameMode() == GameMode.ADVENTURE) {
            event.setCancelled(true);
            return;
        }

        Role playerRole = Role.manager.getPlayerRole(player.getUniqueId());
        Role damagerRole = Role.manager.getPlayerRole(damager.getUniqueId());

        double playerResistanceBonus = Stats.get(player.getUniqueId()).getBonus(StatValues.RESISTANCE);
        double damagerStrengthBonus = Stats.get(damager.getUniqueId()).getBonus(StatValues.STRENGTH);

        if (damagerRole != null)
            damagerStrengthBonus += damagerRole.bonusStrength(player, damager);

        double multiplier = 1 + (damagerStrengthBonus - playerResistanceBonus) / 100;
        double finalDamage = event.getDamage() * multiplier;

        event.setDamage(finalDamage);

        if (playerRole != null)
            playerRole.whenHit(player, damager, finalDamage, event);

        if (damagerRole != null)
            damagerRole.onHit(player, damager, finalDamage, event);
    }
}
