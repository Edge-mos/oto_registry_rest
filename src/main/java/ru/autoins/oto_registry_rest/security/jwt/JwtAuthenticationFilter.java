package ru.autoins.oto_registry_rest.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import ru.autoins.oto_registry_rest.security.security_utils.SecurityUser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtProperties jwtProperties;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtProperties jwtProperties) {
        this.authenticationManager = authenticationManager;
        this.jwtProperties = jwtProperties;
    }

    @Override
    // срабатывает при POST request на /login
    // надо передать {"username": "..", "password": ".."} в request body
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        Optional<LoginViewModel> credentials = Optional.empty();
        // берём догин/пароль и маппим их на класс LoginViewModel

        try {
            credentials = Optional.ofNullable(new ObjectMapper().readValue(request.getInputStream(), LoginViewModel.class));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //создаём токен
        final LoginViewModel loginViewModel = credentials.orElseGet(LoginViewModel::new);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginViewModel.getUsername(),
                loginViewModel.getPassword(),
                new ArrayList<>()
        );
        // верифицируем
        return this.authenticationManager.authenticate(authenticationToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // конвертируем в объект SecurityUser
        final SecurityUser securityUser = (SecurityUser) authResult.getPrincipal();

        // создаём токен
        String token = JWT.create()
                .withSubject(securityUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + this.jwtProperties.getExpirationTime()))
                .sign(Algorithm.HMAC512(this.jwtProperties.getSecret().getBytes()));

        // добавляем токен в хедеры
        response.addHeader(this.jwtProperties.getHeaderString(), this.jwtProperties.getTokenPrefix().concat(" ").concat(token));

    }
}
