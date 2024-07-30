package com.api.RecordTimeline.domain.signup.email.service;

import com.api.RecordTimeline.domain.common.CertificationNumber;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.domain.signup.email.domain.EmailCertification;
import com.api.RecordTimeline.domain.signup.email.dto.request.CheckCertificationRequestDto;
import com.api.RecordTimeline.domain.signup.email.dto.request.EmailCertificationRequestDto;
import com.api.RecordTimeline.domain.signup.email.dto.response.CheckCertificationResponseDto;
import com.api.RecordTimeline.domain.signup.email.dto.response.EmailCertificationResponseDto;
import com.api.RecordTimeline.domain.signup.email.provider.EmailProvider;
import com.api.RecordTimeline.domain.signup.email.repository.EmailCertificationRepository;
import com.api.RecordTimeline.domain.common.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<? super EmailCertificationResponseDto> emailCertification(EmailCertificationRequestDto dto) {
        try {
            String email = dto.getEmail();
            String context = dto.getContext();

            // 회원가입 과정에서는 중복된 이메일이 있으면 안 되므로 체크
            if ("SIGNUP".equals(context)) {
                boolean isExistEmail = memberRepository.existsByEmailAndIsDeletedFalse(email);
                if (isExistEmail) return EmailCertificationResponseDto.duplicateEmail();
            }

            String certificationNumber = CertificationNumber.getCertificationNumber();
            boolean isSuccessed = emailProvider.sendCertificationMail(email, certificationNumber);
            if(!isSuccessed) return EmailCertificationResponseDto.mailSendFail();

            EmailCertification emailCertification = new EmailCertification(email, certificationNumber, context);
            emailCertificationRepository.save(emailCertification);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return EmailCertificationResponseDto.success();
    }

    @Override
    @Transactional
    public ResponseEntity<? super CheckCertificationResponseDto> checkCertification(CheckCertificationRequestDto dto) {
        try{
            String email = dto.getEmail();
            String certificationNumber = dto.getCertificationNumber();
            String context = dto.getContext();

            EmailCertification emailCertification = emailCertificationRepository.findByEmailAndContext(email, context);
            if(emailCertification == null)
                return CheckCertificationResponseDto.memberNotFound();

            boolean isMatched = emailCertification.getEmail().equals(email) && emailCertification.getCertificationNumber().equals(certificationNumber);
            if(!isMatched)
                return CheckCertificationResponseDto.certificationFail();

            emailCertification.markAsVerified();
            emailCertificationRepository.save(emailCertification);

        } catch (Exception exception){
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }
        return CheckCertificationResponseDto.success();
    }
}
