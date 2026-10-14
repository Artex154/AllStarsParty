package be.artex.AllStarsParty.role.autre.minato.raijinKunai;

import be.artex.AllStarsParty.api.item.CustomItem;
import be.artex.AllStarsParty.api.itemBuilder.ItemBuilder;
import be.artex.AllStarsParty.api.message.Message;
import be.artex.AllStarsParty.api.role.Role;
import be.artex.AllStarsParty.role.autre.minato.Minato;
import be.artex.AllStarsParty.util.PlayerUtil;
import com.google.common.collect.Lists;
import com.lunarclient.apollo.Apollo;
import com.lunarclient.apollo.common.location.ApolloLocation;
import com.lunarclient.apollo.module.hologram.Hologram;
import com.lunarclient.apollo.module.hologram.HologramModule;
import com.lunarclient.apollo.player.ApolloPlayer;
import com.lunarclient.apollo.recipients.Recipients;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class RaijinKunai extends CustomItem {
    private final ItemStack STACK = new ItemBuilder(Material.NETHER_STAR).name(ChatColor.GOLD + "" + ChatColor.BOLD + "Hiraishin no Jutsu").build();

    private static final HologramModule hologramModule = Apollo.getModuleManager()
            .getModule(HologramModule.class);

    @Override
    public ItemStack getStack() {
        return STACK.clone();
    }

    @Override
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.PHYSICAL)
            return;

        Player player = event.getPlayer();
        Role role = Role.getPlayerRole(player);

        if (!(role instanceof Minato)) {
            player.setItemInHand(new ItemStack(Material.AIR));
            player.updateInventory();
            return;
        }

        Minato r = (Minato) role;

        if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK) {
            int markCount = r.lastMarkCount + 1;
            r.lastMarkCount = markCount;

            Player pl = PlayerUtil.getPlayerTargetEntity(player, 50);

            if (pl != null) {
                r.consumeChakra(2000, 200, player);

                for (RaijinKunaiHolder mark : r.marks) {
                    if (mark.getPlayer() == null)
                        continue;

                    if (mark.getPlayer().equals(pl)) {
                        player.sendMessage(Message.info(ChatColor.YELLOW + pl.getName() + ChatColor.WHITE + " est déjà " + ChatColor.YELLOW + "marqué" + ChatColor.WHITE + " par votre " + ChatColor.YELLOW + "sceau" + ChatColor.WHITE + "."));
                        return;
                    }
                }

                if (r.marks.size() >= 3) {
                    RaijinKunaiHolder oldHolder = r.marks.poll();

                    if (oldHolder != null) {
                        if (oldHolder.getBlock() != null)
                            removeBlockMark(player, oldHolder.getMarkCount());
                        else if (oldHolder.getPlayer() != null)
                            PlayerUtil.resetLunarNametag(oldHolder.getPlayer());
                    }
                }

                PlayerUtil.setLunarNametagForAnotherPlayer(player, pl, ChatColor.DARK_GRAY + "» " + ChatColor.YELLOW + ChatColor.BOLD + markCount + "e" + ChatColor.YELLOW + " sceau" + ChatColor.DARK_GRAY + " «");

                r.marks.add(new RaijinKunaiHolder(pl, markCount));

                player.sendMessage(Message.info("Vous avez " + ChatColor.YELLOW + "marqué " + pl.getName() + ChatColor.WHITE + " de votre " + ChatColor.YELLOW + "sceau" + ChatColor.WHITE + "."));
                player.playSound(player.getLocation(), Sound.STEP_STONE, 1F, 1F);

                return;
            }

            Block block = player.getTargetBlock((Set<Material>) null, 50);

            if (block != null) {
                r.consumeChakra(2000, 200, player);

                for (RaijinKunaiHolder mark : r.marks) {
                    if (mark.getBlock() == null)
                        continue;

                    if (mark.getBlock().equals(block)) {
                        player.sendMessage(Message.info(ChatColor.WHITE + "Ce bloc est déjà " + ChatColor.YELLOW + "marqué" + ChatColor.WHITE + " par votre " + ChatColor.YELLOW + "sceau" + ChatColor.WHITE + "."));
                        return;
                    }
                }

                RaijinKunaiHolder holder = new RaijinKunaiHolder(block, markCount);

                if (r.marks.size() >= 3) {
                    RaijinKunaiHolder oldHolder = r.marks.poll();

                    if (oldHolder != null) {
                        if (oldHolder.getBlock() != null) {
                            removeBlockMark(player, oldHolder.getMarkCount());
                        } else if (oldHolder.getPlayer() != null)
                            PlayerUtil.resetLunarNametag(oldHolder.getPlayer());
                    }
                }

                r.marks.offer(holder);

                markBlock(player, markCount, block.getLocation());

                player.sendMessage(Message.info("Vous avez " + ChatColor.YELLOW + "marqué ce bloc" + ChatColor.WHITE + " de votre " + ChatColor.YELLOW + "sceau" + ChatColor.WHITE + "."));
                player.playSound(player.getLocation(), Sound.STEP_STONE, 1F, 1F);

                return;
            }

            player.sendMessage(Message.info("Vous ne visez aucun bloc / joueur"));

            return;
        }

        Player pl = PlayerUtil.getPlayerTargetEntity(player, 50);

        if (pl != null) {
            RaijinKunaiHolder holder = r.getMarkHolder(pl);

            if (holder == null) {
                player.sendMessage(Message.info("Ce joueur ne dispose pas de" + ChatColor.YELLOW + " sceau" + ChatColor.WHITE + "."));
                return;
            }

            r.consumeChakra(1500, 150, player);
            teleport(player, pl.getLocation(), holder.getMarkCount());

            return;
        }

        Block block = player.getTargetBlock((Set<Material>) null, 50);

        for (RaijinKunaiHolder holder : r.marks) {
            if (holder.getBlock() == null)
                continue;

            if (holder.getBlock().getWorld().equals(block.getWorld()) && holder.getBlock().getLocation().distance(block.getLocation()) <= 3.0) {
                r.consumeChakra(1500, 150, player);
                teleport(player, block.getLocation().add(0.5, 1, 0.5), holder.getMarkCount());

                return;
            }
        }
    }

    public void markBlock(Player minato, int markCount, Location location) {
        Optional<ApolloPlayer> apolloViewer = Apollo.getPlayerManager()
                .getPlayer(minato.getUniqueId());

        if (!apolloViewer.isPresent())
            return;

        Recipients recipients = Recipients.of(Collections.singletonList(apolloViewer.get()));

        String id = "minato_" + minato.getName().toLowerCase() + "_" + markCount;

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        hologramModule.displayHologram(
                recipients,
                Hologram.builder()
                    .id(id)
                    .location(ApolloLocation.builder()
                        .world(location.getWorld().getName())
                        .x(x + 0.5)
                        .y(y + 0.5)
                        .z(z + 0.5)
                        .build()
                    )
                    .lines(Lists.newArrayList(
                        Component.text()
                                .content(markCount + "e sceau")
                                .color(NamedTextColor.YELLOW)
                                .decorate(TextDecoration.BOLD)
                                .build(),
                        Component.text()
                                .content(x + ", " + y + ", " + z)
                                .build()
                    ))
                    .showThroughWalls(false)
                    .showShadow(true)
                    .showBackground(false)
                    .build()
        );
    }

    private void removeBlockMark(Player player, int markCount) {
        Optional<ApolloPlayer> apolloPlayer = Apollo.getPlayerManager()
                .getPlayer(player.getUniqueId());

        if (!apolloPlayer.isPresent())
            return;

        Recipients recipients = Recipients.of(
                Collections.singletonList(apolloPlayer.get())
        );

        String id = "minato_" + player.getName().toLowerCase() + "_" + markCount;

        hologramModule.removeHologram(recipients, id);
    }

    private void teleport(Player player, Location location, int markCount) {
        player.teleport(location);
        player.getWorld().playSound(location, Sound.ENDERMAN_TELEPORT, 1f, 1f);
        player.sendMessage(Message.info("Vous vous téléportez sur votre " + ChatColor.YELLOW + ChatColor.BOLD + markCount + "e sceau" + ChatColor.WHITE + "."));
    }
}
