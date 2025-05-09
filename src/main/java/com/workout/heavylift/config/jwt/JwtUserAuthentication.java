package com.workout.heavylift.config.jwt;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.Collections;

public class JwtUserAuthentication extends AbstractAuthenticationToken {

    private final Long userId;

    public JwtUserAuthentication(Long userId) {
        super(Collections.emptyList());
        this.userId = userId;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.userId;
    }

    public Long getUserId() {
        return this.userId;
    }
}
