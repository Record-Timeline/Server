package com.api.RecordTimeline.domain.career.repository;

import com.api.RecordTimeline.domain.career.domain.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {

    @Query("SELECT c FROM Certificate c WHERE c.careerDetail.member.id = :memberId ORDER BY c.date ASC")
    List<Certificate> findAllByMemberIdOrderByDateAsc(@Param("memberId") Long memberId);
}
