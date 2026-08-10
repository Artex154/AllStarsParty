package be.artex.AllStarsParty.role.divergents.sasuke;

import be.artex.AllStarsParty.AllStarsParty;
import be.artex.AllStarsParty.api.item.CustomItem;
import be.artex.AllStarsParty.api.role.Aura;
import be.artex.AllStarsParty.api.role.Role;
import be.artex.AllStarsParty.api.role.Side;
import be.artex.AllStarsParty.registry.ItemRegistry;
import be.artex.AllStarsParty.util.PlayerUtil;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Sasuke extends Role {
    public static final HashMap<UUID, Integer> playersChakra = new HashMap<>();

    private final String DESCRIPTION =
            ChatColor.GRAY + " Vous êtes " + ChatColor.YELLOW + ChatColor.BOLD + "Sasuke\n" +
                    ChatColor.GRAY + " Objectif:" + ChatColor.WHITE + " Vous devez gagner avec les " + ChatColor.YELLOW + "divergents" + ChatColor.WHITE + ".\n \n" +
                    ChatColor.GRAY + ChatColor.BOLD + "» Passifs: \n" +
                    ChatColor.WHITE + " Vous possédez " + ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "➤" + ChatColor.DARK_GRAY + "]" + ChatColor.YELLOW + " Vitesse 1 " + ChatColor.WHITE + "ainsi que " + ChatColor.GOLD + "resistance au feu" + ChatColor.WHITE + ".\n" +
                    ChatColor.WHITE + " Vous disposez de 100 unités de " + ChatColor.AQUA + "chakra" + ChatColor.WHITE + ". Chaque compétence activable vous en coûte un certain nombre. Vous gagnez 2 unités de " + ChatColor.AQUA + "chakra " + ChatColor.WHITE + "à chaque coup.\n \n" +
                    ChatColor.GRAY + ChatColor.BOLD + "» Compétences activables: \n" +
                    ChatColor.DARK_GRAY + " [" + ChatColor.GOLD + "✦" + ChatColor.DARK_GRAY + "]" + ChatColor.GOLD + ChatColor.BOLD + " Amaterasu" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "clic droit" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "1x/20s" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "70 chakra/utilisation\n" +
                    ChatColor.WHITE + "     Vous infligez un" + ChatColor.GOLD + " feu non-éteignable " + ChatColor.WHITE + "au joueur ciblé pendant 8 secondes.\n" +
                    ChatColor.DARK_GRAY + " [" + ChatColor.DARK_PURPLE + "✦" + ChatColor.DARK_GRAY + "]" + ChatColor.DARK_PURPLE + ChatColor.BOLD + " Rinnegan" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "clic droit" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "1x/5s" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "45 chakra/utilisation\n" +
                    ChatColor.WHITE + "     Vous échangez de place avec le joueur ciblé.";


    @Override
    public @NotNull String getName() {
        return "Sasuke";
    }

    @Override
    public @NotNull Side getSide() {
        return Side.DIVERGENTS;
    }

    @Override
    public @NotNull TextComponent descriptionInitialization() {
        return new TextComponent(DESCRIPTION);
    }

    @Override
    public @NotNull Aura getAura() {
        return Aura.FORTE;
    }

    @Override
    public int getBonusSpeed() {
        return 10;
    }

    @Override
    public void whenAssigned(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
        playersChakra.put(player.getUniqueId(), 100);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!playersChakra.containsKey(player.getUniqueId()) || playersChakra.get(player.getUniqueId()) == null) {
                    cancel();
                    playersChakra.remove(player.getUniqueId());
                }

                PlayerUtil.sendActionBar(player, ChatColor.GRAY + "» " + ChatColor.AQUA + playersChakra.get(player.getUniqueId()).toString() + "/100" + ChatColor.GRAY + " chakra" + " «");
            }

        }.runTaskTimer(AllStarsParty.instance, 0, 20);
    }

    @Override
    public void onHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
        Integer ch = playersChakra.get(damager.getUniqueId());

        if (ch + 2 >= 101)
            return;

        playersChakra.put(damager.getUniqueId(), ch + 2);
    }

    @Override
    public List<CustomItem> getCustomItems() {
        List<CustomItem> customItems = new ArrayList<>();

        customItems.add(ItemRegistry.SASUKE_AMATERASU);
        customItems.add(ItemRegistry.SASUKE_RINNEGAN);

        return customItems;
    }
}
