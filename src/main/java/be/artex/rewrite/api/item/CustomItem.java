package be.artex.rewrite.api.item;

import be.artex.rewrite.AllStarsParty;
import be.artex.rewrite.api.role.Role;
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

    public abstract ItemStack getStack();

    public void onInteract(PlayerInteractEvent event) {
    }

    public final void register() {
        manager.REGISTERED_ITEMS.add(this);
    }

    public static class Manager {
        private static final Map<Plugin, Manager> managers = new HashMap<>();

        private final List<CustomItem> REGISTERED_ITEMS = new ArrayList<>();

        private Manager() {
        }

        public static @NotNull Manager get(@NotNull Plugin plugin) {
            return managers.computeIfAbsent(plugin, ignored -> new Manager());
        }

        public @Nullable CustomItem getItemFromStack(@NotNull ItemStack stack) {
            for (CustomItem item : REGISTERED_ITEMS) {
                if (item.getStack().equals(stack))
                    return item;
            }

            return null;
        }
    }
}
