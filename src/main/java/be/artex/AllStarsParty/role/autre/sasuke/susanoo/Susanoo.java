package be.artex.AllStarsParty.role.autre.sasuke.susanoo;

import be.artex.AllStarsParty.api.item.CustomItem;
import be.artex.AllStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.AllStarsParty.api.message.Message;
import be.artex.AllStarsParty.api.role.Role;
import be.artex.AllStarsParty.role.autre.sasuke.SSasuke;
import be.artex.AllStarsParty.util.PlayerUtil;
import be.artex.AllStarsParty.util.StatValues;
import be.artex.AllStarsParty.util.Stats;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Susanoo extends CustomItem {
    private final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Susanoo").build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Role role = Role.getPlayerRole(player);

        if (!(role instanceof SSasuke))
            return;

        SSasuke r = (SSasuke) role;

        if (r.isInSusanoo) {
            player.sendMessage(Message.info("Vous êtes déjà équipé du " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Susanoo" + ChatColor.WHITE + "."));
            return;
        }

        enableSusanoo(player, r);
    }

    public static void enableSusanoo(Player player, SSasuke role) {
        if (!role.consumeChakra(5000)) {
            player.sendMessage(Message.info("Il ne vous reste pas assez de " + ChatColor.AQUA + "chakra" + ChatColor.WHITE + "."));
            return;
        }

        role.isInSusanoo = true;
        role.amountOfArrows = 2;

        PlayerUtil.inflictTrueDamage(player, 3);

        Stats stats = Stats.get(player.getUniqueId());
        stats.addBonus(StatValues.SPEED, -60);

        player.sendMessage(Message.info("Vous activez votre " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Susanoo" + ChatColor.WHITE + "."));
    }

    public static void disableSusanoo(Player player, SSasuke role) {
        role.isInSusanoo = false;
        role.amountOfArrows = 0;
        role.amountOfHitTaken = 0;

        Stats stats = Stats.get(player.getUniqueId());
        stats.addBonus(StatValues.SPEED, 60);
    }
}
