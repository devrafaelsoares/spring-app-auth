package br.devrafaelsoares.SpringBootAuth.services;

import br.devrafaelsoares.SpringBootAuth.domain.user.*;
import br.devrafaelsoares.SpringBootAuth.exceptions.user.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.UUID;

public interface UserService extends UserDetailsService {
    User findUserByUsername(String username) throws UserNotFoundException;

    User findUserById(UUID id) throws UserNotFoundException;

    boolean isExistsUserByUsername(String username);

    boolean isExistsUserByEmail(String email);

    void updateIsEnableUser(User user, boolean status);

}
