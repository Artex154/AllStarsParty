package be.artex.allStarsParty.role.rewrited.antagoniste.obito;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.registry.ItemRegistry;
import be.artex.allStarsParty.role.rewrited.protagoniste.all_might.OFA;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

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
        return Side.PROTAGONISTE;
    }

    @Override
    public int getStrength() {
        return 110;
    }

    @Override
    public void whenAssigned(Player player) {
        player.getInventory().setItem(0, GUNBAI.clone());
    }

    @Override
    public List<ASPItem> getItems() {
        return Collections.singletonList(ItemRegistry.INTIGIBILITY);
    }

    @Override
    public void onKill(PlayerDeathEvent event) {
        Player player = event.getEntity().getKiller();
        long timeLeft = OFA.playersTimeLeft.getOrDefault(player, 400L);

        OFA.playersTimeLeft.put(player, timeLeft + (10 * 20L));
    }

    @Override
    public void onHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
        long timeLeft = OFA.playersTimeLeft.getOrDefault(damager, 400L);

        OFA.playersTimeLeft.put(damager, timeLeft + 2L);
    }
}
