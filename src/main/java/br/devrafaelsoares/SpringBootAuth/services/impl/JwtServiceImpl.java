package br.devrafaelsoares.SpringBootAuth.services.impl;

import br.devrafaelsoares.SpringBootAuth.config.JwtProperties;
import br.devrafaelsoares.SpringBootAuth.domain.user.User;
import br.devrafaelsoares.SpringBootAuth.services.JwtService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtProperties jwtProperties;

    private RSAPrivateKey privateKey;
    private RSAPublicKey publicKey;

    @PostConstruct
    public void init() {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");

            String privateKeyPem = new String(
                    jwtProperties.getPrivateKey().getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8
            );
            String publicKeyPem = new String(
                    jwtProperties.getPublicKey().getInputStream().readAllBytes(),
                    StandardCharsets.UTF_8
            );

            this.privateKey = loadPrivateKey(keyFactory, privateKeyPem);
            this.publicKey = loadPublicKey(keyFactory, publicKeyPem);

            log.info("Chaves RSA carregadas com sucesso para assinatura JWT");
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new IllegalStateException("Falha ao carregar chaves RSA para JWT", e);
        }
    }

    @Override
    public String generateToken(User user) {
        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);

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
            Algorithm algorithm = Algorithm.RSA256(publicKey, null);

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

    private RSAPrivateKey loadPrivateKey(
            KeyFactory keyFactory, String pem
    ) throws InvalidKeySpecException {
        String keyContent = pem
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replace("-----BEGIN RSA PRIVATE KEY-----", "")
                .replace("-----END RSA PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(keyContent);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(decoded);
        return (RSAPrivateKey) keyFactory.generatePrivate(spec);
    }

    private RSAPublicKey loadPublicKey(
            KeyFactory keyFactory, String pem
    ) throws InvalidKeySpecException {
        String keyContent = pem
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] decoded = Base64.getDecoder().decode(keyContent);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        return (RSAPublicKey) keyFactory.generatePublic(spec);
    }

}
