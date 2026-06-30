package be.artex.allStarsParty.role.rewrited.protagoniste.shoto;

import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.registry.ItemRegistry;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;

public class Shoto extends Role {

    @Override
    public String getName() {
        return "Shoto";
    }

    @Override
    public Side getSide() {
        return Side.PROTAGONISTE;
    }

    @Override
    public List<ASPItem> getItems() {
        List<ASPItem> items = new ArrayList<>();

        items.add(ItemRegistry.FIRE_SIDE);
        items.add(ItemRegistry.ICE_SIDE);

        return items;
    }

    @Override
    public int getResistance() {
        return 110;
    }

    @Override
    public void whenAssigned(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
    }
}
