package br.devrafaelsoares.SpringBootAuth.services.impl;

import br.devrafaelsoares.SpringBootAuth.config.JwtProperties;
import br.devrafaelsoares.SpringBootAuth.domain.user.User;
import br.devrafaelsoares.SpringBootAuth.services.JwtService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;

    @Override
    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecretKey());

        return JWT.create()
                .withIssuer("spring-boot-auth")
                .withSubject(user.getUsername())
                .withClaim("userId", user.getId().toString())
                .withIssuedAt(Instant.now())
                .withExpiresAt(Instant.now().plusMillis(jwtProperties.getExpiration()))
                .sign(algorithm);
    }

    @Override
    public String validateTokenAndGetSubject(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(jwtProperties.getSecretKey());

            return JWT.require(algorithm)
                    .withIssuer("spring-boot-auth")
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException ex) {
            return null;
        }
    }

    @Override
    public long getExpiration() {
        return jwtProperties.getExpiration();
    }

}
