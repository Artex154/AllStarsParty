package be.artex.allStarsParty.items;

import be.artex.allStarsParty.gameLogic.ASPItem;
import be.artex.allStarsParty.itemBuilder.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class TestItem extends ASPItem {
    private static final ItemStack STACK = new ItemBuilder(Material.BOOK).name(ChatColor.BOLD + "Test").build();

    @Override
    public ItemStack getStack() {
        return STACK;
    }

    @Override
    public void onInteraction(PlayerInteractEvent event) {
        event.getPlayer().sendMessage("TEST");
    }
}
