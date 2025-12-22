package be.artex.allStarsParty.gameLogic.manager;

import be.artex.allStarsParty.gameLogic.ASPItem;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ItemManager {
    private final List<ASPItem> registeredItems = new ArrayList<>();

    // getter

    public ASPItem getItemFromStack(ItemStack stack) {
        for (ASPItem item : registeredItems) {
            if (item.getStack().equals(stack))
                return item;
        }

        return null;
    }

    // registration

    public void registerItem(ASPItem item) {
        registeredItems.add(item);
    }
}
