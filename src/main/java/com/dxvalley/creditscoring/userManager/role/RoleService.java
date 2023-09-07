package com.dxvalley.creditscoring.userManager.role;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();

    Role getRoleByName(String roleName);

}
