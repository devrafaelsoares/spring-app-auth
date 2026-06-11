package br.devrafaelsoares.SpringBootAuth.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.security.cookie")
@Getter
@Setter
public class CookieProperties {

    private String name = "access_token";
    private int maxAge = 86400;
    private boolean secure = true;
    private boolean httpOnly = true;
    private String sameSite = "Strict";
    private String path = "/";
    private String domain;

}
