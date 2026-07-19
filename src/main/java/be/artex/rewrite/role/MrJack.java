package be.artex.rewrite.role;

import be.artex.rewrite.api.role.Role;
import be.artex.rewrite.api.role.Side;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

public class MrJack extends Role {
    @Override
    public @NotNull String getName() {
        return "Mr Jack";
    }

    @Override
    public @NotNull Side getSide() {
        return Side.PROTAGONISTES;
    }

    @Override
    public @NotNull String getDescription() {
        return "a";
    }
}
