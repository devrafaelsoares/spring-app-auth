package br.devrafaelsoares.SpringBootAuth.dtos;

import br.devrafaelsoares.SpringBootAuth.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@AllArgsConstructor
public class UserResponse {

    private final UUID id;
    private final String name;
    private final String username;
    private final String email;
    private final boolean enabled;
    private final List<String> roles;

    public static UserResponse fromEntity(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .email(user.getEmail())
                .enabled(user.isEnabled())
                .roles(user.getRoles().stream()
                        .map(role -> role.getName())
                        .toList())
                .build();
    }

}
