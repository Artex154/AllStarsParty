package be.artex.allStarsParty.api.gui;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ItemButton {
    private final ItemStack stack;
    private final String id;
    private Consumer<InventoryClickEvent> onClick = null;

    public ItemButton(ItemStack stack, String id) {
        this.stack = stack;
        this.id = id;
    }

    public ItemStack getStack() {
        return stack.clone();
    }

    public String getId() {
        return id;
    }

    public ItemButton onClick(Consumer<InventoryClickEvent> onClick) {
        this.onClick = onClick;
        return this;
    }

    public void clicked(InventoryClickEvent event) {
        onClick.accept(event);
    }
}
