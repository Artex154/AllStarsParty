package be.artex.AllStarsParty.role.solo.LGB;

import be.artex.AllStarsParty.api.message.Message;
import be.artex.AllStarsParty.AllStarsParty;
import be.artex.AllStarsParty.api.role.Aura;
import be.artex.AllStarsParty.api.role.Role;
import be.artex.AllStarsParty.api.role.Side;
import be.artex.AllStarsParty.listener.PlayerListeners;
import be.artex.AllStarsParty.util.StatValues;
import be.artex.AllStarsParty.util.Stats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class LGB extends Role {
    private final String DESCRIPTION =
            ChatColor.GRAY + " Vous êtes " + ChatColor.GOLD + ChatColor.BOLD + "Loup-Garou Blanc\n" +
                    ChatColor.GRAY + " Objectif:" + ChatColor.WHITE + " Vous devez gagner " + ChatColor.GOLD + "seul" + ChatColor.WHITE + ". Vous êtes un traitre chez les " + ChatColor.RED + "antagonistes" + ChatColor.WHITE + ".\n \n" +
                    ChatColor.GRAY + ChatColor.BOLD + "» Passifs: \n" +
                    ChatColor.WHITE + " Vous possédez " + ChatColor.DARK_GRAY + "[" + ChatColor.RED + ChatColor.BOLD + "⚔" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " force 1" + ChatColor.WHITE + " ainsi que" + ChatColor.LIGHT_PURPLE + " 13❤ permanents" + ChatColor.WHITE + ".\n" +
                    ChatColor.WHITE + " Lorsque tout les " + ChatColor.RED + "antagonistes" + ChatColor.WHITE + " meurent, vous gagnez " + ChatColor.YELLOW + "10%" + ChatColor.WHITE + " de " + ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "➤" + ChatColor.DARK_GRAY + "]" + ChatColor.YELLOW + " Vitesse" + ChatColor.WHITE + ".\n" +
                    ChatColor.WHITE + " Lorsque vous tuez un joueur, vous gagnez 5% d'un effet aléatoire.\n";

    @Override
    public @NotNull String getName() {
        return "Loup-Garou Blanc";
    }

    @Override
    public String getScoreboardName() {
        return "LGB";
    }

    @Override
    public @NotNull Side getSide() {
        return Side.LGB;
    }

    @Override
    public @NotNull String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public @NotNull Aura getAura() {
        return Aura.FORTE;
    }

    @Override
    public ChatColor getDisplayColor() {
        return ChatColor.RED;
    }

    @Override
    public int getBonusStrength() {
        return 10;
    }

    @Override
    public int getBonusMaxHealth() {
        return 6;
    }

    @Override
    public void whenAssigned(Player player) {
        Bukkit.getScheduler().runTaskLater(AllStarsParty.instance, () -> {
            if (Side.ANTAGONISTES.getPlayers().isEmpty()) {
                player.sendMessage(Message.info("Aucun " + ChatColor.RED + "antagoniste" + ChatColor.WHITE + " est présent dans cette partie. Vous écoper alors de " + ChatColor.YELLOW + "10%" + ChatColor.WHITE + " de " + ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "➤" + ChatColor.DARK_GRAY + "]" + ChatColor.YELLOW + " Vitesse" + ChatColor.WHITE + " supplémentaire."));
                Stats plStats = Stats.get(player.getUniqueId());

                plStats.addBonus(StatValues.SPEED, 10);

                PlayerListeners.hasLGBBonus = true;
            }
        }, 10);
    }

    @Override
    public void onKill(PlayerDeathEvent event) {
        Player player = event.getEntity().getKiller();
        Stats playerStat = Stats.get(player.getUniqueId());

        int pick = new Random().nextInt(StatValues.values().length);
        StatValues stat = StatValues.values()[pick];

        playerStat.addBonus(stat, 5);

        String effect = "";

        switch (stat) {
            case SPEED:
                effect = ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "➤" + ChatColor.DARK_GRAY + "]" + ChatColor.YELLOW + " Vitesse";
            case RESISTANCE:
                effect = ChatColor.DARK_GRAY + "[" + ChatColor.GRAY + "✦" + ChatColor.DARK_GRAY + "]" + ChatColor.GRAY + " Resistance";
            case STRENGTH:
                effect = ChatColor.DARK_GRAY + "[" + ChatColor.RED + ChatColor.BOLD + "⚔" + ChatColor.DARK_GRAY + "]" + ChatColor.RED + " Force";
        }

        player.sendMessage(Message.info("Vous avez gagné 5% de " + effect + ChatColor.WHITE + "."));
    }
}
