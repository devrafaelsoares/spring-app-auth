package br.devrafaelsoares.SpringBootAuth.controllers;

import br.devrafaelsoares.SpringBootAuth.domain.user.User;
import br.devrafaelsoares.SpringBootAuth.domain.user.UserConfirmation;
import br.devrafaelsoares.SpringBootAuth.dtos.AuthResponse;
import br.devrafaelsoares.SpringBootAuth.dtos.LoginRequest;
import br.devrafaelsoares.SpringBootAuth.dtos.RegisterRequest;
import br.devrafaelsoares.SpringBootAuth.dtos.UserResponse;
import br.devrafaelsoares.SpringBootAuth.services.CookieService;
import br.devrafaelsoares.SpringBootAuth.services.JwtService;
import br.devrafaelsoares.SpringBootAuth.services.UserConfirmationService;
import br.devrafaelsoares.SpringBootAuth.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final CookieService cookieService;
    private final UserConfirmationService userConfirmationService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(
            @Valid @RequestBody RegisterRequest request
    ) {
        User user = userService.registerUser(request);
        UserConfirmation confirmation = userConfirmationService.saveConfirmationUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                "message", "Usuário registrado com sucesso. Verifique seu e-mail para ativar a conta.",
                "confirmationToken", confirmation.getToken().toString()
        ));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
    ) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (DisabledException ex) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                    AuthResponse.builder()
                            .message("Conta não ativada. Verifique seu e-mail.")
                            .build()
            );
        } catch (BadCredentialsException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    AuthResponse.builder()
                            .message("Credenciais inválidas")
                            .build()
            );
        }

        User user = userService.findUserByUsername(request.getUsername());
        String token = jwtService.generateToken(user);

        cookieService.addAuthCookie(response, token);

        return ResponseEntity.ok(AuthResponse.builder()
                .message("Login realizado com sucesso")
                .user(UserResponse.fromEntity(user))
                .expiresIn(jwtService.getExpiration() / 1000)
                .build());
    }

    @GetMapping("/confirm")
    public ResponseEntity<Map<String, String>> confirmAccount(
            @RequestParam("token") UUID token
    ) {
        UserConfirmation confirmation = userConfirmationService.findConfirmationUserByToken(token);
        User user = confirmation.getUser();

        userService.updateIsEnableUser(user, true);
        userConfirmationService.deleteConfirmationUser(confirmation);

        return ResponseEntity.ok(Map.of(
                "message", "Conta ativada com sucesso"
        ));
    }

    @PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletResponse response) {
        cookieService.removeAuthCookie(response);

        return ResponseEntity.ok(Map.of(
                "message", "Logout realizado com sucesso"
        ));
    }

}
