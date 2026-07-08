package be.artex.allStarsParty.registry;

import be.artex.allStarsParty.api.Role;
import be.artex.allStarsParty.manager.RoleManager;
import be.artex.allStarsParty.role.rewrited.antagoniste.muzan.Muzan;
import be.artex.allStarsParty.role.rewrited.antagoniste.obito.Obito;
import be.artex.allStarsParty.role.rewrited.antagoniste.sasuke.Sasuke;
import be.artex.allStarsParty.role.rewrited.antagoniste.tomura.Tomura;
import be.artex.allStarsParty.role.rewrited.duo.jigoZen.jigoro.Jigoro;
import be.artex.allStarsParty.role.rewrited.duo.jigoZen.zenitsu.Zenitsu;
import be.artex.allStarsParty.role.rewrited.protagoniste.all_might.AllMight;
import be.artex.allStarsParty.role.rewrited.protagoniste.shoto.Shoto;
import be.artex.allStarsParty.role.rewrited.protagoniste.tobirama.Tobirama;

public class RoleRegistry {
    public static RoleManager roleManager = new RoleManager();

    public static final Role ZENITSU = new Zenitsu();
    public static final Role JIGORO = new Jigoro();
    public static final Role SHOTO = new Shoto();
    public static final Role TOBIRAMA = new Tobirama().register();
    public static final Role MUZAN = new Muzan().register();
}
