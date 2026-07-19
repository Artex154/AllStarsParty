package be.artex.rewrite.role.antagoniste.katarina;

import be.artex.allStarsParty.api.message.Message;
import be.artex.rewrite.AllStarsParty;
import be.artex.rewrite.api.item.Cooldown;
import be.artex.rewrite.api.item.CustomItem;
import be.artex.rewrite.api.itemBuilder.EnchantmentHolder;
import be.artex.rewrite.api.itemBuilder.ItemBuilder;
import be.artex.rewrite.api.role.Role;
import be.artex.rewrite.api.role.Side;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class Dague extends CustomItem {
    private final ItemStack STACK = new ItemBuilder(Material.DIAMOND_SWORD).name(ChatColor.RED + "" + ChatColor.BOLD + "Dague").itemFlags(ItemFlag.HIDE_ENCHANTS).addEnchants(new EnchantmentHolder(Enchantment.DAMAGE_ALL, 3)).lore(" ",
            ChatColor.GRAY + " Lame rebondissante" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "clic droit en sneakant" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "1x/1mn20",
            ChatColor.WHITE + "   Votre " + ChatColor.RED + ChatColor.BOLD + "Dague " + ChatColor.WHITE + "rebondit sur un " + ChatColor.UNDERLINE + "maximum de 5 joueurs" + ChatColor.WHITE + " dans un rayon de 20 blocs.",
            ChatColor.WHITE + "   Vous perdez l'utilisation de votre " + ChatColor.RED + ChatColor.BOLD + "Dague" + ChatColor.WHITE + " pendant 0,5 secondes par joueur.",
            ChatColor.GRAY + "  » S'il s'agit d'un joueur allié: ", ChatColor.LIGHT_PURPLE + "      1,5❤" + ChatColor.WHITE + " lui est régénéré.", ChatColor.GRAY + "  » S'il s'agit d'un joueur ennemi: ", ChatColor.LIGHT_PURPLE + "      1❤" + ChatColor.WHITE + " lui est infligé.", " ",
            ChatColor.GRAY + "Tranchant III")
            .build();

    private final ItemStack DISABLED_STACK = new ItemBuilder(Material.BARRIER).name(ChatColor.RED + "Lame en vol").build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL || event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)
            return;

        Player player = event.getPlayer();

        if (!player.isSneaking())
            return;

        Cooldown cooldown = Cooldown.getCooldown("katarina_dague", 120*20, ChatColor.RED + "" + ChatColor.BOLD + "Lame rebondissante" + ChatColor.WHITE + "(" + ChatColor.RED + ChatColor.BOLD + "Dague" + ChatColor.WHITE + ")");

        if (cooldown.isPlayerInCooldown(player)) {
            player.sendMessage(Message.cooldownTimeLeft(cooldown.getPlayerCooldownTimeLeft(player)));
            return;
        }

        int amountOfAffectedPlayers = 0;

        for (Entity entity : player.getNearbyEntities(20, 20, 20)) {
            if (amountOfAffectedPlayers == 5)
                break;

            if (!(entity instanceof Player))
                continue;

            Player p = (Player) entity;
            amountOfAffectedPlayers++;

            Role role = Role.manager.getPlayerRole(p.getUniqueId());

            if (role == null)
                continue;

            Side pSide = role.getSide();

            if (pSide == Side.ANTAGONISTES) {
                if (p.getHealth() + 3 >= p.getMaxHealth())
                    p.setHealth(p.getMaxHealth());
                else p.setHealth(p.getHealth() + 3);

                p.sendMessage(Message.info("La lame de " + ChatColor.RED + "Katarina" + ChatColor.WHITE + " rebondit sur vous, elle vous régénère " + ChatColor.LIGHT_PURPLE + "1,5❤" + ChatColor.WHITE + "."));
            } else {
                if (p.getHealth() - 2 <= 0) {
                    p.damage(0);
                    p.setHealth(0);
                }
                else {
                    p.damage(0);
                    p.setHealth(p.getHealth() - 2);
                }

                p.sendMessage(Message.info("La lame de " + ChatColor.RED + "Katarina" + ChatColor.WHITE + " rebondit sur vous, elle vous inflige " + ChatColor.LIGHT_PURPLE + "1❤" + ChatColor.WHITE + "."));
            }

        }

        if (amountOfAffectedPlayers == 0) {
            player.sendMessage(Message.info("Aucun joueur est présent dans le rayon."));
            return;
        }

        player.sendMessage(Message.info("Vous avez touché " + amountOfAffectedPlayers + " joueurs."));

        int slot = player.getInventory().first(Material.DIAMOND_SWORD);
        player.getInventory().setItem(slot, DISABLED_STACK.clone());

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> player.getInventory().setItem(slot, STACK.clone()), amountOfAffectedPlayers * 10L);

        cooldown.putPlayerInCooldown(player);

    }

}
