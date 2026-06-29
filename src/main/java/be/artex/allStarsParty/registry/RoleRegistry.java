package be.artex.allStarsParty.registry;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.manager.RoleManager;
import be.artex.allStarsParty.role.old.solo.hisoka.Hisoka;
import be.artex.allStarsParty.role.rewrited.all_might.AllMight;
import be.artex.allStarsParty.role.rewrited.muzan.Muzan;
import be.artex.allStarsParty.role.rewrited.sasuke.Sasuke;
import be.artex.allStarsParty.role.rewrited.shoto.Shoto;
import be.artex.allStarsParty.role.rewrited.tobirama.Tobirama;
import be.artex.allStarsParty.role.rewrited.zenitsu.Zenitsu;

public class RoleRegistry {
    public static RoleManager roleManager = new RoleManager();

    public static final Role ALL_MIGHT = roleManager.registerRole(new AllMight());
}
