package be.artex.allStarsParty.role.old.solo.hisoka;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.stats.Resistance;
import be.artex.allStarsParty.api.stats.Speed;
import be.artex.allStarsParty.api.stats.Strength;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public enum HisokaPower {
    PIQUE(ChatColor.DARK_GRAY + "pique",
            player -> Strength.setPlayerStrength(player, 120),
            player -> Strength.setPlayerStrength(player, 100)),
    TREFLE(ChatColor.DARK_GRAY + "trèfle",
            player -> Speed.setPlayerSpeed(player, 120),
            player -> Speed.setPlayerSpeed(player, 100)),
    COEUR(ChatColor.RED + "coeur",
            player -> {
                player.setMaxHealth(30);
                if (player.getHealth() + 10 > 30)
                    player.setHealth(30);
                else
                    player.setHealth(player.getHealth() + 10);
                },
            player -> player.setMaxHealth(20)),
    CARREAU(ChatColor.RED + "carreau",
            player -> Resistance.setPlayerResistance(player, 120),
            player -> Resistance.setPlayerResistance(player, 100));

    public static final List<Player> powerAffectedPlayers = new ArrayList<>();

    private final String name;
    private final Consumer<Player> power;
    private final Consumer<Player> removePower;

    HisokaPower(String name, Consumer<Player> power, Consumer<Player> removePower) {
        this.name = name;
        this.power = power;
        this.removePower = removePower;
    }

    public String getName() {
        return name;
    }

    public void apply(Player player) {
        this.power.accept(player);

        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            if (powerAffectedPlayers.contains(player))
                this.removePower.accept(player);
        }, 90*20L);
    }
}
