package com.api.RecordTimeline.domain.career.repository;

import com.api.RecordTimeline.domain.career.domain.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
}
