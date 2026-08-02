package be.artex.rewrite.registry;

import be.artex.rewrite.api.role.Role;
import be.artex.rewrite.role.antagoniste.akaza.Akaza;
import be.artex.rewrite.role.protagonistes.mrjack.MrJack;
import be.artex.rewrite.role.antagoniste.katarina.Katarina;
import be.artex.rewrite.role.solo.LGB.LGB;
import be.artex.rewrite.role.solo.malenia.Malenia;

public class RoleRegistry {
    public static final Role KATARINA = new Katarina();
    public static final Role MR_JACK = new MrJack();
    public static final Role MALENIA = new Malenia();
    public static final Role LGB = new LGB();
    public static final Role AKAZA = new Akaza();

    public static void registerRoles() {
        KATARINA.register();
        MR_JACK.register();
        MALENIA.register();
        LGB.register();
        AKAZA.register();
    }
}
