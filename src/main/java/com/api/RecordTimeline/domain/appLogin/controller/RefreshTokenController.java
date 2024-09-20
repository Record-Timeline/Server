package com.api.RecordTimeline.domain.appLogin.controller;

import com.api.RecordTimeline.domain.appLogin.domain.RefreshToken;
import com.api.RecordTimeline.domain.appLogin.repository.RefreshTokenRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import com.api.RecordTimeline.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody String refreshToken) {
        try {
            jwtProvider.validateToken(refreshToken); // 리프레시 토큰 검증

            Long userId = jwtProvider.getUserIdFromToken(refreshToken);
            String email = jwtProvider.getUserEmailFromToken(refreshToken);

            // 리프레시 토큰이 유효한지 확인
            RefreshToken savedToken = refreshTokenRepository.findByUserId(userId)
                    .orElseThrow(() -> new ApiException(ErrorType._REFRESH_TOKEN_NOT_FOUND));


            if (!savedToken.getToken().equals(refreshToken)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }



            // 새로운 액세스 토큰 발급
            String newAccessToken = jwtProvider.generateJwtToken(userId, email);
            return ResponseEntity.ok(newAccessToken);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}

