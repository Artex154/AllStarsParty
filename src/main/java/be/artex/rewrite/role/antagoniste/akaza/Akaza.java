package be.artex.rewrite.role.antagoniste.akaza;

import be.artex.rewrite.AllStarsParty;
import be.artex.rewrite.api.item.CustomItem;
import be.artex.rewrite.api.role.Aura;
import be.artex.rewrite.api.role.Role;
import be.artex.rewrite.api.role.Side;
import be.artex.rewrite.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class Akaza extends Role {
    private final String DESCRIPTION =
            ChatColor.GRAY + " Vous êtes " + ChatColor.RED + ChatColor.BOLD + "Akaza\n" +
                    ChatColor.GRAY + " Objectif:" + ChatColor.WHITE + " Vous devez gagner avec les " + ChatColor.RED + "antagonistes" + ChatColor.WHITE + ".\n \n" +
                    ChatColor.GRAY + ChatColor.BOLD + "» Passifs: \n" +
                    ChatColor.WHITE + " Vous possédez " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + ChatColor.BOLD + "⚔" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " Force 0,5" + ChatColor.WHITE + ".\n" +
                    ChatColor.WHITE + " Vous voyez l'aura de tout les joueurs dans leur pseudos. Vous infligez x% de dégâts en plus à chaque joueur en fonction de leur aura.\n" +
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
    public @NotNull Side getSide() {
        return Side.ANTAGONISTES;
    }

    @Override
    public @NotNull String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public @NotNull Aura getAura() {
        return Aura.FORTE;
    }

    @Override
    public int getBonusStrength() {
        return 5;
    }

    @Override
    public void whenAssigned(Player player) {
        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            for (Player pl : AllStarsParty.gameManager.getAlivePlayers()) {
                Role plRole = Role.manager.getPlayerRole(pl.getUniqueId());

                PlayerUtil.setNametagForOtherPlayer(player, pl, "", " " + ChatColor.ITALIC + ChatColor.BOLD + plRole.getAura().getName());
            }
        }, 10);
    }

    @Override
    public double bonusStrength(Player player, Player damager) {
        Role plRole = Role.manager.getPlayerRole(damager.getUniqueId());

        if (plRole == null)
            return 0;

       Boolean isActive = Boussole.playersWithCompassActive.get(damager.getUniqueId());

        if (isActive == null || !isActive) {
            switch (plRole.getAura()) {
                case FORTE:
                    return 6;
                case MOYENNE:
                    return 3;
                case FAIBLE:
                    return 0;
                case AUCUNE:
                    return -3;
            }
        } else {
            switch (plRole.getAura()) {
                case FORTE:
                    return 12;
                case MOYENNE:
                    return 6;
                case FAIBLE:
                    return 3;
                case AUCUNE:
                    return 0;
            }
        }

        return 0;
    }

    @Override
    public List<CustomItem> getCustomItems() {
        return Collections.singletonList(new Boussole());
    }
}
