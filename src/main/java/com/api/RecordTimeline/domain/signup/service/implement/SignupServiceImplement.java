package com.api.RecordTimeline.domain.signup.service.implement;

import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.signup.dto.request.BasicSignupRequestDto;
import com.api.RecordTimeline.domain.signup.dto.request.EmailCheckResquestDto;
import com.api.RecordTimeline.domain.signup.dto.request.KakaoSignupRequestDto;
import com.api.RecordTimeline.domain.signup.dto.request.NicknameCheckResquestDto;
import com.api.RecordTimeline.domain.signup.dto.response.EmailCheckResponseDto;
import com.api.RecordTimeline.domain.signup.dto.response.NicknameCheckResponseDto;
import com.api.RecordTimeline.domain.signup.dto.response.ResponseDto;
import com.api.RecordTimeline.domain.signup.dto.response.SignupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class SignupServiceImplement implements SignupService {

    private final MemberRepository memberRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public ResponseEntity<? super EmailCheckResponseDto> emailCheck(EmailCheckResquestDto dto) {

        try{
            String email = dto.getEmail();
            boolean isExistEmail = memberRepository.existsByEmail(email);
            if(isExistEmail)
                return EmailCheckResponseDto.duplicateEmail();

        } catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return EmailCheckResponseDto.success();
    }

    @Override
    public ResponseEntity<? super NicknameCheckResponseDto> nicknameCheck(NicknameCheckResquestDto dto) {
        try{
            String nickname = dto.getNickname();
            boolean isExistNickname = memberRepository.existsByNickname(nickname);
            if(isExistNickname)
                return NicknameCheckResponseDto.duplicateNickname();

        } catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return NicknameCheckResponseDto.success();
    }


    @Override
    public ResponseEntity<? super SignupResponseDto> basicSignup(BasicSignupRequestDto basicDto) {

        try {
            String email = basicDto.getEmail();
            boolean isExistEmail = memberRepository.existsByEmail(email);
            if (isExistEmail)
                return SignupResponseDto.duplicateEmail();

            String password = basicDto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            basicDto.setPassword(encodedPassword); //후에 빌더 패턴으로 변경

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
