package be.artex.allStarsParty.manager;

import be.artex.allStarsParty.api.gui.GUI;
import be.artex.allStarsParty.api.gui.ItemButton;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class GUIManager {
    private final Map<Player, GUI> openGuis = new HashMap<>();

    public void openGUI(Player player, GUI gui) {
        openGuis.put(player, gui);
        player.openInventory(gui.getInventory());
    }

    public void closeGUI(Player player) {
        openGuis.remove(player);
    }

    public GUI getOpenGUI(Player player) {
        return openGuis.get(player);
    }

    public ItemButton getButton(Player player, int slot) {
        GUI gui = openGuis.get(player);
        if (gui == null) return null;

        return gui.getButtons().get(slot);
    }
}