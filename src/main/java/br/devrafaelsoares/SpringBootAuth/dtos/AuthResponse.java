package br.devrafaelsoares.SpringBootAuth.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthResponse {

    private final String message;

    private final UserResponse user;

    @JsonProperty("expires_in")
    private final long expiresIn;

}
