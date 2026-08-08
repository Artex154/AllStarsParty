package be.artex.allStarsParty.api.item;

import be.artex.allStarsParty.AllStarsParty;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CustomItem {
    public static final Manager manager = CustomItem.Manager.get(AllStarsParty.instance);

    /**
     * @return the ItemStack of the CustomItem.
     */
    public abstract ItemStack getStack();

    /**
     * The code executed when a player interacts with the CustomItem.
     */
    public void onInteract(PlayerInteractEvent event) {
    }

    /**
     * Registers the CustomItem.
     */
    public final void register() {
        manager.REGISTERED_ITEMS.add(this);
    }

    public static class Manager {
        private static final Map<Plugin, Manager> managers = new HashMap<>();

        private final List<CustomItem> REGISTERED_ITEMS = new ArrayList<>();

        private Manager() {
        }

        /**
         * @param plugin this takes a Plugin to make sure someone can't have 2 managers.
         * @return the plugin's ItemManager
         */
        public static @NotNull Manager get(@NotNull Plugin plugin) {
            return managers.computeIfAbsent(plugin, ignored -> new Manager());
        }

        /**
         * Finds the CustomItem only from an ItemStack.
         * @param stack the ItemStack to find the CustomItem of.
         * @return the found CustomItem. Null otherwise.
         */
        public @Nullable CustomItem getItemFromStack(@NotNull ItemStack stack) {
            for (CustomItem item : REGISTERED_ITEMS) {
                if (item.getStack().equals(stack))
                    return item;
            }

            return null;
        }
    }
}
