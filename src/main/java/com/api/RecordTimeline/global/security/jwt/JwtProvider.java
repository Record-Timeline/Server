package com.api.RecordTimeline.global.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
@Component
public class JwtProvider {

    @Value("{secret-key}")
    private String secretKey;

    @Value("${expiration.hours}")
    private int expirationHours;

    /** jwt 생성 **/
    public String create(String memberId) {

        Date expiredDate = Date.from(Instant.now().plus(expirationHours, ChronoUnit.HOURS));
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        String jwt = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256)
                .setSubject(memberId).setIssuedAt(new Date()).setExpiration(expiredDate)
                .compact();

        return jwt;
    }


    /** jwt 검증 **/
    public String validate(String jwt) {

        String subject = null;
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        try {

            subject = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();

        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
        return subject;
    }
}
