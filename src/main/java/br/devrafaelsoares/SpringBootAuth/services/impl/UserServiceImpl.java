package br.devrafaelsoares.SpringBootAuth.services.impl;

import br.devrafaelsoares.SpringBootAuth.domain.user.*;
import br.devrafaelsoares.SpringBootAuth.exceptions.user.UserNotFoundException;
import br.devrafaelsoares.SpringBootAuth.repositories.UserRepository;
import br.devrafaelsoares.SpringBootAuth.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
		    .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    @Override
    public User findUserById(UUID id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }

    @Override
    public boolean isExistsUserByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean isExistsUserByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void updateIsEnableUser(User user, boolean status) {
        user.setEnabled(status);
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }
}
