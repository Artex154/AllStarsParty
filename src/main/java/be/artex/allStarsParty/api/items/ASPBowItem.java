package be.artex.allStarsParty.api.items;

import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public abstract class ASPBowItem extends ASPItem {
    public void onProjectileHit(ProjectileHitEvent event) {
    }

    public void onShoot(EntityShootBowEvent event) {
    }
}
