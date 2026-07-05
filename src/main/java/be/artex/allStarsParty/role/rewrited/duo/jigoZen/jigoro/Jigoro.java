package be.artex.allStarsParty.role.rewrited.duo.jigoZen.jigoro;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.api.message.Message;
import be.artex.allStarsParty.manager.RoleManager;
import be.artex.allStarsParty.registry.RoleRegistry;
import be.artex.allStarsParty.role.rewrited.duo.jigoZen.zenitsu.SleepState;
import be.artex.allStarsParty.role.rewrited.duo.jigoZen.zenitsu.Zenitsu;
import org.bukkit.ChatColor;
import org.bukkit.event.entity.PlayerDeathEvent;

public class Jigoro extends Role {
    private static final RoleManager ROLE_MANAGER = RoleRegistry.roleManager;

    @Override
    public String getName() {
        return "Jigoro";
    }

    @Override
    public Side getSide() {
        return Side.JIGO_ZEN;
    }

    @Override
    public void whenKilled(PlayerDeathEvent event) {
        ROLE_MANAGER.getPlayersWithRole(RoleRegistry.ZENITSU).forEach((player) -> {
            Zenitsu.putAsleep(player, SleepState.ASLEEP_PERMANENT);
            player.sendMessage(Message.info(ChatColor.YELLOW + "Jigoro" + ChatColor.WHITE + " est mort, vous avez donc les effets d'endormissement de façon permanente en plus d'une utilisation supplémentaire du " + ChatColor.GOLD + ChatColor.BOLD + "Dieu du Feu Céleste" + ChatColor.WHITE + "."));
        });
    }
}
