package be.artex.AllStarsParty.registry;

import be.artex.AllStarsParty.api.role.Role;
import be.artex.AllStarsParty.role.antagoniste.akaza.Akaza;
import be.artex.AllStarsParty.role.autre.minato.Minato;
import be.artex.AllStarsParty.role.divergents.light.Light;
import be.artex.AllStarsParty.role.divergents.sasuke.Sasuke;
import be.artex.AllStarsParty.role.protagonistes.bakugo.Bakugo;
import be.artex.AllStarsParty.role.protagonistes.gyomei.Gyomei;
import be.artex.AllStarsParty.role.protagonistes.mrjack.MrJack;
import be.artex.AllStarsParty.role.antagoniste.katarina.Katarina;
import be.artex.AllStarsParty.role.solo.LGB.LGB;
import be.artex.AllStarsParty.role.solo.malenia.Malenia;

public class RoleRegistry {
    public static void registerRoles() {
        //KATARINA.register();
        //MR_JACK.register();
        //MALENIA.register();
        //LGB.register();
        //AKAZA.register();
        //LIGHT.register();
        //GYOMEI.register();
        Role.register(Minato.class);
        //BAKUGO.register();
        Role.register(Minato.class);
    }
}
