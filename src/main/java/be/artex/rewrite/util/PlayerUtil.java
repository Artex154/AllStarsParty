package be.artex.rewrite.util;

import be.artex.rewrite.api.HitCountHolder;
import be.artex.rewrite.api.item.Cooldown;
import be.artex.rewrite.api.role.RevivableRole;
import be.artex.rewrite.role.protagonistes.mrjack.Costumes;
import be.artex.rewrite.role.protagonistes.mrjack.CostumesHolder;
import be.artex.rewrite.role.protagonistes.mrjack.MrJack;
import be.artex.rewrite.role.solo.malenia.Malenia;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class PlayerUtil {
    private static final Map<UUID, ChatColor> playersColor = new HashMap<>();

    public static void setGlobalNameColor(Player player, ChatColor color) {
        String teamName = "color_" + color.getChar();

        for (Player p : Bukkit.getOnlinePlayers()) {
            Scoreboard board = p.getScoreboard();

            if (board == null) {
                board = Bukkit.getScoreboardManager().getNewScoreboard();
                p.setScoreboard(board);
            }

            Team team = board.getTeam(teamName);

            if (team == null) {
                team = board.registerNewTeam(teamName);
                team.setPrefix(color.toString());
            }

            for (ChatColor c : ChatColor.values()) {
                if (c != color && c.isColor()) {
                    Team oldTeam = board.getTeam("color_" + c.getChar());

                    if (oldTeam != null && oldTeam.hasEntry(player.getName()))
                        oldTeam.removeEntry(player.getName());
                }
            }

            team.addEntry(player.getName());

            playersColor.put(player.getUniqueId(), color);
        }
    }

    public static @Nullable Player getPlayerTargetEntity(Player player, double maxDistance) {
        Location eye = player.getEyeLocation();
        Vector direction = eye.getDirection().normalize();
        World world = player.getWorld();

        double step = 0.2;

        for (double d = 0; d <= maxDistance; d += step) {
            Location point = eye.clone().add(direction.clone().multiply(d));

            if (point.getBlock().getType().isSolid())
                return null;

            for (Entity entity : world.getNearbyEntities(point, 0.5, 0.5, 0.5)) {
                if (entity.equals(player))
                    continue;

                if (entity instanceof Player)
                    return (Player) entity;
            }
        }

        return null;
    }

    public static void resetPlayerStates(Player player) {
        player.getInventory().clear();
        player.setMaxHealth(20);
        player.setHealth(20);
        PlayerUtil.setGlobalNameColor(player, ChatColor.WHITE);
        Cooldown.clearAllCooldowns(player);

        for (PotionEffect effect : player.getActivePotionEffects())
            player.removePotionEffect(effect.getType());

        Stats.remove(player.getUniqueId());

        CostumesHolder.remove(player.getUniqueId());
        MrJack.playersInFire.remove(player.getUniqueId());
        MrJack.playersWithSpeedDebuff.remove(player.getUniqueId());
        Costumes.playersWhenActivated.remove(player.getUniqueId());
        RevivableRole.playersRevived.remove(player.getUniqueId());
        HitCountHolder.removePlayer(player.getUniqueId());
        Malenia.playersBleeding.remove(player.getUniqueId());
        Malenia.playersPercentage.remove(player.getUniqueId());
        Malenia.playersWithPutrefecation.remove(player.getUniqueId());
    }

    public static void sendActionBar(Player player, String s) {
        PacketContainer packet = ProtocolLibrary.getProtocolManager()
                .createPacket(PacketType.Play.Server.CHAT);

        packet.getChatComponents()
                .write(0, WrappedChatComponent.fromText(s));

        packet.getBytes()
                .write(0, (byte) 2);

        ProtocolLibrary.getProtocolManager()
                .sendServerPacket(player, packet);
    }

    public static void setNametagForOtherPlayer(Player viewer, Player target, String prefix, String suffix) {
        prefix = playersColor.get(target.getUniqueId()) + prefix;

        PacketContainer packet = ProtocolLibrary.getProtocolManager()
                .createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);

        packet.getStrings()
                .write(0, "fake_" + target.getEntityId());

        packet.getIntegers()
                .write(0, 2);

        packet.getStrings()
                .write(2, prefix);
        packet.getStrings()
                .write(3, suffix);

        packet.getSpecificModifier(Collection.class)
                .write(0, Collections.singletonList(target.getName()));

        ProtocolLibrary.getProtocolManager()
                .sendServerPacket(viewer, packet);
    }
}
