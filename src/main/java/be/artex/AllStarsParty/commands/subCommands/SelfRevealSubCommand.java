package be.artex.AllStarsParty.commands.subCommands;

import be.artex.AllStarsParty.api.message.Message;
import be.artex.AllStarsParty.api.role.Role;
import be.artex.AllStarsParty.commands.SubCommand;
import be.artex.AllStarsParty.registry.RoleRegistry;
import be.artex.AllStarsParty.util.PlayerUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SelfRevealSubCommand extends SubCommand {
    public static final List<UUID> revealedPlayers = new ArrayList<>();

    @Override
    public String[] getArgument() {
        return new String[]{"selfreveal", "sr", "lightreveal", "lr"};
    }

    @Override
    public void whenCalled(Player sender) {
        Role role = Role.manager.getPlayerRole(sender.getUniqueId());

        if (role == null || !role.equals(RoleRegistry.LIGHT))
            return;

        PlayerUtil.setPlayerName(sender, ChatColor.YELLOW + "" + ChatColor.BOLD + "Kira");
        sender.setPlayerListName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Kira");

        Bukkit.broadcastMessage(Message.info(ChatColor.DARK_AQUA + sender.getName() + ChatColor.WHITE + " se dévoile entant que " + ChatColor.YELLOW + ChatColor.BOLD + "Kira" + ChatColor.WHITE + "."));

        revealedPlayers.add(sender.getUniqueId());

        sender.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE, 3));

        Role.manager.getPlayersWithRole(RoleRegistry.AKAZA).forEach(akaza -> PlayerUtil.setLunarNametagForAnotherPlayer(Bukkit.getPlayer(akaza), sender, ChatColor.GRAY + "» Aura " + RoleRegistry.LIGHT.getAura().getName() + ChatColor.GRAY + " «"));
    }

    @Override
    public String getDescription(Player sender) {
        Role role = Role.manager.getPlayerRole(sender.getUniqueId());

        if (role == null || !role.equals(RoleRegistry.LIGHT))
            return null;

        return "Permet de se reveal en tant que kira.";
    }
}
