package com.meta.cloud.oauth2.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.meta.cloud.domain.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class OAuth2UserPrincipal implements OAuth2User, UserDetails {
    private final String userId;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final Map<String, Object> attributes;

    @Builder
    public OAuth2UserPrincipal(String userId, String email, String password, Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.attributes = attributes;
    }

    public static OAuth2UserPrincipal create(User user) {
        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));

        return OAuth2UserPrincipal.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(user.getLoginPw())
                .authorities(authorities)
                .attributes(null)
                .build();
    }

    public static OAuth2UserPrincipal create(User user, Map<String, Object> attributes) {
        return OAuth2UserPrincipal.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(user.getLoginPw())
                .authorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
                .attributes(attributes)
                .build();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String getName() {
        return userId;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
