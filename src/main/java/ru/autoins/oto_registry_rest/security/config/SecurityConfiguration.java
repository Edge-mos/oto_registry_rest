package ru.autoins.oto_registry_rest.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.autoins.oto_registry_rest.security.jwt.JwtAuthenticationFilter;
import ru.autoins.oto_registry_rest.security.jwt.JwtAuthorizationFilter;
import ru.autoins.oto_registry_rest.security.jwt.JwtProperties;
import ru.autoins.oto_registry_rest.security.models.Permission;
import ru.autoins.oto_registry_rest.security.models.Role;
import ru.autoins.oto_registry_rest.security.security_dao.UserRepository;


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtProperties jwtProperties;

    @Autowired
    public SecurityConfiguration(@Qualifier("userDetailServiceCustom") UserDetailsService userDetailsService, UserRepository userRepository, JwtProperties jwtProperties) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.jwtProperties = jwtProperties;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http    .csrf().disable()
//                .authorizeRequests()                                                          // авторизовать запрос
//                .antMatchers("/").permitAll()
//                .antMatchers("/user/**").hasAnyAuthority(Permission.Permission_desc.READ.name(), Role.Role_desc.USER.name())
//                .antMatchers("/**").hasAnyAuthority(Permission.Permission_desc.WRITE.name(), Role.Role_desc.ADMIN.name())
//                .anyRequest().authenticated()                                                // каждый запрос должен аунтифицирован
//                .and()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .httpBasic();

        http
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(new JwtAuthenticationFilter(this.authenticationManager(), this.jwtProperties))
                .addFilter(new JwtAuthorizationFilter(this.authenticationManager(), this.jwtProperties, this.userRepository))
                .authorizeRequests()
                .antMatchers("/login", "/swagger-ui/", "/v2/api-docs").permitAll()
                .antMatchers("/user/**").hasAnyAuthority(Permission.Permission_desc.READ.name(), Role.Role_desc.USER.name())
                .antMatchers("/admin/**").hasAnyAuthority(Permission.Permission_desc.WRITE.name(), Role.Role_desc.ADMIN.name());
    }

    @Override
    @Bean
    // authenticationManager находится в абстрактном классе WebSecurityConfigurerAdapter, который унаследован
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }


    //    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {                                // используем сам бин вместо наследования
//        return new InMemoryUserDetailsManager(
//                User.builder()
//                        .username("admin")
//                        .password(getBCryptPasswordEncoder().encode("admin"))
//                        .authorities(Role.ADMIN.getAuthorities())
//                        .build(),
//                User.builder()
//                        .username("user")
//                        .password(getBCryptPasswordEncoder().encode("user"))
//                        .authorities(Role.USER.getAuthorities())
//                        .build()
//        );
//    }

    @Bean
    public BCryptPasswordEncoder getBCryptPasswordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

}
