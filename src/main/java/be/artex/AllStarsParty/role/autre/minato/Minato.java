package be.artex.AllStarsParty.role.autre.minato;

import be.artex.AllStarsParty.api.descriptionBuilder.HoverHolder;
import be.artex.AllStarsParty.api.item.CustomItem;
import be.artex.AllStarsParty.api.message.Message;
import be.artex.AllStarsParty.api.role.Aura;
import be.artex.AllStarsParty.api.role.Role;
import be.artex.AllStarsParty.api.role.Side;
import be.artex.AllStarsParty.registry.ItemRegistry;
import be.artex.AllStarsParty.role.autre.minato.raijinKunai.RaijinKunaiHolder;
import be.artex.AllStarsParty.role.autre.minato.rasengan.RasenganState;
import be.artex.AllStarsParty.util.PlayerUtil;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class Minato extends Role {
    public RasenganState rasenganState = RasenganState.NONE;
    public long rasenganCooldownTicks = 0;

    public final Queue<RaijinKunaiHolder> marks = new ArrayDeque<>(3);
    public int lastMarkCount = 0;

    public long chakra = 18000;
    public long ermiteChakra = 4000;
    public boolean isInErmite = false;

    private boolean shouldTick = true;

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
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Lorsque vous tappez un joueur après avoir cliquer sur cet item, vous lui " + ChatColor.RED + "infligerez 1,5❤" + ChatColor.WHITE + " (brut, non-additionnés aux dégâts de base), et vous le repousserez         \n    d'une dizaine de blocs en arrière, en cassant les blocs sur le chemin.  \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Le rasengan coute " + ChatColor.AQUA + "5000 unités de chakra.     \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Si vous êtes en " + ChatColor.DARK_GREEN + "mode ermite" + ChatColor.WHITE + ", vous " + ChatColor.RED + "infligerez 1❤ supplémentaire" + ChatColor.WHITE + ".\n    De plus, le pouvoir coutera " + ChatColor.DARK_GREEN + "500 chakra d'ermite" + ChatColor.WHITE + " à la place de" + ChatColor.AQUA + " chakra normal" + ChatColor.WHITE + ".       \n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Cooldown:" + ChatColor.YELLOW + " 30 secondes.\n  "
        );

        HoverHolder RAIHolder = new HoverHolder(
                ChatColor.DARK_GRAY + " [" + ChatColor.GOLD + ChatColor.BOLD + "Raijin no Kunai" + ChatColor.DARK_GRAY + "]",
                "\n" + ChatColor.DARK_GRAY + "   [" + ChatColor.GOLD + ChatColor.BOLD + "Raijin no Kunai" + ChatColor.DARK_GRAY + "]" + "\n" +
                        ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous tirez une flèche automatiquement à l'aide d'un clic droit " + ChatColor.RED + "infligerez 1,5❤" + ChatColor.WHITE + " (brut, non-additionnés aux dégâts de base), et vous le repousserez         \n    d'une dizaine de blocs en arrière, en cassant les blocs sur le chemin.  \n" +
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
        component.addExtra(RAIHolder.toComponent());

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
    public @NotNull String getID() {
        return "minato";
    }

    @Override
    public boolean shouldTick() {
        return shouldTick;
    }

    @Override
    public void tick(Player player) {
        if (rasenganCooldownTicks <= 10 && rasenganCooldownTicks > 0)
            rasenganCooldownTicks = 0;
        else if (rasenganCooldownTicks > 10)
            rasenganCooldownTicks -= 10;

        if (isInErmite) {
            if (ermiteChakra < 10) {
                ermiteChakra = 0;
                Ermite.disable(player, this);
                player.sendMessage(Message.warn("Il ne vous reste plus de " + ChatColor.DARK_GREEN + "chakra d'ermite" + ChatColor.WHITE + "."));
            } else {
                ermiteChakra -= 10;
            }
        }

        chakra += 50;

        if (chakra >= 18000)
            chakra = 18000;

        PlayerUtil.sendActionBar(player, ChatColor.DARK_GRAY + "» " + ChatColor.AQUA + ChatColor.BOLD + chakra + ChatColor.GRAY + "/18000" + ChatColor.AQUA + " chakra " + ChatColor.DARK_GRAY + " ▏ " + ChatColor.DARK_GREEN + ChatColor.BOLD + ermiteChakra + ChatColor.GRAY + "/4000" + ChatColor.DARK_GREEN + " chakra d'ermite" + ChatColor.DARK_GRAY + " «");
    }

    @Override
    public int getBonusSpeed() {
        return 10;
    }

    @Override
    public List<CustomItem> getCustomItems() {
        List<CustomItem> customItems = new ArrayList<>();

        customItems.add(ItemRegistry.MINATO_ERMITE);
        customItems.add(ItemRegistry.MINATO_RASENGAN);
        customItems.add(ItemRegistry.MINATO_RAIJIN);

        return customItems;
    }

    @Override
    public void onDeath(PlayerDeathEvent event) {
        shouldTick = false;
    }

    @Override
    public void onHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
        if (rasenganState == RasenganState.NONE)
            return;

        event.setDamage(0);
        rasenganCooldownTicks = 600;
        player.getWorld().playSound(player.getLocation(), Sound.CREEPER_HISS, 1f, 1f);

        int dti = 3;

        if (rasenganState == RasenganState.NORMAL)
            player.sendMessage(Message.info("Vous subissez le " + ChatColor.BLUE + ChatColor.BOLD + "rasengan" + ChatColor.WHITE + " de " + ChatColor.YELLOW + "Minato" + ChatColor.WHITE + ". Il vous inflige " + ChatColor.RED + "1,5❤" + ChatColor.WHITE + "."));
        else {
            player.sendMessage(Message.info("Vous subissez le " + ChatColor.BLUE + ChatColor.BOLD + "rasengan géant" + ChatColor.WHITE + " de " + ChatColor.YELLOW + "Minato" + ChatColor.WHITE + ". Il vous inflige " + ChatColor.RED + "2,5❤" + ChatColor.WHITE + "."));
            dti += 2;
        }

        PlayerUtil.inflictTrueDamage(player, dti);
        rasenganState = RasenganState.NONE;
    }

    @Nullable
    public RaijinKunaiHolder getMarkHolder(@NotNull Player player) {
        for (RaijinKunaiHolder holder : marks) {
            if (holder.getPlayer() == null)
                continue;

            if (holder.getPlayer().equals(player))
                return holder;
        }

        return null;
    }

    @Nullable
    public RaijinKunaiHolder getMarkHolder(@NotNull Block block) {
        for (RaijinKunaiHolder holder : marks) {
            if (holder.getBlock() == null)
                continue;

            if (holder.getBlock().equals(block))
                return holder;
        }

        return null;
    }

    public void setMark(@NotNull RaijinKunaiHolder newMark) {
        marks.removeIf(mark ->
                mark.getMarkCount() == newMark.getMarkCount()
        );

        marks.add(newMark);
    }
}
