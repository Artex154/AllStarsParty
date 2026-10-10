package be.artex.AllStarsParty.role.autre.minato.raijinKunai;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RaijinKunaiHolder {
    private final Block block;
    private final Player player;
    private final int markCount;

    public RaijinKunaiHolder(@NotNull Block block, int markCount) {
        this.block = block;
        this.player = null;
        this.markCount = markCount;
    }

    public RaijinKunaiHolder(@NotNull Player player, int markCount) {
        this.block = null;
        this.player = player;
        this.markCount = markCount;
    }

    public int getMarkCount() {
        return markCount;
    }

    @Nullable
    public Block getBlock() {
        return block;
    }

    @Nullable
    public Player getPlayer() {
        return player;
    }
}
