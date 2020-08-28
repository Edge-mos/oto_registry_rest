package ru.autoins.oto_registry_rest.security.security_utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.autoins.oto_registry_rest.security.models.User;
import ru.autoins.oto_registry_rest.security.security_dao.UserRepository;

@Service("userDetailServiceCustom")
public class UserDetailServiceCustom implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailServiceCustom(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final User user = userRepository.getUserByName(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found!")
        );
        return new SecurityUser(user);
    }
}
