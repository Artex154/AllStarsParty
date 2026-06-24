package be.artex.allStarsParty.listener.projectile;

import be.artex.allStarsParty.api.items.ASPBowItem;
import be.artex.allStarsParty.api.items.ASPItem;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

import static be.artex.allStarsParty.registry.ItemRegistry.itemManager;

public class ProjectileListeners implements Listener {
    private final Map<Arrow, ItemStack> arrowBows = new HashMap<>();

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent event) {
        if (!(event.getProjectile() instanceof Arrow))
            return;

        ItemStack bow = event.getBow();

        arrowBows.put((Arrow) event.getProjectile(), bow.clone());

        ASPItem item = itemManager.getItemFromStack(bow);

        if (item instanceof ASPBowItem)
            ((ASPBowItem) item).onShoot(event);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (!(event.getEntity() instanceof Arrow))
            return;

        Arrow arrow = (Arrow) event.getEntity();

        ItemStack bow = arrowBows.remove(arrow);

        ASPItem item = itemManager.getItemFromStack(bow);

        if (item instanceof ASPBowItem)
            ((ASPBowItem) item).onProjectileHit(event);
    }
}
