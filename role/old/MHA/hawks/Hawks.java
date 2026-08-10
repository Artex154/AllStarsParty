package be.artex.role.old.MHA.hawks;

import be.artex.AllStarsParty.api.Role;
import be.artex.AllStarsParty.api.Side;
import be.artex.AllStarsParty.api.items.ASPItem;
import be.artex.AllStarsParty.registry.ItemRegistry;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Hawks extends Role {

    @Override
    public String getName() {
        return "Hawks";
    }

    @Override
    public Side getSide() {
        return Side.MHA;
    }

    @Override
    public int getSpeed() {
        return 110;
    }

    @Override
    public List<ASPItem> getItems() {
        return Collections.singletonList(ItemRegistry.AILES_ACIER);
    }

    @Override
    public void onHit(Player player, Player damager, double damage) {
        Random random = new Random();
        int randomInt = random.nextInt(100);

        if (randomInt > 20)
            return;

        damager.getInventory().addItem(new ItemStack(Material.ARROW));
    }
}