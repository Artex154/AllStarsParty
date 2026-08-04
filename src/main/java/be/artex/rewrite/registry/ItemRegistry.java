package be.artex.rewrite.registry;

import be.artex.rewrite.api.item.CustomItem;
import be.artex.rewrite.role.antagoniste.akaza.Boussole;
import be.artex.rewrite.role.antagoniste.katarina.Dague;
import be.artex.rewrite.role.antagoniste.katarina.Shunpo;
import be.artex.rewrite.role.divergents.light.Deathnote;
import be.artex.rewrite.role.divergents.sasuke.Amaterasu;
import be.artex.rewrite.role.divergents.sasuke.Rinnegan;
import be.artex.rewrite.role.protagonistes.bakugo.Cluster;
import be.artex.rewrite.role.protagonistes.bakugo.Propulsion;
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
    public static final CustomItem SASUKE_AMATERASU = new Amaterasu();
    public static final CustomItem SASUKE_RINNEGAN = new Rinnegan();
    public static final CustomItem BAKUGO_PROPULSION = new Propulsion();
    public static final CustomItem BAKUGO_CLUSTER = new Cluster();

    public static void registerItems() {
        KATARINA_DAGUE.register();
        KATARINA_SHUNPO.register();
        JACK_COSTUME.register();
        MALENIA_DANCE.register();
        AKAZA_COMPASS.register();
        LIGHT_DEATHNOTE.register();
        GYOMEI_KUSARIGAMA.register();
        SASUKE_AMATERASU.register();
        SASUKE_RINNEGAN.register();
        BAKUGO_PROPULSION.register();
        BAKUGO_CLUSTER.register();
    }
}
