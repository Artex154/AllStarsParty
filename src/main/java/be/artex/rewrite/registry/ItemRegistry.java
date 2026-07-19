package be.artex.rewrite.registry;

import be.artex.rewrite.api.item.CustomItem;
import be.artex.rewrite.role.antagoniste.katarina.Dague;
import be.artex.rewrite.role.antagoniste.katarina.Shunpo;

public class ItemRegistry {
    public static final CustomItem KATARINA_DAGUE = new Dague();
    public static final CustomItem KATARINA_SHUNPO = new Shunpo();

    public static void registerItems() {
        KATARINA_DAGUE.register();
        KATARINA_SHUNPO.register();
    }
}
