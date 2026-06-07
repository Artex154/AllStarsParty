package be.artex.allStarsParty.logic.manager;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.logic.Cooldown;
import be.artex.allStarsParty.role.AOT.reiner.TransformationReiner;
import be.artex.allStarsParty.role.HXH.kurapika.Serment;
import be.artex.allStarsParty.role.MHA.denki.Decharge;
import be.artex.allStarsParty.role.solo.hisoka.HisokaPower;
import org.bukkit.entity.Player;

public class GameManager {
    private final RoleManager roleManager = AllStarsParty.roleManager;
    private boolean inGame = false;

    public int getMaxPlayerCount() {
        return roleManager.getRegisteredRoles().size();
    }

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean isInGame) {
        inGame = isInGame;
    }

    public void resetAllPlayerState(Player player) {
        Serment.playersInSerment.remove(player);
        Decharge.electrocutedPlayers.remove(player);
        TransformationReiner.transformedPlayer.remove(player);
        HisokaPower.powerAffectedPlayers.remove(player);

        Cooldown.clearAllCooldowns(player);
    }
}
