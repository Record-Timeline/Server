package com.api.RecordTimeline.global.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class AuthHandshakeInterceptor implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) {
        String token = request.getHeaders().getFirst("Authorization"); // 헤더에서 토큰 추출
        if (token != null && isValidToken(token)) {
            attributes.put("user", getUserFromToken(token)); // 사용자 정보를 WebSocket 세션에 저장
            return true; // 인증 성공
        }
        return false; // 인증 실패
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // 핸드셰이크 성공 후 작업
        System.out.println("WebSocket 연결 성공");
    }

    private boolean isValidToken(String token) {
        try {
            Jwts.parser().setSigningKey("SECRET_KEY").parseClaimsJws(token); // 토큰 검증
            return true;
        } catch (SignatureException e) {
            System.out.println("Invalid Token Signature");
            return false;
        } catch (Exception e) {
            System.out.println("Token 검증 실패: " + e.getMessage());
            return false;
        }
    }

    private String getUserFromToken(String token) {
        // 토큰에서 subject(사용자 ID) 추출
        return Jwts.parser().setSigningKey("SECRET_KEY").parseClaimsJws(token).getBody().getSubject();
    }
}
