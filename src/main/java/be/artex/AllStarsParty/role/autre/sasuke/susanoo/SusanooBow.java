package be.artex.AllStarsParty.role.autre.sasuke.susanoo;

import be.artex.AllStarsParty.AllStarsParty;
import be.artex.AllStarsParty.api.item.CustomItem;
import be.artex.AllStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.AllStarsParty.api.message.Message;
import be.artex.AllStarsParty.api.role.Role;
import be.artex.AllStarsParty.role.autre.sasuke.SSasuke;
import be.artex.AllStarsParty.util.PlayerUtil;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class SusanooBow extends CustomItem {
    private final ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

    private final ItemStack STACK = new ItemBuilder(Material.BOW).name(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Arc de Susanoo").addEnchant(Enchantment.DURABILITY, 1).itemFlags(ItemFlag.HIDE_ENCHANTS).build();

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Role role = Role.getPlayerRole(player);

        if (!(role instanceof SSasuke))
            return;

        SSasuke r = (SSasuke) role;

        if (!r.isInSusanoo) {
            player.sendMessage(Message.info("Vous n'êtes pas équipé du " + ChatColor.DARK_PURPLE + ChatColor.BOLD + "Susanoo" + ChatColor.WHITE + "."));
            return;
        }

        if (r.amountOfArrows <= 0) {
            player.sendMessage(Message.info("Il ne vous reste plus de flèches."));
            return;
        }

        r.amountOfArrows -= 1;

        Location eye = player.getEyeLocation();
        Vector direction = eye.getDirection().normalize();
        World world = player.getWorld();
        Player target = null;
        double step = 0.45;
        int stepCount = 0;
        boolean isTargetNull = true;

        for (double d = 0; d <= 30; d += step) {
            Location point = eye.clone().add(direction.clone().multiply(d));
            spawnParticle(point);

            if (point.getBlock().getType().isSolid())
                break;

            for (Entity entity : world.getNearbyEntities(point, 0.5, 0.5, 0.5)) {
                if (entity.equals(player))
                    continue;

                if (entity instanceof Player)
                    target = (Player) entity;
            }
        }

        if (target == null)
            return;

        PlayerUtil.inflictTrueDamage(target, 3);

        Vector direction2 = target.getLocation().toVector()
                .subtract(player.getLocation().toVector())
                .normalize();

        target.setVelocity(direction2.multiply(1.2)
                .setY(1.2));

        Player finalTarget = target;
        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> PlayerUtil.stunPlayer(finalTarget, 60), 40);
    }

    private void spawnParticle(Location location) {
        PacketContainer packet = protocolManager.createPacket(PacketType.Play.Server.WORLD_PARTICLES);

        packet.getIntegers().write(0, 8);
        packet.getFloat().write(0, (float) location.getX());
        packet.getBooleans().write(0, true);
        packet.getFloat().write(1, (float) location.getY());
        packet.getFloat().write(2, (float) location.getZ());
        packet.getFloat().write(3, 0.0F);
        packet.getFloat().write(4, -0.5F);
        packet.getFloat().write(5, 0.0F);
        packet.getFloat().write(6, 0.0F);
        packet.getIntegerArrays().write(0, new int[0]);

        for (Player recipient : Bukkit.getOnlinePlayers())
            if (recipient.getWorld().equals(location.getWorld()))
                protocolManager.sendServerPacket(recipient, packet);
    }
}
