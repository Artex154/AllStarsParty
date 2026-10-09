package be.artex.AllStarsParty.role.autre.minato;

import be.artex.AllStarsParty.api.item.CustomItem;
import be.artex.AllStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.AllStarsParty.api.message.Message;
import be.artex.AllStarsParty.api.role.Role;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RaijinKunai extends CustomItem {
    private final ItemStack STACK = new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_INFINITE, 1).build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onShoot(EntityShootBowEvent event) {
        Player player = (Player) event.getEntity();
        Role role = Role.getPlayerRole(player);

        if (!(role instanceof Minato))
            return;

        Minato r = (Minato) role;
        r.arrow = (Arrow) event.getProjectile();
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK)
            return;

        Player player = event.getPlayer();
        Role role = Role.getPlayerRole(player);

        if (!(role instanceof Minato)) {
            player.setItemInHand(new ItemStack(Material.AIR));
            player.updateInventory();
            return;
        }

        Minato r = (Minato) role;

        if (r.arrow == null || r.arrow.isDead() || !r.arrow.isValid()) {
            player.sendMessage(Message.info("Flèche invalide."));
            return;
        }

        if (r.isInErmite) {
            if (r.ermiteChakra < 150) {
                player.sendMessage(Message.info("Il ne vous reste pas assez de" + ChatColor.DARK_GREEN + " chakra d'ermite" + ChatColor.WHITE + "."));
                return;
            }

            r.ermiteChakra -= 150;
        } else {
            if (r.chakra < 1500) {
                player.sendMessage(Message.info("Il ne vous reste pas assez de" + ChatColor.AQUA + " chakra" + ChatColor.WHITE + "."));
                return;
            }

            r.chakra -= 1500;
        }

        Location aLoc = r.arrow.getLocation();
        r.arrow.remove();
        r.arrow = null;

        player.teleport(aLoc);

        aLoc.getWorld().playSound(aLoc, Sound.ENDERMAN_TELEPORT, 1f, 1f);
    }
}
