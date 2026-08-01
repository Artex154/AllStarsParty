package be.artex.rewrite.registry;

import be.artex.rewrite.api.item.CustomItem;
import be.artex.rewrite.role.antagoniste.katarina.Dague;
import be.artex.rewrite.role.antagoniste.katarina.Shunpo;
import be.artex.rewrite.role.protagonistes.mrjack.Costumes;
import be.artex.rewrite.role.solo.malenia.WaterfowlDance;

public class ItemRegistry {
    public static final CustomItem KATARINA_DAGUE = new Dague();
    public static final CustomItem KATARINA_SHUNPO = new Shunpo();
    public static final CustomItem JACK_COSTUME = new Costumes();
    public static final CustomItem MALENIA_DANCE = new WaterfowlDance();

    public static void registerItems() {
        KATARINA_DAGUE.register();
        KATARINA_SHUNPO.register();
        JACK_COSTUME.register();
        MALENIA_DANCE.register();
    }
}
