package be.artex.rewrite.api.role;

import be.artex.allStarsParty.api.message.Message;
import be.artex.rewrite.AllStarsParty;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class RevivableRole extends Role {
    public static final Map<UUID, Boolean> playersRevived = new HashMap<>();

    public abstract long getUninteractableTicks();

    public void onRevive(Player player) {
    }

    public void afterRevive(Player player) {
    }

    public void onPlayerHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
    }

    @Override
    public final void onHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
        if (playersRevived.get(damager.getUniqueId()) != null && playersRevived.get(damager.getUniqueId())) {
            event.setCancelled(true);
            return;
        }

        onPlayerHit(player, damager, damage, event);
    }

    @Override
    public final void whenHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
        if (playersRevived.get(player.getUniqueId()) != null && playersRevived.get(player.getUniqueId())) {
            event.setCancelled(true);
            return;
        }

        if (player.getHealth() - event.getFinalDamage() >= 0 || playersRevived.containsKey(player.getUniqueId()))
            return;

        event.setCancelled(true);
        playersRevived.put(player.getUniqueId(), true);

        spawnParticleCircle(player.getLocation(), Effect.LARGE_SMOKE, 2);
        player.getLocation().getWorld().playSound(player.getLocation(), Sound.IRONGOLEM_HIT, 1, 1);
        onRevive(player);
        player.sendMessage(Message.info("Vous ne subissez pas ce coup fatal. Cependant, vous ne pouvez pas intéragir avec votre environnement pendant " + getUninteractableTicks() / 20 + " secondes."));

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            if (playersRevived.get(player.getUniqueId()) == null)
                return;

            playersRevived.put(player.getUniqueId(), false);
            player.sendMessage(Message.info("Vous pouvez désormais intéragir avec votre environnement."));
            spawnParticleCircle(player.getLocation(), Effect.CLOUD, 2);
            player.getLocation().getWorld().playSound(player.getLocation(), Sound.ITEM_BREAK, 1, 1);
            afterRevive(player);
        }, getUninteractableTicks());
    }

    public void spawnParticleCircle(Location center, Effect particle, double radius) {
        World world = center.getWorld();

        for (double angle = 0; angle < 360; angle += 10) {
            double radians = Math.toRadians(angle);

            double x = radius * Math.cos(radians);
            double z = radius * Math.sin(radians);

            Location particleLoc = center.clone().add(x, 1, z);

            world.spigot().playEffect(
                    particleLoc,
                    particle,
                    0, 0,
                    0, 0, 0,
                    0,
                    1,
                    64
            );
        }
    }
}
