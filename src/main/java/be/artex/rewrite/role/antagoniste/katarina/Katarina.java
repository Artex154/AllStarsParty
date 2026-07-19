package be.artex.rewrite.role.antagoniste.katarina;

import be.artex.allStarsParty.api.message.Message;
import be.artex.rewrite.api.item.Cooldown;
import be.artex.rewrite.api.item.CustomItem;
import be.artex.rewrite.api.role.Role;
import be.artex.rewrite.api.role.Side;
import be.artex.rewrite.registry.ItemRegistry;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class Katarina extends Role {
    private final String DESCRIPTION =
            ChatColor.GRAY + " Vous êtes " + ChatColor.RED + ChatColor.BOLD + "Katarina\n" +
            ChatColor.GRAY + " Objectif:" + ChatColor.WHITE + " Vous devez gagner avec les " + ChatColor.RED + "antagonistes" + ChatColor.WHITE + ".\n \n" +
            ChatColor.GRAY + ChatColor.BOLD + "» Passifs: \n" +
            ChatColor.WHITE + " Vous possédez " + ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "➤" + ChatColor.DARK_GRAY + "]" + ChatColor.YELLOW + " Vitesse 1" + ChatColor.WHITE + ".\n" +
            ChatColor.WHITE + " Vous disposez d'une " + ChatColor.AQUA + "épée en diamant" + ChatColor.WHITE + ", votre" + ChatColor.RED + ChatColor.BOLD + " Dague" + ChatColor.WHITE + ". Elle est enchantée avec" + ChatColor.AQUA + " tranchant 3" + ChatColor.WHITE + ". Lorsque vous tuez un joueur avec, le cooldown de" + ChatColor.GOLD + " Shunpo" + ChatColor.WHITE + " baisse de 45s. \n \n" +
            ChatColor.GRAY + ChatColor.BOLD + "» Compétences activables: \n" +
            ChatColor.DARK_GRAY + " [" + ChatColor.GOLD + "✦" + ChatColor.DARK_GRAY + "]" + ChatColor.GOLD + ChatColor.BOLD + " Shunpo" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "clic droit" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "1x/1mn20s\n" +
            ChatColor.WHITE + "     Vous vous téléportez dans le dos du joueur ciblé. S'il ne s'agit pas d'un joueur de votre camp, " + ChatColor.LIGHT_PURPLE + "1❤" + ChatColor.WHITE + " lui sera infligé et vous serez invisible à ses yeux pendant une seconde.\n " +
            ChatColor.DARK_GRAY + " [" + ChatColor.RED + ChatColor.BOLD + "⚔" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + ChatColor.BOLD + " Dague" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "clic droit en sneakant" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "1x/3mn\n" +
            ChatColor.WHITE + "   Votre " + ChatColor.RED + ChatColor.BOLD + "Dague " + ChatColor.WHITE + "rebondit sur un " + ChatColor.UNDERLINE + "maximum de 5 joueurs" + ChatColor.WHITE + " dans un rayon de 20 blocs.\n" +
            ChatColor.WHITE + "   Vous perdez l'utilisation de votre " + ChatColor.RED + ChatColor.BOLD + "Dague" + ChatColor.WHITE + " pendant 0,5 secondes par joueur.\n" +
            ChatColor.GRAY + "  » S'il s'agit d'un joueur allié:\n" + ChatColor.LIGHT_PURPLE + "      1,5❤" + ChatColor.WHITE + " lui est régénéré.\n" +
            ChatColor.GRAY + "  » S'il s'agit d'un joueur ennemi:\n " + ChatColor.LIGHT_PURPLE + "      1❤" + ChatColor.WHITE + " lui est infligé.";

    @Override
    public @NotNull String getName() {
        return "Katarina";
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
    public int getBonusSpeed() {
        return 10;
    }

    @Override
    public void whenAssigned(Player player) {
        player.getInventory().setItem(0, ItemRegistry.KATARINA_DAGUE.getStack());
    }

    @Override
    public void onKill(PlayerDeathEvent event) {
        Player player = event.getEntity().getKiller();
        Cooldown cooldown = Cooldown.getCooldown("katarina_shunpo", 120, ChatColor.GOLD + "" + ChatColor.BOLD + "Shunpo");

        if (!cooldown.isPlayerInCooldown(player))
            return;

        cooldown.removePlayerCooldown(player);
        player.sendMessage(Message.info("En tuant " + event.getEntity().getName() + ", vous enlevez le cooldown de Shunpo."));
    }

    @Override
    public List<CustomItem> getCustomItems() {
        return Collections.singletonList(ItemRegistry.KATARINA_SHUNPO);
    }
}
