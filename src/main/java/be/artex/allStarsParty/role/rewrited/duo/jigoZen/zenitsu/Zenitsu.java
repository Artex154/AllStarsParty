package be.artex.allStarsParty.role.rewrited.duo.jigoZen.zenitsu;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.message.Message;
import be.artex.allStarsParty.api.stats.Speed;
import be.artex.allStarsParty.api.stats.Strength;
import be.artex.allStarsParty.registry.ItemRegistry;
import be.artex.allStarsParty.registry.RoleRegistry;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.*;

public class Zenitsu extends Role {
    public static final Map<UUID, SleepState> playersAsleep = new HashMap<>();
    private static final Random RANDOM = new Random();

    @Override
    public String getName() {
        return "Zen'itsu";
    }

    @Override
    public Side getSide() {
        return Side.JIGO_ZEN;
    }

    @Override
    public int getSpeed() {
        return 110;
    }

    @Override
    public int getStrength() {
        return 90;
    }

    @Override
    public List<ASPItem> getItems() {
        ArrayList<ASPItem> list = new ArrayList<>();

        list.add(ItemRegistry.FRAPPE_FOUDROYANTE);
        list.add(ItemRegistry.DDFC);

        return list;
    }

    @Override
    public void onHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
        if (isAwake(damager.getUniqueId()))
            return;

        int i = RANDOM.nextInt(100);

        if (i < 10) {
            Speed.addSpeedToPlayer(damager, 1);
            damager.sendMessage(Message.info("Vous avez gagné " + ChatColor.YELLOW + "1% de vitesse" + ChatColor.WHITE + "."));
        }
    }

    @Override
    public void onKill(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();

        if (isAwake(killer.getUniqueId()))
            return;

        Speed.addSpeedToPlayer(killer, 7);
        killer.sendMessage(Message.info("Vous avez gagné " + ChatColor.YELLOW + "7% de vitesse" + ChatColor.WHITE + "."));
    }

    @Override
    public void whenHit(Player player, Player damager, double damage, EntityDamageByEntityEvent event) {
        if (!isAwake(player.getUniqueId()))
            return;

        int i = RANDOM.nextInt(100);

        if (i < 40)
            putAsleep(player, SleepState.ASLEEP_TEMPORARY);
    }

    @Override
    public void whenAssigned(Player player) {
        if (!RoleRegistry.roleManager.getRolesAlive().contains(RoleRegistry.JIGORO)) {
            putAsleep(player, SleepState.ASLEEP_PERMANENT);
            player.sendMessage(Message.info(ChatColor.YELLOW + "Jigoro " + ChatColor.WHITE + "n'est pas présent dans cette partie. Vous avez donc les effets d'endormissement de façon permanente en plus d'une utilisation supplémentaire du " + ChatColor.GOLD + ChatColor.BOLD + "Dieu du Feu Céleste" + ChatColor.WHITE + "."));
        }
    }

    public static SleepState getPlayerSleepState(UUID player) {
        return playersAsleep.getOrDefault(player, SleepState.AWAKE);
    }

    public static boolean isAwake(UUID player) {
        return getPlayerSleepState(player) == SleepState.AWAKE;
    }

    public static void putAsleep(Player player, SleepState sleepState) {
        UUID uuid = player.getUniqueId();

        if (!isAwake(uuid) || sleepState == SleepState.AWAKE || sleepState == SleepState.ASLEEP_PERMANENT)
            return;

        playersAsleep.put(uuid, sleepState);

        if (sleepState == SleepState.ASLEEP_TEMPORARY)
            player.sendMessage(Message.info("Vous vous endormez."));

        Strength.addStrengthToPlayer(player, 15);
        Speed.addSpeedToPlayer(player, 10);

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            if (sleepState == SleepState.ASLEEP_TEMPORARY && playersAsleep.get(player.getUniqueId()) == SleepState.ASLEEP_TEMPORARY)
                wakeUp(player);
        }, 600);
    }

    private static void wakeUp(Player player) {
        Strength.removeStrengthFromPlayer(player, 15);
        Speed.removeSpeedFromPlayer(player, 10);

        player.sendMessage(Message.warn("Vous vous réveillez."));

        playersAsleep.put(player.getUniqueId(), SleepState.AWAKE);
    }
}
