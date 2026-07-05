package be.artex.allStarsParty.registry;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.manager.RoleManager;
import be.artex.allStarsParty.role.rewrited.duo.jigoZen.jigoro.Jigoro;
import be.artex.allStarsParty.role.rewrited.duo.jigoZen.zenitsu.Zenitsu;
import be.artex.allStarsParty.role.rewrited.protagoniste.all_might.AllMight;

public class RoleRegistry {
    public static RoleManager roleManager = new RoleManager();

    public static final Role ZENITSU = new Zenitsu();
    public static final Role JIGORO = new Jigoro();
}
