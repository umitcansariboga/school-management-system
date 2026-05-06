package com.ucs.security.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Getter
public class UserDetailsImpl implements UserDetails {

    private final Long id;
    private final String username;
    private final String name;
    private final Boolean isAdvisor;
    private final String ssn;
    private final boolean isActive;
    private final Boolean isLocked;
    private final LocalDate lastPasswordChange;

    @JsonIgnore
    private final String password;

    private final Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id,
                           String username,
                           String name,
                           Boolean isAdvisor,
                           String ssn,
                           String password,
                           String role,
                           boolean isActive,
                           Boolean isLocked,
                           LocalDate lastPasswordChange) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.isAdvisor = isAdvisor;
        this.ssn = ssn;
        this.password = password;
        this.isActive = isActive;
        this.authorities = List.of(new SimpleGrantedAuthority(role));
        this.isLocked = isLocked;
        this.lastPasswordChange = lastPasswordChange;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.isLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return lastPasswordChange != null && lastPasswordChange.isAfter(LocalDate.now().minusDays(90));
    }

    @Override
    public boolean isEnabled() {
        return this.isActive;
    }
}
