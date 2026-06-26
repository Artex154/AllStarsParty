package be.artex.allStarsParty.command.subCommands;

import be.artex.allStarsParty.AllStarsParty;
import be.artex.allStarsParty.PlayerUtil;
import be.artex.allStarsParty.command.SubCommand;
import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.allStarsParty.manager.GameManager;
import be.artex.allStarsParty.manager.RoleManager;
import be.artex.allStarsParty.registry.RoleRegistry;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Start extends SubCommand {
    private static final RoleManager roleManager = RoleRegistry.roleManager;
    private static final GameManager gameManager = AllStarsParty.gameManager;

    @Override
    public String getArgument() {
        return "start";
    }

    @Override
    public void whenCalled(Player sender) {
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + "Vous devez être " + ChatColor.RED + "opérateur" + ChatColor.WHITE + " pour exécuter cette " + ChatColor.RED + "commande" + ChatColor.WHITE + ".");
            return;
        }

        Collection<? extends Player> onlinePlayers = Bukkit.getOnlinePlayers();
        int maxPlayers = gameManager.getMaxPlayerCount();

        if (maxPlayers != onlinePlayers.size()) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + "Vous n'avez pas le nombre de " + ChatColor.RED + "joueurs connectés" + ChatColor.WHITE + " nécessaire pour commencer la " + ChatColor.RED + "partie" + ChatColor.WHITE + "." + ChatColor.GRAY + " (" + onlinePlayers.size() + "/" + maxPlayers + ")");
            return;
        }

        if (gameManager.isInGame()) {
            sender.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏ " + ChatColor.WHITE + "Une " + ChatColor.RED + "partie" + ChatColor.WHITE + " est déjà en cours.");
            return;
        }

        World world = AllStarsParty.world;

        for (Player p : onlinePlayers)
            setupPlayer(p, world);

        roleManager.clearPlayersRole();
        roleManager.resetAliveRoles();

        List<Player> players = new ArrayList<>(onlinePlayers);
        roleManager.assignRolesRandomly(players);

        PlayerUtil.updateAllPlayerScoreboards();

        gameManager.setInGame(true);
    }

    private static void setupPlayer(Player player, World world) {
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(generateRandomLocation(world));
        setupInventory(player.getInventory());
        gameManager.resetAllPlayerState(player);
    }

    private static Location generateRandomLocation(World world) {
        Random random = new Random();

        int x = random.nextInt(25);
        int z = random.nextInt(25);
        int y = world.getHighestBlockYAt(x, z);

        return new Location(world, x, y, z);
    }

    private static void setupInventory(PlayerInventory inv) {
        inv.setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build());
        inv.setChestplate(new ItemBuilder(Material.DIAMOND_CHESTPLATE).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        inv.setLeggings(new ItemBuilder(Material.IRON_LEGGINGS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 3).build());
        inv.setBoots(new ItemBuilder(Material.DIAMOND_BOOTS).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
        inv.setItem(0, new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 3).build());
        inv.setItem(1, new ItemBuilder(Material.BOW).addEnchant(Enchantment.ARROW_DAMAGE, 1).build());
        inv.setItem(2, new ItemStack(Material.LAVA_BUCKET));
        inv.setItem(3, new ItemStack(Material.COBBLESTONE, 64));
        inv.setItem(4, new ItemStack(Material.GOLDEN_APPLE, 24));
        inv.setItem(5, new ItemStack(Material.GOLDEN_CARROT, 64));
        inv.setItem(6, new ItemStack(Material.COBBLESTONE, 64));
        inv.setItem(7, new ItemStack(Material.COBBLESTONE, 64));
        inv.setItem(8, new ItemStack(Material.WATER_BUCKET));
        inv.addItem(new ItemStack(Material.WATER_BUCKET));
        inv.addItem(new ItemStack(Material.WATER_BUCKET));
        inv.addItem(new ItemStack(Material.LAVA_BUCKET));
        inv.addItem(new ItemStack(Material.ARROW, 24));
    }
}
