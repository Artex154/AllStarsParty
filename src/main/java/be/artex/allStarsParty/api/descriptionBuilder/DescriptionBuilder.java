package be.artex.allStarsParty.api.descriptionBuilder;

import be.artex.allStarsParty.api.role.Role;
import be.artex.allStarsParty.api.role.Side;
import be.artex.allStarsParty.api.role.SidePropriety;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DescriptionBuilder {
    private final Role role;
    private boolean hasFireRes = false;
    private final List<HoverHolder> passifs = new ArrayList<>();
    private final List<HoverHolder> activables = new ArrayList<>();

    /**
     * Start of the builder.
     * @param role the role used to generate most of the base informations: name, side, effects.
     */
    public DescriptionBuilder(Role role) {
        this.role = role;
    }

    /**
     * @param has if yes or no, the role has fire resistance.
     * @return the builder to continue the chain.
     */
    public DescriptionBuilder hasFireRes(boolean has) {
        hasFireRes = has;
        return this;
    }

    /**
     * @param holders list of HoverHolders to add the list of passives.
     * @return the builder to continue the chain.
     */
    public DescriptionBuilder passifs(HoverHolder... holders) {
        Collections.addAll(passifs, holders);
        return this;
    }

    /**
     * @param holders list of HoverHolders to add the list of activable abilities.
     * @return the builder to continue the chain.
     */
    public DescriptionBuilder activables(HoverHolder... holders) {
        Collections.addAll(activables, holders);
        return this;
    }

    /**
     * Builds the description with all given information.
     * @return the description.
     */
    public TextComponent build() {
        TextComponent description = new TextComponent(" ");
        Side roleSide = role.getSide();

        description.addExtra(ChatColor.GRAY + "Vous êtes " + roleSide.getColor() + ChatColor.BOLD + role.getName() + "\n");
        description.addExtra(ChatColor.GRAY + " Objectif:" + ChatColor.WHITE + " Vous devez gagner " + roleSide.getPropriety().getObjective());

        if (roleSide.getPropriety() == SidePropriety.TEAM)
            description.addExtra(roleSide.getColor() + roleSide.getName());

        description.addExtra(ChatColor.WHITE + ".");

        HoverHolder effets = getEffectHolder(role);

        if (effets != null)
            passifs.add(0, effets);

        if (!passifs.isEmpty()) {
            description.addExtra("\n \n" + ChatColor.GRAY + ChatColor.BOLD + "» Passifs:");

            passifs.forEach(p -> {
                description.addExtra("\n");
                description.addExtra(p.toComponent());
            });
        }

        if (!activables.isEmpty()) {
            description.addExtra("\n \n" + ChatColor.GRAY + ChatColor.BOLD + "» Compétences activables:");

            activables.forEach(a -> {
                description.addExtra("\n");
                description.addExtra(a.toComponent());
            });
        }

        return description;
    }


    private @Nullable HoverHolder getEffectHolder(Role role) {
        StringBuilder effectStrB = new StringBuilder();

        if (role.getBonusStrength() != 0)
            effectStrB.append(ChatColor.RED + "    Force " + (role.getBonusStrength() / 10));

        if (role.getBonusResistance() != 0)
            effectStrB.append(ChatColor.GRAY + "    Résistance " + (role.getBonusResistance() / 10));

        if (role.getBonusSpeed() != 0)
            effectStrB.append(ChatColor.YELLOW + "    Vitesse " + (role.getBonusSpeed() / 10));

        if (role.getBonusMaxHealth() != 0)
            effectStrB.append(ChatColor.LIGHT_PURPLE + "    " + ((role.getBonusMaxHealth() + 20) / 2) + " coeurs permanents  ");

        if (hasFireRes)
            effectStrB.append(ChatColor.GOLD + "    Résistance au feu");

        if (effectStrB.length() == 0)
            return null;

        effectStrB.append("\n ");
        effectStrB.insert(0, " \n" + ChatColor.DARK_GRAY + "   [" + ChatColor.GOLD + ChatColor.BOLD + "Effets" + ChatColor.DARK_GRAY + "]\n" + ChatColor.DARK_GRAY + " » " + ChatColor.WHITE + "Vous disposez de ces effets : \n");

        return new HoverHolder(
                ChatColor.DARK_GRAY + "  [" + ChatColor.GOLD + ChatColor.BOLD + "Effets" + ChatColor.DARK_GRAY + "]",
                String.valueOf(effectStrB)
        );
    }
}
