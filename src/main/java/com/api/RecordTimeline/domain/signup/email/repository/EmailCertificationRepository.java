package com.api.RecordTimeline.domain.signup.email.repository;

import com.api.RecordTimeline.domain.signup.email.domain.EmailCertification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailCertificationRepository extends JpaRepository<EmailCertification,Long> {
    EmailCertification findByEmail(String email);
    boolean existsByEmailAndCertificationNumber(String email, String certificationNumber);

}
