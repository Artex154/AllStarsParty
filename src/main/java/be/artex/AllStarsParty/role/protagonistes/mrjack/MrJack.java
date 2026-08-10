package be.artex.AllStarsParty.role.protagonistes.mrjack;

import be.artex.AllStarsParty.api.message.Message;
import be.artex.AllStarsParty.AllStarsParty;
import be.artex.AllStarsParty.api.HitCountHolder;
import be.artex.AllStarsParty.api.item.CustomItem;
import be.artex.AllStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.AllStarsParty.api.role.Aura;
import be.artex.AllStarsParty.api.role.RevivableRole;
import be.artex.AllStarsParty.api.role.Side;
import be.artex.AllStarsParty.registry.ItemRegistry;
import be.artex.AllStarsParty.util.PlayerUtil;
import be.artex.AllStarsParty.util.StatValues;
import be.artex.AllStarsParty.util.Stats;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MrJack extends RevivableRole {
    public static final List<UUID> playersWithSpeedDebuff = new ArrayList<>();
    public static final List<UUID> playersInFire = new ArrayList<>();

    private final String DESCRIPTION =
            ChatColor.GRAY + " Vous êtes " + ChatColor.GREEN + ChatColor.BOLD + "Mr. Jack\n" +
                    ChatColor.GRAY + " Objectif:" + ChatColor.WHITE + " Vous devez gagner avec les " + ChatColor.GREEN + "protagonistes" + ChatColor.WHITE + ".\n \n" +
                    ChatColor.GRAY + ChatColor.BOLD + "» Passifs: \n" +
                    ChatColor.WHITE + " Vous possédez " + ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "➤" + ChatColor.DARK_GRAY + "]" + ChatColor.YELLOW + " Vitesse 1" + ChatColor.WHITE + ".\n" +
                    ChatColor.WHITE + " Lorsque vous recevez un " + ChatColor.UNDERLINE + "coup fatal" + ChatColor.WHITE + ", vous devenez invincible et ne pourrez plus intéragir avec votre environnement pendant 5 secondes. De plus, vous disposerez de " + ChatColor.LIGHT_PURPLE + "régénaration 2" + ChatColor.WHITE + ", aussi pendant 5 secondes.\n \n" +
                    ChatColor.GRAY + ChatColor.BOLD + "» Compétences activables: \n" +
                    ChatColor.DARK_GRAY + " [" + ChatColor.GOLD + "✦" + ChatColor.DARK_GRAY + "]" + ChatColor.GOLD + ChatColor.BOLD + " Costumes\n" + ChatColor.DARK_GRAY +
                    ChatColor.WHITE + "     Vous possédez 2 costumes, le costume du " + ChatColor.AQUA + "Père Noël" + ChatColor.WHITE + " et le costume du " + ChatColor.GOLD + "Roi des Citrouilles" + ChatColor.WHITE + ".\n " +
                    ChatColor.WHITE + "     Vous changez de costumes à l'aide d'un " + ChatColor.UNDERLINE + "clic gauche" + ChatColor.WHITE + " et activez votre costume à l'aide d'un " + ChatColor.UNDERLINE + "clic droit" + ChatColor.WHITE + ".\n" +
                    ChatColor.WHITE + "     Vous disposez d'une minute pour chaque costume. Cependant, si aucun costume n'est activé, vous gagnez 2 secondes sur chaque costume par coup infligé. Cependant, vous ne pouvez pas dépasser les 3 minutes.\n" +
                    ChatColor.AQUA + ChatColor.BOLD + "   Costume du Père Noël:\n" +
                    ChatColor.WHITE + "       Vous disposez de " + ChatColor.LIGHT_PURPLE + "2 coeurs permanents" + ChatColor.WHITE + " supplémentaires.\n" +
                    ChatColor.WHITE + "       Tous les dix coups, vous donnez un cadeau à votre adversaire. Il dispose de 3 secondes pour le jeté. Si le cadeau n'est pas jeté, il explose, inflige" + ChatColor.LIGHT_PURPLE + " 1❤" + ChatColor.WHITE + " ainsi que " + ChatColor.GRAY + "lenteur 2 " + ChatColor.WHITE + "pendant 4 secondes.\n" +
                    ChatColor.GOLD + ChatColor.BOLD + "   Costume du Roi des Citrouilles:\n" +
                    ChatColor.WHITE + "       Vous disposez de " + ChatColor.GOLD + "resistance au feu" + ChatColor.WHITE + ".\n" +
                    ChatColor.WHITE + "       Tous les dix coups, vous" + ChatColor.GOLD + " enflammez" + ChatColor.WHITE + " le joueur ciblé d'un " + ChatColor.GOLD + "feu non-éteignable " + ChatColor.WHITE + "pendant 4 secondes.";

    private final ItemStack GIFT = new ItemBuilder(Material.CHEST).name(ChatColor.AQUA + "Cadeau de Mr. Jack").lore(ChatColor.WHITE + "Vous disposez de 3 secondes pour jeter ce cadeau.").build();

    @Override
    public @NotNull String getName() {
        return "Mr. Jack";
    }

    @Override
    public @NotNull Side getSide() {
        return Side.PROTAGONISTES;
    }

    @Override
    public @NotNull TextComponent descriptionInitialization() {
        return new TextComponent(DESCRIPTION);
    }

    @Override
    public @NotNull Aura getAura() {
        return Aura.FAIBLE;
    }

    @Override
    public int getBonusSpeed() {
        return 10;
    }

    @Override
    public List<CustomItem> getCustomItems() {
        return Collections.singletonList(ItemRegistry.JACK_COSTUME);
    }

    @Override
    public void onRevive(Player player) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));
    }

    @Override
    public long getUninteractableTicks() {
        return 100;
    }

    @Override
    public void onPlayerHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
        CostumesHolder pHolder = CostumesHolder.get(damager.getUniqueId());

        if (!pHolder.isActive()) {
            if ((pHolder.getTypeTicksLeft(CostumesType.NOEL) + 40) <= 3600)
                pHolder.addTicks(CostumesType.NOEL, 40);

            if ((pHolder.getTypeTicksLeft(CostumesType.CITROUILLE) + 40) <= 3600)
                pHolder.addTicks(CostumesType.CITROUILLE, 40);

            return;
        }

        HitCountHolder HCHolder = HitCountHolder.get(damager.getUniqueId());
        HCHolder.addHit();

        if (HCHolder.getHitCount() < 10) {
            PlayerUtil.sendActionBar(damager, ChatColor.GOLD + "" + ChatColor.BOLD + "» " + ChatColor.GOLD + HCHolder.getHitCount() + "/10 coups " + ChatColor.BOLD + "«");
            return;
        }

        HCHolder.resetCount();

        if (pHolder.getType() == CostumesType.NOEL) {
            player.getInventory().addItem(GIFT.clone());
            player.sendMessage(Message.info(ChatColor.GREEN + "Mr. Jack" + ChatColor.WHITE + " vous donne un cadeau. Vous diposez de 3 secondes pour le jeter avant qu'il " + ChatColor.RED + "explose" + ChatColor.WHITE + "!"));
            damager.sendMessage(Message.info("Vous avez donné un cadeau à " + ChatColor.DARK_AQUA + player.getName() + ChatColor.WHITE + "."));

            Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
                if (!player.getInventory().contains(Material.CHEST))
                    return;

                player.sendMessage(Message.warn("Vous n'avez pas jeté le cadeau de " + ChatColor.GREEN + "Mr. Jack" + ChatColor.WHITE + ". Il a " + ChatColor.RED + "explosé" + ChatColor.WHITE + ". Vous perdez " + ChatColor.LIGHT_PURPLE + "1❤" + ChatColor.WHITE + " et êtes infligé" + ChatColor.GRAY + " Lenteur 2" + ChatColor.WHITE + " pour 4 secondes."));
                damager.sendMessage(Message.info("Votre cadeau a " + ChatColor.RED + "explosé" + ChatColor.WHITE + "."));

                player.getInventory().remove(Material.CHEST);
                player.damage(0);

                if (player.getHealth() - 2 <= 0)
                    player.setHealth(0);
                else
                    player.setHealth(player.getHealth() - 2);

                Stats playerStats = Stats.get(player.getUniqueId());

                playerStats.addBonus(StatValues.SPEED, -20);

                playersWithSpeedDebuff.add(player.getUniqueId());

                Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
                    if (playersWithSpeedDebuff.contains(player.getUniqueId()))
                        playerStats.addBonus(StatValues.SPEED, 20);
                }, 80);
            }, 60);
        } else {
            damager.sendMessage(Message.info("Vous" + ChatColor.GOLD + " enflammez " + ChatColor.DARK_AQUA + player.getName() + ChatColor.WHITE + "."));
            player.sendMessage(Message.info(ChatColor.GREEN + "Mr. Jack" + ChatColor.WHITE + " vous " + ChatColor.GOLD + "enflamme" + ChatColor.WHITE + "."));

            playersInFire.add(player.getUniqueId());

            new BukkitRunnable() {
                int runs = 0;

                @Override
                public void run() {
                    if (!playersInFire.contains(player.getUniqueId()) || runs++ == 16) {
                        playersInFire.remove(player.getUniqueId());
                        cancel();
                        return;
                    }

                    if (!(player.getFireTicks() > 0))
                        player.setFireTicks(20);
                }
            }.runTaskTimer(AllStarsParty.instance, 0L, 5L);
        }
    }
}
