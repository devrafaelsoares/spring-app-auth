package br.devrafaelsoares.SpringBootAuth.services.impl;

import br.devrafaelsoares.SpringBootAuth.config.CookieProperties;
import br.devrafaelsoares.SpringBootAuth.services.CookieService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CookieServiceImpl implements CookieService {

    private final CookieProperties cookieProperties;

    @Override
    public void addAuthCookie(HttpServletResponse response, String token) {
        ResponseCookie cookie = buildCookie(token, cookieProperties.getMaxAge());
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    @Override
    public void removeAuthCookie(HttpServletResponse response) {
        ResponseCookie cookie = buildCookie("", 0);
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private ResponseCookie buildCookie(String value, int maxAge) {
        ResponseCookie.ResponseCookieBuilder builder = ResponseCookie
                .from(cookieProperties.getName(), value)
                .httpOnly(cookieProperties.isHttpOnly())
                .secure(cookieProperties.isSecure())
                .sameSite(cookieProperties.getSameSite())
                .path(cookieProperties.getPath())
                .maxAge(maxAge);

        if (cookieProperties.getDomain() != null) {
            builder.domain(cookieProperties.getDomain());
        }

        return builder.build();
    }

}
