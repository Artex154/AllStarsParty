package be.artex.allStarsParty.role.rewrited.DS.zenitsu;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.api.items.ASPItem;
import be.artex.allStarsParty.api.stats.Speed;
import be.artex.allStarsParty.registry.ItemRegistry;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Zenitsu extends Role {
    @Override
    public String getName() {
        return "Zen'itsu";
    }

    @Override
    public Side getSide() {
        return Side.DS;
    }

    @Override
    public int getSpeed() {
        return 120;
    }

    @Override
    public List<ASPItem> getItems() {
        return Collections.singletonList(ItemRegistry.FRAPPE_FOUDROYANTE);
    }

    @Override
    public void onHit(Player player, Player damager, double damage) {
        Random random = new Random();
        int i = random.nextInt(100);

        if (i <= 33) {
            Speed.addSpeedToPlayer(damager, 1);
            damager.sendMessage(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE +" Vous avez gagné " + ChatColor.YELLOW + "1% de vitesse" + ChatColor.WHITE + ".");
        }
    }
}
