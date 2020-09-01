package ru.autoins.oto_registry_rest.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtProperties {

    @Value("${jwt.expiration.time}")
    private int expirationTime;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.header.string}")
    private String headerString;

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;

    public int getExpirationTime() {
        return expirationTime;
    }

    public String getSecret() {
        return secret;
    }

    public String getHeaderString() {
        return headerString;
    }

    public String getTokenPrefix() {
        return tokenPrefix;
    }
}
