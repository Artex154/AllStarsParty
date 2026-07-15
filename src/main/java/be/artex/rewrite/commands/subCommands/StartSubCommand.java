package be.artex.rewrite.commands.subCommands;

import be.artex.allStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.rewrite.AllStarsParty;
import be.artex.rewrite.scoreboard.ScoreboardManager;
import be.artex.rewrite.api.GameManager;
import be.artex.rewrite.commands.SubCommand;
import be.artex.rewrite.util.PlayerUtil;
import be.artex.rewrite.world.WorldUtil;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.Collection;

public class StartSubCommand extends SubCommand {
    private final GameManager gameManager = AllStarsParty.gameManager;

    @Override
    public String[] getArgument() {
        return new String[]{"start", "s"};
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

        for (Player p : onlinePlayers)
            setupPlayer(p);

        gameManager.start();

        ScoreboardManager.updateAllPlayerScoreboards();
    }

    @Override
    public String getDescription() {
        return ChatColor.RED + "" + ChatColor.BOLD + "COMMANDE OPERATEUR" + ChatColor.WHITE + " commmence la partie.";
    }

    private void setupPlayer(Player player) {
        PlayerUtil.resetPlayerStates(player);
        player.setGameMode(GameMode.SURVIVAL);
        player.teleport(WorldUtil.getRandomSpawnLocation().getLocation());
        setupInventory(player.getInventory());
    }

    private void setupInventory(PlayerInventory inv) {
        inv.setHelmet(new ItemBuilder(Material.DIAMOND_HELMET).addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 2).build());
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
        inv.addItem(new ItemStack(Material.DIAMOND_PICKAXE));
    }


}
