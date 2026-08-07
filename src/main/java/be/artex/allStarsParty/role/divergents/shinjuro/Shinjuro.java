package be.artex.allStarsParty.role.divergents.shinjuro;

import be.artex.allStarsParty.api.role.Aura;
import be.artex.allStarsParty.api.role.Role;
import be.artex.allStarsParty.api.role.Side;
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
    public @NotNull String getDescription() {
        return "";
    }

    @Override
    public @NotNull Aura getAura() {
        return Aura.MOYENNE;
    }
}
