package be.artex.allStarsParty.role.divergents.shinjuro;

import be.artex.allStarsParty.api.role.Aura;
import be.artex.allStarsParty.api.role.Role;
import be.artex.allStarsParty.api.role.Side;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

public class Shinjuro extends Role {
    @Override
    public @NotNull String getName() {
        return "Shinjuro";
    }

    @Override
    public @NotNull Side getSide() {
        return Side.DIVERGENTS;
    }

    @Override
    public @NotNull TextComponent descriptionInitialization() {
        return new TextComponent("a");
    }

    @Override
    public @NotNull Aura getAura() {
        return Aura.MOYENNE;
    }
}
