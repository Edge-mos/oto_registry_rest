package ru.autoins.oto_registry_rest.security.security_utils;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.autoins.oto_registry_rest.security.models.Role;
import ru.autoins.oto_registry_rest.security.models.Status;
import ru.autoins.oto_registry_rest.security.models.User;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class SecurityUser implements UserDetails {

    private final User user;

    public SecurityUser(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.user.getListOfRoles()
                .stream()
                .flatMap(role -> role.getListOfPermissions().stream())
                .map(permission -> new SimpleGrantedAuthority(permission.getPermissionName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return this.user.getPassword();
    }

    @Override
    public String getUsername() {
        return this.user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.isActive();
    }

    @Override
    public boolean isEnabled() {
        return this.isActive();
    }

    private boolean isActive() {
        return !this.user.getStatus().getStatusName().equals(Status.Status_desc.BANNED.name());
    }
}
