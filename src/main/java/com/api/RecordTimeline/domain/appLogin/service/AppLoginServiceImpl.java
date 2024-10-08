package com.api.RecordTimeline.domain.appLogin.service;

import com.api.RecordTimeline.domain.appLogin.dto.request.AppLoginRequestDto;
import com.api.RecordTimeline.domain.appLogin.dto.response.AppLoginResponseDto;
import com.api.RecordTimeline.domain.appLogin.domain.RefreshToken;
import com.api.RecordTimeline.domain.appLogin.repository.RefreshTokenRepository;
import com.api.RecordTimeline.domain.common.ResponseDto;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import com.api.RecordTimeline.global.security.jwt.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AppLoginServiceImpl implements AppLoginService {

    private final MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository; // 리프레시 토큰 저장소

    @Override
    @Transactional
    public ResponseEntity<? super AppLoginResponseDto> appLogin(AppLoginRequestDto dto) {
        try {
            String email = dto.getEmail();

            Member member = memberRepository.findByEmailAndIsDeletedFalse(email);
            if(member == null)
                return AppLoginResponseDto.memberNotFound();

            String password = dto.getPassword();
            String encodedPassword = member.getPassword();
            boolean isMatched = passwordEncoder.matches(password, encodedPassword);
            if (!isMatched) {
                return AppLoginResponseDto.appLoginFail();
            }

            // 기존 Refresh Token 삭제 (로그인 시)
            refreshTokenRepository.deleteByUserId(member.getId());

            // 새로운 엑세스 토큰과 리프레시 토큰 발급
            String accessToken = jwtProvider.generateJwtToken(member.getId(), email);
            String refreshToken = jwtProvider.generateRefreshToken(member.getId(), email);

            // 새로운 리프레시 토큰 저장
            refreshTokenRepository.save(new RefreshToken(member.getId(), refreshToken));

            return AppLoginResponseDto.success(accessToken, refreshToken);

        } catch (Exception exception) {

            return ResponseDto.databaseError();
        }
    }


}
