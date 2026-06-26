package be.artex.allStarsParty.role.old.DS;


import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Kokushibo extends Role {
    @Override
    public String getName() {
        return "Kokushibo";
    }

    @Override
    public Side getSide() {
        return Side.DS;
    }

    @Override
    public int getStrength() {
        return 105;
    }

    @Override
    public void onKill(PlayerDeathEvent event) {
        Player killer = event.getEntity().getKiller();

        killer.setMaxHealth(killer.getMaxHealth() + 2);
        killer.setHealth(killer.getHealth() + 2);
    }
}
