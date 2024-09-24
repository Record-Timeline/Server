package com.api.RecordTimeline.domain.appLogin.controller;

import com.api.RecordTimeline.domain.appLogin.domain.RefreshToken;
import com.api.RecordTimeline.domain.appLogin.dto.request.TokenRequestDto;
import com.api.RecordTimeline.domain.appLogin.dto.response.AppLoginResponseDto;
import com.api.RecordTimeline.domain.appLogin.repository.RefreshTokenRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import com.api.RecordTimeline.global.security.jwt.JwtProvider;
import io.jsonwebtoken.ExpiredJwtException;
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

        } catch (ApiException e) {
            if (e.getErrorType() == ErrorType._JWT_EXPIRED) {
                // Refresh Token 만료 시 재로그인 처리
                throw new ApiException(ErrorType._REFRESH_TOKEN_EXPIRED);
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/renew-tokens")
    public ResponseEntity<?> renewTokens(@RequestBody TokenRequestDto tokenRequestDto) {
        String accessToken = tokenRequestDto.getAccessToken();
        String refreshToken = tokenRequestDto.getRefreshToken();

        try {
            // Access Token이 만료되지 않았다면 그대로 토큰 재발급
            jwtProvider.validateToken(accessToken);
            // Access Token이 유효하다면 새롭게 Access/Refresh 토큰을 발급
            Long userId = jwtProvider.getUserIdFromToken(accessToken);
            String email = jwtProvider.getUserEmailFromToken(accessToken);

            // 새로운 토큰 발급
            String newAccessToken = jwtProvider.generateJwtToken(userId, email);
            String newRefreshToken = jwtProvider.generateRefreshToken(userId, email);

            return AppLoginResponseDto.success(newAccessToken, newRefreshToken);
        } catch (ExpiredJwtException e) {
            // Access Token이 만료되었을 경우, Refresh Token 검증
            try {
                jwtProvider.validateToken(refreshToken); // Refresh Token 검증

                Long userId = jwtProvider.getUserIdFromToken(refreshToken);
                String email = jwtProvider.getUserEmailFromToken(refreshToken);

                // Refresh Token DB에서 확인
                RefreshToken savedToken = refreshTokenRepository.findByUserId(userId)
                        .orElseThrow(() -> new ApiException(ErrorType._REFRESH_TOKEN_NOT_FOUND));

                if (!savedToken.getToken().equals(refreshToken)) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }

                // 새로운 토큰 발급
                String newAccessToken = jwtProvider.generateJwtToken(userId, email);
                String newRefreshToken = jwtProvider.generateRefreshToken(userId, email);

                // 새롭게 발급된 Refresh Token 저장 (옵션)
                refreshTokenRepository.save(new RefreshToken(userId, newRefreshToken));

                return AppLoginResponseDto.success(newAccessToken, newRefreshToken);
            } catch (Exception refreshEx) {
                throw new ApiException(ErrorType._REFRESH_TOKEN_EXPIRED);
            }
        } catch (Exception e) {
            throw new ApiException(ErrorType._REFRESH_TOKEN_INVALID);
        }
    }


}

