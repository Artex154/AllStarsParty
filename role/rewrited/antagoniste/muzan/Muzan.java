package be.artex.role.rewrited.antagoniste.muzan;

import be.artex.AllStarsParty.api.Role;
import be.artex.AllStarsParty.api.Side;
import be.artex.AllStarsParty.api.items.ASPItem;
import be.artex.AllStarsParty.api.stats.Resistance;
import be.artex.AllStarsParty.api.stats.Speed;
import be.artex.AllStarsParty.api.stats.Strength;
import be.artex.AllStarsParty.registry.ItemRegistry;
import be.artex.role.rewrited.antagoniste.muzan.effectSelector.EffectSelector;
import be.artex.role.rewrited.antagoniste.muzan.effectSelector.EffectSelectorHolder;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;

public class Muzan extends Role {
    @Override
    public String getName() {
        return "Muzan";
    }

    @Override
    public Side getSide() {
        return Side.ANTAGONISTE;
    }

    @Override
    public int getMaxHealth() {
        return 26;
    }

    @Override
    public List<ASPItem> getItems() {
        ArrayList<ASPItem> list = new ArrayList<>();

        list.add(ItemRegistry.SANG);
        list.add(ItemRegistry.EFFECT_SELECTOR);

        return list;
    }

    @Override
    public void onKill(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();

        EffectSelectorHolder holder = EffectSelector.playerEffects.get(killer);

        if (!holder.speedEffect && !holder.resistanceEffect && !holder.strengthEffect)
            return;

        killer.getInventory().addItem(ItemRegistry.EFFECT_SELECTOR.getStack());

        Strength.setPlayerStrength(killer, 100);
        Resistance.setPlayerResistance(killer, 100);
        Speed.resetPlayerSpeed(killer);
    }
}
