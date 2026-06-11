package br.devrafaelsoares.SpringBootAuth.services.impl;

import br.devrafaelsoares.SpringBootAuth.domain.user.*;
import br.devrafaelsoares.SpringBootAuth.dtos.RegisterRequest;
import br.devrafaelsoares.SpringBootAuth.exceptions.user.UserExistsException;
import br.devrafaelsoares.SpringBootAuth.exceptions.user.UserNotFoundException;
import br.devrafaelsoares.SpringBootAuth.repositories.UserRepository;
import br.devrafaelsoares.SpringBootAuth.services.RoleService;
import br.devrafaelsoares.SpringBootAuth.services.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

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
    public User registerUser(RegisterRequest request) throws UserExistsException {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new UserExistsException("Nome de usuário já está em uso");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserExistsException("E-mail já está em uso");
        }

        Role userRole = roleService.findRoleByName("USER");

        User user = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .isEnabled(false)
                .roles(List.of(userRole))
                .build();

        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }
}
