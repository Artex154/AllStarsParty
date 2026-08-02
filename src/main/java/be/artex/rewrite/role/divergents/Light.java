package be.artex.rewrite.role.divergents;

import be.artex.allStarsParty.api.message.Message;
import be.artex.rewrite.AllStarsParty;
import be.artex.rewrite.api.item.CustomItem;
import be.artex.rewrite.api.role.Aura;
import be.artex.rewrite.api.role.Role;
import be.artex.rewrite.api.role.Side;
import be.artex.rewrite.commands.subCommands.SelfRevealSubCommand;
import be.artex.rewrite.registry.ItemRegistry;
import be.artex.rewrite.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class Light extends Role {
    private final String DESCRIPTION =
            ChatColor.GRAY + " Vous êtes " + ChatColor.YELLOW + ChatColor.BOLD + "Light\n" +
                    ChatColor.GRAY + " Objectif:" + ChatColor.WHITE + " Vous devez gagner avec les " + ChatColor.YELLOW + "divergents" + ChatColor.WHITE + ". Vous êtes un traitre chez les" + ChatColor.GREEN + " protagonistes" + ChatColor.WHITE + ".\n \n" +
                    ChatColor.GRAY + ChatColor.BOLD + "» Passifs: \n" +
                    ChatColor.WHITE + " Vous êtes un traitre chez les " + ChatColor.GREEN + "protagonistes" + ChatColor.WHITE + ". Vous pouvez révéler votre identité grâce à la commande " + ChatColor.GOLD + "/as selfreveal " + ChatColor.ITALIC + "(ou /as sr)" + ChatColor.WHITE + ". Vous gagnerez alors 3 " + ChatColor.YELLOW + "pommes d'or" + ChatColor.WHITE + ". De plus, vous pourrez aussi voir la vie des joueurs en pourcentage sous leur pseudo. " + ChatColor.GRAY +  ChatColor.ITALIC + "(Lunar Client requis : le mod 'nametags') \n \n" +
                    ChatColor.GRAY + ChatColor.BOLD + "» Compétences activables: \n" +
                    ChatColor.DARK_GRAY + " [" + ChatColor.GOLD + "✦" + ChatColor.DARK_GRAY + "]" + ChatColor.GOLD + ChatColor.BOLD + " Death Note" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "clic droit" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "1x/2mn\n" +
                    ChatColor.WHITE + "     Vous enlevez " + ChatColor.LIGHT_PURPLE + "2❤ permanents" + ChatColor.WHITE + ". De plus, vous tuerez instantanément le joueur s'il est sous 20%. Ceci pour 20 secondes.";

    @Override
    public @NotNull String getName() {
        return "Light";
    }

    @Override
    public @NotNull Side getSide() {
        return Side.DIVERGENTS;
    }

    @Override
    public @NotNull String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public @NotNull Aura getAura() {
        return Aura.FAIBLE;
    }

    @Override
    public ChatColor getDisplayColor() {
        return ChatColor.GREEN;
    }

    @Override
    public void onHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
        if (!SelfRevealSubCommand.revealedPlayers.contains(damager.getUniqueId()))
            return;

        float healthPercentage = Math.round((player.getHealth() - event.getFinalDamage()) * 5);

        PlayerUtil.setLunarNametagForAnotherPlayer(damager, player, ChatColor.GRAY + "» " + ChatColor.RED + healthPercentage + "% ❤" + ChatColor.GRAY + " «");

        if (healthPercentage <= 20 && Deathnote.playersAffectedByDeathNote.contains(player.getUniqueId())) {
            player.damage(2000);

            damager.sendMessage(Message.info("Vous avez éxécuté " + ChatColor.DARK_AQUA + player.getName() + ChatColor.WHITE + "."));
            player.sendMessage(Message.info(ChatColor.YELLOW + "Kira " + ChatColor.WHITE + "vous a éxécuté."));
        }
    }

    @Override
    public List<CustomItem> getCustomItems() {
        return Collections.singletonList(ItemRegistry.LIGHT_DEATHNOTE);
    }

    @Override
    public void whenAssigned(Player player) {
        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            for (Player pl : Side.DIVERGENTS.getPlayers()) {
                PlayerUtil.setNametagForOtherPlayer(pl, player, ChatColor.YELLOW + "", "");
            }
        }, 10);
    }
}
