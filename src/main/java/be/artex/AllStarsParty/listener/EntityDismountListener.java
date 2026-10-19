package be.artex.AllStarsParty.listener;

import be.artex.AllStarsParty.AllStarsParty;
import be.artex.AllStarsParty.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;

public class EntityDismountListener implements Listener {
    @EventHandler
    public void onPlayerToggleSneak(EntityDismountEvent event) {
        if (!(event.getEntity() instanceof Player))
            return;

        Player player = (Player) event.getEntity();

        if (PlayerUtil.stunnedPlayers.contains(player))
            Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> event.getDismounted().setPassenger(player), 1);
    }
}
