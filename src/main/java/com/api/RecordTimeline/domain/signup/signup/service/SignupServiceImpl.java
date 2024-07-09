package com.api.RecordTimeline.domain.signup.signup.service;

import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.profile.domain.Profile;
import com.api.RecordTimeline.domain.profile.repository.ProfileRepository;
import com.api.RecordTimeline.domain.signup.email.repository.EmailCertificationRepository;
import com.api.RecordTimeline.domain.signup.signup.dto.request.BasicSignupRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.request.KakaoSignupRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.request.UnRegisterRequestDto;
import com.api.RecordTimeline.domain.signup.signup.dto.response.UnRegisterResponseDto;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import com.api.RecordTimeline.global.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
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
    public String basicSignup(BasicSignupRequestDto basicDto) {
        String email = basicDto.getEmail();
        boolean isExistEmail = memberRepository.existsByEmailAndIsDeletedFalse(email);
        if (isExistEmail) {
            throw new ApiException(ErrorType._DUPLICATE_EMAIL);
        }

        String nickname = basicDto.getNickname();
        boolean isExistNickname = memberRepository.existsByNicknameAndIsDeletedFalse(nickname);
        if (isExistNickname) {
            throw new ApiException(ErrorType._DUPLICATE_NICKNAME);
        }

        String password = basicDto.getPassword();
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        if (!matcher.matches()) {
            throw new ApiException(ErrorType.INVALID_PASSWORD_PATTERN);
        }

        String encodedPassword = passwordEncoder.encode(password);
        basicDto.setPassword(encodedPassword);

        String certificationNumber = basicDto.getCertificationNumber();
        boolean isCertificationNumberValid = emailCertificationRepository.existsByEmailAndCertificationNumber(email, certificationNumber);
        if (!isCertificationNumberValid) {
            throw new ApiException(ErrorType.CERTIFICATION_FAILED);
        }

        Member member = new Member(basicDto);
        memberRepository.save(member);

        Profile profile = new Profile(); // Member 객체 생성될 때 Profile 객체도 생성
        profile.setMember(member);
        profileRepository.save(profile);

        return jwtProvider.generateJwtToken(email);
    }

    @Override
    public String kakaoSignup(KakaoSignupRequestDto kakaoDto) {
        // 이후에 구현
        return null;
    }

    @Override
    public UnRegisterResponseDto unRegister(String email, UnRegisterRequestDto dto) {
        Member member = memberRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new ApiException(ErrorType._USER_NOT_FOUND_DB));

        boolean isMatched = passwordEncoder.matches(dto.getPassword(), member.getPassword());
        if (!isMatched) {
            throw new ApiException(ErrorType._PASSWORD_MISMATCH);
        }

        member.markAsDeleted();
        memberRepository.save(member);
        return null;
    }
}
