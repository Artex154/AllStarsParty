package be.artex.allStarsParty.itemBuilder;

import org.bukkit.enchantments.Enchantment;

public class EnchantmentHolder {
    private final Enchantment type;
    private final int level;

    public EnchantmentHolder(Enchantment ench, int level) {
        this.type = ench;
        this.level = level;
    }

    public Enchantment getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }
}
