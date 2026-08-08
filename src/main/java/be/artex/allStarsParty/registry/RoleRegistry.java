package be.artex.allStarsParty.registry;

import be.artex.allStarsParty.api.role.Role;
import be.artex.allStarsParty.role.antagoniste.akaza.Akaza;
import be.artex.allStarsParty.role.divergents.light.Light;
import be.artex.allStarsParty.role.divergents.sasuke.Sasuke;
import be.artex.allStarsParty.role.protagonistes.bakugo.Bakugo;
import be.artex.allStarsParty.role.protagonistes.gyomei.Gyomei;
import be.artex.allStarsParty.role.protagonistes.mrjack.MrJack;
import be.artex.allStarsParty.role.antagoniste.katarina.Katarina;
import be.artex.allStarsParty.role.solo.LGB.LGB;
import be.artex.allStarsParty.role.solo.malenia.Malenia;

public class RoleRegistry {
    public static final Role KATARINA = new Katarina();
    public static final Role MR_JACK = new MrJack();
    public static final Role MALENIA = new Malenia();
    public static final Role LGB = new LGB();
    public static final Role AKAZA = new Akaza();
    public static final Role LIGHT = new Light();
    public static final Role GYOMEI = new Gyomei();
    public static final Role SASUKE = new Sasuke();
    public static final Role BAKUGO = new Bakugo();

    public static void registerRoles() {
        //KATARINA.register();
        //MR_JACK.register();
        //MALENIA.register();
        //LGB.register();
        //AKAZA.register();
        //LIGHT.register();
        GYOMEI.register();
        //SASUKE.register();
        //BAKUGO.register();
    }
}
