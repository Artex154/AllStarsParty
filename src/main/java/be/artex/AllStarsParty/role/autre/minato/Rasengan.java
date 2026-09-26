package be.artex.AllStarsParty.role.autre.minato;

import be.artex.AllStarsParty.api.item.Cooldown;
import be.artex.AllStarsParty.api.item.CustomItem;
import be.artex.AllStarsParty.api.message.Message;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class Rasengan extends CustomItem {
    private final ItemStack STACK = new ItemStack(Material.DIAMOND_BLOCK);

    public static final List<UUID> PLAYERS_ACTIVATED = new ArrayList<>();

    @Override
    public ItemStack getStack() {
        return STACK;
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (PLAYERS_ACTIVATED.contains(player.getUniqueId())) {
            player.sendMessage(Message.info("Le " + ChatColor.BLUE + "rasengan" + ChatColor.WHITE + " est déjà activé."));
            return;
        }

        Cooldown cooldown = Cooldown.getCooldown("rasengan", 10L);
    }
}
