package com.api.RecordTimeline.domain.subTimeline.service;

import com.api.RecordTimeline.domain.subTimeline.domain.SubTimeline;
import com.api.RecordTimeline.domain.subTimeline.repository.SubTimelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubTimelineService {
    @Autowired
    private SubTimelineRepository subTimelineRepository;

    public SubTimeline createSubTimeline(SubTimeline subTimeline) {
        return subTimelineRepository.save(subTimeline);
    }

    public List<SubTimeline> getSubTimelinesByMainTimelineId(Long mainTimelineId) {
        return subTimelineRepository.findByMainTimelineId(mainTimelineId);
    }

    // 추가적인 메소드 구현
}

