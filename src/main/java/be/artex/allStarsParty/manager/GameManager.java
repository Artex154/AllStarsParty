package be.artex.allStarsParty.manager;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.PlayerUtil;
import be.artex.allStarsParty.api.Cooldown;
import be.artex.allStarsParty.api.Side;
import be.artex.allStarsParty.registry.RoleRegistry;
import be.artex.allStarsParty.role.AOT.reiner.TransformationReiner;
import be.artex.allStarsParty.role.DS.muzan.Sang;
import be.artex.allStarsParty.role.DS.muzan.effectSelector.EffectSelector;
import be.artex.allStarsParty.role.HXH.kurapika.Serment;
import be.artex.allStarsParty.role.MHA.denki.Decharge;
import be.artex.allStarsParty.role.MHA.shoto.FireSide;
import be.artex.allStarsParty.role.MHA.shoto.IceSide;
import be.artex.allStarsParty.role.solo.hisoka.HisokaPower;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;

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
        Sang.playersBleeding.remove(player);

        Cooldown.clearAllCooldowns(player);
    }

    public void finishGame(Side winningSide) {
        setInGame(false);

        PlayerUtil.sendMessageToAllPlayers(
                ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.GREEN + "Victoire " + ChatColor.WHITE + "du camp " + winningSide.getColor() + winningSide.getName() + ChatColor.WHITE + "."
        );

        for (Player p : Bukkit.getOnlinePlayers()) {
            p.teleport(new Location(AllStarsParty.world, AllStarsParty.CENTER_X, AllStarsParty.CENTER_Y + 2, AllStarsParty.CENTER_Z));
            p.setGameMode(GameMode.ADVENTURE);
            p.getInventory().clear();
            p.setMaxHealth(20);
            p.setHealth(20);
            resetAllPlayerState(p);

            for (PotionEffect effect : p.getActivePotionEffects())
                p.removePotionEffect(effect.getType());
        }
    }
}
