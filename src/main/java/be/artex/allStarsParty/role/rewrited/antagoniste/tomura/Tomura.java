package be.artex.allStarsParty.role.rewrited.antagoniste.tomura;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.registry.ItemRegistry;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

public class Tomura extends Role {
    @Override
    public String getName() {
        return "Tomura";
    }

    @Override
    public Side getSide() {
        return Side.ANTAGONISTE;
    }

    @Override
    public int getStrength() {
        return 110;
    }

    @Override
    public List<ASPItem> getItems() {
        return Collections.singletonList(ItemRegistry.MAINS);
    }

    @Override
    public void whenAssigned(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, Integer.MAX_VALUE, 0));
    }
}
