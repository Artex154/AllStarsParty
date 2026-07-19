package be.artex.rewrite.registry;

import be.artex.rewrite.api.role.Role;
import be.artex.rewrite.role.MrJack;
import be.artex.rewrite.role.antagoniste.katarina.Katarina;

public class RoleRegistry {
    public static final Role KATARINA = new Katarina();
    public static final Role MR_JACK = new MrJack();

    public static void registerRoles() {
        KATARINA.register();
        MR_JACK.register();
    }
}
