package be.artex.allStarsParty.role.rewrited.MHA.shoto;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.Cooldown;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.api.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class IceSide extends ASPItem {
    private static final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.AQUA + "" + ChatColor.BOLD + "côté droit").build();
    public static final List<Player> playersInIce = new ArrayList<>();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onHit(Player damager, Player player, double damage) {
        Cooldown cooldown = Cooldown.getCooldown("shoto_ice_side", 65*20, ChatColor.WHITE + "Votre" + ChatColor.AQUA + ChatColor.BOLD + " côté droit");

        if (cooldown.isPlayerInCooldown(damager)) {
            damager.sendMessage(Message.cooldownTimeLeft(cooldown.getPlayerCooldownTimeLeft(damager)));
            return;
        }

        playersInIce.add(player);

        damager.sendMessage(Message.info("Vous avez enfermé " + ChatColor.AQUA + player.getName() + ChatColor.WHITE + " dans un" + ChatColor.AQUA + " cocon de glace" + ChatColor.WHITE + "."));
        player.sendMessage(Message.info(ChatColor.BLUE + "Shoto" + ChatColor.WHITE + " vous enferme dans un " + ChatColor.AQUA + "cocon de glace" + ChatColor.WHITE + "."));

        Location loc = player.getLocation();

        loc.setX(loc.getBlockX() + 0.5);
        loc.setY(loc.getBlockY());
        loc.setZ(loc.getBlockZ() + 0.5);

        player.teleport(loc);

        int radius = 1;

        for (int x = -radius; x <= radius; x++) {
            for (int y = 0; y <= 2; y++) {
                for (int z = -radius; z <= radius; z++) {
                    Location blockLoc = loc.clone().add(x, y, z);

                    if (x == 0 && y == 1 && z == 0) continue;

                    blockLoc.getBlock().setType(Material.ICE);
                }
            }
        }

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                Location blockLoc = loc.clone().add(x, 2, z);
                blockLoc.getBlock().setType(Material.ICE);
            }
        }

        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                Location blockLoc = loc.clone().add(x, -1, z);
                blockLoc.getBlock().setType(Material.ICE);
            }
        }

        loc.getBlock().setType(Material.AIR);

        Bukkit.getScheduler().scheduleSyncDelayedTask(AllStarsParty.instance, () ->
                player.setVelocity(new Vector(0, 0, 0)), 1L);

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            for (int x = -radius; x <= radius; x++) {
                for (int y = 0; y <= 2; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        Location blockLoc = loc.clone().add(x, y, z);

                        if (x == 0 && y == 1 && z == 0) continue;

                        blockLoc.getBlock().setType(Material.AIR);
                    }
                }
            }

            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    Location blockLoc = loc.clone().add(x, 2, z);
                    blockLoc.getBlock().setType(Material.AIR);
                }
            }

            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    Location blockLoc = loc.clone().add(x, -1, z);
                    blockLoc.getBlock().setType(Material.AIR);
                }
            }

            playersInIce.remove(player);
        }, 7*20L);

        cooldown.putPlayerInCooldown(damager);
    }
}
