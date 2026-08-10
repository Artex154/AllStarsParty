package be.artex.AllStarsParty.role.protagonistes.mrjack;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.function.Consumer;

public enum CostumesType {
    NOEL(ChatColor.AQUA + "Costume du Père Noël",
            p -> p.setMaxHealth(p.getMaxHealth() + 4),
            p -> p.setMaxHealth(p.getMaxHealth() - 4)),
    CITROUILLE(ChatColor.GOLD + "Costume du Roi des Citrouilles",
            p -> p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, Integer.MAX_VALUE, 0)),
            p -> p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE));

    private final String name;
    private final Consumer<Player> whenActive;
    private final Consumer<Player> whenDeactivated;

    CostumesType(String name, Consumer<Player> whenActive, Consumer<Player> whenDeactivated) {
        this.name = name;
        this.whenActive = whenActive;
        this.whenDeactivated = whenDeactivated;
    }

    public String getName() {
        return name;
    }

    public void onActivation(Player player) {
        whenActive.accept(player);
    }

    public void onDeactivation(Player player) {
        whenDeactivated.accept(player);
    }
}
