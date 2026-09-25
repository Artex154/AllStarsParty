package be.artex.AllStarsParty.role.autre.minato;

import be.artex.AllStarsParty.AllStarsParty;
import be.artex.AllStarsParty.api.descriptionBuilder.HoverHolder;
import be.artex.AllStarsParty.api.item.CustomItem;
import be.artex.AllStarsParty.api.message.Message;
import be.artex.AllStarsParty.api.role.Aura;
import be.artex.AllStarsParty.api.role.Role;
import be.artex.AllStarsParty.api.role.Side;
import be.artex.AllStarsParty.registry.ItemRegistry;
import be.artex.AllStarsParty.util.PlayerUtil;
import be.artex.AllStarsParty.util.Stats;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Minato extends Role {
    public static final HashMap<UUID, Integer> playersChakra = new HashMap<>();
    public static final HashMap<UUID, Integer> playersErmiteChakra = new HashMap<>();

    @Override
    public @NotNull String getName() {
        return "Minato";
    }

    @Override
    public @NotNull TextComponent descriptionInitialization() {
        HoverHolder ERHolder = new HoverHolder(
                ChatColor.DARK_GRAY + " [" + ChatColor.DARK_GREEN + ChatColor.BOLD + "Mode Ermite" + ChatColor.DARK_GRAY + "]",
                "\n" + ChatColor.DARK_GRAY + "   [" + ChatColor.DARK_GREEN + ChatColor.BOLD + "Mode Ermite" + ChatColor.DARK_GRAY + "]" + "\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous disposez de " + ChatColor.DARK_GREEN + "4 000 unités de chakra d'ermite" + ChatColor.WHITE + ".  \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous pouvez " + ChatColor.GREEN + "activer " + ChatColor.WHITE + "et" + ChatColor.RED + " désactiver" + ChatColor.WHITE + " ce pouvoir au souhait, sans limites.     \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Lorsque vous l'" + ChatColor.GREEN + "activez" + ChatColor.WHITE + ", " + ChatColor.WHITE + "vous utilisez " + ChatColor.DARK_GREEN + "300 chakra d'ermite" + ChatColor.WHITE + ".       \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous en consommez par la suite " + ChatColor.DARK_GREEN + "20 " + ChatColor.WHITE + "par seconde.    \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Votre " + ChatColor.GOLD + ChatColor.BOLD + "Hiraishin no Kunai" + ChatColor.WHITE + " consommera " + ChatColor.DARK_GREEN + "150 chakra d'ermite " + ChatColor.WHITE + "au lieu du " + ChatColor.AQUA + "chakra normal" + ChatColor.WHITE + ".    \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Votre " + ChatColor.BLUE + ChatColor.BOLD + "Rasengan" + ChatColor.WHITE + " se transformera en " + ChatColor.BLUE + ChatColor.BOLD + "Rasengan Géant" + ChatColor.WHITE + ", consommera " + ChatColor.DARK_GREEN + "500 chakra d'ermite " + ChatColor.WHITE + "et " + ChatColor.RED + "infligera 2❤" + ChatColor.WHITE + ".    \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous gagnerez aussi 10% de " + ChatColor.YELLOW + "speed" + ChatColor.WHITE + " et 7,5% de " + ChatColor.RED + "strength" + ChatColor.WHITE + ".    \n"
        );

        HoverHolder RASHolder = new HoverHolder(
                ChatColor.DARK_GRAY + " [" + ChatColor.BLUE + ChatColor.BOLD + "Rasengan" + ChatColor.DARK_GRAY + "]",
                "\n" + ChatColor.DARK_GRAY + "   [" + ChatColor.BLUE + ChatColor.BOLD + "Rasengan" + ChatColor.DARK_GRAY + "]" + "\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Lorsque vous tappez un joueur après avoir cliquer sur cet item, vous lui " + ChatColor.RED + "infligerez 1❤" + ChatColor.WHITE + " et vous le repousserez         \n    d'une dizaine de blocs en arrière, en cassant les blocs sur le chemin.  \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Le rasengan coute " + ChatColor.AQUA + "5000 unités de chakra.     \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Si vous êtes en " + ChatColor.DARK_GREEN + "mode ermite" + ChatColor.WHITE + ", vous " + ChatColor.RED + "infligerez 1❤ supplémentaire" + ChatColor.WHITE + ".\n    De plus, le pouvoir coutera " + ChatColor.DARK_GREEN + "500 chakra d'ermite" + ChatColor.WHITE + " à la place de" + ChatColor.AQUA + " chakra normal" + ChatColor.WHITE + ".       \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Cooldown:" + ChatColor.YELLOW + " 30 secondes.\n  "
        );

        TextComponent component = new TextComponent(ChatColor.GRAY + " Vous êtes " + ChatColor.YELLOW + ChatColor.BOLD + "Minato");
        component.addExtra("\n " + ChatColor.GRAY + "Vous devez " + ChatColor.WHITE + "gagner avec le clan " + ChatColor.YELLOW + "Uzumaki" + ChatColor.WHITE + ".");
        component.addExtra("\n ");
        component.addExtra("\n     " + ChatColor.YELLOW + ChatColor.BOLD + "PASSIFS: ");
        component.addExtra("\n " + ChatColor.GRAY + " »" + ChatColor.WHITE + " Vous disposez de " + ChatColor.YELLOW + "speed 0,5" + ChatColor.WHITE + ".");
        component.addExtra("\n " + ChatColor.GRAY + " »" + ChatColor.WHITE + " Vous disposez de " + ChatColor.AQUA + "18 000 unités de chakra" + ChatColor.WHITE + ". Vous en régénérez " + ChatColor.AQUA + "100" + ChatColor.WHITE + " toutes les secondes.");
        component.addExtra("\n ");
        component.addExtra("\n     " + ChatColor.YELLOW + ChatColor.BOLD + "CAPACITES: ");
        component.addExtra("\n" + ChatColor.GRAY + "  »");
        component.addExtra(ERHolder.toComponent());
        component.addExtra("\n" + ChatColor.GRAY + "  »");
        component.addExtra(RASHolder.toComponent());
        component.addExtra("\n" + ChatColor.GRAY + "  »");
        component.addExtra(RASHolder.toComponent());

        return component;
    }

    @Override
    public @NotNull Side getSide() {
        return Side.PROTAGONISTES;
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
    public List<CustomItem> getCustomItems() {
        return Collections.singletonList(ItemRegistry.MINATO_ERMITE);
    }

    @Override
    public void whenAssigned(Player player) {
        playersChakra.put(player.getUniqueId(), 18000);
        playersErmiteChakra.put(player.getUniqueId(), 4000);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!playersChakra.containsKey(player.getUniqueId()) || playersChakra.get(player.getUniqueId()) == null) {
                    cancel();
                    playersChakra.remove(player.getUniqueId());
                    return;
                }

                if (!playersErmiteChakra.containsKey(player.getUniqueId()) || playersErmiteChakra.get(player.getUniqueId()) == null) {
                    cancel();
                    playersErmiteChakra.remove(player.getUniqueId());
                    return;
                }

                int ermiteChakra = playersErmiteChakra.get(player.getUniqueId());

                if (Ermite.PLAYERS_ACTIVATED.getOrDefault(player.getUniqueId(), false) == true) {
                    if (ermiteChakra < 20) {
                        ermiteChakra = 0;
                        Ermite.disable(player);
                        player.sendMessage(Message.warn("Il ne vous reste plus de " + ChatColor.DARK_GREEN + "chakra d'ermite" + ChatColor.WHITE + "."));
                    } else {
                        ermiteChakra -= 20;
                    }
                }

                int chakra = playersChakra.get(player.getUniqueId()) + 100;

                if (chakra >= 18000)
                    chakra = 18000;

                playersChakra.put(player.getUniqueId(), chakra);
                playersErmiteChakra.put(player.getUniqueId(), ermiteChakra);

                PlayerUtil.sendActionBar(player, ChatColor.DARK_GRAY + "» " + ChatColor.AQUA + ChatColor.BOLD + chakra + ChatColor.GRAY + "/18000" + ChatColor.AQUA + " chakra " + ChatColor.DARK_GRAY + " ▏ " + ChatColor.DARK_GREEN + ChatColor.BOLD + ermiteChakra + ChatColor.GRAY + "/4000" + ChatColor.DARK_GREEN + " chakra d'ermite" + ChatColor.DARK_GRAY + " «");
            }
        }.runTaskTimer(AllStarsParty.instance, 0, 20);
    }
}
