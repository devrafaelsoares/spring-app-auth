package br.devrafaelsoares.SpringBootAuth.services;

import br.devrafaelsoares.SpringBootAuth.domain.user.User;

public interface JwtService {

    String generateToken(User user);

    String validateTokenAndGetSubject(String token);

    long getExpiration();

}
