package be.artex.allStarsParty.role.rewrited.DS.muzan.effectSelector;

import be.artex.allStarsParty.api.stats.Resistance;
import be.artex.allStarsParty.api.stats.Speed;
import be.artex.allStarsParty.api.stats.Strength;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public enum EffectSelectorType {
    STRENGTH(player -> Strength.addStrengthToPlayer(player, 15)),
    RESISTANCE(player -> Resistance.addResistanceToPlayer(player, 10)),
    SPEED(player -> Speed.addSpeedToPlayer(player, 30));

    private final Consumer<Player> whenApplied;

    EffectSelectorType(Consumer<Player> whenApplied) {
        this.whenApplied = whenApplied;
    }

    public void apply(Player player) {
        whenApplied.accept(player);
    }
}
