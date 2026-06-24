package be.artex.allStarsParty.api.items;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.gui.GUI;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

public abstract class ASPGuiOpenerItem extends ASPItem {
    public abstract GUI createGUI();

    @Override
    public void onInteraction(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        GUI gui = createGUI();

        AllStarsParty.GUIManager.openGUI(player, gui);
    }
}
