package com.api.RecordTimeline.global.security.jwt;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import java.util.Collections;

@Getter
public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private final Long userId;
    private final String email;
    private final String token;

    public JwtAuthenticationToken(Long userId, String email, String token) {
        super(Collections.emptyList());
        this.userId = userId;
        this.email = email;
        this.token = token;
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    @Override
    public Object getPrincipal() {
        return userId;
    }

    @Override
    public String getName() {
        return email;
    }
}
