package be.artex.allStarsParty.role.DS.muzan;

import be.artex.allStarsParty.TextUtil;
import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.stats.Resistance;
import be.artex.allStarsParty.api.stats.Speed;
import be.artex.allStarsParty.api.stats.Strength;
import be.artex.allStarsParty.registry.ItemRegistry;
import be.artex.allStarsParty.role.DS.muzan.effectSelector.EffectSelector;
import be.artex.allStarsParty.role.DS.muzan.effectSelector.EffectSelectorHolder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.ArrayList;
import java.util.List;

public class Muzan extends Role {
    private static final String DESCRIPTION = TextUtil.BORDER +
            "\n" + ChatColor.WHITE + " Vous êtes " + ChatColor.RED + "Muzan" + ChatColor.WHITE + "." +
            "\n Vous possédez 105% de " + ChatColor.RED + "force" + ChatColor.WHITE + "." +
            "\n Quand vous " + ChatColor.RED + "tappez " + ChatColor.WHITE + "un joueur, vous régénérez " + ChatColor.LIGHT_PURPLE + "5% des dégats infligés " + ChatColor.WHITE + "." +
            "\n" + TextUtil.BORDER;

    @Override
    public String getName() {
        return "Muzan";
    }

    @Override
    public Side getSide() {
        return Side.DS;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
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
