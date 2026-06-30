package be.artex.allStarsParty.registry;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.manager.RoleManager;
import be.artex.allStarsParty.role.rewrited.DS.muzan.Muzan;
import be.artex.allStarsParty.role.rewrited.MHA.all_might.AllMight;
import be.artex.allStarsParty.role.rewrited.NS.sasuke.Sasuke;

public class RoleRegistry {
    public static RoleManager roleManager = new RoleManager();

    public static final Role ALL_MIGHT = roleManager.registerRole(new AllMight());
}
