package com.api.RecordTimeline.domain.signup.signup.service;

import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.profile.domain.Profile;
import com.api.RecordTimeline.domain.profile.repository.ProfileRepository;
import com.api.RecordTimeline.domain.signup.email.domain.EmailCertification;
import com.api.RecordTimeline.domain.signup.email.dto.response.CheckCertificationResponseDto;
import com.api.RecordTimeline.domain.signup.email.repository.EmailCertificationRepository;
import com.api.RecordTimeline.domain.signup.signup.dto.request.BasicSignupRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.request.KakaoSignupRequestDto;
import com.api.RecordTimeline.domain.common.ResponseDto;
import com.api.RecordTimeline.domain.signup.signup.dto.request.UnRegisterRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.response.SignupResponseDto;
import com.api.RecordTimeline.domain.signup.signup.dto.response.UnRegisterResponseDto;
import com.api.RecordTimeline.global.security.jwt.JwtProvider;
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
    private final EmailCertificationRepository emailCertificationRepository;
    private final ProfileRepository profileRepository;
    private final JwtProvider jwtProvider;
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private static final String PASSWORD_PATTERN = "^(?=.*[a-zA-Z])(?=.*[0-9])[a-zA-Z0-9]{8,}$";

    @Override
    public ResponseEntity<? super SignupResponseDto> basicSignup(BasicSignupRequestDto basicDto) {

        String token;
        try {
            String email = basicDto.getEmail();
            boolean isExistEmail = memberRepository.existsByEmailAndIsDeletedFalse(email);
            if (isExistEmail)
                return SignupResponseDto.duplicateEmail();

            String nickname = basicDto.getNickname();
            boolean isExistNickname = memberRepository.existsByNicknameAndIsDeletedFalse(nickname);
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

            String certificationNumber = basicDto.getCertificationNumber();
            EmailCertification emailCertification = emailCertificationRepository.findByEmailAndContext(email, "SIGNUP");
            if (emailCertification == null || !emailCertification.getCertificationNumber().equals(certificationNumber)) {
                return CheckCertificationResponseDto.certificationFail();
            }

            Member member = new Member(basicDto);
            memberRepository.save(member);

            Profile profile = new Profile(); // Member 객체 생성될 때 Profile 객체도 생성
            profile.setMember(member);
            profileRepository.save(profile);

            token = jwtProvider.generateJwtToken(member.getId(),email);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return SignupResponseDto.success(token);
    }

    @Override
    public ResponseEntity<? super SignupResponseDto> kakaoSignup(KakaoSignupRequestDto kakaoDto) {
        return null; //이후에 구현
    }

    @Override
    public ResponseEntity<? super UnRegisterResponseDto> unRegister(String email, UnRegisterRequestDto dto) {
        try {

            Member member = memberRepository.findByEmailAndIsDeletedFalse(email);

            if (member == null || member.isDeleted()) {
                return UnRegisterResponseDto.memberNotFound();
            }

            boolean isMatched = passwordEncoder.matches(dto.getPassword(), member.getPassword());
            if (!isMatched) {
                return UnRegisterResponseDto.passwordMismatch();
            }

            member.markAsDeleted();
            memberRepository.save(member);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return UnRegisterResponseDto.success();
    }


}
