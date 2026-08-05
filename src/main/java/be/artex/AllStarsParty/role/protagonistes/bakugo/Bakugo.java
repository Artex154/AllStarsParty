package be.artex.AllStarsParty.role.protagonistes.bakugo;

import be.artex.AllStarsParty.api.message.Message;
import be.artex.AllStarsParty.AllStarsParty;
import be.artex.AllStarsParty.api.item.CustomItem;
import be.artex.AllStarsParty.api.role.Aura;
import be.artex.AllStarsParty.api.role.Role;
import be.artex.AllStarsParty.api.role.Side;
import be.artex.AllStarsParty.registry.ItemRegistry;
import be.artex.AllStarsParty.util.PlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Bakugo extends Role {
    public static final HashMap<UUID, Integer> playersNitro = new HashMap<>();

    private final String DESCRIPTION =
            ChatColor.GRAY + " Vous êtes " + ChatColor.GREEN + ChatColor.BOLD + "Bakugo\n" +
                    ChatColor.GRAY + " Objectif:" + ChatColor.WHITE + " Vous devez gagner avec les " + ChatColor.GREEN + "protagonistes" + ChatColor.WHITE + ".\n \n" +
                    ChatColor.GRAY + ChatColor.BOLD + "» Passifs: \n" +
                    ChatColor.WHITE + " Vous possédez 5% d'" + ChatColor.RED + "exploser " + ChatColor.WHITE + "un joueur en le tappant. Le joueur sera " + ChatColor.GOLD + "enflammé " + ChatColor.WHITE + "et perdera" + ChatColor.LIGHT_PURPLE + " 1❤" + ChatColor.WHITE + ".\n" +
                    ChatColor.WHITE + " Vous commencez avec" + ChatColor.GOLD + " 5 gouttes de nitroglycérine" + ChatColor.WHITE + ". Vous en gagner une goutte toute les 15 secondes. Chaque compétence activable vous en coûte un certain nombre.\n \n" +
                    ChatColor.GRAY + ChatColor.BOLD + "» Compétences activables: \n" +
                    ChatColor.DARK_GRAY + " [" + ChatColor.GOLD + "✦" + ChatColor.DARK_GRAY + "]" + ChatColor.GOLD + ChatColor.BOLD + " Propulsion" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "clic droit" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "1x/20s" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "1 goutte/utilisation\n" +
                    ChatColor.WHITE + "     Vous vous propulser dans les airs.\n" +
                    ChatColor.DARK_GRAY + " [" + ChatColor.GOLD + "✦" + ChatColor.DARK_GRAY + "]" + ChatColor.GOLD + ChatColor.BOLD + " Cluster" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "clic droit" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "1x/5s" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "7 gouttes/utilisation\n" +
                    ChatColor.WHITE + "     Vous infligez un " + ChatColor.GOLD + "feu non-éteignable" + ChatColor.WHITE + " pendant 4 secondes ainsi que " + ChatColor.LIGHT_PURPLE + "2❤" + ChatColor.WHITE + " aux joueur ciblé. Cependant, vous et le joueur ciblé subissez une propulsion en arrière.";

    @Override
    public @NotNull String getName() {
        return "Bakugo";
    }

    @Override
    public @NotNull Side getSide() {
        return Side.PROTAGONISTES;
    }

    @Override
    public @NotNull String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public @NotNull Aura getAura() {
        return Aura.MOYENNE;
    }

    @Override
    public void whenAssigned(Player player) {
        playersNitro.put(player.getUniqueId(), 5);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!playersNitro.containsKey(player.getUniqueId()) || playersNitro.get(player.getUniqueId()) == null) {
                    cancel();
                    playersNitro.remove(player.getUniqueId());
                }

                PlayerUtil.sendActionBar(player, ChatColor.GRAY + "» " + ChatColor.GOLD + playersNitro.get(player.getUniqueId()).toString() + " gouttes " + ChatColor.GRAY + "de " + ChatColor.GOLD + "nitroglycérine" + ChatColor.GRAY + " «");
            }

        }.runTaskTimer(AllStarsParty.instance, 0, 20);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!playersNitro.containsKey(player.getUniqueId()) || playersNitro.get(player.getUniqueId()) == null) {
                    cancel();
                    playersNitro.remove(player.getUniqueId());
                }

                playersNitro.put(player.getUniqueId(), playersNitro.get(player.getUniqueId()) + 1);
            }

        }.runTaskTimer(AllStarsParty.instance, 5*20, 15*20);
    }

    @Override
    public List<CustomItem> getCustomItems() {
        List<CustomItem> customItems = new ArrayList<>();

        customItems.add(ItemRegistry.BAKUGO_PROPULSION);
        customItems.add(ItemRegistry.BAKUGO_CLUSTER);

        return customItems;
    }

    @Override
    public void onHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
        Random random = new Random();
        int i = random.nextInt(100);

        if (i <= 5) {
            player.damage(0);

            if (player.getHealth() - 2 <= 0)
                player.setHealth(0);
            else
                player.setHealth(player.getHealth() - 2);

            player.setFireTicks(100);
            player.getWorld().playSound(player.getLocation(), Sound.EXPLODE, 1f, 1f);
            player.getWorld().spigot().playEffect(player.getLocation(), Effect.EXPLOSION_HUGE, 0, 0, 0, 0, 0, 0, 1, 5);

            player.sendMessage(Message.info(ChatColor.GREEN + "Bakugo" + ChatColor.WHITE + " vous explose."));
        }
    }
}
