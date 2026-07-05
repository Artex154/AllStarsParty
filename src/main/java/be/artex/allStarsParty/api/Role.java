package be.artex.allStarsParty.api;

import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.registry.RoleRegistry;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Collections;
import java.util.List;

public abstract class Role {
    public abstract String getName();
    public abstract Side getSide();

    public void onKill(PlayerDeathEvent event) {
    }

    /**
     * @deprecated in favor of {@link #onHit(Player, Player, double, EntityDamageByEntityEvent)}
     */
    @Deprecated
    public void onHit(Player player, Player damager, double damage) {
    }

    public void onHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
    }

    public void whenKilled(PlayerDeathEvent event) {
    }

    public void whenHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {

    }

    public List<ASPItem> getItems() {
        return Collections.emptyList();
    }

    public int getMaxHealth() {
        return 20;
    }

    public int getStrength() {
        return 100;
    }

    public int getResistance() {
        return 100;
    }

    public int getSpeed() {
        return 100;
    }

    public boolean hasNoFall() {
        return false;
    }

    public void whenAssigned(Player player) {
    }

    public final Role register() {
        RoleRegistry.roleManager.registerRole(this);
        return this;
    }
}
