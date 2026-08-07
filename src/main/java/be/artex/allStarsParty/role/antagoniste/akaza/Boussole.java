package be.artex.allStarsParty.role.antagoniste.akaza;

import be.artex.allStarsParty.api.message.Message;
import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.item.CustomItem;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.role.Role;
import be.artex.allStarsParty.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Boussole extends CustomItem {
    private final ItemStack STACK = new ItemBuilder(Material.COMPASS).name(ChatColor.GOLD + "" + ChatColor.BOLD + "Boussole").addEnchant(Enchantment.DURABILITY, 1).itemFlags(ItemFlag.HIDE_ENCHANTS).lore().build();
    public static final Map<UUID, Boolean> playersWithCompassActive = new HashMap<>();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (playersWithCompassActive.containsKey(player.getUniqueId())) {
            player.sendMessage(Message.error("Vous avez déjà utilisé votre boussole lors de cette partie."));
            return;
        }

        playersWithCompassActive.put(player.getUniqueId(), true);

        for (Player pl : AllStarsParty.gameManager.getAlivePlayers()) {
            Role role = Role.manager.getPlayerRole(pl.getUniqueId());

            boolean hasColor = false;

            switch (role.getAura()) {
                case FORTE:
                    PlayerUtil.setLunarGlowForAnotherPlayer(player, pl, Color.RED);
                    hasColor = true;
                case MOYENNE:
                    PlayerUtil.setLunarGlowForAnotherPlayer(player, pl, Color.YELLOW);
                    hasColor = true;
            }

            if (hasColor) {
                Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () ->
                    PlayerUtil.removeLunarGlow(pl)
                , 700);
            }
        }

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            if (!playersWithCompassActive.containsKey(player.getUniqueId()))
                return;

            playersWithCompassActive.put(player.getUniqueId(), false);
            player.sendMessage(Message.info("Les effets de votre boussole s'éstompent."));
        }, 700);
    }
}
