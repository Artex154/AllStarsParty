package be.artex.AllStarsParty.role.autre.minato.rasengan;

import be.artex.AllStarsParty.api.item.CustomItem;
import be.artex.AllStarsParty.api.message.Message;
import be.artex.AllStarsParty.api.role.Role;
import be.artex.AllStarsParty.role.autre.minato.Ermite;
import be.artex.AllStarsParty.role.autre.minato.Minato;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class Rasengan extends CustomItem {
    private final ItemStack STACK = new ItemStack(Material.DIAMOND_BLOCK);

    @Override
    public ItemStack getStack() {
        return STACK;
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Role role = Role.getPlayerRole(player);

        if (!(role instanceof Minato)) {
            player.setItemInHand(new ItemStack(Material.AIR));
            player.updateInventory();
            return;
        }

        Minato r = (Minato) role;

        if (r.rasenganState != RasenganState.NONE) {
            player.sendMessage(Message.info("Le " + ChatColor.BLUE + "rasengan" + ChatColor.WHITE + " est déjà activé."));
            return;
        }

        if (r.rasenganCooldownTicks != 0) {
            player.sendMessage(Message.cooldownTimeLeft(r.rasenganCooldownTicks / 20));
            return;
        }

        if (r.isInErmite) {
           if (r.ermiteChakra < 500) {
               player.sendMessage(Message.info("Il ne vous reste pas assez de" + ChatColor.DARK_GREEN + " chakra d'ermite" + ChatColor.WHITE + "."));
               return;
           }

           player.sendMessage(Message.info("Vous avez activé le " + ChatColor.BLUE + ChatColor.BOLD + "rasengan géant" + ChatColor.WHITE + "."));

           r.rasenganState = RasenganState.ERMITE;
           r.ermiteChakra -= 500;
        } else {
            if (r.chakra < 5000) {
                player.sendMessage(Message.info("Il ne vous reste pas assez de" + ChatColor.AQUA + " chakra" + ChatColor.WHITE + "."));
                return;
            }

            player.sendMessage(Message.info("Vous avez activé le " + ChatColor.BLUE + ChatColor.BOLD + "rasengan" + ChatColor.WHITE + "."));

            r.rasenganState = RasenganState.NORMAL;
            r.chakra -= 5000;
        }
    }
}
