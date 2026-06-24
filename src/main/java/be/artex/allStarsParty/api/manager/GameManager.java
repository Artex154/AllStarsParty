package be.artex.allStarsParty.api.manager;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.api.Cooldown;
import be.artex.allStarsParty.registry.RoleRegistry;
import be.artex.allStarsParty.role.AOT.reiner.TransformationReiner;
import be.artex.allStarsParty.role.DS.muzan.effectSelector.EffectSelector;
import be.artex.allStarsParty.role.HXH.kurapika.Serment;
import be.artex.allStarsParty.role.MHA.denki.Decharge;
import be.artex.allStarsParty.role.MHA.shoto.FireSide;
import be.artex.allStarsParty.role.MHA.shoto.IceSide;
import be.artex.allStarsParty.role.solo.hisoka.HisokaPower;
import org.bukkit.entity.Player;

public class GameManager {
    private final RoleManager roleManager = RoleRegistry.roleManager;
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
        FireSide.playersInFire.remove(player);
        IceSide.playersInIce.remove(player);
        EffectSelector.playerEffects.remove(player);

        Cooldown.clearAllCooldowns(player);
    }
}
