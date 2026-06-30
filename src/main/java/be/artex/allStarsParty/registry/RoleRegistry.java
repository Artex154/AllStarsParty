package be.artex.allStarsParty.registry;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.manager.RoleManager;
import be.artex.allStarsParty.role.old.DS.zenitsu.Zenitsu;
import be.artex.allStarsParty.role.rewrited.protagoniste.all_might.AllMight;

public class RoleRegistry {
    public static RoleManager roleManager = new RoleManager();

    public static final Role ALL_MIGHT = roleManager.registerRole(new AllMight());
    public static final Role ZENITSU = roleManager.registerRole(new Zenitsu());
}
