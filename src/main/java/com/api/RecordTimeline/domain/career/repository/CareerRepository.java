package com.api.RecordTimeline.domain.career.repository;

import com.api.RecordTimeline.domain.career.domain.Career;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CareerRepository extends JpaRepository<Career, Long> {

    @Query("SELECT c FROM Career c WHERE c.careerDetail.member.id = :memberId ORDER BY c.startDate ASC")
    List<Career> findAllByMemberIdOrderByStartDateAsc(@Param("memberId") Long memberId);
}
