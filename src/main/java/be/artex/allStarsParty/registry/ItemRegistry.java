package be.artex.allStarsParty.registry;

import be.artex.allStarsParty.api.item.CustomItem;
import be.artex.allStarsParty.role.antagoniste.akaza.AirType;
import be.artex.allStarsParty.role.antagoniste.akaza.LCAB;
import be.artex.allStarsParty.role.antagoniste.katarina.Dague;
import be.artex.allStarsParty.role.antagoniste.katarina.Shunpo;
import be.artex.allStarsParty.role.divergents.light.Deathnote;
import be.artex.allStarsParty.role.divergents.sasuke.Amaterasu;
import be.artex.allStarsParty.role.divergents.sasuke.Rinnegan;
import be.artex.allStarsParty.role.protagonistes.bakugo.Cluster;
import be.artex.allStarsParty.role.protagonistes.bakugo.Propulsion;
import be.artex.allStarsParty.role.protagonistes.gyomei.Kusarigama;
import be.artex.allStarsParty.role.protagonistes.mrjack.Costumes;
import be.artex.allStarsParty.role.solo.malenia.WaterfowlDance;

public class ItemRegistry {
    public static final CustomItem KATARINA_DAGUE = new Dague();
    public static final CustomItem KATARINA_SHUNPO = new Shunpo();
    public static final CustomItem JACK_COSTUME = new Costumes();
    public static final CustomItem MALENIA_DANCE = new WaterfowlDance();
    public static final CustomItem AKAZA_LCAB = new LCAB();
    public static final CustomItem AKAZA_AIRTYPE = new AirType();
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
        AKAZA_LCAB.register();
        AKAZA_AIRTYPE.register();
        LIGHT_DEATHNOTE.register();
        GYOMEI_KUSARIGAMA.register();
        SASUKE_AMATERASU.register();
        SASUKE_RINNEGAN.register();
        BAKUGO_PROPULSION.register();
        BAKUGO_CLUSTER.register();
    }
}
