package com.api.RecordTimeline.domain.signup.email.service;

import com.api.RecordTimeline.domain.common.CertificationNumber;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.signup.email.domain.EmailCertification;
import com.api.RecordTimeline.domain.signup.email.dto.request.CheckCertificationRequestDto;
import com.api.RecordTimeline.domain.signup.email.dto.request.EmailCertificationRequestDto;
import com.api.RecordTimeline.domain.signup.email.provider.EmailProvider;
import com.api.RecordTimeline.domain.signup.email.repository.EmailCertificationRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final MemberRepository memberRepository;
    private final EmailProvider emailProvider;
    private final EmailCertificationRepository emailCertificationRepository;

    @Override
    @Transactional
    public void emailCertification(EmailCertificationRequestDto dto) {
        String email = dto.getEmail();

        boolean isExistEmail = memberRepository.existsByEmailAndIsDeletedFalse(email);
        if (isExistEmail) {
            throw new ApiException(ErrorType._DUPLICATE_EMAIL);
        }

        String certificationNumber = CertificationNumber.getCertificationNumber();
        boolean isSuccess = emailProvider.sendCertificationMail(email, certificationNumber);
        if (!isSuccess) {
            throw new ApiException(ErrorType.MAIL_SEND_FAIL);
        }

        EmailCertification emailCertification = new EmailCertification(email, certificationNumber);
        emailCertificationRepository.save(emailCertification);
    }

    @Override
    @Transactional
    public void checkCertification(CheckCertificationRequestDto dto) {
        String email = dto.getEmail();
        String certificationNumber = dto.getCertificationNumber();

        EmailCertification emailCertification = emailCertificationRepository.findByEmail(email);
        if (emailCertification == null || !emailCertification.getCertificationNumber().equals(certificationNumber)) {
            throw new ApiException(ErrorType.CERTIFICATION_FAILED);
        }
    }
}
