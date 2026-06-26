package be.artex.allStarsParty.registry;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.manager.RoleManager;
import be.artex.allStarsParty.role.rewrited.muzan.Muzan;
import be.artex.allStarsParty.role.rewrited.sasuke.Sasuke;
import be.artex.allStarsParty.role.rewrited.shoto.Shoto;
import be.artex.allStarsParty.role.rewrited.tobirama.Tobirama;

public class RoleRegistry {
    public static RoleManager roleManager = new RoleManager();

    public static final Role SASUKE = roleManager.registerRole(new Sasuke());
}
