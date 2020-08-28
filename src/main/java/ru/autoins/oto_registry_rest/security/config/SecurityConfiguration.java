package ru.autoins.oto_registry_rest.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.autoins.oto_registry_rest.security.models.Permission;
import ru.autoins.oto_registry_rest.security.models.Role;


@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfiguration(@Qualifier("userDetailServiceCustom") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http    .csrf().disable()
                .authorizeRequests()                                                          // авторизовать запрос
                .antMatchers("/").permitAll()
                .antMatchers("/user/**").hasAnyAuthority(Permission.Permission_desc.READ.name(), Role.Role_desc.USER.name())
                .antMatchers("/**").hasAnyAuthority(Permission.Permission_desc.WRITE.name(), Role.Role_desc.ADMIN.name())
                .anyRequest().authenticated()                                                // каждый запрос должен аунтифицирован
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic();
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
