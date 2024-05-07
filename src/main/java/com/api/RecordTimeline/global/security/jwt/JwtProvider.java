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

    public static final long ACCESS_TOKEN_VALID_TIME = 7200000L;
    @Value("${jwt.secret.key}")
    private String secretKey;


    public String generateJwtToken(final String email) {
        Date now = new Date();
        long expiredDate = now.getTime() + ACCESS_TOKEN_VALID_TIME;
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(new Date(expiredDate))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

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

    public String getUserEmailFromToken(final String jwtToken) {
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(jwtToken)
                .getBody();

        return claims.getSubject();
    }


}
