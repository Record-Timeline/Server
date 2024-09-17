package com.api.RecordTimeline.domain.career.repository;

import com.api.RecordTimeline.domain.career.domain.Education;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {
    @Query("SELECT e FROM Education e WHERE e.careerDetail.member.id = :memberId ORDER BY e.startDate ASC")
    List<Education> findAllByMemberIdOrderByStartDateAsc(@Param("memberId") Long memberId);
}