package com.api.RecordTimeline.global.security.jwt;

import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

            // Publicuri일 경우 검증 안함
            String requestURI = request.getRequestURI();
            if (isPublicUri(requestURI)) {
                filterChain.doFilter(request, response);
                return;
            }

            try {
                String token = parseBearerToken(request);
                if(token == null) {
                    filterChain.doFilter(request, response);
                    return;
                }

                String memberId = jwtProvider.validate(token);
                if(memberId == null) {
                    filterChain.doFilter(request, response);
                    return;
                }

                Member member = memberRepository.findByMemberId(memberId);

                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();

                AbstractAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(memberId, null);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                securityContext.setAuthentication(authenticationToken);
                SecurityContextHolder.setContext(securityContext);

            } catch (Exception exception) {
                exception.printStackTrace();
            }

            filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");

        boolean hasAuthorization = StringUtils.hasText(authorization);
        if(!hasAuthorization)
            return null;

        boolean isBearer = authorization.startsWith("Bearer ");
        if(!isBearer)
            return null;
        String token = authorization.substring(7); //"Bearer " 뒤부터 토큰 값 가져옴.
        return token;
    }


    private boolean isPublicUri(final String requestURI) {
        return
                requestURI.startsWith("/swagger-ui/**") ||
                        requestURI.startsWith("/api/v1/**"); //개발 기간 동안만 임시로 적어놓음.
    }
}
