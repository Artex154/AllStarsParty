package be.artex.allStarsParty.item_builder;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemBuilder {
    private final ItemStack stack;
    private final ItemMeta meta;

    private String name = "";
    private int amount = 1;
    private int durability = 0;

    private ItemFlag[] flags = {};

    private final List<String> lore = new ArrayList<>();
    private final List<EnchantmentHolder> enchants = new ArrayList<>();

    public ItemBuilder(ItemStack stack) {
        this.stack = stack;
        this.meta = this.stack.getItemMeta();
    }

    public ItemBuilder(Material material) {
        this.stack = new ItemStack(material);
        this.meta = this.stack.getItemMeta();
    }

    public ItemBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder lore(String... strings) {
        this.lore.addAll(Arrays.asList(strings));
        return this;
    }

    public ItemBuilder durability(int durability) {
        this.durability = durability;
        return this;
    }

    public ItemBuilder addEnchants(EnchantmentHolder... enchants) {
        this.enchants.addAll(Arrays.asList(enchants));
        return this;
    }

    public ItemBuilder addEnchant(Enchantment type, int level) {
        this.enchants.add(new EnchantmentHolder(type, level));
        return this;
    }

    public ItemBuilder itemFlags(ItemFlag... flags) {
        this.flags = flags;
        return this;
    }

    public ItemStack build() {
        this.meta.setDisplayName(this.name);
        this.meta.setLore(this.lore);
        this.meta.addItemFlags(this.flags);

        this.enchants.forEach((enchant) ->
                this.meta.addEnchant(enchant.getType(), enchant.getLevel(), true));

        this.stack.setAmount(this.amount);
        this.stack.setItemMeta(this.meta);

       if (this.durability != 0)
           this.stack.setDurability((short) this.durability);

        return this.stack;
    }
}
