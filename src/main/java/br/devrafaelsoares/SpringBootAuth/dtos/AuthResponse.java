package br.devrafaelsoares.SpringBootAuth.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class AuthResponse {

    @JsonProperty("access_token")
    private final String accessToken;

    @JsonProperty("token_type")
    @Builder.Default
    private final String tokenType = "Bearer";

    @JsonProperty("expires_in")
    private final long expiresIn;

}
