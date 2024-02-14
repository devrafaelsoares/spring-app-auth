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

    private static final String USER_NAME_THAT_EXIST = "Toby";

    private static final String USER_NAME_THAT_NOT_EXIST = "David";

    private static final String USER_EMAIL_THAT_EXIST = "toby@email.com";

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

        when(userRepository.findByUsername(USER_NAME_THAT_EXIST)).thenReturn(Optional.ofNullable(userTest));

        User userFound = userService.findUserByUsername(USER_NAME_THAT_EXIST);

        assertEquals(userTest.getUsername(), userFound.getUsername());
        assertNotNull(userFound);
    }

    @Test
    @DisplayName("Should throw a UserNotFoundException if the username does not match any user")
    void UserServiceImpl_findUserByUsername_should_throw_a_UserNotFoundException_if_the_username_does_not_match_any_user() {

        when(userRepository.findByUsername(USER_NAME_THAT_NOT_EXIST)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(
                UserNotFoundException.class,
                () -> userService.findUserByUsername(USER_NAME_THAT_NOT_EXIST)
        );

        assertEquals("Usuário não encontrado", exception.getMessage());
        assertEquals(UserNotFoundException.class, exception.getClass());

    }

    @Test
    @DisplayName("Should return the user based on the id entered")
    void UserServiceImpl_findUserById_should_return_the_user_based_on_the_id_entered() {

        when(userRepository.findById(userTest.getId())).thenReturn(Optional.ofNullable(userTest));

        User userFound = userService.findUserById(userTest.getId());

        assertEquals(userTest.getId(), userFound.getId());
        assertNotNull(userFound);
    }

    @Test
    @DisplayName("Should throw a UserNotFoundException if the id does not match any user")
    void UserServiceImpl_findUserById_should_throw_a_UserNotFoundException_if_the_id_does_not_match_any_user() {

        when(userRepository.findById(UUID.randomUUID())).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.findUserById(UUID.randomUUID()));

        assertEquals("Usuário não encontrado", exception.getMessage());
        assertEquals(UserNotFoundException.class, exception.getClass());

    }


    @Test
    @DisplayName("Should return true if the user exists with the given username")
    void UserServiceImpl_isExistsUserByUsername_should_return_true_if_the_user_exists_with_the_given_username() {

        when(userRepository.existsByUsername(USER_NAME_THAT_EXIST)).thenReturn(true);

        boolean isExistsUser = userService.isExistsUserByUsername(USER_NAME_THAT_EXIST);

        assertTrue(isExistsUser);
    }

    @Test
    @DisplayName("Should return true if the user exists with the given email")
    void UserServiceImpl_isExistsUserByEmail_should_return_true_if_the_user_exists_with_the_given_email() {

        when(userRepository.existsByEmail(USER_EMAIL_THAT_EXIST)).thenReturn(true);

        boolean isExistsUser = userService.isExistsUserByEmail(USER_EMAIL_THAT_EXIST);

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

        when(userRepository.findByUsername(USER_NAME_THAT_EXIST)).thenReturn(Optional.ofNullable(userTest));

        UserDetails userFound = userService.loadUserByUsername(USER_NAME_THAT_EXIST);

        assertInstanceOf(UserDetails.class, userFound);
    }
}