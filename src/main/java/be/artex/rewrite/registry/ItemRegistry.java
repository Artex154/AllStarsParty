package be.artex.rewrite.registry;

import be.artex.rewrite.api.item.CustomItem;
import be.artex.rewrite.role.antagoniste.akaza.Boussole;
import be.artex.rewrite.role.antagoniste.katarina.Dague;
import be.artex.rewrite.role.antagoniste.katarina.Shunpo;
import be.artex.rewrite.role.divergents.Deathnote;
import be.artex.rewrite.role.protagonistes.gyomei.Kusarigama;
import be.artex.rewrite.role.protagonistes.mrjack.Costumes;
import be.artex.rewrite.role.solo.malenia.WaterfowlDance;

public class ItemRegistry {
    public static final CustomItem KATARINA_DAGUE = new Dague();
    public static final CustomItem KATARINA_SHUNPO = new Shunpo();
    public static final CustomItem JACK_COSTUME = new Costumes();
    public static final CustomItem MALENIA_DANCE = new WaterfowlDance();
    public static final CustomItem AKAZA_COMPASS = new Boussole();
    public static final CustomItem LIGHT_DEATHNOTE = new Deathnote();
    public static final CustomItem GYOMEI_KUSARIGAMA = new Kusarigama();

    public static void registerItems() {
        KATARINA_DAGUE.register();
        KATARINA_SHUNPO.register();
        JACK_COSTUME.register();
        MALENIA_DANCE.register();
        AKAZA_COMPASS.register();
        LIGHT_DEATHNOTE.register();
        GYOMEI_KUSARIGAMA.register();
    }
}
