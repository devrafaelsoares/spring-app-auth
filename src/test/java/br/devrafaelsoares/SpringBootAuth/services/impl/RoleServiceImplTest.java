package br.devrafaelsoares.SpringBootAuth.services.impl;

import br.devrafaelsoares.SpringBootAuth.domain.user.Role;
import br.devrafaelsoares.SpringBootAuth.exceptions.role.RoleNotFoundException;
import br.devrafaelsoares.SpringBootAuth.repositories.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role roleTest;

    private static final String ROLE_THAT_EXIST = "ADMIN";

    private static final String ROLE_THAT_NOT_EXIST = "OWNER";

    @BeforeEach
    void setUp() {

        roleTest = Role
                .builder()
                    .name("ADMIN")
                .build();

    }

    @Test
    @DisplayName("Should return true if the role exist with the given name")
    void RoleServiceImpl_isExistsRoleByName_should_return_true_if_the_role_exists_with_then_given_name_role() {

        when(roleRepository.existsByName(ROLE_THAT_EXIST)).thenReturn(true);

        boolean isExistsRole = roleService.isExistsRoleByName(ROLE_THAT_EXIST);

        assertTrue(isExistsRole);

    }

    @Test
    @DisplayName("Should return false if the role does not exist with the given name")
    void RoleServiceImpl_isExistsRoleByName_should_return_false_if_the_role_does_not_exist_with_the_given_name() {

        when(roleRepository.existsByName(ROLE_THAT_NOT_EXIST)).thenReturn(false);

        boolean isExistsRole = roleService.isExistsRoleByName(ROLE_THAT_NOT_EXIST);

        assertFalse(isExistsRole);

    }

    @Test
    @DisplayName("Should return the role based on the name entered")
    void RoleServiceImpl_findRoleByName_should_return_the_role_based_on_the_name_entered() {

        when(roleRepository.findByName(ROLE_THAT_EXIST)).thenReturn(Optional.ofNullable(roleTest));

        Role roleFound = roleService.findRoleByName(ROLE_THAT_EXIST);

        assertNotNull(roleFound);

    }

    @Test
    @DisplayName("Should throw a RoleNotFoundException if the name does not match any role")
    void RoleServiceImpl_findRoleByName_should_throw_a_RoleNotFoundException_if_the_name_does_not_match_any_role() {

        when(roleRepository.findByName(ROLE_THAT_NOT_EXIST)).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> roleService.findRoleByName(roleThatNotExist));

    }

}