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
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.module.glow.GlowModule;
import com.lunarclient.apollo.player.ApolloPlayer;
import com.lunarclient.apollo.recipients.Recipients;
import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.Color;
import java.util.*;

public class PlayerUtil {
    private static final Map<UUID, ChatColor> playersColor = new HashMap<>();
    private static final ProtocolManager protocolManager =
            ProtocolLibrary.getProtocolManager();
    private static final GlowModule glowModule = Apollo.getModuleManager()
            .getModule(GlowModule.class);

    public static void setGlobalNameColor(@NotNull Player player, @NotNull ChatColor color) {
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

    public static @Nullable Player getPlayerTargetEntity(@NotNull Player player, double maxDistance) {
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

    public static void resetPlayerStates(@NotNull Player player) {
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

    public static void sendActionBar(@NotNull Player player, @NotNull String s) {
        PacketContainer packet = protocolManager
                .createPacket(PacketType.Play.Server.CHAT);

        packet.getChatComponents()
                .write(0, WrappedChatComponent.fromText(s));

        packet.getBytes()
                .write(0, (byte) 2);

        protocolManager
                .sendServerPacket(player, packet);
    }

    public static void setNametagForOtherPlayer(@NotNull Player viewer, @NotNull Player target, @NotNull String prefix, @NotNull String suffix) {
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

    public static void sendTitle(@NotNull Player player, @NotNull String title, @NotNull String subtitle, int fadeIn, int stay, int fadeOut) {
        PacketContainer titlePacket =
                protocolManager.createPacket(PacketType.Play.Server.TITLE);

        titlePacket.getTitleActions().write(0,
                com.comphenix.protocol.wrappers.EnumWrappers.TitleAction.TITLE);

        titlePacket.getChatComponents().write(0,
                WrappedChatComponent.fromText(title));

        protocolManager.sendServerPacket(player, titlePacket);

        PacketContainer subtitlePacket =
                protocolManager.createPacket(PacketType.Play.Server.TITLE);

        subtitlePacket.getTitleActions().write(0,
                com.comphenix.protocol.wrappers.EnumWrappers.TitleAction.SUBTITLE);

        subtitlePacket.getChatComponents().write(0,
                WrappedChatComponent.fromText(subtitle));

        protocolManager.sendServerPacket(player, subtitlePacket);

        PacketContainer timesPacket =
                protocolManager.createPacket(PacketType.Play.Server.TITLE);

        timesPacket.getTitleActions().write(0,
                com.comphenix.protocol.wrappers.EnumWrappers.TitleAction.TIMES);

        timesPacket.getIntegers().write(0, fadeIn);
        timesPacket.getIntegers().write(1, stay);
        timesPacket.getIntegers().write(2, fadeOut);

        protocolManager.sendServerPacket(player, timesPacket);
    }

    public static void setLunarGlowForAnotherPlayer(@NotNull Player viewer, @NotNull Player target, @NotNull Color color) {
        Optional<ApolloPlayer> apolloViewer = Apollo.getPlayerManager()
                .getPlayer(viewer.getUniqueId());

        if (!apolloViewer.isPresent())
            return;

        glowModule.overrideGlow(
                Recipients.of(Collections.singletonList(apolloViewer.get())),
                target.getUniqueId(),
                color
        );
    }

    public static void removeLunarGlow(@NotNull Player player) {
        glowModule.resetGlow(Recipients.ofEveryone(), player.getUniqueId());
    }
}
