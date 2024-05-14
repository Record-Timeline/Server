package com.api.RecordTimeline.domain.subTimeline.repository;

import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubTimelineRepository extends JpaRepository<SubTimeline, Long> {
    List<SubTimeline> findByMainTimelineId(Long mainTimelineId);

    // 서브 타임라인을 시작 날짜 기준으로 정렬하여 조회하는 쿼리
    @Query("select s from SubTimeline s where s.mainTimeline.id = ?1 order by s.startDate")
    List<SubTimeline> findByMainTimelineIdOrderByStartDate(Long mainTimelineId);
}



