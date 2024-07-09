package com.api.RecordTimeline.domain.appLogin.service;

import com.api.RecordTimeline.domain.appLogin.dto.request.AppLoginRequestDto;
import com.api.RecordTimeline.domain.appLogin.dto.response.AppLoginResponseDto;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import com.api.RecordTimeline.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppLoginServiceImpl implements AppLoginService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtProvider jwtProvider;

    @Override
    public AppLoginResponseDto appLogin(AppLoginRequestDto dto) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(dto.getEmail())
                .orElseThrow(() -> new ApiException(ErrorType._USER_NOT_FOUND_DB));

        if (!passwordEncoder.matches(dto.getPassword(), member.getPassword())) {
            throw new ApiException(ErrorType._APP_LOGIN_FAIL);
        }

        String token = jwtProvider.generateJwtToken(dto.getEmail());
        return AppLoginResponseDto.success(token);
    }
}
