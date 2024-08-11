package com.api.RecordTimeline.global.util;

import com.api.RecordTimeline.global.security.jwt.JwtAuthenticationToken;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.api.RecordTimeline.global.security.jwt.JwtProvider;

@Component
public class SecurityUtil {

    public static Long getCurrentMemberId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("No authentication information found");
        }

        if (authentication instanceof JwtAuthenticationToken) {
            return ((JwtAuthenticationToken) authentication).getUserId();
        } else if (authentication instanceof AnonymousAuthenticationToken) {
            throw new RuntimeException("Anonymous user cannot access member ID");
        } else {
            throw new IllegalStateException("Unexpected authentication token: " + authentication.getClass().getName());
        }
    }
}
