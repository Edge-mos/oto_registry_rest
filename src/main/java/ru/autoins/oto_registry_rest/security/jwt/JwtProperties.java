package ru.autoins.oto_registry_rest.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Base64;

@Configuration
@ConfigurationProperties("jwt")
public class JwtProperties {

    private Long expirationTime;

    private String secret;

    private String headerString;

    private String tokenPrefix;


    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String getHeaderString() {
        return headerString;
    }

    public void setHeaderString(String headerString) {
        this.headerString = headerString;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
        this.tokenPrefix = tokenPrefix;
    }
}
