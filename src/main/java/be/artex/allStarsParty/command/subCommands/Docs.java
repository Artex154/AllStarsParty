package be.artex.allStarsParty.command.subCommands;

import be.artex.allStarsParty.command.SubCommand;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Docs extends SubCommand {
    @Override
    public String getArgument() {
        return "docs";
    }

    @Override
    public void whenCalled(Player sender) {
        TextComponent component = new TextComponent(ChatColor.DARK_AQUA + "" + ChatColor.BOLD + " All Stars Party" + ChatColor.GRAY + " ▏" + ChatColor.WHITE + " Cliquez sur ce message pour ouvrir la documentation sur votre opérateur.");
        component.setClickEvent(new ClickEvent(
                ClickEvent.Action.OPEN_URL,
                "https://artexs-plug-ins.gitbook.io/all-stars-party"
        ));

        sender.spigot().sendMessage(component);
    }
}
