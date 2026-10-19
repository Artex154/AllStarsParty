package be.artex.AllStarsParty.role.autre.sasuke;

import be.artex.AllStarsParty.api.descriptionBuilder.HoverHolder;
import be.artex.AllStarsParty.api.item.CustomItem;
import be.artex.AllStarsParty.api.message.Message;
import be.artex.AllStarsParty.api.role.Aura;
import be.artex.AllStarsParty.api.role.Role;
import be.artex.AllStarsParty.api.role.Side;
import be.artex.AllStarsParty.registry.ItemRegistry;
import be.artex.AllStarsParty.role.autre.sasuke.susanoo.Susanoo;
import be.artex.AllStarsParty.util.PlayerUtil;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SSasuke extends Role {
    public long chakra = 25000;

    public boolean isInSusanoo = false;
    public int amountOfHitTaken = 0;
    public int amountOfArrows = 0;

    @Override
    public @NotNull String getName() {
        return "Sasuke";
    }

    @Override
    protected @NotNull TextComponent descriptionInitialization() {
        HoverHolder SUHolder = new HoverHolder(
                ChatColor.DARK_GRAY + " [" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Susanoo" + ChatColor.DARK_GRAY + "]",
                "\n" + ChatColor.DARK_GRAY + "   [" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Susanoo" + ChatColor.DARK_GRAY + "]" + "\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous activez votre " + ChatColor.DARK_PURPLE + "susanoo" + ChatColor.WHITE + ", perdez automatiquement " + ChatColor.RED + "1,5❤" + ChatColor.WHITE + " et consommez " + ChatColor.AQUA + "5000 chakra" + ChatColor.WHITE + ".  \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous perdez automatiquement " + ChatColor.YELLOW + "-60% de vitesse" + ChatColor.WHITE + ".     \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Lorsque vous recevez un " + ChatColor.RED + "coup" + ChatColor.WHITE + ", vous ne subissez que " + ChatColor.RED + "0,5❤" + ChatColor.WHITE + ", ne subissez aucun " + ChatColor.LIGHT_PURPLE + "knockback" + ChatColor.WHITE + " et consommez " + ChatColor.AQUA + "150 chakra" + ChatColor.WHITE + ".       \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Le " + ChatColor.DARK_PURPLE + "susanoo " + ChatColor.WHITE + "se " + ChatColor.RED + "brise " + ChatColor.WHITE + "automatiquement après " + ChatColor.YELLOW + "5 coups" + ChatColor.WHITE + ".    \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous ne pouvez pas " + ChatColor.RED + "tapper" + ChatColor.WHITE + " d'autres joueurs.    \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous gagnez deux utilisations de l'" + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Arc de Susanoo" + ChatColor.WHITE + ".    \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Avec un clic avec l'" + ChatColor.DARK_PURPLE + "arc" + ChatColor.WHITE + ", vous envoyez instantanémant une flèche invisible qui inflige " + ChatColor.RED + "1,5❤" + ChatColor.WHITE + " ainsi qu'un recul conséquent qui stun pendant 3 secondes.             \n"
        );

        HoverHolder RASHolder = new HoverHolder(
                ChatColor.DARK_GRAY + " [" + ChatColor.BLUE + ChatColor.BOLD + "Rasengan" + ChatColor.DARK_GRAY + "]",
                "\n" + ChatColor.DARK_GRAY + "   [" + ChatColor.BLUE + ChatColor.BOLD + "Rasengan" + ChatColor.DARK_GRAY + "]" + "\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Lorsque vous tappez un joueur après avoir cliquer sur cet item, vous lui " + ChatColor.RED + "infligerez 1,5❤" + ChatColor.WHITE + " (brut, non-additionnés aux dégâts de base), et vous le repousserez         \n    d'une dizaine de blocs en arrière, en cassant les blocs sur le chemin.  \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Le rasengan coute " + ChatColor.AQUA + "5000 unités de chakra.     \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Si vous êtes en " + ChatColor.DARK_GREEN + "mode ermite" + ChatColor.WHITE + ", vous " + ChatColor.RED + "infligerez 1❤ supplémentaire" + ChatColor.WHITE + ".\n    De plus, le pouvoir coutera " + ChatColor.DARK_GREEN + "500 chakra d'ermite" + ChatColor.WHITE + " à la place de" + ChatColor.AQUA + " chakra normal" + ChatColor.WHITE + ".       \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Cooldown:" + ChatColor.YELLOW + " 30 secondes.\n  "
        );

        HoverHolder RAIHolder = new HoverHolder(
                ChatColor.DARK_GRAY + " [" + ChatColor.GOLD + ChatColor.BOLD + "Raijin no Kunai" + ChatColor.DARK_GRAY + "]",
                "\n" + ChatColor.DARK_GRAY + "   [" + ChatColor.GOLD + ChatColor.BOLD + "Raijin no Kunai" + ChatColor.DARK_GRAY + "]" + "\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Avec un " + ChatColor.UNDERLINE + "clic gauche" + ChatColor.WHITE + ", en visant un " + ChatColor.YELLOW + ChatColor.BOLD + "joueur" + ChatColor.WHITE + ", vous le " + ChatColor.YELLOW + "marquerez" + ChatColor.WHITE + " de votre " + ChatColor.YELLOW + "sceau" + ChatColor.WHITE + ".\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "De même en visant un " + ChatColor.YELLOW + ChatColor.BOLD + "bloc" + ChatColor.WHITE + ".\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous disposez de " + ChatColor.YELLOW + ChatColor.BOLD + "3 sceaux" + ChatColor.WHITE + ".\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Poser un " + ChatColor.YELLOW + "sceau" + ChatColor.WHITE + " vous coute " + ChatColor.AQUA + "2000 chakra" + ChatColor.WHITE + " ou " + ChatColor.DARK_GREEN + "200 chakra d'ermite" + ChatColor.WHITE + " si en " + ChatColor.DARK_GREEN + "mode ermite" + ChatColor.WHITE + ".          \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Avec un " + ChatColor.UNDERLINE + "clic droit" + ChatColor.WHITE + " cependant, en visant un " + ChatColor.YELLOW + ChatColor.BOLD + "joueur" + ChatColor.WHITE + " ou " + ChatColor.YELLOW + ChatColor.BOLD + "bloc" + ChatColor.WHITE + " ayant " + ChatColor.YELLOW + "sceau" + ChatColor.WHITE + ", vous vous téléporterez dessus.       \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Cette " + ChatColor.YELLOW + "téléportation" + ChatColor.WHITE + " vous coute " + ChatColor.AQUA + "1500 chakra" + ChatColor.WHITE + " ou " + ChatColor.DARK_GREEN + "150 chakra d'ermite" + ChatColor.WHITE + " si en " + ChatColor.DARK_GREEN + "mode ermite" + ChatColor.WHITE + ", encore une fois.          \n"

        );

        TextComponent component = new TextComponent(ChatColor.GRAY + " Vous êtes " + ChatColor.RED + ChatColor.BOLD + "Sasuke");
        component.addExtra("\n " + ChatColor.GRAY + "Vous devez " + ChatColor.WHITE + "gagner avec le clan " + ChatColor.RED + "Uchiha" + ChatColor.WHITE + ".");
        component.addExtra("\n ");
        component.addExtra("\n     " + ChatColor.YELLOW + ChatColor.BOLD + "PASSIFS: ");
        component.addExtra("\n " + ChatColor.DARK_GRAY + " »" + ChatColor.WHITE + " Vous disposez de " + ChatColor.GOLD + "fire resistance" + ChatColor.WHITE + ".");
        component.addExtra("\n " + ChatColor.DARK_GRAY + " »" + ChatColor.WHITE + " Vous disposez de " + ChatColor.AQUA + "25 000 unités de chakra" + ChatColor.WHITE + ". Vous en régénérez " + ChatColor.AQUA + "80" + ChatColor.WHITE + " toutes les secondes.");
        component.addExtra("\n ");
        component.addExtra("\n     " + ChatColor.YELLOW + ChatColor.BOLD + "CAPACITES: ");
        component.addExtra("\n" + ChatColor.DARK_GRAY + "  »");
        component.addExtra(SUHolder.toComponent());

        return component;
    }

    @Override
    public @NotNull Side getSide() {
        return Side.UCHIHA;
    }

    @Override
    public @NotNull Aura getAura() {
        return Aura.FORTE;
    }

    @Override
    public @NotNull String getID() {
        return "sasuke_sr";
    }

    @Override
    public boolean shouldTick() {
        return true;
    }

    @Override
    public void tick(Player player) {
        chakra += 40;

        if (chakra >= 25000)
            chakra = 25000;

        if (!isInSusanoo) {
            PlayerUtil.sendActionBar(player, ChatColor.DARK_GRAY + "» " + ChatColor.AQUA + ChatColor.BOLD + chakra + ChatColor.GRAY + "/25000" + ChatColor.AQUA + " chakra" + ChatColor.DARK_GRAY + " «");
            return;
        }

        PlayerUtil.sendActionBar(player, ChatColor.DARK_GRAY + "» " + ChatColor.AQUA + ChatColor.BOLD + chakra + ChatColor.GRAY + "/25000" + ChatColor.AQUA + " chakra" + ChatColor.DARK_GRAY + " ▏ " + ChatColor.DARK_PURPLE + ChatColor.BOLD + amountOfHitTaken + ChatColor.GRAY + "/5" + ChatColor.DARK_PURPLE + " coups pris" + ChatColor.DARK_GRAY + " ▏ " + ChatColor.DARK_PURPLE + ChatColor.BOLD + amountOfArrows + ChatColor.GRAY + "/2" + ChatColor.DARK_PURPLE + " flèches restantes" + ChatColor.DARK_GRAY + " «");
    }

    @Override
    public void whenAssigned(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0));
    }

    @Override
    public List<CustomItem> getCustomItems() {
        List<CustomItem> customItems = new ArrayList<>();

        customItems.add(ItemRegistry.SASUKE_SUSANOO);
        customItems.add(ItemRegistry.SASUKE_SUSANOO_BOW);

        return customItems;
    }

    @Override
    public void whenHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
        if (!isInSusanoo)
            return;

        if (!consumeChakra(150)) {
            Susanoo.disableSusanoo(player, this);
            player.sendMessage(Message.info("Il ne vous reste plus assez de " + ChatColor.AQUA + "chakra" + ChatColor.WHITE + ", le " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Susanoo " + ChatColor.WHITE + "se " + ChatColor.RED + "brise" + ChatColor.WHITE + "."));
            return;
        }

        amountOfHitTaken += 1;

        event.setCancelled(true);

        if (event.getFinalDamage() > 1)
            PlayerUtil.inflictTrueDamage(player, 1);
        else
            PlayerUtil.inflictTrueDamage(player, (int) Math.round(event.getFinalDamage()));

        if (amountOfHitTaken == 5) {
            Susanoo.disableSusanoo(player, this);
            player.sendMessage(Message.info("Vous avez subi 5 coups, le " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Susanoo " + ChatColor.WHITE + "se " + ChatColor.RED + "brise" + ChatColor.WHITE + "."));
        }
    }

    @Override
    public void onHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
        if (isInSusanoo) {
            event.setCancelled(true);
            damager.sendMessage(Message.info("Vous ne pouvez pas frapper de joueurs avec le " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Susanoo" + ChatColor.WHITE + " d'équipé."));
        }
    }

    public boolean consumeChakra(int chakra) {
        if (this.chakra < chakra)
            return false;

        this.chakra -= chakra;

        return true;
    }
}
