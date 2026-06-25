package be.artex.allStarsParty.registry;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.manager.RoleManager;
import be.artex.allStarsParty.role.DS.muzan.Muzan;
import be.artex.allStarsParty.role.MHA.shoto.Shoto;

public class RoleRegistry {
    public static RoleManager roleManager = new RoleManager();

    public static final Role MUZAN = roleManager.registerRole(new Muzan());
    public static final Role SHOTO = roleManager.registerRole(new Shoto());
}
