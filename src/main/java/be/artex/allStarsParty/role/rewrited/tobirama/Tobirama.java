package be.artex.allStarsParty.role.rewrited.tobirama;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.registry.ItemRegistry;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Collections;
import java.util.List;

public class Tobirama extends Role {
    public final ItemStack DEPTH_STRIDER_BOOTS = new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).addEnchant(Enchantment.DEPTH_STRIDER, 3).build();

    @Override
    public String getName() {
        return "Tobirama";
    }

    @Override
    public Side getSide() {
        return Side.NS;
    }

    @Override
    public List<ASPItem> getItems() {
        return Collections.singletonList(ItemRegistry.HNJ);
    }

    @Override
    public boolean hasNoFall() {
        return true;
    }

    @Override
    public int getSpeed() {
        return 130;
    }

    @Override
    public void whenAssigned(Player player) {
        PlayerInventory inventory = player.getInventory();

        inventory.setBoots(DEPTH_STRIDER_BOOTS);
        inventory.setItem(0, ItemRegistry.RNK.getStack());
    }
}
