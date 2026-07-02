package be.artex.allStarsParty.role.rewrited.antagoniste.tomura.mains;

import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.message.Message;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Unusable extends ASPItem {
    private static final ItemStack STACK = new ItemBuilder(Material.BARRIER).name(ChatColor.RED + "" + ChatColor.BOLD + "Inutilisable").build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteraction(PlayerInteractEvent event) {
        event.getPlayer().sendMessage(Message.error(ChatColor.RED + "Tomura " + ChatColor.WHITE + "a désactivé cet item."));
    }
}
