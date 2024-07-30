package com.api.RecordTimeline.domain.findInfo.password.service;

import com.api.RecordTimeline.domain.common.CertificationNumber;
import com.api.RecordTimeline.domain.findInfo.password.dto.request.PasswordResetDto;
import com.api.RecordTimeline.domain.findInfo.password.dto.request.PasswordResetRequestDto;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.signup.email.domain.EmailCertification;
import com.api.RecordTimeline.domain.signup.email.dto.request.CheckCertificationRequestDto;
import com.api.RecordTimeline.domain.signup.email.repository.EmailCertificationRepository;
import com.api.RecordTimeline.domain.signup.email.provider.EmailProvider;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final MemberRepository memberRepository;
    private final EmailCertificationRepository emailCertificationRepository;
    private final EmailProvider emailProvider;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional
    public void sendResetEmail(PasswordResetRequestDto requestDto) {
        String email = requestDto.getEmail();
        if (!memberRepository.existsByEmailAndIsDeletedFalse(email)) {
            throw new ApiException(ErrorType._USER_NOT_FOUND_DB);
        }

        String certificationNumber = CertificationNumber.getCertificationNumber();
        boolean emailSent = emailProvider.sendCertificationMail(email, certificationNumber);
        if (!emailSent) {
            throw new ApiException(ErrorType._MAIL_SEND_FAIL);
        }

        EmailCertification emailCertification = new EmailCertification(email, certificationNumber, "PASSWORD_RESET");
        emailCertificationRepository.save(emailCertification);
    }

    @Transactional
    public void verifyCertificationNumber(CheckCertificationRequestDto requestDto) {
        String email = requestDto.getEmail();
        String certificationNumber = requestDto.getCertificationNumber();

        EmailCertification emailCertification = emailCertificationRepository.findByEmailAndContext(email, "PASSWORD_RESET");
        if (emailCertification == null || !emailCertification.getCertificationNumber().equals(certificationNumber)) {
            throw new ApiException(ErrorType._CERTIFICATION_FAILED);
        }

        emailCertification.markAsVerified();
        emailCertificationRepository.save(emailCertification);
    }

    @Transactional
    public void resetPassword(PasswordResetDto passwordResetDto) {
        String email = passwordResetDto.getEmail();
        EmailCertification emailCertification = emailCertificationRepository.findByEmailAndContext(email, "PASSWORD_RESET");

        if (emailCertification == null || !emailCertification.isVerified()) {
            throw new ApiException(ErrorType._CERTIFICATION_NOT_VERIFIED);
        }

        Member member = memberRepository.findByEmailAndIsDeletedFalse(email);
        if (member == null) {
            throw new ApiException(ErrorType._USER_NOT_FOUND_DB);
        }

        String encodedPassword = passwordEncoder.encode(passwordResetDto.getNewPassword());

        //비밀번호 업데이트
        Member updatedMember = member.toBuilder()
                .password(encodedPassword)
                .build();

        memberRepository.save(updatedMember);
    }
}
