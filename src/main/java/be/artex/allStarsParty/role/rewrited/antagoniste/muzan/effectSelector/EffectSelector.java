package be.artex.allStarsParty.role.rewrited.antagoniste.muzan.effectSelector;

import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.gui.GUI;
import be.artex.allStarsParty.api.gui.ItemButton;
import be.artex.allStarsParty.api.items.ASPGuiOpenerItem;
import be.artex.allStarsParty.api.message.Message;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class EffectSelector extends ASPGuiOpenerItem {

    public static final Map<Player, EffectSelectorHolder> playerEffects = new HashMap<>();

    private final ItemStack STACK =
            new ItemBuilder(Material.NETHER_STAR)
                    .name(ChatColor.GOLD + "" + ChatColor.BOLD + "Sélecteur d'effets")
                    .build();

    private final int[] borderSlots = {0, 1, 7, 8, 9, 17, 18, 19, 25, 26};

    private final ItemStack ALREADY_USED =
            new ItemBuilder(Material.BARRIER)
                    .name(ChatColor.RED + "Effet déjà pris")
                    .lore(ChatColor.WHITE + "Vous avez déjà utilisé cet effet durant cette partie.")
                    .build();

    private final ItemStack STRENGTH =
            new ItemBuilder(Material.REDSTONE)
                    .name(ChatColor.RED + "Force")
                    .build();

    private final ItemStack RESISTANCE =
            new ItemBuilder(Material.IRON_CHESTPLATE)
                    .name(ChatColor.GRAY + "Resistance")
                    .build();

    private final ItemStack SPEED =
            new ItemBuilder(Material.QUARTZ)
                    .name(ChatColor.YELLOW + "Vitesse")
                    .build();

    private void applyEffect(Player player, EffectSelectorType type) {
        player.getInventory().remove(Material.NETHER_STAR);
        player.updateInventory();
        player.closeInventory();
        GUI.openGuis.remove(player);

        type.apply(player);
    }

    @Override
    public GUI createGUI() {
        GUI gui = new GUI("Effets disponibles", 3);

        gui.borderSlots(borderSlots);

        gui.addButton(new ItemButton(STRENGTH, "strength").onClick(event -> {
            Player player = (Player) event.getWhoClicked();
            EffectSelectorHolder holder = playerEffects.computeIfAbsent(player, p -> new EffectSelectorHolder());

            if (!holder.strengthEffect) {
                gui.addButton(new ItemButton(ALREADY_USED, "used").onClick(e -> e.setCancelled(true)), 11);
                return;
            }

            holder.strengthEffect = false;
            applyEffect(player, EffectSelectorType.STRENGTH);
            player.sendMessage(Message.info("Vous avez choisi " + ChatColor.RED + "force" + ChatColor.WHITE + "."));
        }), 11);

        gui.addButton(new ItemButton(RESISTANCE, "resistance").onClick(event -> {
            Player player = (Player) event.getWhoClicked();
            EffectSelectorHolder holder = playerEffects.computeIfAbsent(player, p -> new EffectSelectorHolder());

            if (!holder.resistanceEffect) {
                gui.addButton(new ItemButton(ALREADY_USED, "used").onClick(e -> e.setCancelled(true)), 13);
                return;
            }

            holder.resistanceEffect = false;
            playerEffects.put(player, holder);
            player.sendMessage(Message.info("Vous avez choisi " + ChatColor.GRAY + "resistance" + ChatColor.WHITE + "."));

            applyEffect(player, EffectSelectorType.RESISTANCE);
        }), 13);

        gui.addButton(new ItemButton(SPEED, "speed").onClick(event -> {
            Player player = (Player) event.getWhoClicked();
            EffectSelectorHolder holder = playerEffects.computeIfAbsent(player, p -> new EffectSelectorHolder());

            if (!holder.speedEffect) {
                gui.addButton(new ItemButton(ALREADY_USED, "used").onClick(e -> e.setCancelled(true)), 15);
                return;
            }

            holder.speedEffect = false;
            playerEffects.put(player, holder);
            player.sendMessage(Message.info("Vous avez choisi " + ChatColor.YELLOW + "vitesse" + ChatColor.WHITE + "."));

            applyEffect(player, EffectSelectorType.SPEED);
        }), 15);

        return gui;
    }

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }
}
