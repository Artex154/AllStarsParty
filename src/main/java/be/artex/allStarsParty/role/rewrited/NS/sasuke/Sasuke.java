package be.artex.allStarsParty.role.rewrited.NS.sasuke;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.registry.ItemRegistry;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Sasuke extends Role {
    @Override
    public String getName() {
        return "Sasuke";
    }

    @Override
    public Side getSide() {
        return Side.NS;
    }

    @Override
    public int getStrength() {
        return 110;
    }

    @Override
    public void whenAssigned(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
    }

    @Override
    public List<ASPItem> getItems() {
        ArrayList<ASPItem> list = new ArrayList<>();

        list.add(ItemRegistry.FDMO);
        list.add(ItemRegistry.SUSANO);

        return list;
    }

    @Override
    public void onHit(Player player, Player damager, double damage) {
        if (Susano.playersWithSusano.contains(damager))
            player.setFireTicks(100);
    }
}
