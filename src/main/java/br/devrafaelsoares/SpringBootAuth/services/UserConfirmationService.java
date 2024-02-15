package br.devrafaelsoares.SpringBootAuth.services;

import br.devrafaelsoares.SpringBootAuth.domain.user.UserConfirmation;
import br.devrafaelsoares.SpringBootAuth.domain.user.User;
import br.devrafaelsoares.SpringBootAuth.exceptions.user.UserConfirmationTokenException;

import java.util.UUID;

public interface UserConfirmationService {

    UserConfirmation findConfirmationUserByToken(UUID token) throws UserConfirmationTokenException;

    UserConfirmation saveConfirmationUser(User user);

    void deleteConfirmationUser(UserConfirmation confirmationUser);

}
