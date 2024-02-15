package br.devrafaelsoares.SpringBootAuth.exceptions.user;

import jakarta.persistence.EntityExistsException;

public class UserExistsException extends EntityExistsException {

    public UserExistsException(String message) {
        super(message);
    }

}
