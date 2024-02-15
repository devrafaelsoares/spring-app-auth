package br.devrafaelsoares.SpringBootAuth.services.impl;

import br.devrafaelsoares.SpringBootAuth.domain.user.UserConfirmation;
import br.devrafaelsoares.SpringBootAuth.domain.user.Role;
import br.devrafaelsoares.SpringBootAuth.domain.user.User;
import br.devrafaelsoares.SpringBootAuth.exceptions.user.UserConfirmationTokenException;
import br.devrafaelsoares.SpringBootAuth.repositories.UserConfirmationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(SpringExtension.class)
@DisplayName("UserConfirmationService test's")
class UserConfirmationServiceImplTest {

    @InjectMocks
    private UserConfirmationServiceImpl userConfirmationService;

    @Mock
    private UserConfirmationRepository userConfirmationRepository;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private User userTest;

    private UserConfirmation userConfirmationTest;


    @BeforeEach
    void setUp() {

        userTest = User
                .builder()
                    .id(UUID.randomUUID())
                    .name("Toby")
                    .username("toby")
                    .email("toby@email.com")
                    .roles(List.of(Role.builder().name("ADMIN").build()))
                    .isEnabled(true)
                    .password(passwordEncoder.encode("toby"))
                .build();

        userConfirmationTest = UserConfirmation
                .builder()
                    .id(UUID.randomUUID())
                    .token(UUID.randomUUID())
                    .createdDate(LocalDateTime.now())
                    .user(userTest)
                .build();
    }

    @Test
    @DisplayName("Should return a UserConfirmation by user token")
    void UserConfirmationServiceImpl_findUserConfirmationByToken_should_return_a_UserConfirmation_by_user_token() {

        when(userConfirmationRepository.findByToken(any())).thenReturn(Optional.ofNullable(userConfirmationTest));

        UserConfirmation userConfirmationFound = userConfirmationService.findConfirmationUserByToken(userConfirmationTest.getToken());

        assertNotNull(userConfirmationFound);
        assertNotNull(userConfirmationFound.getUser());

    }

    @Test
    @DisplayName("Should throw a UserConfirmationTokenException if the token does not match any user confirmation")
    void UserConfirmationServiceImpl_findUserConfirmationByToken_should_throw_a_UserConfirmationTokenException_if_the_token_does_not_match_any_user_confirmation() {

        when(userConfirmationRepository.findByToken(any())).thenReturn(Optional.empty());

        UserConfirmationTokenException exception = assertThrows(
                UserConfirmationTokenException.class,
                () -> userConfirmationService.findConfirmationUserByToken(UUID.randomUUID())
        );

        assertEquals(UserConfirmationTokenException.class, exception.getClass());
        assertEquals("Token inválido ou expirado", exception.getMessage());

    }

    @Test
    @DisplayName("Should return a UserConfirmation based on the created user")
    void UserConfirmationServiceImpl_saveUserConfirmation_should_return_a_UserConfirmation_based_on_the_created_user() {

        when(userConfirmationRepository.save(any(UserConfirmation.class))).thenReturn(userConfirmationTest);

        UserConfirmation confirmationUserSaved = userConfirmationService.saveConfirmationUser(userTest);

        assertNotNull(confirmationUserSaved);
        assertEquals(userConfirmationTest.getUser().getName(), confirmationUserSaved.getUser().getName());

    }

    @Test
    @DisplayName("Should delete UserConfirmation")
    void UserConfirmationServiceImpl_deleteUserConfirmation_should_delete_UserConfirmation() {

        doNothing().when(userConfirmationRepository).delete(any(UserConfirmation.class));

        assertAll(() -> userConfirmationService.deleteConfirmationUser(userConfirmationTest));

    }
}