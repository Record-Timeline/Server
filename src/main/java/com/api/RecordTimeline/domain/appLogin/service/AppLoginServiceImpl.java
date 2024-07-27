package com.api.RecordTimeline.domain.appLogin.service;

import com.api.RecordTimeline.domain.appLogin.dto.request.AppLoginRequestDto;
import com.api.RecordTimeline.domain.appLogin.dto.response.AppLoginResponseDto;
import com.api.RecordTimeline.domain.common.ResponseDto;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppLoginServiceImpl implements AppLoginService {

    private final MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private final JwtProvider jwtProvider;


    @Override
    public ResponseEntity<? super AppLoginResponseDto> appLogin(AppLoginRequestDto dto) {
        String token = null;
        try {

            String email = dto.getEmail();
            Member member = memberRepository.findByEmailAndIsDeletedFalse(email);
            if(member == null)
                return AppLoginResponseDto.memberNotFound();

            String password = dto.getPassword();
            String encodedPassword = member.getPassword();
            boolean isMatched = passwordEncoder.matches(password, encodedPassword); // password 매칭
            if(!isMatched)
                return AppLoginResponseDto.appLoginFail();

            token = jwtProvider.generateJwtToken(member.getId(),email);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return AppLoginResponseDto.success(token);
    }
}
