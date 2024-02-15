package br.devrafaelsoares.SpringBootAuth.services.impl;

import br.devrafaelsoares.SpringBootAuth.domain.user.UserConfirmation;
import br.devrafaelsoares.SpringBootAuth.domain.user.User;
import br.devrafaelsoares.SpringBootAuth.exceptions.user.UserConfirmationTokenException;
import br.devrafaelsoares.SpringBootAuth.repositories.UserConfirmationRepository;
import br.devrafaelsoares.SpringBootAuth.services.UserConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserConfirmationServiceImpl implements UserConfirmationService {

    private final UserConfirmationRepository confirmationUserRepository;

    @Override
    public UserConfirmation findConfirmationUserByToken(
            UUID token
    ) throws UserConfirmationTokenException {

        return confirmationUserRepository.findByToken(token)
                .orElseThrow(() -> new UserConfirmationTokenException("Token inválido ou expirado"));
    }

    @Override
    public UserConfirmation saveConfirmationUser(
            User user
    ) {
        return confirmationUserRepository.save(
                UserConfirmation
                        .builder()
                            .id(UUID.randomUUID())
                            .createdDate(LocalDateTime.now().atZone(ZoneId.systemDefault()).toLocalDateTime())
                            .token(UUID.randomUUID())
                            .user(user)
                        .build());
    }

    @Override
    public void deleteConfirmationUser(
            UserConfirmation confirmationUser
    ) {
        confirmationUserRepository.delete(confirmationUser);
    }

}
