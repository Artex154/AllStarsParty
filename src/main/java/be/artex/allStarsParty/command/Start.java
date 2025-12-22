package be.artex.allStarsParty.command;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.PlayerUtil;
import be.artex.allStarsParty.gameLogic.Role;
import be.artex.allStarsParty.itemBuilder.ItemBuilder;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Start implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player))
            return true;

        Player player = (Player) commandSender;

        if (!commandSender.isOp()) {
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + "Vous devez être " + ChatColor.RED + "opérateur" + ChatColor.WHITE + " pour exécuter cette " + ChatColor.RED + "commande" + ChatColor.WHITE + ".");
            return true;
        }

        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        int maxPlayers = AllStarsParty.maxPlayers;

        if (maxPlayers != onlinePlayers.size()) {
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + "Vous n'avez pas le nombre de " + ChatColor.RED + "joueurs connectés" + ChatColor.WHITE + " nécessaire pour commencer la " + ChatColor.RED + "partie" + ChatColor.WHITE + "." + ChatColor.GRAY + " (" + onlinePlayers.size() + "/" + maxPlayers + ")");
            return true;
        }

        if (AllStarsParty.inGame) {
            player.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + "Une " + ChatColor.RED + "partie" + ChatColor.WHITE + " est déjà en cours.");
            return true;
        }

        World world = AllStarsParty.world;

        for (Player p : onlinePlayers) {
            setupPlayer(p, world);
        }

        List<Player> players = new ArrayList<>(onlinePlayers);

        Role.assignRolesRandomly(AllStarsParty.registeredRoles, players);

        PlayerUtil.updateAllPlayerScoreboards();

        AllStarsParty.inGame = true;

        return true;
    }

    private static void setupPlayer(Player player, World world) {
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(generateRandomLocation(world));
        setupInventory(player.getInventory());
    }

    private static Location generateRandomLocation(World world) {
        Random random = new Random();

        int x = random.nextInt(25);
        int z = random.nextInt(25);
        int y = world.getHighestBlockYAt(x, z);

        return new Location(world, x, y, z);
    }

    private static void setupInventory(PlayerInventory inv) {
        inv.setHelmet(new ItemBuilder(Material.IRON_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build());
        inv.setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        inv.setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build());
        inv.setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        inv.setItem(0, new ItemBuilder(Material.IRON_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 3).build());
        inv.setItem(1, new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 1).build());
        inv.setItem(2, new ItemStack(Material.WATER_BUCKET));
        inv.setItem(3, new ItemStack(Material.COBBLESTONE, 64));
        inv.setItem(4, new ItemStack(Material.GOLDEN_APPLE, 12));
        inv.setItem(5, new ItemStack(Material.GOLDEN_CARROT, 64));
        inv.setItem(6, new ItemStack(Material.COBBLESTONE, 64));
        inv.setItem(7, new ItemStack(Material.COBBLESTONE, 64));
        inv.setItem(8, new ItemStack(Material.WATER_BUCKET));
        inv.addItem(new ItemStack(Material.ARROW, 32));
    }
}
