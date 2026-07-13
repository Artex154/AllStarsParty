package be.artex.rewrite.registry;

import be.artex.rewrite.api.role.Role;
import be.artex.rewrite.role.A;
import be.artex.rewrite.role.B;

public class RoleRegistry {
    public static final Role ROLE_A = new A();
    public static final Role ROLE_B = new B();

    public static void registerRoles() {
        ROLE_A.register();
        ROLE_B.register();
    }
}
