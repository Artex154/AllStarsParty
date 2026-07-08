package be.artex.allStarsParty.role.rewrited.antagoniste.obito;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.message.Message;
import be.artex.allStarsParty.registry.ItemRegistry;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collections;
import java.util.List;

public class Obito extends Role {
    public final ItemStack GUNBAI = new ItemBuilder(Material.IRON_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 4).name(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + "Gunbai").build();

    @Override
    public String getName() {
        return "Obito";
    }

    @Override
    public Side getSide() {
        return Side.ANTAGONISTE;
    }

    @Override
    public int getStrength() {
        return 110;
    }

    @Override
    public void whenAssigned(Player player) {
        player.getInventory().setItem(0, GUNBAI.clone());
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
    }

    @Override
    public void whenHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
        if (!Intigibility.playersActivated.contains(player))
            return;

        event.setCancelled(true);
        damager.sendMessage(Message.error(ChatColor.RED + "Obito" + ChatColor.WHITE + " a activé son " + ChatColor.LIGHT_PURPLE + "intigibilité" + ChatColor.WHITE + ". Vous ne savez donc pas le tapper."));
    }

    @Override
    public List<ASPItem> getItems() {
        return Collections.singletonList(ItemRegistry.INTIGIBILITY);
    }

    @Override
    public void onKill(PlayerDeathEvent event) {
        Player player = event.getEntity().getKiller();
        long timeLeft = Intigibility.playersTimeLeft.getOrDefault(player, 400L);

        Intigibility.playersTimeLeft.put(player, timeLeft + (10 * 20L));
    }

    @Override
    public void onHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
        long timeLeft = Intigibility.playersTimeLeft.getOrDefault(damager, 400L);

        Intigibility.playersTimeLeft.put(damager, timeLeft + 2L);

        if (!Intigibility.playersActivated.contains(player))
            return;

        player.sendMessage(Message.info("Vous avez tappé un joueur, votre " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "intigibilité" + ChatColor.WHITE + " se " + ChatColor.RED + "désactive " + ChatColor.WHITE + "donc."));
        Intigibility.disableIntigibility(player, timeLeft, System.currentTimeMillis());
    }
}
