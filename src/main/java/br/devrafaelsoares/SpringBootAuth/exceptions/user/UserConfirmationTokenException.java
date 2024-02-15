package br.devrafaelsoares.SpringBootAuth.exceptions.user;

import jakarta.persistence.EntityNotFoundException;

public class UserConfirmationTokenException extends EntityNotFoundException {
    public UserConfirmationTokenException(String message) {
        super(message);
    }

}
