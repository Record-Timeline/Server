package com.api.RecordTimeline.domain.signup.email.repository;

import com.api.RecordTimeline.domain.signup.email.domain.EmailCertification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailCertificationRepository extends JpaRepository<EmailCertification,String> {
    EmailCertification findByMemberId (String memberId);
    boolean existsByMemberIdAndEmailAndCertificationNumber(String memberId, String email, String certificationNumber);

}
