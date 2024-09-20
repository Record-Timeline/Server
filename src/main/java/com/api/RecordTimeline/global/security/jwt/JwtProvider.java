package com.api.RecordTimeline.global.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import com.api.RecordTimeline.global.exception.ApiException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import static com.api.RecordTimeline.global.exception.ErrorType._JWT_EXPIRED;
import static com.api.RecordTimeline.global.exception.ErrorType._JWT_PARSING_ERROR;

@Component
public class JwtProvider {

    // 액세스 토큰 유효 기간: 2시간
    public static final long ACCESS_TOKEN_VALID_TIME = 7200000L;

    // 리프레시 토큰 유효 기간: 7일
    public static final long REFRESH_TOKEN_VALID_TIME = 604800000L;

    @Value("${jwt.secret.key}")
    private String secretKey;

    // 액세스 토큰 발급
    public String generateJwtToken(final Long memberId, final String email) {
        Date now = new Date();
        long expiredDate = now.getTime() + ACCESS_TOKEN_VALID_TIME;
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(email)
                .claim("memberId", memberId)
                .setIssuedAt(now)
                .setExpiration(new Date(expiredDate))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 리프레시 토큰 발급
    public String generateRefreshToken(final Long memberId, final String email) {
        Date now = new Date();
        long expiredDate = now.getTime() + REFRESH_TOKEN_VALID_TIME;
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(email)
                .claim("memberId", memberId)
                .setIssuedAt(now)
                .setExpiration(new Date(expiredDate))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // 토큰 검증
    public void validateToken(final String jwtToken) throws ApiException {
        try {
            SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken);
        } catch (ExpiredJwtException e) {
            throw new ApiException(_JWT_EXPIRED); //토큰 만료
        } catch (Exception e) {
            throw new ApiException(_JWT_PARSING_ERROR);
        }
    }

    // 토큰에서 이메일 추출
    public String getUserEmailFromToken(final String jwtToken) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        return claims.getSubject();
    }

    // 토큰에서 회원 ID 추출
    public Long getUserIdFromToken(final String jwtToken) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        return claims.get("memberId", Long.class);
    }
}
