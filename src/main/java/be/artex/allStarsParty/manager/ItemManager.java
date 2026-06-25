package be.artex.allStarsParty.manager;

import be.artex.allStarsParty.api.items.ASPItem;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    private final List<ASPItem> registeredItems = new ArrayList<>();

    public ASPItem getItemFromStack(ItemStack stack) {
        for (ASPItem item : registeredItems) {
            if (item.getStack().equals(stack))
                return item;
        }

        return null;
    }

    public ASPItem registerItem(ASPItem item) {
        registeredItems.add(item);
        return item;
    }
}
