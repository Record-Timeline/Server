package com.api.RecordTimeline.domain.signup.signup.service;

import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.signup.signup.dto.request.BasicSignupRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.request.KakaoSignupRequestDto;
import com.api.RecordTimeline.domain.common.ResponseDto;
import com.api.RecordTimeline.domain.signup.signup.dto.response.SignupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class SignupServiceImpl implements SignupService {

    private final MemberRepository memberRepository;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$";

    @Override
    public ResponseEntity<? super SignupResponseDto> basicSignup(BasicSignupRequestDto basicDto) {

        try {
            String memberId = basicDto.getMemberId();
            boolean isExistMemberId = memberRepository.existsByMemberId(memberId);
            if (isExistMemberId)
                return SignupResponseDto.duplicateId();

            String nickname = basicDto.getNickname();
            boolean isExistNickname = memberRepository.existsByNickname(nickname);
            if (isExistNickname)
                return SignupResponseDto.duplicateNickname();

            String password = basicDto.getPassword();
            Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
            Matcher matcher = pattern.matcher(password);
            if (!matcher.matches()) {
                return SignupResponseDto.invalidPasswordPattern();
            }

            String encodedPassword = passwordEncoder.encode(password);
            basicDto.setPassword(encodedPassword);

            Member member = new Member(basicDto);
            memberRepository.save(member);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return SignupResponseDto.success();
    }

    @Override
    public ResponseEntity<? super SignupResponseDto> kakaoSignup(KakaoSignupRequestDto kakaoDto) {
        return null; //이후에 구현
    }
}
