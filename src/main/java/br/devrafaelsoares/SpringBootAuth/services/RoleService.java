package br.devrafaelsoares.SpringBootAuth.services;

import br.devrafaelsoares.SpringBootAuth.domain.user.Role;
import br.devrafaelsoares.SpringBootAuth.exceptions.role.RoleNotFoundException;

public interface RoleService {

    boolean isExistsRoleByName(String name);

    Role findRoleByName(String name) throws RoleNotFoundException;
}
