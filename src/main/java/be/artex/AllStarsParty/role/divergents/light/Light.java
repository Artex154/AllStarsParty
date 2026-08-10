package be.artex.AllStarsParty.role.divergents.light;

import be.artex.AllStarsParty.api.descriptionBuilder.DescriptionBuilder;
import be.artex.AllStarsParty.api.descriptionBuilder.HoverHolder;
import be.artex.AllStarsParty.api.message.Message;
import be.artex.AllStarsParty.AllStarsParty;
import be.artex.AllStarsParty.api.item.CustomItem;
import be.artex.AllStarsParty.api.role.Aura;
import be.artex.AllStarsParty.api.role.Role;
import be.artex.AllStarsParty.api.role.Side;
import be.artex.AllStarsParty.commands.subCommands.SelfRevealSubCommand;
import be.artex.AllStarsParty.registry.ItemRegistry;
import be.artex.AllStarsParty.util.PlayerUtil;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class Light extends Role {
    @Override
    public @NotNull String getName() {
        return "Light";
    }

    @Override
    public @NotNull Side getSide() {
        return Side.DIVERGENTS;
    }

    @Override
    public @NotNull TextComponent descriptionInitialization() {
        HoverHolder TIHolder = new HoverHolder(
                ChatColor.DARK_GRAY + "  [" + ChatColor.GOLD + ChatColor.BOLD + "Traître inflitré" + ChatColor.DARK_GRAY + "]",
                "\n" + ChatColor.DARK_GRAY + "   [" + ChatColor.GOLD + ChatColor.BOLD + "Traître infiltré" + ChatColor.DARK_GRAY + "]" + "\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Tout les joueurs vous voient comme un " + ChatColor.GREEN + "protagoniste" + ChatColor.WHITE + ".\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Les " + ChatColor.YELLOW + "divergents" + ChatColor.WHITE + " vous voient avec le suffix" + ChatColor.YELLOW + ChatColor.ITALIC + " Kira" + ChatColor.WHITE + ".\n"
        );

        HoverHolder SRHolder = new HoverHolder(
                ChatColor.DARK_GRAY + "  [" + ChatColor.GOLD + ChatColor.BOLD + "/as selfreveal" + ChatColor.DARK_GRAY + "]",
                "\n" + ChatColor.DARK_GRAY + "   [" + ChatColor.GOLD + ChatColor.BOLD + "/as selfreveal (ou /as sr)" + ChatColor.DARK_GRAY + "]" + "\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous vous révellez en " + ChatColor.YELLOW + ChatColor.BOLD + "Kira" + ChatColor.WHITE + ", vous renommant.\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous gagnez alors " + ChatColor.YELLOW + "3 pommes dorées" + ChatColor.WHITE + ".\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "De plus, vous verez aussi la vie des joueurs sous leur pseudo. " + ChatColor.GRAY + ChatColor.ITALIC + "(Lunar Client requis)  \n"
        );

        HoverHolder DNHolder = new HoverHolder(
                ChatColor.DARK_GRAY + "  [" + ChatColor.GOLD + ChatColor.BOLD + "Death Note" + ChatColor.DARK_GRAY + "]",
                "\n" + ChatColor.DARK_GRAY + "   [" + ChatColor.GOLD + ChatColor.BOLD + "Death Note" + ChatColor.DARK_GRAY + "]" + "\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous enlevez " + ChatColor.LIGHT_PURPLE + "2 coueurs permanents" + ChatColor.WHITE + " au joueur ciblé. \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "De plus, si le joueur passe sous 20% de vie, il est éxécuté.\n"
        );

        DescriptionBuilder descBuilder = new DescriptionBuilder(this)
                .passifs(TIHolder)
                .activables(SRHolder, DNHolder);

        return descBuilder.build();
    }

    @Override
    public @NotNull Aura getAura() {
        return Aura.FAIBLE;
    }

    @Override
    public int getBonusResistance() {
        return 10;
    }

    @Override
    public ChatColor getDisplayColor() {
        return ChatColor.GREEN;
    }

    @Override
    public void onHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
        if (!SelfRevealSubCommand.revealedPlayers.contains(damager.getUniqueId()))
            return;

        float healthPercentage = Math.round((player.getHealth() - event.getFinalDamage()));

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
            for (Player pl : Side.DIVERGENTS.getPlayers())
                PlayerUtil.setNametagForOtherPlayer(pl, player, ChatColor.YELLOW + "", ChatColor.ITALIC + " Kira");
        }, 10);
    }
}
