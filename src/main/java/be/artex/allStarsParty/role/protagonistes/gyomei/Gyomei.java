package be.artex.allStarsParty.role.protagonistes.gyomei;

import be.artex.allStarsParty.api.descriptionBuilder.DescriptionBuilder;
import be.artex.allStarsParty.api.descriptionBuilder.HoverHolder;
import be.artex.allStarsParty.api.message.Message;
import be.artex.allStarsParty.api.role.Aura;
import be.artex.allStarsParty.api.role.Role;
import be.artex.allStarsParty.api.role.Side;
import be.artex.allStarsParty.registry.ItemRegistry;
import be.artex.allStarsParty.util.PlayerUtil;
import be.artex.allStarsParty.util.StatValues;
import be.artex.allStarsParty.util.Stats;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Gyomei extends Role {
    public static final Map<UUID, Integer> playersHitCount = new HashMap<>();
    public static final List<UUID> playersWithMark = new ArrayList<>();

    @Override
    public @NotNull String getName() {
        return "Gyomei";
    }

    @Override
    public @NotNull Side getSide() {
        return Side.PROTAGONISTES;
    }

    @Override
    public @NotNull TextComponent descriptionInitialization() {
        HoverHolder MPHolder = new HoverHolder(
                ChatColor.DARK_GRAY + "  [" + ChatColor.GOLD + ChatColor.BOLD + "Marque de pourfendeur" + ChatColor.DARK_GRAY + "]",
                "\n" + ChatColor.DARK_GRAY + "   [" + ChatColor.GOLD + ChatColor.BOLD + "Marque de pourfendeur" + ChatColor.DARK_GRAY + "]" + "\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Après avoir subi un total de " + ChatColor.UNDERLINE + "20 coups" + ChatColor.WHITE + " vous écopez la marque de pourfendeur.  \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous gagnez alors" + ChatColor.YELLOW + " 5% de vitesse" + ChatColor.WHITE + ".\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous infligerez alors " + ChatColor.RED + "+20% de dégâts" + ChatColor.WHITE + " aux joueurs avec des " + ChatColor.YELLOW + "coeurs d'absorption" + ChatColor.WHITE + ". \n"
        );

        HoverHolder kusaPHolder = new HoverHolder(
                ChatColor.DARK_GRAY + "  [" + ChatColor.RED + ChatColor.BOLD + "Kusarigama" + ChatColor.DARK_GRAY + "]",
                "\n" + ChatColor.DARK_GRAY + "   [" + ChatColor.RED + ChatColor.BOLD + "Kusiragama" + ChatColor.DARK_GRAY + "]" + "\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous disposez d'une " + ChatColor.AQUA + "hache en diamant" + ChatColor.WHITE + ", nommée " + ChatColor.RED + ChatColor.BOLD + "Kusarigama" + ChatColor.WHITE + ". \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Elle est enchantée avec" + ChatColor.DARK_PURPLE + " tranchant IV" + ChatColor.WHITE + ".\n"
        );

        HoverHolder kusaAHolder = new HoverHolder(
                ChatColor.DARK_GRAY + "  [" + ChatColor.RED + ChatColor.BOLD + "Kusarigama" + ChatColor.DARK_GRAY + "]",
                "\n" + ChatColor.DARK_GRAY + "   [" + ChatColor.RED + ChatColor.BOLD + "Kusiragama" + ChatColor.DARK_GRAY + "]" + "\n" +
                        ChatColor.GRAY + "  clic droit (en étant accroupi)" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "1x/45s\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous téléportez le joueur ciblé sur vous.\n" +
                        ChatColor.GRAY + "  clic gauche (en étant accroupi)" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "1x/1mn30\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous infligez " + ChatColor.LIGHT_PURPLE + "1❤" + ChatColor.WHITE + " ainsi que " + ChatColor.YELLOW + "-40% de vitesse" + ChatColor.WHITE + " pendant 10 secondes au joueur ciblé.  \n"
        );

        DescriptionBuilder descBuilder = new DescriptionBuilder(this)
                .passifs(MPHolder, kusaPHolder)
                .activables(kusaAHolder);

        return descBuilder.build();
    }

    @Override
    public @NotNull Aura getAura() {
        return Aura.FORTE;
    }

    @Override
    public int getBonusMaxHealth() {
        return 4;
    }

    @Override
    public void whenAssigned(Player player) {
        player.getInventory().setItem(0, ItemRegistry.GYOMEI_KUSARIGAMA.getStack());
    }

    @Override
    public void whenHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
        int i = playersHitCount.getOrDefault(player.getUniqueId(), 0);

        if (i == 20)
            return;

        i++;

        if (i != 20) {
            playersHitCount.put(player.getUniqueId(), i);
            return;
        }

        playersWithMark.add(player.getUniqueId());

        Stats stats = Stats.get(player.getUniqueId());
        stats.addBonus(StatValues.SPEED, 5);

        player.sendMessage(Message.info("Votre marque de pourfendeur s'éveille."));
    }

    @Override
    public double bonusStrength(Player player, Player damager) {
        if (playersWithMark.contains(damager.getUniqueId()) && PlayerUtil.hasAbsorption(player))
            return 20;

        return 0;
    }
}
