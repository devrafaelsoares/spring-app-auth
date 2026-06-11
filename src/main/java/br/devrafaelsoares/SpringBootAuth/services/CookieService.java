package br.devrafaelsoares.SpringBootAuth.services;

import jakarta.servlet.http.HttpServletResponse;

public interface CookieService {

    void addAuthCookie(HttpServletResponse response, String token);

    void removeAuthCookie(HttpServletResponse response);

}
