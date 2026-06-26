package be.artex.allStarsParty.role.old.HXH;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Kirua extends Role {
    public static final HashMap<Player, Integer> playerHitNumber = new HashMap<>();

    @Override
    public String getName() {
        return "Kirua";
    }

    @Override
    public Side getSide() {
        return Side.HXH;
    }

    @Override
    public int getSpeed() {
        return 120;
    }

    @Override
    public void onHit(Player player, Player damager, double damage) {
        int hitNumber;

        if (playerHitNumber.get(damager) == null) {
            playerHitNumber.put(damager, 1);
            return;
        } else {
            hitNumber = playerHitNumber.get(damager);
            hitNumber++;

            playerHitNumber.put(damager, hitNumber);
        }

        if (hitNumber != 10)
            return;

        playerHitNumber.put(damager, 0);

        player.getWorld().strikeLightning(player.getLocation());

        if ((player.getHealth() - 3) < 0)
            player.setHealth(1);
        else
            player.setHealth(player.getHealth() - 3);
    }
}
