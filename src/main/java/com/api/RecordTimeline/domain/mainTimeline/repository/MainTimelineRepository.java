package com.api.RecordTimeline.domain.mainTimeline.repository;

import com.api.RecordTimeline.domain.mainTimeline.domain.MainTimeline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MainTimelineRepository extends JpaRepository<MainTimeline, Long> {
    List<MainTimeline> findByMemberId(Long memberId); // 특정 사용자가 생성한 메인 타임라인 조회

}

