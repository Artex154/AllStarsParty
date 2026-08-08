package be.artex.allStarsParty.role.antagoniste.akaza;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.descriptionBuilder.DescriptionBuilder;
import be.artex.allStarsParty.api.descriptionBuilder.HoverHolder;
import be.artex.allStarsParty.api.item.CustomItem;
import be.artex.allStarsParty.api.role.Aura;
import be.artex.allStarsParty.api.role.Role;
import be.artex.allStarsParty.api.role.Side;
import be.artex.allStarsParty.registry.ItemRegistry;
import be.artex.allStarsParty.util.PlayerUtil;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Akaza extends Role {
    public static final Map<UUID, Integer> playersWithAkaza = new HashMap<>();

    private final String DESCRIPTION =
            ChatColor.GRAY + " Vous êtes " + ChatColor.RED + ChatColor.BOLD + "Akaza\n" +
                    ChatColor.GRAY + " Objectif:" + ChatColor.WHITE + " Vous devez gagner avec les " + ChatColor.RED + "antagonistes" + ChatColor.WHITE + ".\n \n" +
                    ChatColor.GRAY + ChatColor.BOLD + "» Passifs: \n" +
                    ChatColor.WHITE + " Vous possédez " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + ChatColor.BOLD + "⚔" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " Force 1" + ChatColor.WHITE + ".\n" +
                    ChatColor.WHITE + " Vous voyez l'aura de tout les joueurs sous leur pseudos." + ChatColor.GRAY + ChatColor.ITALIC + " (Lunar Client requis : le mod 'Nametags')\n" +
                    ChatColor.WHITE + " Vous infligez x% de dégâts en plus à chaque joueur en fonction de leur aura.\n" +
                    ChatColor.GRAY + "   Aucune" + ChatColor.WHITE + ":    -3%\n" +
                    ChatColor.GREEN + "   Faible" + ChatColor.WHITE + ":      +0%\n" +
                    ChatColor.YELLOW + "   Moyenne" + ChatColor.WHITE + ":  +3%\n" +
                    ChatColor.RED + "   Forte" + ChatColor.WHITE + ":       +6%\n \n" +
                    ChatColor.GRAY + ChatColor.BOLD + "» Compétences activables: \n" +
                    ChatColor.DARK_GRAY + " [" + ChatColor.GOLD + "✦" + ChatColor.DARK_GRAY + "]" + ChatColor.GOLD + ChatColor.BOLD + " Boussole" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "clic droit" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "1x/partie\n" +
                    ChatColor.WHITE + "  Vous voyez tout les joueurs avec un aura " + ChatColor.YELLOW + "moyenne" + ChatColor.WHITE + " ou " + ChatColor.RED + "forte" + ChatColor.WHITE + " en couleur. " + ChatColor.GRAY + ChatColor.ITALIC + "(Lunar Client requis)\n" +
                    ChatColor.WHITE + "  Vous infligez aussi plus de dégâts aux joueurs en fonction de leur aura:\n" +
                    ChatColor.GRAY + "   Aucune" + ChatColor.WHITE + ":    -3% -> +0%\n" +
                    ChatColor.GREEN + "   Faible" + ChatColor.WHITE + ":       +0% -> +3%\n" +
                    ChatColor.YELLOW + "   Moyenne" + ChatColor.WHITE + ":  +3% -> +6%\n" +
                    ChatColor.RED + "   Forte" + ChatColor.WHITE + ":       +6% -> +12%\n" +
                    ChatColor.WHITE + "  Tout cela pendant 35 secondes.";

    @Override
    public @NotNull String getName() {
        return "Akaza";
    }

    @Override
    public @NotNull TextComponent descriptionInitialization() {
        HoverHolder REHolder = new HoverHolder(
                ChatColor.DARK_GRAY + "  [" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Régénération extrême" + ChatColor.DARK_GRAY + "]",
                "\n" + ChatColor.DARK_GRAY + "   [" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Régénération extrême" + ChatColor.DARK_GRAY + "]" + "\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous " + ChatColor.LIGHT_PURPLE + "régénérez 1❤" + ChatColor.WHITE + " toutes les 10 secondes.  \n"
        );

        HoverHolder BHolder = new HoverHolder(
                ChatColor.DARK_GRAY + "  [" + ChatColor.GOLD + ChatColor.BOLD + "Boussole" + ChatColor.DARK_GRAY + "]",
                "\n" + ChatColor.DARK_GRAY + "   [" + ChatColor.GOLD + ChatColor.BOLD + "Boussole" + ChatColor.DARK_GRAY + "]" + "\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous voyez l'" + ChatColor.RED + "aura" + ChatColor.WHITE + " des joueurs sous leur pseudo." + ChatColor.GRAY + ChatColor.ITALIC + " (Lunar Client requis)  \n"  +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous infligez plus de dégâts aux joueurs en fonction de leur aura:\n"  +
                        ChatColor.GRAY + "    Aucune" + ChatColor.WHITE + ": -3%\n" +
                        ChatColor.GREEN + "    Faible" + ChatColor.WHITE + ": +0%\n" +
                        ChatColor.YELLOW + "    Moyenne" + ChatColor.WHITE + ": +3%\n" +
                        ChatColor.RED + "    Forte" + ChatColor.WHITE + ": +7%\n"
        );

        HoverHolder ATHolder = new HoverHolder(
                ChatColor.DARK_GRAY + "  [" + ChatColor.BLUE + ChatColor.BOLD + "Air Type" + ChatColor.DARK_GRAY + "]",
                "\n" + ChatColor.DARK_GRAY + "   [" + ChatColor.BLUE + ChatColor.BOLD + "Air Type" + ChatColor.DARK_GRAY + "]" + "\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous disposez de " + ChatColor.BLUE + "3 balles" + ChatColor.WHITE + ". Chacune des capacités en consome une. \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Lorsque vous les utiliser tous, vous les regagnez une minute après.  \n \n" +
                        ChatColor.GRAY + "  clic droit\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous envoyez un projectile invisible infligeant un recul.\n" +
                        ChatColor.GRAY + "  clic gauche\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous envoyez un projectile invisible infligeant " + ChatColor.LIGHT_PURPLE + "0,5❤" + ChatColor.WHITE + ".\n"
        );

        HoverHolder LCABolder = new HoverHolder(
                ChatColor.DARK_GRAY + "  [" + ChatColor.BLUE + ChatColor.BOLD + "Lueur chaotique d'argent bleu" + ChatColor.DARK_GRAY + "]",
                "\n" + ChatColor.DARK_GRAY + "   [" + ChatColor.BLUE + ChatColor.BOLD + "Lueur chaotique d'argent bleu" + ChatColor.DARK_GRAY + "] - " + ChatColor.GRAY + "1x/partieA\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous infligez un recul ainsi que " + ChatColor.LIGHT_PURPLE + "1❤" + ChatColor.WHITE + " aux joueur dans un rayon de 10 blocs.  \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "3x en total, avec une seconde de délai.  \n"
        );

        DescriptionBuilder descBuilder = new DescriptionBuilder(this)
                .passifs(REHolder, BHolder)
                .activables(ATHolder, LCABolder);

        return descBuilder.build();
    }

    @Override
    public @NotNull Side getSide() {
        return Side.ANTAGONISTES;
    }

    @Override
    public @NotNull Aura getAura() {
        return Aura.FORTE;
    }

    @Override
    public int getBonusStrength() {
        return 10;
    }

    @Override
    public void whenAssigned(Player player) {
        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            for (Player pl : AllStarsParty.gameManager.getAlivePlayers()) {
                Role plRole = Role.manager.getPlayerRole(pl.getUniqueId());

                PlayerUtil.setLunarNametagForAnotherPlayer(player, pl, ChatColor.GRAY + "» Aura " + plRole.getAura().getName() + ChatColor.GRAY + " «");
            }
        }, 10);

        playersWithAkaza.put(player.getUniqueId(), 3);

        new BukkitRunnable() {
            int i = 0;

            @Override
            public void run() {
                if (!playersWithAkaza.containsKey(player.getUniqueId()) || playersWithAkaza.get(player.getUniqueId()) == null) {
                    cancel();
                    return;
                }

                i++;

                if (i == 10) {
                    i = 0;

                    if (player.getHealth() + 2 >= player.getMaxHealth())
                        player.setHealth(player.getHealth());
                    else player.setHealth(player.getHealth() + 2);
                }

                PlayerUtil.sendActionBar(player, ChatColor.DARK_GRAY + "» " + ChatColor.BLUE + playersWithAkaza.get(player.getUniqueId()) + " balles " + ChatColor.GRAY + "de " + ChatColor.BLUE + ChatColor.BOLD + "Air Type" + ChatColor.GRAY + " restantes «");
            }
        }.runTaskTimer(AllStarsParty.instance, 0, 20);
    }

    @Override
    public double bonusStrength(Player player, Player damager) {
        Role plRole = Role.manager.getPlayerRole(damager.getUniqueId());

        if (plRole == null)
            return 0;

        switch (plRole.getAura()) {
            case FORTE:
                return 7;
            case MOYENNE:
                return 3;
            case FAIBLE:
                return 0;
            case AUCUNE:
                return -3;
            default:
                return 0;
        }
    }

    @Override
    public List<CustomItem> getCustomItems() {
        List<CustomItem> customItems = new ArrayList<>();

        customItems.add(ItemRegistry.AKAZA_AIRTYPE);
        customItems.add(ItemRegistry.AKAZA_LCAB);

        return customItems;
    }
}
