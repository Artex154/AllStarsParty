package be.artex.allStarsParty.registry;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.manager.RoleManager;
import be.artex.allStarsParty.role.rewrited.muzan.Muzan;
import be.artex.allStarsParty.role.rewrited.shoto.Shoto;
import be.artex.allStarsParty.role.rewrited.tobirama.Tobirama;

public class RoleRegistry {
    public static RoleManager roleManager = new RoleManager();

    public static final Role TOBIRAMA = roleManager.registerRole(new Tobirama());
}
