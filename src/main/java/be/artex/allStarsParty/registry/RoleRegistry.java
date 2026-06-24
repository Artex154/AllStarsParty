package be.artex.allStarsParty.registry;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.api.manager.RoleManager;
import be.artex.allStarsParty.role.DS.muzan.Muzan;

public class RoleRegistry {
    public static RoleManager roleManager = new RoleManager();

    public static final Role MUZAN = roleManager.registerRole(new Muzan());
}
