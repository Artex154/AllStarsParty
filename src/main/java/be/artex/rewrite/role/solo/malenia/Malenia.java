package be.artex.rewrite.role.solo.malenia;

import be.artex.allStarsParty.api.message.Message;
import be.artex.rewrite.AllStarsParty;
import be.artex.rewrite.api.HitCountHolder;
import be.artex.rewrite.api.item.CustomItem;
import be.artex.rewrite.api.itemBuilder.ItemBuilder;
import be.artex.rewrite.api.role.Aura;
import be.artex.rewrite.api.role.RevivableRole;
import be.artex.rewrite.api.role.Side;
import be.artex.rewrite.registry.ItemRegistry;
import be.artex.rewrite.util.PlayerUtil;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Malenia extends RevivableRole {
    public static List<UUID> playersWithPutrefecation = new ArrayList<>();
    public static List<UUID> playersBleeding = new ArrayList<>();
    public static Map<UUID, Integer> playersPercentage = new HashMap<>();

    private final String DESCRIPTION =
            ChatColor.GRAY + " Vous êtes " + ChatColor.GOLD + ChatColor.BOLD + "Melenia\n" +
                    ChatColor.GRAY + " Objectif:" + ChatColor.WHITE + " Vous devez gagner " + ChatColor.GOLD + "seul" + ChatColor.WHITE + ".\n \n" +
                    ChatColor.GRAY + ChatColor.BOLD + "» Passifs: \n" +
                    ChatColor.WHITE + " Vous possédez " + ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "➤" + ChatColor.DARK_GRAY + "]" + ChatColor.YELLOW + " Vitesse 1" + ChatColor.WHITE + " ainsi que" + ChatColor.LIGHT_PURPLE + " 12❤ permanents" + ChatColor.WHITE + ".\n" +
                    ChatColor.WHITE + " Vous disposez d'une " + ChatColor.AQUA + "épée en diamant" + ChatColor.WHITE + ", l'" + ChatColor.RED + ChatColor.BOLD + "Épée de Miquella" + ChatColor.WHITE + ". Elle est enchantée avec" + ChatColor.AQUA + " tranchant 3" + ChatColor.WHITE + ". Tout les 5 coups que vous infligez, vous vous régénérez du double des dégâts du prochain coup. \n" +
                    ChatColor.WHITE + " Lorsque vous subissez un coup fatal, vous devenez invincible et êtes régénéré complétement. Cependant, vous ne pourrez pas intéragir avec votre environnement pendant 2,5 secondes. Après ce lapse de temps, vous effectuez une explosion qui inflige 2❤ de dégâts aux joueurs dans un rayon de 5 blocs. Vous obtiendrez votre passif " + ChatColor.GOLD + ChatColor.BOLD + "Putréfaction" + ChatColor.WHITE + ". \n \n" +
                    ChatColor.DARK_GRAY + " [" + ChatColor.GOLD + "✦" + ChatColor.DARK_GRAY + "]" + ChatColor.GOLD + ChatColor.BOLD + " Putréfaction\n" +
                    ChatColor.WHITE + "   Chaque joueur dispose d'un % qui commence à 0% et qui peut aller jusqu'à 100%. A chaque fois que vous frappez un joueur, ce % augmente aléatoirement entre 5% et 10%. Lorsque vous frappez un joueur qui est a 100%, vous infligez un saignement de 0.5❤/3s pendant 9s que vous êtes régénéré. Le pourcentage du joueur retombra à 15%.\n \n" +
                    ChatColor.GRAY + ChatColor.BOLD + "» Compétences activables: \n" +
                    ChatColor.DARK_GRAY + " [" + ChatColor.GOLD + "✦" + ChatColor.DARK_GRAY + "]" + ChatColor.GOLD + ChatColor.BOLD + " Waterfowl Dance" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "clic droit" + ChatColor.DARK_GRAY + " - " + ChatColor.GRAY + "1x/45s\n" +
                    ChatColor.WHITE + "     Vous faites un dash. Lorsque vous attérissez, vous infligez " + ChatColor.LIGHT_PURPLE + "1❤" + ChatColor.WHITE + " aux joueurs dans un rayon de 10 blocs de vous.";

    private final ItemStack SWORD = new ItemBuilder(Material.DIAMOND_SWORD).name(ChatColor.BOLD + "" + ChatColor.RED + "Épée de Miquella").addEnchant(Enchantment.DAMAGE_ALL, 3).build();

    @Override
    public @NotNull String getName() {
        return "Melenia";
    }

    @Override
    public @NotNull Side getSide() {
        return Side.MELENIA;
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
    public void whenAssigned(Player player) {
        player.getInventory().setItem(0, SWORD.clone());
    }

    @Override
    public int getBonusSpeed() {
        return 10;
    }

    @Override
    public int getBonusMaxHealth() {
        return 4;
    }

    @Override
    public long getUninteractableTicks() {
        return 50;
    }

    @Override
    public void onRevive(Player player) {
        player.setHealth(player.getMaxHealth());
    }

    @Override
    public void afterRevive(Player player) {
        playersWithPutrefecation.add(player.getUniqueId());

        player.damage(0);

        for (Entity entity : player.getNearbyEntities(5, 5, 5)) {
            if (!(entity instanceof Player))
                return;

            Player p = (Player) entity;

            p.damage(0);

            if ((p.getHealth() - 4) <= 0)
                p.setHealth(0);
            else p.setHealth(p.getHealth() - 4);
        }
    }

    @Override
    public void onPlayerHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
        if (playersWithPutrefecation.contains(damager.getUniqueId())) {
            int perc = playersPercentage.getOrDefault(player.getUniqueId(), 0);

            int i = perc + 6 + new Random().nextInt(5);

            if (i >= 100) {
                PlayerUtil.setNametagForOtherPlayer(damager, player, "", ChatColor.BOLD + " 15%");
                playersPercentage.put(player.getUniqueId(), 15);
                playersBleeding.add(player.getUniqueId());

                player.sendMessage(Message.info(ChatColor.GOLD + "Malenia" + ChatColor.WHITE + " vous applique un" + ChatColor.RED + " saignement" + ChatColor.WHITE + "."));
                damager.sendMessage(Message.info("Vous appliquez un saignement à " + ChatColor.GOLD + player.getName() + ChatColor.WHITE + "."));

                new BukkitRunnable() {
                    int runs = 0;

                    @Override
                    public void run() {
                        if (!playersBleeding.contains(player.getUniqueId()) || runs++ == 3) {
                            playersBleeding.remove(player.getUniqueId());
                            cancel();
                            return;
                        }

                        player.damage(0);

                        if ((player.getHealth() - 1) <= 0)
                            player.setHealth(0);
                        else player.setHealth(player.getHealth() - 1);

                        if ((damager.getHealth() + 1) >= player.getMaxHealth())
                            damager.setHealth(damager.getMaxHealth());
                        else damager.setHealth(damager.getHealth() + 1);

                        player.getWorld().playSound(player.getLocation(), Sound.IRONGOLEM_HIT, 1, 1);
                    }
                }.runTaskTimer(AllStarsParty.instance, 0L, 60L);

            } else {
                PlayerUtil.setNametagForOtherPlayer(damager, player, "", ChatColor.BOLD + " " + i + "%");
                playersPercentage.put(player.getUniqueId(), i);
            }
        }

        HitCountHolder HCHolder = HitCountHolder.get(damager.getUniqueId());
        HCHolder.addHit();

        if (HCHolder.getHitCount() < 5) {
            PlayerUtil.sendActionBar(damager, ChatColor.GOLD + "" + ChatColor.BOLD + "» " + ChatColor.GOLD + HCHolder.getHitCount() + "/5 coups " + ChatColor.BOLD + "«");
            return;
        }

        HCHolder.resetCount();

        double healthToAdd = event.getFinalDamage() * 2;

        damager.setHealth(Math.min(damager.getHealth() + healthToAdd, damager.getMaxHealth()));
    }

    @Override
    public List<CustomItem> getCustomItems() {
        return Collections.singletonList(ItemRegistry.MALENIA_DANCE);
    }
}
