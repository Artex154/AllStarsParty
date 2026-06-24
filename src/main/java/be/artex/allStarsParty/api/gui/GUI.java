package be.artex.allStarsParty.api.gui;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class GUI {
    private final Inventory inventory;
    private final Map<Integer, ItemButton> buttons;

    public static final ItemStack BORDER_STACK = new ItemBuilder(new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3)).name(" ").build();

    public static final Map<Player, GUI> openGuis = new HashMap<>();

    public GUI(String name, int rowsNumber) {
        this.inventory = Bukkit.createInventory(null, rowsNumber * 9, name);
        this.buttons = new HashMap<>();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Map<Integer, ItemButton> getButtons() {
        return Collections.unmodifiableMap(buttons);
    }

    public GUI addButton(ItemButton button, int slot) {
        buttons.put(slot, button);
        this.inventory.setItem(slot, button.getStack().clone());
        return this;
    }

    public void borderSlots(int[] slots) {
        for (int slot : slots)
            inventory.setItem(slot, BORDER_STACK);
    }

}
