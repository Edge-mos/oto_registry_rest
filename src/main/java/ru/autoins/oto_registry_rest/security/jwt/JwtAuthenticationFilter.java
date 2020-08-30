package ru.autoins.oto_registry_rest.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.autoins.oto_registry_rest.security.security_utils.SecurityUser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    @Value("${jwt.expiration.time}")
    private int expirationTime;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.header.string}")
    private String headerString;

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;

    @Autowired
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    // срабатывает при POST request на /login
    // надо передать {"username": "..", "password": ".."} в request body
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        // берём догин/пароль и маппим их на класс LoginViewModel
        LoginViewModel credentials = null;
        try {
            credentials = new ObjectMapper().readValue(request.getInputStream(), LoginViewModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //создаём токен
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                credentials.getUserName(),
                credentials.getPassword(),
                new ArrayList<>()
        );
        // верифицируем
        Authentication auth = this.authenticationManager.authenticate(authenticationToken);
        return auth;

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // конвертируем в объект SecurityUser
        final SecurityUser securityUser = (SecurityUser) authResult.getPrincipal();

        // создаём токен
        String token = JWT.create()
                .withSubject(securityUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + this.expirationTime))
                .sign(Algorithm.HMAC512(this.secret.getBytes()));

        // добавляем токен в хедеры
        response.addHeader(this.headerString, this.tokenPrefix + token);

    }
}
