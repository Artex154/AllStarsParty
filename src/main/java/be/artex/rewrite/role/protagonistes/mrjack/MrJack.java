package be.artex.rewrite.role.protagonistes.mrjack;

import be.artex.allStarsParty.api.message.Message;
import be.artex.rewrite.AllStarsParty;
import be.artex.rewrite.api.item.CustomItem;
import be.artex.rewrite.api.itemBuilder.ItemBuilder;
import be.artex.rewrite.api.role.Role;
import be.artex.rewrite.api.role.Side;
import be.artex.rewrite.registry.ItemRegistry;
import be.artex.rewrite.util.StatValues;
import be.artex.rewrite.util.Stats;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class MrJack extends Role {
    public static final Map<UUID, Integer> playerHitNumber = new HashMap<>();
    public static final List<UUID> playersWithSpeedDebuff = new ArrayList<>();
    public static final List<UUID> playersInFire = new ArrayList<>();
    public static final Map<UUID, Boolean> playersRevived = new HashMap<>();

    private final String DESCRIPTION =
            ChatColor.GRAY + " Vous êtes " + ChatColor.GREEN + ChatColor.BOLD + "Mr. Jack\n" +
                    ChatColor.GRAY + " Objectif:" + ChatColor.WHITE + " Vous devez gagner avec les " + ChatColor.GREEN + "protagonistes" + ChatColor.WHITE + ".\n \n" +
                    ChatColor.GRAY + ChatColor.BOLD + "» Passifs: \n" +
                    ChatColor.WHITE + " Vous possédez " + ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "➤" + ChatColor.DARK_GRAY + "]" + ChatColor.YELLOW + " Vitesse 1" + ChatColor.WHITE + ".\n" +
                    ChatColor.WHITE + " Lorsque vous recevez un " + ChatColor.UNDERLINE + "coup fatal" + ChatColor.WHITE + ", vous ne mourrez pas. Des particules noires apparaisent et infligent " + ChatColor.LIGHT_PURPLE + "1❤" + ChatColor.WHITE + " aux joueurs dans un rayon de 10 blocs. Cependant, vous ne pourrez plus intéragir avec votre environnement pendant 5 secondes. De plus, vous disposerez de " + ChatColor.LIGHT_PURPLE + "régénaration 2" + ChatColor.WHITE + ", aussi pendant 5 secondes.\n \n" +
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
    public @NotNull String getDescription() {
        return DESCRIPTION;
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
    public void whenHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
        if (playersRevived.get(player.getUniqueId()) != null && playersRevived.get(player.getUniqueId())) {
            event.setCancelled(true);
            return;
        }

        if (player.getHealth() - event.getFinalDamage() >= 0 || playersRevived.containsKey(player.getUniqueId()))
            return;

        event.setCancelled(true);
        playersRevived.put(player.getUniqueId(), true);

        spawnParticleCircle(player.getLocation(), 2);
        player.getLocation().getWorld().playSound(player.getLocation(), Sound.ANVIL_BREAK, 1, 1);
        player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 100, 1));

        player.sendMessage(Message.info("Vous ne subissez pas ce coup fatal. Vous recevez" + ChatColor.LIGHT_PURPLE + " régénaration 2" + ChatColor.WHITE + " pour 5 secondes. Cependant, vous ne pouvez pas intéragir avec votre environnement, aussi pour 5 secondes."));

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            playersRevived.put(player.getUniqueId(), false);
            player.sendMessage(Message.info("Vous pouvez désormais intéragir avec votre environnement."));
        }, 100);
    }

    @Override
    public void onHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
        if (playersRevived.get(damager.getUniqueId()) != null && playersRevived.get(damager.getUniqueId())) {
            event.setCancelled(true);
            return;
        }

        CostumesHolder pHolder = CostumesHolder.get(damager.getUniqueId());

        if (!pHolder.isActive()) {
            if ((pHolder.getTypeTicksLeft(CostumesType.NOEL) + 40) <= 3600)
                pHolder.addTicks(CostumesType.NOEL, 40);

            if ((pHolder.getTypeTicksLeft(CostumesType.CITROUILLE) + 40) <= 3600)
                pHolder.addTicks(CostumesType.CITROUILLE, 40);

            return;
        }

        int hitNumber;

        if (playerHitNumber.get(damager.getUniqueId()) == null) {
            playerHitNumber.put(damager.getUniqueId(), 1);
            return;
        } else {
            hitNumber = playerHitNumber.get(damager.getUniqueId());
            hitNumber++;

            playerHitNumber.put(damager.getUniqueId(), hitNumber);
        }

        if (hitNumber != 10)
            return;

        playerHitNumber.put(damager.getUniqueId(), 0);

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

    public void spawnParticleCircle(Location center, double radius) {
        World world = center.getWorld();

        for (double angle = 0; angle < 360; angle += 10) {
            double radians = Math.toRadians(angle);

            double x = radius * Math.cos(radians);
            double z = radius * Math.sin(radians);

            Location particleLoc = center.clone().add(x, 1, z);

            world.spigot().playEffect(
                    particleLoc,
                    Effect.LARGE_SMOKE,
                    0, 0,
                    0, 0, 0,
                    0,
                    1,
                    64
            );
        }
    }
}
