package com.api.RecordTimeline.domain.career.repository;

import com.api.RecordTimeline.domain.career.domain.Career;
import com.api.RecordTimeline.domain.career.domain.ForeignLanguage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ForeignLanguageRepository extends JpaRepository<ForeignLanguage, Long> {
    @Query("SELECT f FROM ForeignLanguage f WHERE f.careerDetail.member.id = :memberId")
    List<ForeignLanguage> findAllByMemberIdOrderByStartDateAsc(@Param("memberId") Long memberId);
}
