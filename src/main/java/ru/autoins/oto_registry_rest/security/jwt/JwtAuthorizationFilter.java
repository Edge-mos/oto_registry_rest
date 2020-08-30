package ru.autoins.oto_registry_rest.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import ru.autoins.oto_registry_rest.security.models.User;
import ru.autoins.oto_registry_rest.security.security_dao.UserRepository;
import ru.autoins.oto_registry_rest.security.security_utils.SecurityUser;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private final UserRepository userRepository;

    @Value("${jwt.header.string}")
    private String headerString;

    @Value("${jwt.token.prefix}")
    private String tokenPrefix;

    @Value("${jwt.secret}")
    private String secret;

    @Autowired
    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint, UserRepository userRepository) {
        super(authenticationManager, authenticationEntryPoint);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // читаем хедер авторизации, тут должен быть JWT токен
        String header = request.getHeader(this.headerString);

        // еслив хедере нету Bearer или он пустой - выход
        if (header == null || !header.startsWith(this.tokenPrefix)) {
            chain.doFilter(request, response);
            return;
        }

        // если хедер присутствует(норм) попытаемся вывести данные юзера из БД и произвести авторизацию
        Authentication authentication = getUsernamePasswordAuthentication(request);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // передаём фильт дальше
        chain.doFilter(request, response);
    }

    private Authentication getUsernamePasswordAuthentication(HttpServletRequest request) {
        String token = request.getHeader(this.headerString);

        if (token != null) {
            String userName = JWT.require(Algorithm.HMAC512(this.secret.getBytes()))
                    .build()
                    .verify(token.replace(this.tokenPrefix, ""))
                    .getSubject();
            if (userName != null) {
//                final User user = this.userRepository.getUserByName(userName).get();      // ???? ( исправить Optional)
                final User user = this.userRepository.getUserByName(userName).orElseThrow(() -> new UsernameNotFoundException("User not found! Problem in getUsernamePasswordAuthentication method!"));      // ???? ( исправить Optional)
                SecurityUser securityUser = new SecurityUser(user);
                UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userName, null, securityUser.getAuthorities());
                return auth;
            }
            return null;
        }
        return null;
    }
}
