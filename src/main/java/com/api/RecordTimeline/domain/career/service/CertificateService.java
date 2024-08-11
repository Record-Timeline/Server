package com.api.RecordTimeline.domain.career.service;

import com.api.RecordTimeline.domain.career.domain.CareerDetail;
import com.api.RecordTimeline.domain.career.domain.Certificate;
import com.api.RecordTimeline.domain.career.repository.CareerDetailRepository;
import com.api.RecordTimeline.domain.career.repository.CertificateRepository;
import com.api.RecordTimeline.domain.member.domain.Member;
import com.api.RecordTimeline.domain.member.repository.MemberRepository;
import com.api.RecordTimeline.global.exception.ApiException;
import com.api.RecordTimeline.global.exception.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CertificateService {

    private final CertificateRepository certificateRepository;
    private final MemberRepository memberRepository;
    private final CareerDetailRepository careerDetailRepository;

    @Transactional
    public Certificate addCertificate(Certificate certificate) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        Member member = memberRepository.findByEmailAndIsDeletedFalse(userEmail);

        if (member != null) {
            // CareerDetail이 없는 경우 새로 생성
            CareerDetail careerDetail = careerDetailRepository.findByMember(member)
                    .orElseGet(() -> {
                        CareerDetail newCareerDetail = CareerDetail.builder()
                                .member(member)
                                .build();
                        return careerDetailRepository.save(newCareerDetail);
                    });

            certificate = certificate.toBuilder()
                    .userEmail(userEmail)
                    .date(certificate.getDate().withDayOfMonth(1))
                    .careerDetail(careerDetail)
                    .build();

            return certificateRepository.save(certificate);
        } else {
            throw new ApiException(ErrorType._USER_NOT_FOUND_DB);
        }
    }

    @Transactional
    public Certificate updateCertificate(Long certificateId, Certificate certificate) {
        Certificate existingCertificate = certificateRepository.findById(certificateId)
                .orElseThrow(() -> new NoSuchElementException("자격증을 찾을 수 없습니다: " + certificateId));

        checkOwnership(existingCertificate.getUserEmail());

        Certificate updatedCertificate = existingCertificate.update(certificate);
        return certificateRepository.save(updatedCertificate);
    }

    @Transactional
    public void deleteCertificate(Long certificateId) {
        Certificate existingCertificate = certificateRepository.findById(certificateId)
                .orElseThrow(() -> new NoSuchElementException("자격증을 찾을 수 없습니다: " + certificateId));

        checkOwnership(existingCertificate.getUserEmail());

        certificateRepository.delete(existingCertificate);
    }

    private void checkOwnership(String ownerEmail) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        if (!userEmail.equals(ownerEmail)) {
            throw new ApiException(ErrorType._DO_NOT_HAVE_PERMISSION);
        }
    }

    public Certificate getCertificateById(Long certificateId) {
        return certificateRepository.findById(certificateId)
                .orElseThrow(() -> new NoSuchElementException("자격증을 찾을 수 없습니다: " + certificateId));
    }
}
