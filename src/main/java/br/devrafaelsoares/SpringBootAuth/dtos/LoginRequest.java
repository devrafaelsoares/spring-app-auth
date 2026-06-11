package br.devrafaelsoares.SpringBootAuth.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "O nome de usuário é obrigatório")
    private String username;

    @NotBlank(message = "A senha é obrigatória")
    private String password;

}
