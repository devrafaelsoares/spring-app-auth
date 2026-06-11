package br.devrafaelsoares.SpringBootAuth.services.impl;

import br.devrafaelsoares.SpringBootAuth.domain.user.Role;
import br.devrafaelsoares.SpringBootAuth.exceptions.role.RoleNotFoundException;
import br.devrafaelsoares.SpringBootAuth.repositories.RoleRepository;
import br.devrafaelsoares.SpringBootAuth.services.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public boolean isExistsRoleByName(String name) {
        return roleRepository.existsByName(name);
    }

    @Override
    public Role findRoleByName(String name) throws RoleNotFoundException {
        log.info("ROLE: " + name);
        return roleRepository.findByName(name)
                .orElseThrow(() -> new RoleNotFoundException("Permissão informada não existe"));
    }
}
