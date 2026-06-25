package be.artex.allStarsParty.listener.inventory;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.gui.GUI;
import be.artex.allStarsParty.api.gui.ItemButton;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (event.getCurrentItem() == null || event.getCurrentItem().getType() == Material.AIR)
            return;

        if (event.getCurrentItem().equals(GUI.BORDER_STACK))
            event.setCancelled(true);

        GUI gui = AllStarsParty.GUIManager.getOpenGUI(player);

        if (gui == null || !gui.getInventory().equals(event.getClickedInventory()))
            return;

        int slot = event.getSlot();

        ItemButton button = gui.getButtons().get(slot);

        if (button == null)
            return;

        event.setCancelled(true);
        button.clicked(event);
    }
}
