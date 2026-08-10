package be.artex.role.rewrited.antagoniste.muzan.effectSelector;

import be.artex.AllStarsParty.api.stats.Resistance;
import be.artex.AllStarsParty.api.stats.Speed;
import be.artex.AllStarsParty.api.stats.Strength;
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
