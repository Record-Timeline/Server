package com.api.RecordTimeline.domain.subTimeline.repository;

import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubTimelineRepository extends JpaRepository<SubTimeline, Long> {
    List<SubTimeline> findByMainTimelineId(Long mainTimelineId);
}

