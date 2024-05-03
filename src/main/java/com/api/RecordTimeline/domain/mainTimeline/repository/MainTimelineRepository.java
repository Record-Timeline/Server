package com.api.RecordTimeline.domain.mainTimeline.repository;

import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MainTimelineRepository extends JpaRepository<MainTimeline, Long> {
    // 특정 사용자가 생성한 메인 타임라인 조회
    List<MainTimeline> findByMemberId(Long memberId);

    // 특정 사용자의 메인 타임라인을 시작 날짜 기준으로 정렬하여 조회
    @Query("select m from MainTimeline m where m.member.id = ?1 order by m.startDate")
    List<MainTimeline> findByMemberIdOrderByStartDate(Long memberId);

}