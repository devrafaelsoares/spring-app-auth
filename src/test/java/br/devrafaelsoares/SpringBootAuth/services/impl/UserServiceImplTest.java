package br.devrafaelsoares.SpringBootAuth.services.impl;

import br.devrafaelsoares.SpringBootAuth.domain.user.User;
import br.devrafaelsoares.SpringBootAuth.exceptions.user.UserNotFoundException;
import br.devrafaelsoares.SpringBootAuth.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@DisplayName("UserService Implementation Test's")
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User userTest;

    @BeforeEach
    void setUp() {

        userTest = User
                .builder()
                    .name("Toby")
                    .email("toby@email.com")
                    .username("toby")
                    .isEnabled(false)
                    .password(null)
                .build();

    }

    @Test
    @DisplayName("Should return the user based on the username entered")
    void UserServiceImpl_findUserByUsername_should_return_the_user_based_on_the_username_entered() {

        when(userRepository.findByUsername(any())).thenReturn(Optional.ofNullable(userTest));

        User userFound = userService.findUserByUsername(userTest.getUsername());

        assertEquals(userTest.getUsername(), userFound.getUsername());
        assertNotNull(userFound);
    }

    @Test
    @DisplayName("Should throw a UserNotFoundException if the username does not match any user")
    void UserServiceImpl_findUserByUsername_should_throw_a_UserNotFoundException_if_the_username_does_not_match_any_user() {

        when(userRepository.findByUsername(any())).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> userRepository.findByUsername("Usuário inexistente"));
    }

    @Test
    @DisplayName("Should return the user based on the id entered")
    void UserServiceImpl_findUserById_should_return_the_user_based_on_the_id_entered() {

        when(userRepository.findById(any())).thenReturn(Optional.ofNullable(userTest));

        User userFound = userService.findUserById(userTest.getId());

        assertEquals(userTest.getId(), userFound.getId());
        assertNotNull(userFound);
    }

    @Test
    @DisplayName("Should throw a UserNotFoundException if the id does not match any user")
    void UserServiceImpl_findUserById_should_throw_a_UserNotFoundException_if_the_id_does_not_match_any_user() {

        when(userRepository.findById(any())).thenThrow(UserNotFoundException.class);

        assertThrows(UserNotFoundException.class, () -> userRepository.findById(UUID.randomUUID()));
    }


    @Test
    @DisplayName("Should return true if the user exists with the given username")
    void UserServiceImpl_isExistsUserByUsername_should_return_true_if_the_user_exists_with_the_given_username() {

        when(userRepository.existsByUsername(any())).thenReturn(true);

        boolean isExistsUser = userService.isExistsUserByUsername(userTest.getUsername());

        assertTrue(isExistsUser);
    }

    @Test
    @DisplayName("Should return true if the user exists with the given email")
    void UserServiceImpl_isExistsUserByEmail_should_return_true_if_the_user_exists_with_the_given_email() {

        when(userRepository.existsByEmail(any())).thenReturn(true);

        boolean isExistsUser = userService.isExistsUserByEmail(userTest.getEmail());

        assertTrue(isExistsUser);

    }

    @Test
    @DisplayName("Should update the user's isEnable status and return true")
    void UserServiceImpl_updateIsEnableUser_should_update_the_user_isEnable_status_and_return_true() {

        User userUpdatedTest = userTest;

        when(userRepository.save(any())).thenReturn(userUpdatedTest);

        userService.updateIsEnableUser(userUpdatedTest, true);

        assertTrue(userUpdatedTest.isEnabled());
    }

    @Test
    @DisplayName("Should return a user from a UserDetails instance from the given username")
    void UserService_loadUserByUsername_should_return_a_user_from_a_UserDetails_instance_from_the_given_username() {

        when(userRepository.findByUsername(any())).thenReturn(Optional.ofNullable(userTest));

        UserDetails userFound = userService.loadUserByUsername(userTest.getUsername());

        assertInstanceOf(UserDetails.class, userFound);
    }
}